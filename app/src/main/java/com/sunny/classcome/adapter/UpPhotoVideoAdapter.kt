package com.sunny.classcome.adapter

import android.app.Activity
import android.content.pm.ActivityInfo
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.bean.UserBean
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.GlideUtil
import com.sunny.classcome.utils.MyGlideEngine
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import kotlinx.android.synthetic.main.item_update_img_video.view.*

class UpPhotoVideoAdapter(list: ArrayList<ClassBean.Bean.Data.Material>): BaseRecycleAdapter<ClassBean.Bean.Data.Material>(list) {

    override fun getItemCount(): Int {
        if (list.size < 9){
            return list.size + 1
        }

        return list.size
    }

    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_update_img_video,parent,false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        if (position == list.size && list.size < 9){
            holder.itemView.view_delete.visibility = View.GONE
            holder.itemView.img_photo.setImageResource(R.mipmap.ic_info_add)
            holder.itemView.setOnClickListener {
                Matisse.from(context as Activity)
                        .choose(MimeType.ofAll(), false) // 选择 mime 的类型
                        .theme(R.style.Matisse_Zhihu)
                        .maxSelectable(1) // 图片选择的最多数量
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f) // 缩略图的比例
                        .imageEngine(MyGlideEngine()) // 使用的图片加载引擎
                        .forResult(Constant.REQUEST_CODE_CHOOSE)
            }
        }else{
            holder.itemView.view_delete.visibility = View.VISIBLE
            holder.itemView.setOnClickListener(null)
            GlideUtil.loadDrawable(context,holder.itemView.img_photo,getData(position).url?:"",ColorDrawable(ContextCompat.getColor(context,R.color.color_divider)))
            holder.itemView.view_delete.setOnClickListener {
                list.removeAt(position)
                notifyDataSetChanged()
            }
        }
    }
}