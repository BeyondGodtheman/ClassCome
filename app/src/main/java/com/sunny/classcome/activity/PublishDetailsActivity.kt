package com.sunny.classcome.activity

import android.content.Context
import android.content.Intent
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.adapter.PastReleaseAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.base.BaseFragment
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.bean.ClassDetailBean
import com.sunny.classcome.fragment.CourseDetailsFragment
import com.sunny.classcome.fragment.FieldDetailsFragment
import com.sunny.classcome.fragment.TrainDetailsFragment
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.*
import com.sunny.classcome.wxapi.WXEntryActivity
import kotlinx.android.synthetic.main.activity_publish_details.*
import kotlinx.android.synthetic.main.layout_title_icon.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Desc 发布详情：课程详情、场地详情、培训详情
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/15 20:40
 */
class PublishDetailsActivity : BaseActivity() {

    companion object {
        const val filedDetail = "4"
        const val trainDetail = "5"

        fun startPublishDetail(context: Context, type: String, id: String) {
            val intent = Intent(context, PublishDetailsActivity::class.java)
            intent.putExtra("type", type)
            intent.putExtra("id", id)
            context.startActivity(intent)
        }
    }

    var uid = ""
    var courseId = ""
    var coursetype = ""
    var title = ""

    var classData: ClassBean.Bean.Data? = null

    var classDetailBean: ClassDetailBean? = null
    /**
     * 是否已收藏：
     * 1 已经收藏，
     * 2 未收藏 ,
     * 3 当前用户未登陆，未知是否收藏,
     * -1 隐藏,
     */
    var isCollection = "-1"
    /**
     * 是否已应聘：
     * 1已应聘，
     * 2未应聘 ，
     * 3当前用户未登陆，未知是否应聘,
     * 隐藏：-1 ,
     */
    var isAppointment = "-1"

    private val pastReleaseList = arrayListOf<ClassBean.Bean.Data>()

    lateinit var fragment: BaseFragment

    private val courseDetailFragment: CourseDetailsFragment by lazy {
        CourseDetailsFragment()
    }

    private val fieldDetailsFragment: FieldDetailsFragment by lazy {
        FieldDetailsFragment()
    }

    private val trainDetailsFragment: TrainDetailsFragment by lazy {
        TrainDetailsFragment()
    }


    override fun setLayout(): Int = R.layout.activity_publish_details

    override fun initView() {

        EventBus.getDefault().register(this)


        courseId = intent.getStringExtra("id")

        coursetype = intent.getStringExtra("type")

        fragment = when (coursetype) {
            "1", "2", "3" -> {
                title = "课程详情"
                ll_class_desc.visibility = View.VISIBLE
                courseDetailFragment
            }
            filedDetail -> {
                title = "场地详情"
                ll_class_desc.visibility = View.GONE
                fieldDetailsFragment
            }
            trainDetail -> {
                ll_class_desc.visibility = View.GONE
                title = "培训详情"

                trainDetailsFragment
            }
            else -> courseDetailFragment
        }

        val titleView = titleManager.iconTitle(title, View.OnClickListener {
            share(classData?.course?.title ?: "", classData?.course?.description ?: "")
        })
        titleView.view_icon_right.setBackgroundResource(R.drawable.ic_share)

        showTitle(titleView)

        supportFragmentManager.beginTransaction().replace(R.id.fl_container, fragment).commit()

        rl_user_more.setOnClickListener(this)
        rl_history_more.setOnClickListener(this)
        txt_more.setOnClickListener(this)
        img_more.setOnClickListener(this)
        txt_collection.setOnClickListener(this)
        txt_accept.setOnClickListener(this)

        txt_pintuan.setOnClickListener(this)
        txt_pintuan_self.setOnClickListener(this)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                txt_current.text = (position + 1).toString()
            }

            override fun onPageSelected(position: Int) {

            }

        })

        initRecycleView()
    }


    //拼团支付
    private fun pay() {
        classData?.let { data ->
            //            val pintuan = "0"
//            classDetailBean?.let {
//                if ((it.content.resCourseVO.pintuanlist ?: arrayListOf()).isNotEmpty()) {
//                    pintuan = it.content.resCourseVO.pintuanlist!![0].pintuanInfo?.id ?: "0"
//                }
//            }
            PayActivity.start(this, data, "0")
        }
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.rl_user_more -> MyProfileActivity.start(this, uid)
            R.id.rl_history_more, R.id.txt_more, R.id.img_more -> PastReleaseActivity.start(this, uid)
            R.id.txt_collection -> loadOption(if (isCollection == "1") -1 else 1)// 若为已收藏，点击后为取消收藏状态
            R.id.txt_accept -> {
                when (isAppointment) {
                    "1" -> ToastUtil.show("请不要重复操作")
                    "2" -> {
                        loadOption(2)
                    }
                    "4" -> {
                        if (coursetype == "4" || coursetype == "5") {
                            classData?.let {
                                PayActivity.start(this, it, "")
                            }
                        } else {
                            PayActivity.start(this, courseId)
                        }
                    }
                    "5" -> {
                        if (classDetailBean?.content?.resCourseVO?.state != "3") {
                            pay()
                        }
                    }

                }
            }
            R.id.txt_pintuan -> {
                classData?.let {
                    PayActivity.start(this, it, "0")
                }
            }
            R.id.txt_pintuan_self -> {
                classData?.let {
                    PayActivity.start(this, it, null)
                }
            }
        }
    }


    override fun update() {
        showLoading()
        val map = HashMap<String, String>()
        map["id"] = courseId
//        map["pintuan"] = id
        ApiManager.post(composites, map, Constant.COURSE_GETCOURSEDETAIL, object : ApiManager.OnResult<ClassDetailBean>() {
            override fun onSuccess(data: ClassDetailBean) {
                classDetailBean = data
                MyApplication.getApp().setData(Constant.CLASS_DETAIL, data)
                fragment.initView()

                hideLoading()
                initData(data.content)
                txt_all.text = (data.content.resCourseVO.materialList?.size ?: "0").toString()
                initPhotoVideo(this@PublishDetailsActivity, viewPager, data.content.resCourseVO.materialList
                        ?: arrayListOf())

                uid = data.content.user.id
                courseId = data.content.resCourseVO.course.id

                isCollection = data.content.resCourseVO.isFavorite
                txt_collection.text = if (isCollection == "1") "已收藏" else "收藏"

                isAppointment = data.content.resCourseVO.isAppointment

                val accept = when (isAppointment) {
                    "1" -> {
                        if (coursetype == "4" || coursetype == "5") {
                            "已购买"
                        } else {
                            "已应聘"
                        }
                    }
                    "2" -> {
                        if (coursetype == "4" || coursetype == "5") {
                            if (coursetype == "5" && data.content.resCourseVO.course.assemcost != null) {
                                txt_accept.visibility = View.GONE
                                ll_pintuan.visibility = View.VISIBLE
                                txt_pintuan.text = ("¥" + StringUtil.formatMoney((data.content.resCourseVO.course.assemcost
                                        ?: "0").toDouble()) + "\n发起拼团")
                                txt_pintuan_self.text = ("¥" + StringUtil.formatMoney((data.content.resCourseVO.course.oneallcost
                                        ?: "0").toDouble()) + "\n单独购买")
                            } else {
                                txt_accept.visibility = View.VISIBLE
                                ll_pintuan.visibility = View.GONE
                            }
                            "去购买"
                        } else {
                            "应聘"
                        }
                    }
                    "4" -> "待支付"
                    "5" -> {
                        if (classDetailBean?.content?.resCourseVO?.state == "3") {
                            "已取消"
                        } else
                            "拼团待支付"
                    }
                    else -> ""
                }

                txt_accept.text = accept


                // uid如果是本人的，底部收藏和应聘按钮隐藏
                ll_bottom_btn.visibility = if (uid == UserManger.getLogin()?.content?.userId || isAppointment == "3") {
                    View.GONE
                } else {
                    View.VISIBLE
                }

                //加载发布的课程
                loadPastRelease()

                classData = ClassBean.Bean.Data(
                        data.content.resCourseVO.course,
                        data.content.resCourseVO.materialList,
                        arrayListOf(),
                        ClassBean.Bean.Data.User(data.content.user.userName, data.content.user.telephone, data.content.user.userPic),
                        ClassBean.Bean.Data.Order("", "", "", ""
                                , data.content.resCourseVO.price, "", "", "", "", "", if (data.content.resCourseVO.isAppointment == "5") 1 else 0, null, ""),
                        null, data.content.resCourseVO.isAppraise
                )

            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })
    }

    /**
     * -1 取消收藏
     * 1 已收藏
     * 2 应聘
     * 5 购买场地
     * 6 购买培训
     */
    private fun loadOption(relationType: Int) {

        if (isAppointment == "1" && relationType == 2) {
            ToastUtil.show("请不要重复操作")
            return
        }
        showLoading()
        val params = HashMap<String, String>()
        params["courseId"] = courseId
        params["relationType"] = relationType.toString()
        ApiManager.post(composites, params, Constant.COURSE_OPERATIONCOURSE, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                hideLoading()
                if (data.content?.statu == "1") {// 成功
                    when (relationType) {
                        -1 -> {
                            txt_collection.text = "收藏"
                            isCollection = "0"
                        }
                        1 -> {
                            txt_collection.text = "已收藏"
                            isCollection = "1"
                        }
                        2 -> {
                            if (classData?.course?.coursetype == "4" || classData?.course?.coursetype == "5") {
                                classData?.let {
                                    PayActivity.start(this@PublishDetailsActivity, it, "")
                                }
                            } else {
                                txt_accept.text = "已应聘"
                                isAppointment = "1"
                                ApplicationSuccessActivity.start(this@PublishDetailsActivity, courseId)
                            }
                        }
                    }

                } else {
                    ToastUtil.show(data.content?.info)
                }
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })
    }

    private fun loadPastRelease() {
        val params = HashMap<String, String>()
        params["userId"] = uid
        params["pageSize"] = "2"
        ApiManager.post(composites, params, Constant.CURSE_GETUSERPUBLISHCOURSE, object : ApiManager.OnResult<ClassBean>() {
            override fun onSuccess(data: ClassBean) {
                pastReleaseList.clear()
                pastReleaseList.addAll(data.content?.dataList ?: arrayListOf())
                recl.adapter?.notifyDataSetChanged()
            }

            override fun onFailed(code: String, message: String) {
            }

        })
    }

    private fun initRecycleView() {
        recl.setHasFixedSize(true)
        recl.isNestedScrollingEnabled = false
        recl.layoutManager = GridLayoutManager(this, 2)
        recl.adapter = PastReleaseAdapter(pastReleaseList)
        txt_more.setOnClickListener(this)
    }

    private fun initData(bean: ClassDetailBean.Content) {
        if (bean.resCourseVO.materialList?.isNotEmpty() == true) {
            GlideUtil.loadPhoto(this, img_class_photo, bean.resCourseVO.materialList!![0].url ?: "")
        }



        txt_class_name.text = bean.resCourseVO.title
        txt_class_price.text = ("¥${StringUtil.formatMoney((bean.resCourseVO.course.sumPrice
                ?: "0").toDouble())}")
        txt_class_time.text = DateUtil.dateFormatYYMMdd(bean.resCourseVO.createTime)

        // 课程简介
        txt_brief.text = bean.resCourseVO.description

        // 发布者头像、用户名
        GlideUtil.loadHead(this, img_user_head, bean.user.userPic ?: "")
        txt_user_name.text = bean.user.userName
    }

    override fun close() {
        EventBus.getDefault().unregister(this)
    }

    private fun share(title: String, desc: String) {
        showLoading()
        ApiManager.post(composites, null, Constant.PUB_GETSHOWURL, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                hideLoading()
                data.content?.data.let {
                    WXEntryActivity.shareCourse(it ?: "", title, desc)
                }
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }
        })

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPayEvent(pay: Pay) {
        finishAfterTransition()
    }
}