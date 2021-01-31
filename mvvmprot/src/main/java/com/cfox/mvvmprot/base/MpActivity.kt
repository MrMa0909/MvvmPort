package com.cfox.mvvmprot.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.cfox.mvvmprot.app.MPort
import com.cfox.mvvmprot.base.uievent.ActivityEvent
import com.cfox.mvvmprot.base.uievent.DialogEvent
import com.cfox.mvvmprot.base.uievent.FragmentEvent
import com.cfox.mvvmprot.base.uievent.IUIEvent
import com.cfox.mvvmprot.base.eventstrategy.*
import com.cfox.mvvmprot.base.viewmodel.MpViewModel
import com.cfox.mvvmprot.base.viewmodel.ViewModelRequest
import com.cfox.mvvmprot.base.viewmodel.ViewModelFactory
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import java.lang.reflect.ParameterizedType

abstract class MpActivity<V : ViewDataBinding, VM : MpViewModel<*>> : RxAppCompatActivity(), IBaseView {
    protected lateinit var binding: V
    protected lateinit var viewModel: VM
    private var viewModelId: Int = 0

    internal fun getShareModel() : VM {
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initViewDataBinding(savedInstanceState)
        registerUIEventLiveDataCallBack()
        initData()
        initViewObservable()
    }

    private fun initViewModel() {
        var viewModeTmp = createViewModel()
        if (viewModeTmp == null) {
            val type = javaClass.genericSuperclass
            val modelClass = if (type is ParameterizedType) {
                type.actualTypeArguments[1] as Class<MpViewModel<*>>
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                MpViewModel::class.java
            }
            viewModeTmp = getViewModel(this, modelClass) as VM
        }
        viewModel = viewModeTmp
        //让ViewModel拥有View的生命周期感应
        lifecycle.addObserver(viewModel)
        viewModel.injectLifecycleProvider(this)
    }

    private fun initViewDataBinding(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState))
        viewModelId = initVariableId()
        //关联ViewModel
        binding.setVariable(viewModelId, viewModel)
        //支持LiveData绑定xml，数据改变，UI自动会更新
        binding.lifecycleOwner = this

    }

    private fun registerUIEventLiveDataCallBack() {

        viewModel.let {
            it.getUEvent().getFinishEvent().observe(this, Observer {
                finish()
            })

            it.getUEvent().getBackPressedEvent().observe(this, Observer {
                onBackPressed()
            })

            it.getUEvent().getActivityEvent().observe(this, Observer { activityEventData ->
                onActivityEvent(activityEventData)
            })

            it.getUEvent().getFragmentEvent().observe(this, Observer { fragmentEventData ->
                onFragmentEvent(fragmentEventData)
            })

            it.getUEvent().getDialogEvent().observe(this, Observer { dialogEventData ->
                onDialogEvent(dialogEventData)
            })

            it.getUEvent().getOtherEvent().observe(this, Observer { uiEventData ->
                onOtherEvent(uiEventData)
            })
        }
    }

    open fun onOtherEvent(iuiEvent : IUIEvent) {
        val otherStrategy = MPort.getConfig().getStrategyManager().getStrategy(StrategyType.OTHER)
        if (otherStrategy is IOtherStrategy) {
            otherStrategy.execute(iuiEvent)
        }
    }

    open fun onDialogEvent(dialogEvent: DialogEvent) {
        val dialogStrategy = MPort.getConfig().getStrategyManager().getStrategy(StrategyType.DIALOG)
        if (dialogStrategy is IDialogStrategy) {
            dialogStrategy.execute(dialogEvent)
        }
    }

    open fun onActivityEvent(activityEvent: ActivityEvent) {
        val activityStrategy = MPort.getConfig().getStrategyManager().getStrategy(StrategyType.ACTIVITY)
        if (activityStrategy is IActivityStrategy) {
            activityEvent.setContext(this)
            activityEvent.buildStartIntent()
            activityStrategy.execute(activityEvent)
        }
    }

    open fun onFragmentEvent(fragmentEvent : FragmentEvent) {
        val fragmentStrategy = MPort.getConfig().getStrategyManager().getStrategy(StrategyType.FRAGMENT)
        if (fragmentStrategy is IFragmentStrategy) {
            fragmentStrategy.execute(fragmentEvent)
        }
    }


    override fun initData() {}

    override fun initViewObservable() {}

    open fun createViewModel(): VM? = null

    abstract fun initVariableId(): Int

    abstract fun initContentView(savedInstanceState: Bundle?): Int

    open fun createViewModelRequest() : ViewModelRequest =
        ViewModelRequest()

    private val viewModelFactory =
        ViewModelFactory {
            val vmr = createViewModelRequest()
            vmr.application = application
            vmr
        }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
    </T> */
    private fun <T : ViewModel?> getViewModel(activity: FragmentActivity?, cls: Class<T>?): T {
        return ViewModelProviders.of(activity!!, viewModelFactory)[cls!!]
    }
}