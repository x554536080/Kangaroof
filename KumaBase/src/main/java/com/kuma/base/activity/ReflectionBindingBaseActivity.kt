package com.kuma.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.kuma.base.KumaBaseActivity
import java.lang.reflect.ParameterizedType

abstract class ReflectionBindingBaseActivity<VB : ViewBinding> : KumaBaseActivity() {
    lateinit var mBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = javaClass.genericSuperclass
        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<VB>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
        mBinding = method.invoke(null, layoutInflater) as VB
        setContentView(mBinding.root)

        /**from SumTea**/
//        val type = javaClass.genericSuperclass
//        val vbClass: Class<VB> = type!!.saveAs<ParameterizedType>().actualTypeArguments[0].saveAs()
//        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
//        mBinding = method.invoke(this, layoutInflater)!!.saveAsUnChecked()
//        setContentView(mBinding.root)
    }
}