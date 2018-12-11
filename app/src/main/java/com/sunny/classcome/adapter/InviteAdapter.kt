package com.sunny.classcome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.InviteBean
import com.sunny.classcome.utils.GlideUtil
import com.sunny.classcome.utils.showBlueBtn
import com.sunny.classcome.utils.showGrayBtn
import kotlinx.android.synthetic.main.item_invite.view.*

class InviteAdapter(list: ArrayList<InviteBean.Bean.Data>, var onInvite: (position:Int) -> Unit) : BaseRecycleAdapter<InviteBean.Bean.Data>(list) {
    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_invite, parent, false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.txt_user_name.text = getData(position).userName
        holder.itemView.txt_specialty.text = ("专业：" + getData(position).profession)
        holder.itemView.txt_user_address.text = ("所在地：" + getData(position).address)

        GlideUtil.loadHead(context,holder.itemView.img_user_head,getData(position).userPic)

        if (getData(position).isWelcome == "2") {
            showGrayBtn(holder.itemView.btn_invite, "已邀请")
        } else {
            showBlueBtn(holder.itemView.btn_invite, "邀请")
            holder.itemView.btn_invite.setOnClickListener {
                onInvite(position)
            }
        }

    }
}