package com.cfox.appdemo.base

import androidx.databinding.ViewDataBinding
import com.cfox.mvvmprot.base.MpFragment

abstract class BaserFragment<V : ViewDataBinding, VM : BaseViewModel<*>> : MpFragment<V, VM>() {

}