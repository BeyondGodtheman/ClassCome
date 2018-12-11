package com.sunny.classcome.http

import android.content.Intent
import android.os.Build
import android.provider.Settings
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.sunny.classcome.BuildConfig
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.activity.LoginActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.utils.LogUtil
import com.sunny.classcome.utils.ToastUtil
import com.sunny.classcome.utils.UserManger
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.File
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


/**
 *
 * Created by 张野 on 2017/9/28.
 */
object ApiManager {

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
                .addInterceptor(LoggerInterceptor())
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(getHost())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

    private val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    val gSon = Gson()

    const val STRING = 0X1
    const val OTHER = 0x2


    fun getHost(): String = if (Constant.isDebug()) "http://47.105.193.86:8081/course/api/" else "http://www.coursecoming.com/api/"

    /**
     * 发起一个网络请求并解析成实体类
     */
    private fun <T> request(composites: CompositeDisposable?, observable: Observable<ResponseBody>, onResult: OnResult<T>) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseBody> {

                    override fun onNext(data: ResponseBody) {
                        val body = data.string()
                        LogUtil.e("Body:$body")
                        parserJson(body, onResult)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()

                        var code = "0"
                        var message = ""

                        if (e is SocketTimeoutException || e is TimeoutException) {
                            message = MyApplication.getApp().getString(R.string.timeoutError)
                            onResult.onFailed(code, message)
                        }

                        if (e is ConnectException) {
                            message = MyApplication.getApp().getString(R.string.connectError)
                            onResult.onFailed(code, message)
                        }

                        if (e is HttpException) {
                            code = e.code().toString()
                            message = e.message()
                            onResult.onFailed(code, message)
                        }
                        ToastUtil.show(message)
                    }

                    override fun onComplete() {

                    }

                    override fun onSubscribe(disposable: Disposable) {
                        composites?.add(disposable)
                    }

                })
    }


    /**
     * GET请求
     */
    fun <T> get(composites: CompositeDisposable?, params: Map<String, String>?, url: String, onResult: OnResult<T>) {
        if (params != null) {
            val sb = StringBuilder("?")
            params.forEach {
                sb.append("${it.key}=${it.value}&")
            }
            sb.deleteCharAt(sb.length - 1)
            request(composites, apiService.get(url + sb.toString()), onResult)
        } else {
            request(composites, apiService.get(url), onResult)
        }
    }


    /**
     *  Post请求
     */
    fun <T> post(composites: CompositeDisposable?, params: Map<String, String>?, url: String, onResult: OnResult<T>) {
        val jsonObj = JSONObject()
        params?.forEach {
            jsonObj.put(it.key, it.value)
        }
        jsonObj.put("deviceId", Build.SERIAL)
        jsonObj.put("os_version", Build.VERSION.RELEASE)
        jsonObj.put("platform", "Android")
        jsonObj.put("version", BuildConfig.VERSION_NAME)
        UserManger.getLogin()?.let {
            jsonObj.put("token", it.content.token)
        }

        postJson(composites, jsonObj.toString(), url, onResult)
    }


    /**
     * Post一个JSON
     */
    fun <T> postJson(composites: CompositeDisposable?, params: String, url: String, onResult: OnResult<T>) {
        val requestBody = RequestBody.create(MediaType.parse("application/json"), params)
        request(composites, apiService.post(requestBody, url), onResult)
    }


    /**
     * Post一张图片
     */
    fun <T> postImage(composites: CompositeDisposable?, path: String, onResult: OnResult<T>) {
        val requestBodyBuilder = MultipartBody.Builder().setType(MultipartBody.FORM)
        val file = File(path)
        requestBodyBuilder.addFormDataPart("file", path,
                RequestBody.create(MediaType.parse("image/jpeg"), file))
        requestBodyBuilder.addFormDataPart("picturetype", "jpg")
        request(composites, apiService.post(requestBodyBuilder.build(), Constant.COMMON_UPLOADS), onResult)
    }


    /**
     * 结果回调
     */
    abstract class OnResult<in T> {
        lateinit var typeToken: Type
        var tag: Int

        init {
            val t = javaClass.genericSuperclass
            val args = (t as ParameterizedType).actualTypeArguments
            val type = "class java.lang.String"
            if (args[0].toString() == type) {
                tag = STRING
            } else {
                typeToken = args[0]
                tag = OTHER
            }
        }

        abstract fun onSuccess(data: T)
        abstract fun onFailed(code: String, message: String)
    }


    @Suppress("UNCHECKED_CAST")
    private fun <T> parserJson(json: String, onResult: OnResult<T>) {
        if (onResult.tag == STRING) {
            onResult.onSuccess(json as T)
        } else {

            val jsonObject =JSONObject(json)
            val code = jsonObject.opt("code")
            if (code == 100){
                MyApplication.getApp().let {
                    UserManger.clear()
                }
            }

            if (onResult.typeToken.toString().contains(BaseBean::class.java.name)) {
                val baseModel = gSon.fromJson<T>(json, onResult.typeToken)
                onResult.onSuccess(baseModel)
            } else {
                onResult.onSuccess(gSon.fromJson(json, onResult.typeToken))
            }
        }
    }
}