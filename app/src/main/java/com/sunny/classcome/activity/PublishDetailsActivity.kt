package com.sunny.classcome.activity

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.adapter.PastReleaseAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.bean.ClassDetailBean
import com.sunny.classcome.fragment.CourseDetailsFragment
import com.sunny.classcome.fragment.FieldDetailsFragment
import com.sunny.classcome.fragment.TrainDetailsFragment
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.*
import kotlinx.android.synthetic.main.activity_publish_details.*

/**
 * Desc 发布详情：课程详情、场地详情、培训详情
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/15 20:40
 */
class PublishDetailsActivity : BaseActivity() {

    companion object {
        const val courseDetail = "2"
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

    var data:ClassBean.Bean.Data? = null

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

    lateinit var fragment: Fragment

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

        courseId = intent.getStringExtra("id")

        coursetype = intent.getStringExtra("type")

        fragment = when (coursetype) {
            courseDetail -> {
                title = "课程详情"
                txt_brief_desc.text = "课程简介"
                courseDetailFragment
            }
            filedDetail -> {
                title = "场地详情"
                txt_brief_desc.text = "场地简介"
                fieldDetailsFragment
            }
            trainDetail -> {
                title = "培训详情"
                txt_brief_desc.text = "培训简介"
                trainDetailsFragment
            }
            else -> courseDetailFragment
        }

        showTitle(titleManager.defaultTitle(title))

        rl_user_more.setOnClickListener(this)
        rl_history_more.setOnClickListener(this)
        txt_more.setOnClickListener(this)
        img_more.setOnClickListener(this)
        txt_collection.setOnClickListener(this)
        txt_accept.setOnClickListener(this)

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

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rl_user_more -> MyProfileActivity.start(this, uid)
            R.id.rl_history_more, R.id.txt_more, R.id.img_more -> PastReleaseActivity.start(this, uid)
            R.id.txt_collection -> loadOption(if (isCollection == "1") -1 else 1)// 若为已收藏，点击后为取消收藏状态
            R.id.txt_accept -> {
                if (coursetype == "4" || coursetype == "5"){
                    PayActivity.start(this,courseId)
                }else{
                    when (isAppointment) {
                        "1" -> ToastUtil.show("请不要重复操作")
                        "2" -> {
                            loadOption(2)
                        }
                        "4","5" -> PayActivity.start(this,courseId)
                    }
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

                MyApplication.getApp().setData(Constant.CLASS_DETAIL, data)
                supportFragmentManager.beginTransaction().replace(R.id.fl_container, fragment).commit()

                hideLoading()
                initData(data.content)
                txt_all.text = data.content.resCourseVO.materialList.size.toString()
                initPhotoVideo(this@PublishDetailsActivity, viewPager, data.content.resCourseVO.materialList)

                uid = data.content.user.id
                courseId = data.content.resCourseVO.course.id

                isCollection = data.content.resCourseVO.isFavorite
                txt_collection.text = if (isCollection == "1") "已收藏" else "收藏"

                isAppointment = data.content.resCourseVO.isAppointment

                val accept = when (isAppointment) {
                    "1" -> "已应聘"
                    "2" -> {
                        if (coursetype == "4" || coursetype == "5") {
                            "去支付"
                        } else {
                            "未应聘"
                        }
                    }
                    "4" -> "待支付"
                    "5" -> "拼团待支付"
                    else -> ""
                }
                txt_accept.text = accept

                // uid如果是本人的，底部收藏和应聘按钮隐藏
                ll_bottom_btn.visibility = if (uid == UserManger.getLogin()?.content?.userId) {
                    View.GONE
                } else {
                    View.VISIBLE
                }

                //加载发布的课程
                loadPastRelease()

//                data = ClassBean.Bean.Data(
//                        data.content.resCourseVO.course,
//                        data.content.resCourseVO.materialList,
//                        data.content.resCourseVO.category,
//                        data.content.user,
//                        data.content.resCourseVO,
//                        isAppointment
//                )

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

        val params = HashMap<String, String>()
        params["courseId"] = courseId
        params["relationType"] = relationType.toString()
        ApiManager.post(composites, params, Constant.COURSE_OPERATIONCOURSE, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                ToastUtil.show(data.content?.info)
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
                            txt_accept.text = "已应聘"
                            isAppointment = "1"
                        }
                    }

                }
            }

            override fun onFailed(code: String, message: String) {
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

        GlideUtil.loadPhoto(this, img_class_photo, bean.resCourseVO.materialList[0].url ?: "")

        txt_class_name.text = bean.resCourseVO.title
        txt_class_price.text = ("￥${StringUtil.formatMoney((bean.resCourseVO.course.sumPrice
                ?: "0").toDouble())}")
        txt_class_time.text = DateUtil.dateFormatYYMMdd(bean.resCourseVO.createTime)

        // 课程简介
        txt_brief.text = bean.resCourseVO.description

        // 发布者头像、用户名
        GlideUtil.loadHead(this, img_user_head, bean.user.userPic ?: "")
        txt_user_name.text = bean.user.userName
    }
}