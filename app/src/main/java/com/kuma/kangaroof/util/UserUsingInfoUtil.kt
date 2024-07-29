package com.kuma.kangaroof.util

import android.content.Context
import android.widget.Toast
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener

import cn.bmob.v3.listener.SaveListener
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.kuma.kangaroof.data.LaunchInfo
import java.text.SimpleDateFormat
import java.util.*


class UserUsingInfoUtil {

//    companion object {
//        fun saveLaunchInfo(context: Context) {
//            val currentTime = Date()
//            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE)
//
//            val p2 = LaunchInfo(dateFormat.format(currentTime))
//            p2.save(object : SaveListener<String>() {
//                override fun done(objectId: String?, e: BmobException?) {
//                    if (e == null) {
//                        LogUtils.i("添加数据成功，返回objectId为：$objectId")
////                        Toast.makeText(context,"添加数据成功，返回objectId为：$objectId",
////                                Toast.LENGTH_SHORT).show()
//                    } else {
//                        Toast.makeText(context, "创建数据失败：" + e.message,
//                                Toast.LENGTH_SHORT).show()
//                    }
//                }
//            })
//        }
//
//        fun fetchLaunchTime(objectId: String): String {
//            val query: BmobQuery<LaunchInfo> = BmobQuery<LaunchInfo>()
//            var result: String? = null
//            query.getObject(objectId, object : QueryListener<LaunchInfo>() {
//                override fun done(p0: LaunchInfo, p1: BmobException?) {
//                    if (p1 == null) {
//                        result = p0.launchTime
//                    }
//                }
//            })
//            return result ?: "未取得结果"
//        }
//
//        //改、查...
//    }
}