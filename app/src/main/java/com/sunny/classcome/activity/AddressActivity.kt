package com.sunny.classcome.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.adapter.AddressCountyAdapter
import com.sunny.classcome.adapter.AddressTownAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.LocalCityBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.UserManger
import kotlinx.android.synthetic.main.activity_address.*

class AddressActivity: BaseActivity() {

    override fun setLayout(): Int = R.layout.activity_address

    override fun initView() {
        showTitle(titleManager.defaultTitle("切换位置"))
        recl_city.layoutManager = LinearLayoutManager(this)
        recl_area.layoutManager = LinearLayoutManager(this)

    }

    override fun onClick(v: View?) {

    }

    override fun loadData() {
        showLoading()
        val address = UserManger.getAddress().split(",")
        txt_city.text = address[1]

        ApiManager.post(composites, null, Constant.PUB_GETCITYLIST, object : ApiManager.OnResult<LocalCityBean>() {
            override fun onSuccess(data: LocalCityBean) {
                hideLoading()

                val intent = Intent()
                intent.putExtra("cityId",address[0])

                recl_city.adapter = AddressCountyAdapter(data.content.find { it.cityVoId == address[0]}?.countyList?: arrayListOf()).apply {
                    setOnItemClickListener { _, index ->
                        lastIndex = index
                        intent.putExtra("countyId",getData(index).countyId)
                        intent.putExtra("countyName",getData(index).countyName)

                        notifyDataSetChanged()
                        recl_area.adapter = AddressTownAdapter(getData(index).townlist).apply {
                            setOnItemClickListener { _, index ->
                                intent.putExtra("townId",getData(index).townId)
                                intent.putExtra("townName",getData(index).townName)
                                setResult(1, intent)
                                finish()
                            }
                        }
                    }
                }
            }


            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })

    }
}