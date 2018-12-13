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
import com.sunny.classcome.bean.UserBean
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.GlideApp
import com.sunny.classcome.utils.OSSUtil
import com.sunny.classcome.utils.ToastUtil
import com.zhihu.matisse.Matisse
import kotlinx.android.synthetic.main.layout_photo_video.view.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch

class UpPhotoVideoView : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val list = arrayListOf<UserBean.Material>()
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


    fun setData(mList: ArrayList<UserBean.Material>) {
        list.clear()
        list.addAll(mList)
        upPhotoVideoAdapter.notifyDataSetChanged()
    }

    fun setText(text: String) {
        edit_description.setText(text)
    }


    fun getText() = edit_description.text.toString()

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constant.REQUEST_CODE_CHOOSE && resultCode == AppCompatActivity.RESULT_OK) {
            val uri = Matisse.obtainResult(data).first()
            launch(CommonPool) {
                val file = GlideApp.with(getContext()).asFile().load(uri).submit().get()
                file?.let { it ->
                    if (it.absolutePath.contains(".mp4")) {
                        if (list.any { it.type == "mp4" }) {
                            ToastUtil.show("只能上传一个视频文件！")
                            return@launch
                        }
                    }

                    OSSUtil.updateFile(file.absolutePath, if (it.absolutePath.contains(".mp4")) OSSUtil.VIDEO else OSSUtil.IMAGE) {
                        if (it.contains(".mp4")) {
                            list.add(UserBean.Material("4", it))
                        } else {
                            list.add(UserBean.Material("3", it))
                        }
                        upPhotoVideoAdapter.notifyItemChanged(list.size - 1)
                    }

                }
            }
        }
    }
}