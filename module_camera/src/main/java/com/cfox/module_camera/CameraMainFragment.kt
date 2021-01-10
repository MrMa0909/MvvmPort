package com.cfox.module_camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.cfox.lib_common.arouter.RouterPath
import com.cfox.mvvmprot.base.BaseFragment
import com.cfox.module_camera.databinding.CameraFragmentMainBinding

@Route(path = RouterPath.CAMERA_MAIN_FRAGMENT)
class CameraMainFragment : BaseFragment<CameraFragmentMainBinding, CameraViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.camera_fragment_main
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

}