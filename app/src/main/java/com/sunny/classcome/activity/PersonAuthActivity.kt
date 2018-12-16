package com.sunny.classcome.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Handler
import android.os.Message
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.*
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import kotlinx.android.synthetic.main.activity_person_auth.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import java.io.File


class PersonAuthActivity : BaseActivity() {

    private var position = 1

    private var frontFile: File? = null

    private var backFile: File? = null

    private var frontOk = false

    private var backOk = false

    private var frontUrl = ""

    private var backUrl = ""


    private var handler = Handler {

        if (it.what == 1) {
            (it.obj as String).apply {
                if (isNotEmpty()) {
                    frontOk = true
                    frontUrl = this
                    LogUtil.i("正面URL：$frontUrl")
                }
            }

        }

        if (it.what == 2) {
            (it.obj as String).apply {
                if (isNotEmpty()) {
                    backOk = true
                    backUrl = this
                    LogUtil.i("反面URL：$backUrl")
                }
            }

        }

        if (frontOk && backOk) {
            commit()
        }
        return@Handler false
    }

    override fun setLayout(): Int = R.layout.activity_person_auth

    override fun initView() {
        showTitle(titleManager.defaultTitle(getString(R.string.identity_verification)))

        btn_front_select.setOnClickListener(this)
        btn_back_select.setOnClickListener(this)
        btn_complete.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_front_select -> {
                if (frontFile != null) {
                    frontFile = null
                    img_card_front.setImageResource(R.drawable.bg_default_photo)
                } else {
                    position = 1
                    startPhoto()
                }

                updateState()
            }
            R.id.btn_back_select -> {
                if (backFile != null) {
                    backFile = null
                    img_card_back.setImageResource(R.drawable.bg_default_photo)
                } else {
                    position = 2
                    startPhoto()
                }
                updateState()
            }

            R.id.btn_complete -> {
                updatePhoto()
            }
        }
    }


    private fun startPhoto() {
        Matisse.from(this)
                .choose(MimeType.ofImage(), false) // 选择 mime 的类型
                .theme(R.style.Matisse_Zhihu)
                .maxSelectable(1) // 图片选择的最多数量
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .imageEngine(MyGlideEngine()) // 使用的图片加载引擎
                .forResult(Constant.REQUEST_CODE_CHOOSE)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {

            val uri = Matisse.obtainResult(data).first()
            if (position == 1) {
                launch(CommonPool) {
                    frontFile = GlideApp.with(this@PersonAuthActivity).asFile().load(uri).submit().get()
                    runOnUiThread {
                        frontFile?.let {
                            GlideUtil.loadPhoto(this@PersonAuthActivity, img_card_front, it)
                            updateState()
                        }
                    }
                }

            } else {
                launch(CommonPool) {
                    backFile = GlideApp.with(this@PersonAuthActivity).asFile().load(uri).submit().get()
                    runOnUiThread {
                        backFile?.let {
                            GlideUtil.loadPhoto(this@PersonAuthActivity, img_card_back, it)
                            updateState()
                        }
                    }
                }
            }
        }
    }


    private fun updateState() {
        if (frontFile != null) {
            btn_front_select.text = "删除"
            txt_front_name.text = frontFile?.name
        } else {
            btn_front_select.text = "选择文件"
            txt_front_name.text = ""
            frontOk = false
        }

        if (backFile != null) {
            btn_back_select.text = "删除"
            txt_back_name.text = backFile?.name
        } else {
            btn_back_select.text = "选择文件"
            txt_back_name.text = ""
            backOk = false
        }
    }


    //上传图片
    private fun updatePhoto() {
        if (frontFile == null) {
            ToastUtil.show("请选择身份证正面（国徽面）")
            return
        }

        if (backFile == null) {
            ToastUtil.show("请选择身份证反面（个人信息面）")
            return
        }

        if (frontOk && backOk) {
            commit()
        } else {
            showLoading()
            OSSUtil.updateFile(frontFile?.absolutePath ?: "", OSSUtil.IMAGE) { it ->
                val frontMsg = Message()
                frontMsg.what = 1
                frontMsg.obj = it
                handler.sendMessage(frontMsg)

                OSSUtil.updateFile(backFile?.absolutePath ?: "", OSSUtil.IMAGE) {
                    val backMsg = Message()
                    backMsg.what = 2
                    backMsg.obj = it
                    handler.sendMessage(backMsg)
                    hideLoading()
                }
            }
        }
    }


    //提交url
    fun commit() {
        showLoading()
        val params = hashMapOf<String, String>()
        params["cardFront"] = frontUrl
        params["cardBack"] = backUrl
        ApiManager.post(composites, params, Constant.USER_SAVEIDENTITYCARD, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                hideLoading()
                if (data.content?.statu == "1") {
                    finish()
                }
                ToastUtil.show(data.content?.info)
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }
        })

    }
}