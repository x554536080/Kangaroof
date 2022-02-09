package com.kuma.base.network

import android.text.TextUtils
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File
import java.io.IOException


/**
 * name: RequestUtil
 * author: 熊熊熊
 * date: 2021/11/30 16:20
 * description:
 */
class RequestUtil private constructor(
    private val mMethodType: String,//请求方式，目前只支持get和post
    private var mUrl: String,//接口
    private val mJsonStr: String?,//json类型的参数，post方式
    private val mFile: File?,//文件的参数，post方式,只有一个文件
    private val mFileList: List<File>?,//文件集合，这个集合对应一个key，即mFileKey
    private val mFileKey: String?,//上传服务器的文件对应的key
    private val mFileMap: Map<String, File>?,//文件集合，每个文件对应一个key
    private val mFileType: String?,//文件类型的参数，与file同时存在
    private val mParamsMap: Map<String, String>?,//键值对类型的参数，只有这一种情况下区分post和get
    private val mHeaderMap: Map<String, String>,//头参数
    private val mCallBack: CallBackUtil<*>//回调接口
) {

    constructor(
        methodType: String,
        url: String,
        paramsMap: Map<String, String>,
        headerMap: Map<String, String>,
        callBack: CallBackUtil<*>
    )
            : this(
        methodType,
        url,
        null,
        null,
        null,
        null,
        null,
        null,
        paramsMap,
        headerMap,
        callBack
    )

    private var mOkHttpClient //OkhttpClient对象
            : OkHttpClient? = null
    private var mOkHttpRequest //请求对象
            : Request? = null
    private var mRequestBuilder //请求对象的构建者
            : Request.Builder? = null

    init {
        getInstance()
    }


    /**
     * 创建OKhttpClient实例。
     */
    private fun getInstance() {
        mOkHttpClient = OkHttpClient()
        mRequestBuilder = Request.Builder()

        if (mFile != null || mFileList != null || mFileMap != null) { //先判断是否有文件，
            //todo 4            setFile()
        } else {
            //设置参数
            when (mMethodType) {
                OkhttpUtil.METHOD_GET -> setGetParams()
                OkhttpUtil.METHOD_POST -> mRequestBuilder!!.post(getRequestBody())
                OkhttpUtil.METHOD_PUT -> mRequestBuilder!!.put(getRequestBody())
                OkhttpUtil.METHOD_DELETE -> mRequestBuilder!!.delete(getRequestBody())
            }
        }

        mRequestBuilder!!.url(mUrl)

        if (mHeaderMap != null) {
            setHeader()
        }

        //mRequestBuilder.addHeader("Authorization","Bearer "+"token");可以把token添加到这儿
        mOkHttpRequest = mRequestBuilder!!.build()
    }


    private fun setGetParams() {
        if (mParamsMap != null) {
            mUrl = "$mUrl?"
            for (key in mParamsMap.keys) {
                mUrl = mUrl + key + "=" + mParamsMap[key] + "&"
            }
            mUrl = mUrl.substring(0, mUrl.length - 1)
        }
    }

    private fun getRequestBody(): RequestBody {
        /**
         * 首先判断mJsonStr是否为空，由于mJsonStr与mParamsMap不可能同时存在，所以先判断mJsonStr
         */
        if (!TextUtils.isEmpty(mJsonStr)) {
            val JSON: MediaType? =
                "application/json; charset=utf-8".toMediaTypeOrNull() //数据类型为json格式，
            return RequestBody.create(JSON, mJsonStr!!) //json数据，
        }
        /**
         * post,put,delete都需要body，但也都有body等于空的情况，此时也应该有body对象，但body中的内容为空
         */
        val formBody: FormBody.Builder = FormBody.Builder()
        if (mParamsMap != null) {
            for (key in mParamsMap.keys) {
                formBody.add(key, mParamsMap[key]!!)
            }
        }
        return formBody.build()
    }

    private fun setHeader() {
        if (mHeaderMap != null) {
            for (key in mHeaderMap.keys) {
                mRequestBuilder!!.addHeader(key, mHeaderMap[key]!!)
            }
        }
    }

    fun execute() {
//        mOkHttpClient!!.newCall(mOkHttpRequest!!).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                if (mCallBack != null) {
//                    mCallBack.onError(call, e)
//                }
//            }
//
//            @Throws(IOException::class)
//            override fun onResponse(call: Call, response: Response) {
//                if (mCallBack != null) {
//                    mCallBack.onSuccess(call, response)
//                }
//            }
//        })
    }


}