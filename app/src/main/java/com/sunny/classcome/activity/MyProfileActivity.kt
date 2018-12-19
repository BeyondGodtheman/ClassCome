package com.sunny.classcome.activity

import android.content.Context
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.adapter.PastReleaseAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.bean.UserBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.GlideUtil
import com.sunny.classcome.utils.IntentUtil
import com.sunny.classcome.utils.UserManger
import com.sunny.classcome.utils.initPhotoVideo
import kotlinx.android.synthetic.main.activity_my_profile.*

/**
 * Desc  我的简介
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/10 22:44
 */
class MyProfileActivity : BaseActivity() {

    private val pastReleaseList = arrayListOf<ClassBean.Bean.Data>()
    private var userBean: UserBean? = null

    private val taUid by lazy {
        intent.getStringExtra("uid") ?: "" //他人
    }
    private val myUid = UserManger.getLogin()?.content?.userId ?: "" // 自己


    companion object {
        fun start(context:Context,uid:String){
            context.startActivity(Intent(context,MyProfileActivity::class.java)
                    .putExtra("uid",uid))
        }
    }

    override fun setLayout(): Int = R.layout.activity_my_profile

    override fun initView() {

        val titleView = if (taUid.isEmpty()) {
            titleManager.defaultTitle("我的简介", "编辑", View.OnClickListener {
                MyApplication.getApp().setData(Constant.USER_BEAN, userBean)
                IntentUtil.start(this, MyProfileEditActivity::class.java)
            })
        } else {
            titleManager.defaultTitle("Ta的简介")
        }

        showTitle(titleView)

        recl.setHasFixedSize(true)
        recl.isNestedScrollingEnabled = false
        recl.layoutManager = GridLayoutManager(this, 2)
        recl.adapter = PastReleaseAdapter(pastReleaseList)
        txt_more.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txt_more -> {
                PastReleaseActivity.start(this,if (taUid.isEmpty()) myUid else taUid)
            }

        }
    }


    override fun update() {
        showLoading()
        val params = HashMap<String, String>()
        params["id"] = if (taUid.isEmpty()) myUid else taUid
        ApiManager.post(composites, params, Constant.USER_GETMYINFO, object : ApiManager.OnResult<BaseBean<UserBean>>() {
            override fun onSuccess(data: BaseBean<UserBean>) {
                userBean = data.content?.data

                hideLoading()
                data.content?.data?.user?.let { bean ->
                    GlideUtil.loadHead(this@MyProfileActivity, img_user_head, bean.userPic?:"")
                    txt_name.text = bean.userName

                    txt_points.text = ("${bean.source}积分")
                    txt_member.text = bean.gradeName

                    txt_publish_count.text = bean.publishNum
                    txt_partake_count.text = bean.teachingNum
                    txt_default_count.text = bean.violateNum

                    txt_sex.text = if (bean.sex == "1") "男" else "女"


                    if (bean.isAuth == "1") {
                        txt_identity.text = "已认证"
                    } else {
                        txt_identity.text = "未认证"
                    }
                    txt_age.text = bean.age.toString()
                    txt_specialty.text = bean.speciality
                    txt_brief.text = bean.userInfo
                    txt_specialty.text = bean.profession
                    txt_work.text = ("${bean.workAge}年")
                }

                data.content?.data?.materialList?.let {
                    initPhotoVideo(this@MyProfileActivity, viewPager, it)
                    viewPager.visibility = View.VISIBLE

                }
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })

        //加载发布的课程
        loadPastRelease()
    }

    private fun loadPastRelease() {
        val params = HashMap<String, String>()
        params["userId"] = if (taUid.isEmpty()) myUid else taUid
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
}