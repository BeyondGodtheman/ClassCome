package com.sunny.classcome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.CommentBean
import com.sunny.classcome.utils.DateUtil
import com.sunny.classcome.utils.GlideUtil
import kotlinx.android.synthetic.main.item_comment.view.*

/**
 * Desc
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2019/1/1 01:32
 */
class CommentAdapter(list: ArrayList<CommentBean.Data>) : BaseRecycleAdapter<CommentBean.Data>(list) {
    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        GlideUtil.loadHead(context, holder.itemView.img_user_head, getData(position).userPic ?: "")
        holder.itemView.txt_user_name.text = getData(position).userName ?: ""
        holder.itemView.ratingBar.setStar((getData(position).starLevel ?: "0").toFloat())
        holder.itemView.txt_content.text = getData(position).description ?: ""
        holder.itemView.txt_date.text = DateUtil.dateFormatYYMMddHHssmm(getData(position).createTime
                ?: "")
    }
}