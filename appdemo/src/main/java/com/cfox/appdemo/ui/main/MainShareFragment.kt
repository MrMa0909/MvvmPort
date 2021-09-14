package com.cfox.appdemo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.cfox.appdemo.BR
import com.cfox.appdemo.R
import com.cfox.appdemo.base.BaserFragment
import com.cfox.appdemo.databinding.FragmentMainShareBinding

class MainShareFragment : BaserFragment<FragmentMainShareBinding, MainUPViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int = R.layout.fragment_main_share

    override fun initVariableId(): Int = BR.viewModel

    override fun initData() {

        viewModel.getShareViewModel<MainShareViewModel>().shareValueLiveData.observe(this, Observer {
            binding.shareInfo.text = it
        })
    }


}