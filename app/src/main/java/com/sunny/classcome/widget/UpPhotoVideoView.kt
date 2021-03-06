package com.sunny.classcome.widget

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.sunny.classcome.R
import com.sunny.classcome.adapter.UpPhotoVideoAdapter
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.GlideApp
import com.sunny.classcome.utils.OSSUtil
import com.sunny.classcome.utils.ToastUtil
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import kotlinx.android.synthetic.main.layout_photo_video.view.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class UpPhotoVideoView : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var showLoading:(()->Unit)? = null
    var hideLoading:(()->Unit)? = null
    val list = arrayListOf<ClassBean.Bean.Data.Material>()
    private val upPhotoVideoAdapter: UpPhotoVideoAdapter by lazy {
        UpPhotoVideoAdapter(list)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_photo_video, this, true)
        recl_photo.setHasFixedSize(true)
        recl_photo.isNestedScrollingEnabled = false
        recl_photo.layoutManager = GridLayoutManager(context, 4)
        recl_photo.adapter = upPhotoVideoAdapter
    }


    fun setData(mList: ArrayList<ClassBean.Bean.Data.Material>) {
        list.clear()
        list.addAll(mList)
        upPhotoVideoAdapter.notifyDataSetChanged()
    }

    fun setText(text: String) {
        edit_description.setText(text)
    }


    fun setHint(text: String) {
        edit_description.hint = text
    }


    fun getText() = edit_description.text.toString()

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constant.REQUEST_CODE_CHOOSE && resultCode == AppCompatActivity.RESULT_OK) {
            val uri = Matisse.obtainResult(data).first()
            launch(CommonPool) {
                val file = GlideApp.with(getContext()).asFile().load(uri).submit().get()
                file?.let { _ ->
                    var isAddVideo = false //判断文件类型是否是视屏
                    MimeType.ofVideo().forEach {
                        if (it.checkType(getContext().contentResolver, uri)) {
                            isAddVideo = true
                            return@forEach
                        }
                    }
                    if (isAddVideo && list.find { (it.url ?: "").contains(".mp4") } != null) {
                        launch(UI) {
                            ToastUtil.show("只能上传一个视频文件！")
                        }
                        return@launch
                    }
                    handler.post {
                        showLoading?.invoke()

                        OSSUtil.updateFile(file.absolutePath, if (isAddVideo) OSSUtil.VIDEO else OSSUtil.IMAGE) {
                            hideLoading?.invoke()
                            if (it.contains(".mp4")) {
                                list.add(0, ClassBean.Bean.Data.Material("4", it))
                            } else {
                                list.add(ClassBean.Bean.Data.Material("3", it))
                            }
                            upPhotoVideoAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }
}