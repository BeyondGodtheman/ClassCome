package com.sunny.classcome.activity

import android.content.Intent
import android.net.Uri
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.UserBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.GlideUtil
import com.sunny.classcome.utils.IntentUtil
import com.sunny.classcome.utils.UserManger
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.item_viewpager_profile.view.*

/**
 * Desc  我的简介
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/10 22:44
 */
class MyProfileActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.activity_my_profile

    override fun initView() {
        showTitle(titleManager.defaultTitle("我的简介", "编辑", View.OnClickListener {
            IntentUtil.start(this, MyProfileEditActivity::class.java)
        }))
    }

    override fun onClick(v: View?) {
    }


    override fun loadData() {

        showLoading()
        val params = HashMap<String, String>()
        params["id"] = UserManger.getLogin()?.content?.userId ?: ""
        ApiManager.post(composites, params, Constant.USER_GETMYINFO, object : ApiManager.OnResult<BaseBean<UserBean>>() {
            override fun onSuccess(data: BaseBean<UserBean>) {
                hideLoading()
                data.content?.data?.user?.let { bean ->
                    GlideUtil.loadHead(this@MyProfileActivity, img_user_head, bean.userPic)
                    txt_name.text = bean.userName

                    txt_points.text =  (bean.source +"积分")
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
                    txt_age.text = bean.age
                    txt_specialty.text = bean.speciality
                    txt_brief.text = bean.userInfo
                    txt_specialty.text = bean.profession
                    txt_work.text = (bean.workAge +"年")
                }

                data.content?.data?.materialList?.let {
                    initViewPager(it)
                }
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })
    }


    fun initViewPager(list:ArrayList<UserBean.Material>){
        viewPager.adapter = object : PagerAdapter() {
            override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj

            override fun getCount(): Int = list.size

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val view = LayoutInflater.from(this@MyProfileActivity).inflate(R.layout.item_viewpager_profile,container,false)
                GlideUtil.loadBanner(this@MyProfileActivity,view.img_profile_photo, list[position].url?:"")
                if ((list[position].url?:"").contains(".mp4")){
                    view.view_play.visibility = View.VISIBLE
                }else{
                    view.view_play.visibility = View.GONE
                }

                view.setOnClickListener {
                    val extension = MimeTypeMap.getFileExtensionFromUrl(list[position].url?:"")
                    val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
                    val mediaIntent = Intent(Intent.ACTION_VIEW)
                    mediaIntent.setDataAndType(Uri.parse(list[position].url?:""), mimeType)
                    startActivity(mediaIntent)
                }

                container.addView(view)
                return view
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View?)
            }
        }

    }
}