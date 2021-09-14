package com.cfox.appdemo.base

import androidx.databinding.ViewDataBinding
import com.cfox.mvvmprot.base.MPFragment

abstract class BaserFragment<V : ViewDataBinding, VM : BaseViewModel<*>> : MPFragment<V, VM>() {

}