package com.sunny.classcome.fragment

import android.content.Intent
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.activity.*
import com.sunny.classcome.base.BaseFragment
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.HtmlBean
import com.sunny.classcome.bean.MineBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.*
import kotlinx.android.synthetic.main.fragment_mine.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * Desc：我的
 * Author：JoannChen
 * Mail：yongzuo_chen@dingyuegroup.cn
 * Date：2018/11/22 0022 18:29
 */
class MineFragment : BaseFragment() {

    override fun setLayout(): Int = R.layout.fragment_mine

    override fun initView() {

        EventBus.getDefault().register(this)

        txt_user_name.setOnClickListener(this)
        txt_user_address.setOnClickListener(this)
        rl_points.setOnClickListener(this)
        rl_member.setOnClickListener(this)

        img_message.setOnClickListener(this)
        img_user_head.setOnClickListener(this)
        img_more.setOnClickListener(this)

        rl_my_trip.setOnClickListener(this)
        rl_my_publish.setOnClickListener(this)
        rl_my_partake.setOnClickListener(this)

        txt_my_cheques.setOnClickListener(this)
        txt_invitation_record.setOnClickListener(this)
        txt_my_profile.setOnClickListener(this)
        txt_my_collection.setOnClickListener(this)
        txt_feedback.setOnClickListener(this)
        txt_setting.setOnClickListener(this)
        txt_business_cooperation.setOnClickListener(this)

    }


    override fun update() {
        super.update()
        if (UserManger.isLogin()) {
            ApiManager.post(getBaseActivity().composites, null, Constant.USER_MYPAGE, object : ApiManager.OnResult<BaseBean<MineBean>>() {
                override fun onSuccess(data: BaseBean<MineBean>) {
                    data.content?.let { it ->
                        if (it.statu == "1") {
                            it.data?.let {
                                UserManger.setMine(it)
                            }
                            GlideUtil.loadHead(requireContext(), img_user_head, it.data?.userPic
                                    ?: "")
                            txt_user_name.text = it.data?.userName
                            txt_user_address.text = it.data?.address
                            txt_points.text = (StringUtil.formatMoney((it.data?.source?:"0").toDouble())+"积分")
                            txt_member.text = it.data?.gradeName
                        }
                    }
                }

                override fun onFailed(code: String, message: String) {

                }
            })
        } else {
            (getBaseActivity() as HomeActivity).let {
                if (it.getCurrentIndext() != 0) {
                    it.initTabView(0) //切换到首页
                    intent(LoginActivity::class.java)
                }
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.img_user_head,
            R.id.txt_user_name,
            R.id.txt_user_address,
            R.id.img_more -> {
                if (UserManger.getMine()?.authentication == "1"){
                    intent(ModifyInfoActivity::class.java)
                }else{
                    intent(ModifyCompanyActivity::class.java)
                }
            }
            R.id.img_message -> intent(MyMsgActivity::class.java)

            R.id.rl_points -> startActivity(Intent(context, PointActivity::class.java)
                    .putExtra("point", txt_points.text.toString()))
            R.id.rl_member -> startActivity(Intent(context, LevelActivity::class.java)
                    .putExtra("level", txt_member.text.toString()))

            R.id.rl_my_trip -> intent(MyItineraryActivity::class.java)
            R.id.rl_my_publish -> MyClassActivity.start(requireContext(),1)
            R.id.rl_my_partake -> MyClassActivity.start(requireContext(),2)

            R.id.txt_my_cheques -> intent(MyChequesActivity::class.java)
            R.id.txt_invitation_record -> intent(InvitationRecordActivity::class.java)
            R.id.txt_my_profile -> MyProfileActivity.start(requireContext(),"")
            R.id.txt_my_collection -> intent(MyCollectionActivity::class.java)
            R.id.txt_feedback -> intent(FeedbackActivity::class.java)
            R.id.txt_setting -> intent(SettingActivity::class.java)
            R.id.txt_business_cooperation -> startWeb(Constant.PUB_COOPERATION) //商务合作
        }
    }

    private fun intent(clazz: Class<*>) {
        IntentUtil.start(requireActivity(), clazz)
    }


    private fun startWeb(url: String) {
        showLoading()
        ApiManager.post(getBaseActivity().composites, null, url, object : ApiManager.OnResult<BaseBean<ArrayList<HtmlBean>>>() {
            override fun onSuccess(data: BaseBean<ArrayList<HtmlBean>>) {
                hideLoading()
                data.content?.data?.let {
                    if (it.size > 0) {
                        WebActivity.start(requireContext(), it[0].title, it[0].content)
                    }
                    return
                }
                ToastUtil.show(data.content?.info)
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })
    }

    override fun close() {
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onShowMessageEvent(showMessage: ShowMessage) {
        view_point.visibility = View.VISIBLE
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onHideMessageEvent(hideMessage: HideMessage) {
        view_point.visibility = View.GONE
    }

}