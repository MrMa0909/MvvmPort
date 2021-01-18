package com.cfox.mvvmprot.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cfox.mvvmprot.app.MPort
import com.cfox.mvvmprot.base.eventdata.ActivityEventData
import com.cfox.mvvmprot.base.eventdata.DialogEventData
import com.cfox.mvvmprot.base.eventdata.FragmentEventData
import com.cfox.mvvmprot.base.eventdata.IEventData
import com.cfox.mvvmprot.base.eventstrategy.*
import java.lang.reflect.ParameterizedType

abstract class MpFragment<V : ViewDataBinding, VM : MpViewModel<*>> : RxFragment() , IBaseView {

    private var binding : V ? = null
    private var viewModel : VM? = null
    private var viewModelId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParam()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState) , container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding()
        //私有的ViewModel与View的契约事件回调逻辑
        registerUIEventLiveDataCallBack()
        //页面数据初始化方法
        initData();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable()
    }

    /**
     * 注入绑定
     */
    fun  initViewDataBinding() {
        viewModelId = initVariableId()
        viewModel = initViewModel()
        if (viewModel == null) {
            val type = javaClass.genericSuperclass
            val modelClass = if (type is ParameterizedType) {
                type.actualTypeArguments[1] as Class<MpViewModel<*>>
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                MpViewModel::class.java
            }
            viewModel = createViewModel(this, modelClass) as VM
        }
        binding?.setVariable(viewModelId, viewModel)
        //支持LiveData绑定xml，数据改变，UI自动会更新
        binding?.lifecycleOwner = this
        //让ViewModel拥有View的生命周期感应
        viewModel?.let {
            lifecycle.addObserver(it)
        }

        viewModel?.injectLifecycleProvider(this)

    }

    private fun registerUIEventLiveDataCallBack() {
        viewModel?.let {
            it.getUEvent().getFinishEvent().observe(this,
                Observer {
                    activity?.finish()
                })

            it.getUEvent().getBackPressedEvent().observe(this, Observer {
                activity?.onBackPressed()
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

    open fun onOtherEvent(eventData: IEventData) {
        val otherStrategy = MPort.getConfig()?.getStrategyManager()?.getStrategy(StrategyType.OTHER)
        if (otherStrategy is IOtherStrategy) {
            otherStrategy.execute(eventData)
        }
    }

    open fun onDialogEvent(dialogEventData: DialogEventData) {
        val dialogStrategy = MPort.getConfig()?.getStrategyManager()?.getStrategy(StrategyType.DIALOG)
        if (dialogStrategy is IDialogStrategy) {
            dialogStrategy.execute(dialogEventData)
        }
    }

    open fun onActivityEvent(activityEventData: ActivityEventData) {
        val activityStrategy = MPort.getConfig()?.getStrategyManager()?.getStrategy(StrategyType.ACTIVITY)
        if (activityStrategy is IActivityStrategy) {
            activityEventData.setContext(requireActivity())
            activityStrategy.execute(activityEventData)
        }
    }

    open fun onFragmentEvent(fragmentEventData: FragmentEventData) {
        val fragmentStrategy = MPort.getConfig()?.getStrategyManager()?.getStrategy(StrategyType.FRAGMENT)
        if (fragmentStrategy is IFragmentStrategy) {
            fragmentStrategy.execute(fragmentEventData)
        }
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    abstract fun initContentView(inflater : LayoutInflater,  container : ViewGroup?, savedInstanceState : Bundle?) : Int

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    abstract fun initVariableId() : Int

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    open fun initViewModel() : VM ? = null

    open fun createViewModeFactory() : ViewModelProvider.Factory? = null

    override fun initParam() {}

    override fun initData() {}

    override fun initViewObservable() {}

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
    </T> */
    private fun <T : ViewModel?> createViewModel(fragment: Fragment?, cls: Class<T>?): T {
        return ViewModelProviders.of(fragment!!, createViewModeFactory())[cls!!]
    }
}