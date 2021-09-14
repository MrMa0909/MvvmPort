package com.cfox.appdemo.base

import androidx.databinding.ViewDataBinding
import com.cfox.mvvmprot.base.MPActivity
import com.cfox.mvvmprot.base.viewmodel.MPSViewModel

abstract class ShareActivity<V : ViewDataBinding, VM : BaseViewModel<*>, SVM : MPSViewModel> : MPActivity<V, VM, SVM>() {

}