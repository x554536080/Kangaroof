package com.kuma.kumamedia.image

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.FileDescriptor

class ImageResizer() {
    companion object {
        const val TAG = "ImageResizer"
    }

    /**
     * @param reqWidth  imageView期望宽度
     * @param reqHeight imageView期望高度
     */
    fun decodeSampledBitmapFromResource(
        res: Resources, resId: Int,
        reqWidth: Int, reqHeight: Int
    ): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }

    /**
     * @param reqWidth  imageView期望宽度
     * @param reqHeight imageView期望高度
     */
    fun decodeSampledBitmapFromFileDescriptor(
        fd: FileDescriptor,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFileDescriptor(fd, null, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFileDescriptor(fd, null, options)
    }

    /**
     * @return 返回值计算方法：如果两个长度均已小于需求长度，直接返回1；
     * 否则开始折半计算，当存在其中一边已经小于需求长度时，返回此时的inSampleSize值
     */
    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        if (reqWidth == 0 || reqHeight == 0) {
            return 1
        }
        val height = options.outHeight//加载原始高度
        val width = options.outWidth//加载原始宽度
        var inSampleSize = 1


        if (height > reqHeight || width > reqWidth) {
//            val halfHeight = height / 2
//            val halfWidth = width / 2

            //注意=符号表示满足一次缩放进行条件
            while (height / 2 / inSampleSize >= reqHeight
                && width / 2 / inSampleSize >= reqWidth
            ) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}