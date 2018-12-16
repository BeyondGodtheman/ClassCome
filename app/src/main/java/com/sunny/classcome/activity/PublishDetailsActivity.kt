package com.sunny.classcome.activity

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.adapter.PastReleaseAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.bean.ClassDetailBean
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

    var id = "175073"
    var uid = ""
    var courseId = ""

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

    override fun setLayout(): Int = R.layout.activity_publish_details

    override fun initView() {
//        id = intent.getStringExtra("id")

        showTitle(titleManager.defaultTitle("课程详情"))

        rl_user_more.setOnClickListener(this)
        rl_history_more.setOnClickListener(this)
        txt_collection.setOnClickListener(this)
        txt_accept.setOnClickListener(this)

        initRecycleView()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rl_user_more -> startActivity(Intent(this, MyProfileActivity::class.java).putExtra("uid", uid))
            R.id.rl_history_more -> IntentUtil.start(this, PastReleaseActivity::class.java)
            R.id.txt_collection -> loadOption(if (isCollection == "1") -1 else 1)// 若为已收藏，点击后为取消收藏状态
            R.id.txt_accept -> loadOption(2)
        }
    }


    override fun update() {
        showLoading()
        val map = HashMap<String, String>()
        map["id"] = id
//        map["pintuan"] = id
        ApiManager.post(composites, map, Constant.COURSE_GETCOURSEDETAIL, object : ApiManager.OnResult<ClassDetailBean>() {
            override fun onSuccess(data: ClassDetailBean) {
                hideLoading()
                initData(data.content)
                initPhotoVideo(this@PublishDetailsActivity, viewPager, data.content.resCourseVO.materialList)

                uid = data.content.user.id
                courseId = data.content.resCourseVO.course.id
                
                isCollection = data.content.resCourseVO.isFavorite
                txt_collection.text = if (isCollection == "1") "已收藏" else "收藏"

                isAppointment = data.content.resCourseVO.isAppointment
                txt_accept.text = if (isAppointment == "1") "已应聘" else "应聘"


                // uid如果是本人的，底部收藏和应聘按钮隐藏
                ll_bottom_btn.visibility = if (uid == UserManger.getLogin()?.content?.userId) {
                    View.GONE
                } else {
                    View.VISIBLE
                }

            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })

        //加载发布的课程
        loadPastRelease()

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
                        -1 -> txt_collection.text = "收藏"
                        1 -> txt_collection.text = "已收藏"
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
        params["userId"] = UserManger.getLogin()?.content?.userId ?: ""
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
        txt_class_price.text = ("￥${bean.resCourseVO.sumPrice}")
        txt_class_time.text = DateUtil.dateFormatYYMMdd(bean.resCourseVO.createTime)

//
//        // 课程总节数/招聘人数
//        txt_class_total.text = ("${bean.resCourseVO.courseNum}节")
//        txt_recruit.text = ("接口未定义字段")
//
//        // 单节酬劳/酬劳总价
//        txt_price.text = ("￥${bean.resCourseVO.price}")
//        txt_sum.text = ("￥${bean.resCourseVO.sumPrice}")
//
//        // 课程类别/人员类型/课程日期
//        txt_class_type.text = bean.resCourseVO.category[0]
//        txt_person_type.text = bean.resCourseVO.personType
//        txt_class_date.text = ("${DateUtil.dateFormatYYMMdd(bean.resCourseVO.startTime)}至${DateUtil.dateFormatYYMMdd(bean.resCourseVO.endTime)}")
//
//        // 上课时段/截至日期/上课地点
//        txt_time.text = bean.resCourseVO.classTime[0]
//        txt_by_date.text = DateUtil.dateFormatYYMMdd(bean.resCourseVO.course.expirationTime)
//        txt_address.text = bean.resCourseVO.classAddress

        // 课程简介
        txt_brief.text = bean.resCourseVO.description

        // 发布者头像、用户名
        GlideUtil.loadPhoto(this, img_user_head, bean.user.userPic)
        txt_user_name.text = bean.user.userName
    }
}