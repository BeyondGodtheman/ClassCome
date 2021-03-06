package com.sunny.classcome.activity

import android.content.Context
import android.content.Intent
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.adapter.CommentAdapter
import com.sunny.classcome.adapter.PastReleaseAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.bean.CommentBean
import com.sunny.classcome.bean.UserBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.*
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
    private var commentList = arrayListOf<CommentBean.Data>()
    private var pageIndex = 1

    private val taUid by lazy {
        intent.getStringExtra("uid") ?: "" //他人
    }
    private val myUid = UserManger.getLogin()?.content?.userId ?: "" // 自己


    companion object {
        fun start(context: Context, uid: String) {
            context.startActivity(Intent(context, MyProfileActivity::class.java)
                    .putExtra("uid", uid))
        }
    }

    override fun setLayout(): Int = R.layout.activity_my_profile

    override fun initView() {

        val titleView = if (taUid.isEmpty()) {
            titleManager.defaultTitle("我的简介", "编辑", View.OnClickListener {
                if (userBean == null){
                    ToastUtil.show("信息获取失败,请从新加载！")
                }else{
                    MyApplication.getApp().setData(Constant.USER_BEAN, userBean)
                    if (userBean?.user?.authentication == "1"){
                        IntentUtil.start(this, MyProfileEditActivity::class.java)
                    }else{
                        IntentUtil.start(this, CompanyProfileEditActivity::class.java)
                    }
                }
            })
        } else {
            titleManager.defaultTitle("我的简介")
        }

        showTitle(titleView)

        refresh.setEnableRefresh(false)
        refresh.setRefreshFooter(ClassicsFooter(this))
        refresh.setOnLoadMoreListener {
            pageIndex++
            loadComment()
        }

        recl.setHasFixedSize(true)
        recl.isNestedScrollingEnabled = false
        recl.layoutManager = GridLayoutManager(this, 2)
        recl.adapter = PastReleaseAdapter(pastReleaseList)
        txt_more.setOnClickListener(this)

        recl_comment.layoutManager = LinearLayoutManager(this)
        recl_comment.adapter = CommentAdapter(commentList)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txt_more -> {
                PastReleaseActivity.start(this, if (taUid.isEmpty()) myUid else taUid)
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
                    GlideUtil.loadHead(this@MyProfileActivity, img_user_head, bean.userPic ?: "")
                    txt_name.text = bean.userName

                    txt_points.text = ("${bean.source}积分")
                    txt_member.text = bean.gradeName

                    txt_publish_count.text = bean.publishNum
                    txt_partake_count.text = bean.teachingNum
                    txt_default_count.text = bean.violateNum


                    if (bean.authentication == "1"){
                        info_name.text = "基本信息"
                        txt_type_brief.text = "个人简介"
                        rl_person.visibility = View.VISIBLE
                        txt_sex.text = if (bean.sex == "1") "男" else "女"
                        if (bean.age > 0) {
                            txt_age.text = bean.age.toString()
                        }

                        txt_specialty.text = bean.profession

                        if (bean.workAge > 0) {
                            txt_work.text = ("${bean.workAge}年")
                        }

                        if (bean.isAuth == "1") {
                            txt_identity.text = "已认证"
                        } else {
                            txt_identity.text = "未认证"
                        }

                    }else{
                        info_name.text = "企业信息"
                        txt_type_brief.text = "企业介绍"
                        rl_company.visibility = View.VISIBLE
                        txt_organization.text = bean.organization
                        txt_address.text = bean.address

                        if (bean.isAuth == "1") {
                            txt_company_identity.text = "已认证"
                        } else {
                            txt_company_identity.text = "未认证"
                        }
                    }

                    txt_brief.text = bean.userInfo

                    data.content?.data?.materialList?.let {
                        if (it.size > 0) {
                            ll_count.visibility = View.VISIBLE
                        }
                        txt_all.text = it.size.toString()
                        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                            override fun onPageScrollStateChanged(p0: Int) {

                            }

                            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

                            }

                            override fun onPageSelected(position: Int) {
                                txt_current.text = (position + 1).toString()
                            }

                        })
                        initPhotoVideo(this@MyProfileActivity, viewPager, it)
                    }
                }
            }

                override fun onFailed(code: String, message: String) {
                    hideLoading()
                }

            })

            //加载发布的课程
            loadPastRelease()
            //加载评论
            pageIndex = 1
            loadComment()
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

                private fun loadComment() {
            val params = HashMap<String, String>()
            params["id"] = if (taUid.isEmpty()) myUid else taUid
            params["pageIndex"] = pageIndex.toString()
            ApiManager.post(composites, params, Constant.USER_GETAPPRAISELIST, object : ApiManager.OnResult<BaseBean<CommentBean>>() {
                override fun onSuccess(data: BaseBean<CommentBean>) {
                    if (pageIndex == 1) {
                        commentList.clear()
                    } else {
                        refresh.finishLoadMore()
                        if (data.content?.data?.dataList == null || data.content?.data?.dataList?.isEmpty() == true) {
                            pageIndex--
                        }
                    }
                    data.content?.data?.dataList?.let {
                        if (it.isNotEmpty()) {
                            if (!commentList.containsAll(it))
                                commentList.addAll(it)
                        }
                    }
                    recl_comment.adapter?.notifyDataSetChanged()
                }

                override fun onFailed(code: String, message: String) {
                    refresh.finishLoadMore()
                }

            })
        }
    }