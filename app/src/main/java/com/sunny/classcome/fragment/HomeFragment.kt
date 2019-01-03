package com.sunny.classcome.fragment

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.sunny.classcome.R
import com.sunny.classcome.activity.ClassListActivity
import com.sunny.classcome.activity.LocationActivity
import com.sunny.classcome.activity.LoginActivity
import com.sunny.classcome.activity.MyMsgActivity
import com.sunny.classcome.adapter.ClassListAdapter
import com.sunny.classcome.base.BaseFragment
import com.sunny.classcome.bean.*
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.HideMessage
import com.sunny.classcome.utils.LocationUtil
import com.sunny.classcome.utils.ShowMessage
import com.sunny.classcome.utils.UserManger
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.home_class_list.*
import kotlinx.android.synthetic.main.layout_home_recommend_text.view.*
import kotlinx.android.synthetic.main.layout_home_title.view.*
import kotlinx.android.synthetic.main.layout_home_type.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HomeFragment : BaseFragment() {

    private var isShowLocal = false
    private var localStr = listOf<String>()

    private var sortIndex = 0

    private var sortFlag = false

    private var pageIndex = 1

    private val dataList = arrayListOf<ClassBean.Bean.Data>()


    private val topArrowList: ArrayList<ImageView> by lazy {
        arrayListOf(img_local_top, img_hot_top, img_price_top, img_time_top)
    }

    private val bottomArrowList: ArrayList<ImageView> by lazy {
        arrayListOf(img_local_bottom, img_hot_bottom, img_price_bottom, img_time_bottom)
    }

    private val locationUtil: LocationUtil by lazy {
        LocationUtil(requireContext()) {
            if (isShowLocal) {
                titleView.text_home_Location.text = localStr[1]
            } else {
                if (it.isNotEmpty()) {
                    titleView.text_home_Location.text = it
                }
                loadAddress()
            }
        }
    }

    private val titleView: View by lazy {
        showTitle(titleManager.homeTitle())
    }

    override fun setLayout(): Int = R.layout.fragment_home

    override fun initView() {

        EventBus.getDefault().register(this)

        localStr = UserManger.getAddress().apply {
            if (isNotEmpty()) {
                isShowLocal = true
                launch(UI) {
                    delay(100)
                    pageIndex = 1
                    loadClass(true)
                }
            }
        }.split(",")


        rl_314.setOnClickListener(this)
        rl_311.setOnClickListener(this)
        rl_class.setOnClickListener(this)
        rl_217.setOnClickListener(this)
        rl_213.setOnClickListener(this)

        ll_location.setOnClickListener(this)
        ll_hot.setOnClickListener(this)
        ll_price.setOnClickListener(this)
        ll_time.setOnClickListener(this)

        recl.layoutManager = LinearLayoutManager(context)
        recl.isNestedScrollingEnabled = false
        recl.adapter = ClassListAdapter(dataList)


        titleView.rlLocation.setOnClickListener(this)
        titleView.ivMessage.setOnClickListener(this)

        refresh.setRefreshHeader(ClassicsHeader(context)
                .setPrimaryColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                .setAccentColor(ContextCompat.getColor(requireContext(), R.color.color_white)))
        refresh.setRefreshFooter(ClassicsFooter(context))

        refresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {

            override fun onRefresh(refreshLayout: RefreshLayout) {
                loadBanner()
                pageIndex = 1
                loadClass(true)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                pageIndex++
                loadClass(false)
            }
        })


        locationUtil.startLocation()
        loadBanner()
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.rlLocation -> startActivityForResult(Intent(context, LocationActivity::class.java).putExtra("type", 0), 0)
            R.id.ivMessage -> {
                if (UserManger.isLogin()) {
                    startActivity(Intent(context, MyMsgActivity::class.java))
                } else {
                    startActivity(Intent(context, LoginActivity::class.java))
                }
            }

            R.id.rl_314 -> {
                ClassListActivity.start(requireContext(), "50000")
            }

            R.id.rl_311 -> {
                ClassListActivity.start(requireContext(), "40000")
            }

            R.id.rl_class -> {
                ClassListActivity.start(requireContext(), "")
            }

            R.id.rl_217 -> {
                ClassListActivity.start(requireContext(), "217")
            }

            R.id.rl_213 -> {
                ClassListActivity.start(requireContext(), "213")
            }

            R.id.ll_location -> {
                sort(0)
            }
            R.id.ll_hot -> {
                sort(1)
            }
            R.id.ll_price -> {
                sort(2)
            }
            R.id.ll_time -> {
                sort(3)
            }

        }

    }

    //初始化推荐数据
    private fun initRecommend(arrayList: ArrayList<ClassBean.Bean.Data>) {
        vf_home_commend.stopFlipping()
        vf_home_commend.removeAllViews()

        arrayList.forEach { data ->
            val layoutView = LayoutInflater.from(context)
                    .inflate(R.layout.layout_home_recommend_text, vf_home_commend, false)
            data.categoryList?.let {
                if (it.isNotEmpty()) {
                    layoutView.txt_type.text = it[0].name
                }
            }

            layoutView.txt_title.text = data.course.title
            vf_home_commend.addView(layoutView)
        }
        vf_home_commend.startFlipping()
    }


    private fun loadBanner() {
        ApiManager.post(getBaseActivity().composites, null, Constant.COURSE_GETIMAGEOFPAGE, object : ApiManager.OnResult<BannerBean>() {
            override fun onSuccess(data: BannerBean) {
                rl_banner.loadData(data.content)
            }

            override fun onFailed(code: String, message: String) {
            }
        })
    }

    private fun loadAddress() {
        ApiManager.post(getBaseActivity().composites, null, Constant.PUB_GETCITYLIST, object : ApiManager.OnResult<LocalCityBean>() {
            override fun onSuccess(data: LocalCityBean) {
                val localCityBean = data.content.find { it.cityVoName == titleView.text_home_Location.text.toString() }
                if (localCityBean != null) {
                    UserManger.setAddress(localCityBean.cityVoId, localCityBean.cityVoName)
                } else {
                    UserManger.setAddress("37", "上海市")
                }

                pageIndex = 1
                loadClass(true)
            }

            override fun onFailed(code: String, message: String) {
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == 0) {
            UserManger.getAddress().apply {
                if (isEmpty() || !this.contains(",")) {
                    return
                }
                localStr = split(",")
                isShowLocal = true
                titleView.text_home_Location.text = localStr[1]
                pageIndex = 1
                loadClass(true)
            }
        }
    }


    private fun sort(mSortIndex: Int) {
        bottomArrowList[sortIndex].setImageResource(R.mipmap.ic_arrow_bottom_gray)
        topArrowList[sortIndex].setImageResource(R.mipmap.ic_arrow_top_gray)
        if (mSortIndex != sortIndex) {
            sortFlag = false
        }
        sortIndex = mSortIndex

        sortFlag = if (sortFlag) {
            topArrowList[sortIndex].setImageResource(R.mipmap.ic_arrow_top_blue)
            false
        } else {
            bottomArrowList[sortIndex].setImageResource(R.mipmap.ic_arrow_bottom_blue)
            true
        }
        showLoading()
        pageIndex = 1
        loadClass(true)
    }


    private fun loadClass(isFlag: Boolean) {
        val sortStr = when (sortIndex) {
            0 -> "sortAdress"
            1 -> "sortPopularity"
            2 -> "sortPrice"
            else -> "sortTime"
        }
        val params = HashMap<String, String>()
        params["cityId"] = UserManger.getAddress().split(",")[0]
        params["pageIndex"] = pageIndex.toString()
        params[sortStr] = if (!sortFlag) "1" else "0"

        ApiManager.post(getBaseActivity().composites, params, Constant.COURSE_GETCOURSELISTS, object : ApiManager.OnResult<ClassBean>() {
            override fun onSuccess(data: ClassBean) {
                hideLoading()
                if (isFlag) {
                    dataList.clear()
                    refresh.finishRefresh()
                } else {
                    refresh.finishLoadMore()
                }

                if (isFlag) {
                    loadPinTuan()
                    initRecommend(data.content?.dataList ?: arrayListOf())
                }
                data.content?.dataList?.let {
                    dataList.addAll(it)
                }
                recl.adapter?.notifyDataSetChanged()
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })
    }

    fun loadPinTuan() {
        ApiManager.post(getBaseActivity().composites, null, Constant.ORDER_GETPINTUAN, object : ApiManager.OnResult<BaseBean<ArrayList<HomePinTuanBean>>>() {
            override fun onSuccess(data: BaseBean<ArrayList<HomePinTuanBean>>) {
                titleView.vf_home_pintuan.removeAllViews()
                data.content?.data?.let { list ->
                    list.forEach {
                        val textView = TextView(context)
                        textView.text = ((it.userName ?: "") + "发起了拼团")
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.pt28))
                        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_white))
                        textView.setLines(1)
                        textView.ellipsize = TextUtils.TruncateAt.END
                        titleView.vf_home_pintuan.addView(textView)
                    }
                }
                titleView.vf_home_pintuan.inAnimation.interpolator = LinearInterpolator()
                titleView.vf_home_pintuan.startFlipping()
            }

            override fun onFailed(code: String, message: String) {
            }

        })
    }

    override fun close() {
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onShowMessageEvent(showMessage: ShowMessage) {
        titleView.view_point.visibility = View.VISIBLE
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onHideMessageEvent(hideMessage: HideMessage) {
        titleView.view_point.visibility = View.GONE
    }
}