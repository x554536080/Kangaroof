package com.kuma.base.network

/**
 * name: OkhttpUtil
 * author: 熊熊熊
 * date: 2021/11/30 15:01
 * description:todo 3 暂时只做get和post请求
 */
class OkhttpUtil {

    companion object {
        const val METHOD_GET = "GET"
        const val METHOD_POST = "POST"
        const val METHOD_PUT = "PUT"
        const val METHOD_DELETE = "DELETE"

        const val FILE_TYPE_FILE = "file/*"
        const val FILE_TYPE_IMAGE = "image/*"
        const val FILE_TYPE_AUDIO = "audio/*"
        const val FILE_TYPE_VIDEO = "video/*"


        //todo 0 *星号的问题
        fun okHttpGet(url: String, callBack: CallBackUtil<*>) {
            okHttpGet(url, null, callBack)
        }

        fun okHttpGet(url: String, paramsMap: Map<String, String>?, callBack: CallBackUtil<*>) {
            okHttpGet(url, paramsMap, null, callBack)
        }

        fun okHttpGet(url: String, paramsMap: Map<String, String>?, headerMap: Map<String, String>?, callBack: CallBackUtil<*>) {
            //todo 1
        }

        fun okHttpPost(url: String?, callBack: CallBackUtil<*>?) {
            okHttpPost(url, null, callBack)
        }

        fun okHttpPost(url: String?, paramsMap: Map<String, String>?, callBack: CallBackUtil<*>?) {
            okHttpPost(url, null, null, callBack)
        }

        fun okHttpPost(url: String?, paramsMap: Map<String, String>?, headerMap: Map<String, String>?, callBack: CallBackUtil<*>?) {
            //todo 2
        }
    }


}