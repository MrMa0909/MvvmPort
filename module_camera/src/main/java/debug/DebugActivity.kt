package debug

import androidx.fragment.app.Fragment
import com.cfox.module_camera.CameraMainFragment


class DebugActivity : DebugBaseActivity() {

    override fun createFragment(): Fragment {
//        val fragment = ARouter.getInstance().build(RouterPath.CAMERA_MAIN_FRAGMENT).navigation()
        return CameraMainFragment()// fragment as Fragment
    }
}