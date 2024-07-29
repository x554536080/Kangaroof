package com.kuma.kangaroof.retrofit.response

data class BaseResponse<T>(
    val data: T?,
    val errorCode: Int = 0,
    val errorMsg: String = ""//都默认值了，也不一定要声明类型吧
) {//为啥data类主构造函数非得一定要有一个参数...

    fun isFailed(): Boolean {
        return errorCode != 0
    }
}
