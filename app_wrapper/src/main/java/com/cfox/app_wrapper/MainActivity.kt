package com.cfox.app_wrapper

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.cfox.lib_common.arouter.RouterPath
import com.cfox.mvvmprot.base.BaseActivity

class MainActivity : BaseActivity<ViewDataBinding, MainViewModel>() {

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initViewModel(): MainViewModel? {
        return super.initViewModel()
    }

    override fun initData() {
        val fragment = ARouter.getInstance().build(RouterPath.HOME_MAIN_FRAGMENT).navigation()

        if (fragment is Fragment) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.rootView, fragment)
            transaction.commit();
        }
    }

}
