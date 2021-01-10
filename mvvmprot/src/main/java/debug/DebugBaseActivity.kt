package debug

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.util.concurrent.atomic.AtomicInteger

open class DebugBaseActivity : AppCompatActivity() {

    companion object {
        private val nextGeneratedId = AtomicInteger(1);
        fun generateViewId() : Int {
            while (true) {
                val result = nextGeneratedId.get()
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                var newValue = result + 1
                if (newValue > 0x00FFFFFF) newValue = 1 // Roll over to 1, not 0.
                if (nextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView = getFrameLayout()
        setContentView(rootView)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(rootView.id,createFragment())
        transaction.commit();
    }



    open fun createFragment() : Fragment {
        throw  RuntimeException("Fragment is null , please overate getFragment() method create Fragment view ")
    }

    private fun getFrameLayout() : FrameLayout {
        val frameLayout = FrameLayout(this)
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        frameLayout.layoutParams = params
        frameLayout.id = generateViewId()
        return frameLayout;
    }

}