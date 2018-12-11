package com.sunny.classcome.utils

import com.alibaba.sdk.android.oss.ClientConfiguration
import com.alibaba.sdk.android.oss.ClientException
import com.alibaba.sdk.android.oss.OSSClient
import com.alibaba.sdk.android.oss.ServiceException
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask
import com.alibaba.sdk.android.oss.model.PutObjectRequest
import com.alibaba.sdk.android.oss.model.PutObjectResult
import com.sunny.classcome.MyApplication
import com.sunny.classcome.http.Constant


object OSSUtil {

    var IMAGE = "app/image/"
    var VIDEO = "app/video/"

    private val oss:OSSClient by lazy {
        val credentialProvider = OSSStsTokenCredentialProvider(Constant.ACCESSKEYID,Constant.AccessKeySecret,Constant.TOKEN)

        val conf = ClientConfiguration()
        conf.connectionTimeout = 15 * 1000 // 连接超时，默认15秒
        conf.socketTimeout = 15 * 1000 // socket超时，默认15秒
        conf.maxConcurrentRequest = 5 // 最大并发请求数，默认5个
        conf.maxErrorRetry = 2 // 失败后最大重试次数，默认2次
        OSSClient(MyApplication.getApp(), Constant.endpoint, credentialProvider)
    }

    private var task: OSSAsyncTask<PutObjectResult>? = null

    fun updateFile(filePath:String,type:String,onResult:(url:String)->Unit){
        if (filePath.isEmpty()){
            return
        }
        // 构造上传请求
        val fileName = type+(System.currentTimeMillis()/1000).toString() +  if (type == IMAGE)".jpg" else ".mp4"

        val put = PutObjectRequest(Constant.BUCKET_NAME, fileName,filePath)
        put.progressCallback = OSSProgressCallback<PutObjectRequest> { request, currentSize, totalSize ->
            LogUtil.i("上传进度：$currentSize")
        }

        task = oss.asyncPutObject(put, object : OSSCompletedCallback<PutObjectRequest, PutObjectResult> {
            override fun onSuccess(request: PutObjectRequest, result: PutObjectResult) {
                LogUtil.d("上传成功")
                onResult(Constant.UPDATEHOST + request.objectKey)
            }

            override fun onFailure(request: PutObjectRequest, clientExcepion: ClientException?, serviceException: ServiceException?) {
                // 请求异常
                clientExcepion?.printStackTrace()
                if (serviceException != null) {
                    // 服务异常
                    LogUtil.e("ErrorCode:"+serviceException.errorCode)
                    LogUtil.e("RequestId:"+serviceException.requestId)
                    LogUtil.e("HostId:"+serviceException.hostId)
                    LogUtil.e("RawMessage:"+ serviceException.rawMessage)
                }
            }
        })
    }

    fun closeUpdate(){
        task?.cancel()
    }

}