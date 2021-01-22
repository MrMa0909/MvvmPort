package com.cfox.appdemo.base

import androidx.databinding.ViewDataBinding
import com.cfox.mvvmprot.base.MpActivity

abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel<*>> : MpActivity<V, VM>() {

}