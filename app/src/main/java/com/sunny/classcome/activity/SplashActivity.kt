package com.sunny.classcome.activity

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.utils.PermissionUtil
import com.sunny.classcome.widget.dialog.MyDialog
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

class SplashActivity : BaseActivity() {

    private val permissionUtil: PermissionUtil by lazy {
        PermissionUtil(this)
    }
    private var retry = false //权限重试

    override fun setLayout(): Int = R.layout.activity_splash

    override fun initView() {
        goneTitle()
        if (permissionUtil.allPermission()) {
            startHome()
        }
    }

    override fun update() {
    }

    override fun loadData() {
    }

    override fun close() {
    }

    override fun onClick(view: View) {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissionUtil.checkPermission(permissions)) {
            startHome()
        } else {
            //拒绝权限后弹窗
            val dialog = MyDialog(this)
            dialog.setTitle("温馨提示")
            dialog.setDesc("${permissionUtil.getMissPerName()}权限是必需的，否则我们无法为您提供核心服务")
            dialog.show()
            dialog.setCancelText("残忍拒绝")
            dialog.setPositiveText("授权")
            dialog.cancelOnClickListener = View.OnClickListener {
                dialog.dismiss()
                finish()
            }

            dialog.onClickListener1 = View.OnClickListener {
                //用户勾选不在提醒，跳转至应用设置页面
                if (permissionUtil.isNotReminding()) {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", applicationContext.packageName, null)
                    intent.data = uri
                    startActivity(intent)
                    retry = true
                } else {
                    permissionUtil.allPermission()
                }
            }

            dialog.show()
        }
    }


    //拦截返回键
    override fun onBackPressed() {
    }

    private fun startHome() {
        launch {
            delay(1000)
            startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
        }
    }
}