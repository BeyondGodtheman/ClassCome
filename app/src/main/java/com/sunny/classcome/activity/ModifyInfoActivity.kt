package com.sunny.classcome.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.UserBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.*
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import kotlinx.android.synthetic.main.activity_modify_info.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import java.io.File

/**
 * Desc 完善个人信息
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/5 01:39
 */
class ModifyInfoActivity : BaseActivity() {
    private var headFile: File? = null
    private var headPic = ""
    private var isModifyHead = false
    override fun setLayout(): Int = R.layout.activity_modify_info

    override fun initView() {
        showTitle(titleManager.defaultTitle("完善信息"))

        rl_head.setOnClickListener(this)
        img_head.setOnClickListener(this)
        rl_name.setOnClickListener(this)
        rl_city.setOnClickListener(this)
        rl_phone.setOnClickListener(this)
        txt_commit.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.img_head -> {
                Matisse.from(this)
                        .choose(MimeType.ofImage(), false) // 选择 mime 的类型
                        .theme(R.style.Matisse_Zhihu)
                        .maxSelectable(1) // 图片选择的最多数量
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f) // 缩略图的比例
                        .imageEngine(MyGlideEngine()) // 使用的图片加载引擎
                        .forResult(Constant.REQUEST_CODE_CHOOSE)
            }

            R.id.rl_city -> {
                startActivityForResult(Intent(this, LocationActivity::class.java).putExtra("type", 1), 1)
            }
            R.id.rl_identity -> {
                //身份验证
                startActivity(Intent(this, PersonAuthActivity::class.java))
            }
            R.id.txt_commit -> {
                updateHead()
            }
        }
    }

    override fun loadData() {
        showLoading()
        val params = HashMap<String, String>()
        params["id"] = UserManger.getLogin()?.content?.userId ?: ""
        ApiManager.post(composites, params, Constant.USER_GETMYINFO, object : ApiManager.OnResult<BaseBean<UserBean>>() {
            override fun onSuccess(data: BaseBean<UserBean>) {
                hideLoading()
                data.content?.data?.user?.let {
                    headPic = it.userPic?:""
                    GlideUtil.loadHead(this@ModifyInfoActivity, img_head, it.userPic?:"")
                    edit_name.setText(it.userName)
                    txt_phone.text = it.telephone

                    if (it.sex == "1") {
                        rbtn_man.isChecked = true
                    } else {
                        rbtn_woman.isChecked = true
                    }

                    if (it.isAuth == "1") {
                        txt_identity.text = "已认证"
                    } else {
                        txt_identity.text = "未认证"
                        rl_identity.setOnClickListener(this@ModifyInfoActivity)
                    }

                    txt_city.text = it.address
                }
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })

    }


    private fun updateHead() {
        if (edit_name.text.isEmpty()) {
            ToastUtil.show("请填写姓名")
            return
        }
        if (isModifyHead) {
            showLoading()

            OSSUtil.updateFile(headFile?.absolutePath ?: "", OSSUtil.IMAGE) {
                isModifyHead = false
                headPic = it
                hideLoading()
                commit()

            }

        } else {
            commit()
        }
    }


    private fun commit() {

        val params = hashMapOf<String, String>()
        params["userPic"] = headPic
        params["userName"] = edit_name.text.toString()
        params["address"] = txt_city.text.toString()
        params["sex"] = if (rbtn_man.isChecked) "1" else "2"

        ApiManager.post(composites, params, Constant.USER_EDITPERSONINFO, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                if (data.content?.statu == "1") {
                    finishAfterTransition()
                }
                ToastUtil.show(data.content?.info)
            }

            override fun onFailed(code: String, message: String) {
            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            val uri = Matisse.obtainResult(data).first()
            launch(CommonPool) {
                headFile = GlideApp.with(this@ModifyInfoActivity).asFile().load(uri).submit().get()
                runOnUiThread {
                    headFile?.let {
                        isModifyHead = true
                        GlideUtil.loadPhoto(this@ModifyInfoActivity, img_head, it)
                    }
                }
            }
        }

        if (requestCode == 1 && resultCode == 1) {
            txt_city.text = data?.getStringExtra("cityName") ?: ""
        }
    }
}