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
import androidx.lifecycle.ViewModelProviders
import com.cfox.mvvmprot.base.strategy.IActivityStrategy
import com.cfox.mvvmprot.base.strategy.IDialogStrategy
import com.cfox.mvvmprot.base.strategy.IFragmentStrategy
import com.cfox.mvvmprot.base.strategy.IOtherStrategy
import com.cfox.mvvmprot.base.strategy.uievent.*
import com.cfox.mvvmprot.base.viewmodel.MPViewModel
import com.cfox.mvvmprot.base.viewmodel.ViewModelParam
import com.cfox.mvvmprot.base.viewmodel.ViewModelFactory
import java.lang.reflect.ParameterizedType

abstract class MPFragment<V : ViewDataBinding, VM : MPViewModel<*>> : RxFragment() , IBaseView {

    protected lateinit var binding : V
    protected lateinit var viewModel : VM
    private var viewModelId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState) , container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding()
        //私有的ViewModel与View的契约事件回调逻辑
        registerUIEventLiveDataCallBack()
        //页面数据初始化方法
        initData()
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable()
    }

    private fun initViewModel() {
        var viewModelTmp = createViewModel()
        if (viewModelTmp == null) {
            val type = javaClass.genericSuperclass
            val modelClass = if (type is ParameterizedType) {
                type.actualTypeArguments[1] as Class<MPViewModel<*>>
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                MPViewModel::class.java
            }
            viewModelTmp = getViewModel(this, modelClass) as VM
        }
        viewModel = viewModelTmp
        //让ViewModel拥有View的生命周期感应
        lifecycle.addObserver(viewModel)
        viewModel.injectLifecycleProvider(this)
    }

    /**
     * 注入绑定
     */
    private fun initViewDataBinding() {
        viewModelId = initVariableId()
        binding.setVariable(viewModelId, viewModel)
        //支持LiveData绑定xml，数据改变，UI自动会更新
        binding.lifecycleOwner = this
    }

    private fun registerUIEventLiveDataCallBack() {
        viewModel.let {
            it.getUEvent().getFinishEvent().observe(this, Observer {
                activity?.finish()
            })

            it.getUEvent().getBackPressedEvent().observe(this, Observer {
                activity?.onBackPressed()
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

    private fun activityEvent(activityEvent: AbsActivityEvent) {
        if (activity is MPActivity<*,*,*>) {
            activityEvent.setFragment(this)
            (activity as MPActivity<*,*,*>).activityEvent(activityEvent, initActivityStrategy())
        }
    }

    private fun fragmentEvent(fragmentEvent: AbsFragmentEvent) {
        if (activity is MPActivity<*,*,*>) {
            fragmentEvent.setFragment(this)
            (activity as MPActivity<*,*,*>).fragmentEvent(fragmentEvent, initFragmentStrategy())
        }
    }

    private fun otherEvent(uiEvent : IUIEvent) {
        if (activity is MPActivity<*,*,*>) {
            (activity as MPActivity<*,*,*>).otherEvent(uiEvent, initOtherStrategy())
        }
    }

    private fun dialogEvent(dialogEvent: AbsDialogEvent) {
        if (activity is MPActivity<*,*,*>) {
            dialogEvent.setFragment(this)
            (activity as MPActivity<*,*,*>).dialogEvent(dialogEvent, initDialogStrategy())
        }
    }

    /**
     * tips:下面四个方法暂时隐藏
     */
    private fun initActivityStrategy() : IActivityStrategy<AbsActivityEvent> ? = null
    private fun initFragmentStrategy() : IFragmentStrategy<AbsFragmentEvent>? = null
    private fun initDialogStrategy() : IDialogStrategy<AbsDialogEvent>? = null
    private fun initOtherStrategy() : IOtherStrategy<IUIEvent>? = null

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
    open fun createViewModel() : VM ? = null

    open fun createViewModelRequest() : ViewModelParam = ViewModelParam()

    override fun initData() {}

    override fun initViewObservable() {}
    private val viewModelFactory = ViewModelFactory {
        val vmr = createViewModelRequest()
        activity?.let {
            vmr.application = it.application
            if (activity is MPActivity<*,*,*>) {
                (activity as MPActivity<*,*,*>).getShareModel().let { svm ->
                    vmr.shareViewMode = svm
                }
            }
        }
        vmr
    }
    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
    </T> */
    private fun <T : ViewModel?> getViewModel(fragment: Fragment?, cls: Class<T>?): T {
        return ViewModelProviders.of(fragment!!, viewModelFactory)[cls!!]
    }
}