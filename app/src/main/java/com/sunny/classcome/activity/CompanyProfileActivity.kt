package com.sunny.classcome.activity

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
import kotlinx.android.synthetic.main.activity_company_profile.*


//企业简历
class CompanyProfileActivity : BaseActivity() {

    private val pastReleasList = arrayListOf<ClassBean.Bean.Data>()
    private var userBean: UserBean? = null

    override fun setLayout(): Int = R.layout.activity_company_profile
    override fun initView() {
        showTitle(titleManager.defaultTitle("我的简介", "编辑", View.OnClickListener {
            MyApplication.getApp().setData(Constant.USER_BEAN, userBean)
            IntentUtil.start(this, MyProfileEditActivity::class.java)
        }))

        recl.setHasFixedSize(true)
        recl.isNestedScrollingEnabled = false
        recl.layoutManager = GridLayoutManager(this, 2)
        recl.adapter = PastReleaseAdapter(pastReleasList)
        txt_more.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.txt_more -> {
                startActivity(Intent(this, PastReleaseActivity::class.java))
            }

        }
    }

    override fun update() {
        showLoading()
        val params = HashMap<String, String>()
        params["id"] = UserManger.getLogin()?.content?.userId ?: ""
        ApiManager.post(composites, params, Constant.USER_GETMYINFO, object : ApiManager.OnResult<BaseBean<UserBean>>() {
            override fun onSuccess(data: BaseBean<UserBean>) {
                userBean = data.content?.data

                hideLoading()
                data.content?.data?.user?.let { bean ->
                    GlideUtil.loadHead(this@CompanyProfileActivity, img_user_head, bean.userPic)
                    txt_name.text = bean.userName

                    txt_points.text = ("${bean.source}积分")
                    txt_member.text = bean.gradeName

                    txt_publish_count.text = bean.publishNum
                    txt_partake_count.text = bean.teachingNum
                    txt_default_count.text = bean.violateNum


                    if (bean.isAuth == "1") {
                        txt_identity.text = "已认证"
                    } else {
                        txt_identity.text = "未认证"
                    }

                    txt_company_name.text = bean.organization
                    txt_address.text = bean.address
                    txt_brief.text = bean.userInfo
                }

                data.content?.data?.materialList?.let {
                    initPhotoVideo(this@CompanyProfileActivity, viewPager, it)
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
        params["userId"] = UserManger.getLogin()?.content?.userId ?: ""
        params["pageSize"] = "2"
        ApiManager.post(composites, params, Constant.CURSE_GETUSERPUBLISHCOURSE, object : ApiManager.OnResult<ClassBean>() {
            override fun onSuccess(data: ClassBean) {
                pastReleasList.clear()
                pastReleasList.addAll(data.content?.dataList ?: arrayListOf())
                recl.adapter?.notifyDataSetChanged()
            }

            override fun onFailed(code: String, message: String) {
            }

        })
    }
}