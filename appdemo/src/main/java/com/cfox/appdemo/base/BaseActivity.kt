package com.cfox.appdemo.base

import androidx.databinding.ViewDataBinding
import com.cfox.mvvmprot.base.MpActivity
import com.cfox.mvvmprot.base.MpViewModel

abstract class BaseActivity<V : ViewDataBinding, VM : MpViewModel<*>> : MpActivity<V, VM>() {
}