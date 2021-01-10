package com.cfox.module_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.cfox.lib_common.arouter.RouterPath
import com.cfox.mvvmprot.base.BaseFragment

@Route(path = RouterPath.HOME_MAIN_FRAGMENT)
class HomeMainFragment : BaseFragment<ViewDataBinding, HomeViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.home_fragment_main
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }



}