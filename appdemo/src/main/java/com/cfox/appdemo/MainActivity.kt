package com.cfox.appdemo

import android.os.Bundle
import com.cfox.appdemo.base.BaseActivity
import com.cfox.appdemo.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }
}