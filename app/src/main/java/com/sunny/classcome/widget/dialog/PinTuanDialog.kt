package com.sunny.classcome.widget.dialog

import android.app.Dialog
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Window
import com.sunny.classcome.R
import com.sunny.classcome.adapter.PinTuanAdapter
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.bean.ClassDetailBean
import kotlinx.android.synthetic.main.layout_item_pindan.*

class PinTuanDialog(context:Context, var isAppointment:String, var classBean: ClassBean.Bean.Data, list: ArrayList<ClassDetailBean.Content.ResCourseVO.PintuanResponseVO>): Dialog(context)  {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_item_pindan)

        recycler_view.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = PinTuanAdapter(isAppointment,classBean,list)

        txt_close.setOnClickListener{
            dismiss()
        }

        window?.attributes?.width = context.resources.getDimension(R.dimen.pt570).toInt()
        window?.attributes?.height = context.resources.getDimension(R.dimen.pt716).toInt()
        window?.setBackgroundDrawableResource(R.color.color_transparent)
    }
}