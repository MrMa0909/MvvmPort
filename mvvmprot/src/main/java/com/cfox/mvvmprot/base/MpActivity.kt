package com.cfox.mvvmprot.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.cfox.mvvmprot.app.MPort
import com.cfox.mvvmprot.base.strategy.*
import com.cfox.mvvmprot.base.strategy.uievent.*
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
                activityEvent(activityEventData)
            })

            it.getUEvent().getFragmentEvent().observe(this, Observer { fragmentEventData ->
                fragmentEvent(fragmentEventData)
            })

            it.getUEvent().getDialogEvent().observe(this, Observer { dialogEventData ->
                dialogEvent(dialogEventData)
            })

            it.getUEvent().getOtherEvent().observe(this, Observer { uiEventData ->
                otherEvent(uiEventData)
            })
        }
    }


    internal fun otherEvent(uiEvent : IUIEvent) {
        if (!onOtherEvent(uiEvent)) {
            val otherStrategy = MPort.getConfig().getStrategyManager().getStrategy<IUIEvent>(StrategyType.OTHER)
            if (otherStrategy is IOtherStrategy) {
                otherStrategy.execute(uiEvent)
            }
        }
    }

    internal fun dialogEvent(dialogEvent: DialogEvent) {
        if (!onDialogEvent(dialogEvent)) {
            val dialogStrategy = MPort.getConfig().getStrategyManager().getStrategy<DialogEvent>(StrategyType.DIALOG)
            if (dialogStrategy is IDialogStrategy) {
                dialogStrategy.execute(dialogEvent)
            }
        }
    }

    internal fun activityEvent(activityEvent: ActivityEvent) {
        if (!onActivityEvent(activityEvent)) {
            val activityStrategy = MPort.getConfig().getStrategyManager().getStrategy<ActivityEvent>(StrategyType.ACTIVITY)
            if (activityStrategy is IActivityStrategy) {
                activityEvent.setContext(this)
                activityStrategy.execute(activityEvent)
            }
        }
    }

    internal fun fragmentEvent(fragmentEvent : FragmentEvent) {
        if (fragmentEvent is OrigFragmentEvent) {
            fragmentEvent.setActivityName(this.javaClass.simpleName)
            fragmentEvent.setFragmentManager(supportFragmentManager)
        }
        if (!onFragmentEvent(fragmentEvent)) {
            val fragmentStrategy = MPort.getConfig().getStrategyManager().getStrategy<FragmentEvent>(StrategyType.FRAGMENT)
            if (fragmentStrategy is IFragmentStrategy) {
                fragmentStrategy.execute(fragmentEvent)
            }
        }

    }

    open fun onOtherEvent(iuiEvent : IUIEvent) : Boolean = false

    open fun onDialogEvent(dialogEvent: DialogEvent) : Boolean = false

    open fun onActivityEvent(activityEvent: ActivityEvent) : Boolean = false

    open fun onFragmentEvent(fragmentEvent : FragmentEvent): Boolean = false


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

    override fun onDestroy() {
        super.onDestroy()
        val fragmentStrategy = MPort.getConfig().getStrategyManager().getStrategy<FragmentEvent>(StrategyType.FRAGMENT)
        if (fragmentStrategy is IFragmentStrategy) {
            fragmentStrategy.onActivityDestroy(this.javaClass.simpleName)
        }
    }
}