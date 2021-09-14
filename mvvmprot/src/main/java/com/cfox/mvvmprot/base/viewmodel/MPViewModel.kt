package com.cfox.mvvmprot.base.viewmodel

import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.cfox.mvvmprot.base.IBaseViewModel
import com.cfox.mvvmprot.base.model.MpModel
import com.cfox.mvvmprot.base.strategy.uievent.AbsActivityEvent
import com.cfox.mvvmprot.base.strategy.uievent.AbsDialogEvent
import com.cfox.mvvmprot.base.strategy.uievent.AbsFragmentEvent
import com.cfox.mvvmprot.base.strategy.uievent.IUIEvent
import com.cfox.mvvmprot.utils.SingleLiveEvent
import com.trello.rxlifecycle4.LifecycleProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType

open class MPViewModel<M : MpModel>(@NonNull val viewModelParam: ViewModelParam)
    : AndroidViewModel(viewModelParam.application) ,
    IBaseViewModel {

    protected val model : M
    private var uiEventLiveData = UIEventLiveData()
    private lateinit var lifecyle : WeakReference<LifecycleProvider<*>>
    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    init {
        val modelTmp = viewModelParam.getModel()
        if (modelTmp != null) {
            model = modelTmp as M
        } else {
            model = createModel()
        }
    }

    private fun createModel() : M {
        val type = javaClass.genericSuperclass
        val modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[0] as Class<M>
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            MpModel::class.java
        }
        return modelClass.newInstance() as M
    }

    fun <SVM : MPSViewModel> getShareViewModel() : SVM {
        return viewModelParam.shareViewMode as SVM
    }

    fun addSubscribe(disposable: Disposable?) {
        disposable?.let {
            compositeDisposable.add(it)
        }
    }

    override fun onCleared() {
        model.onCleared()
        compositeDisposable.clear()
    }

    /**
     * 注入RxLifecycle生命周期
     *
     * @param lifecycle
     */
    fun injectLifecycleProvider(lifecycle : LifecycleProvider<*>) {
        lifecyle =  WeakReference(lifecycle);
    }

    fun getUEvent() : UIEventLiveData {
        return uiEventLiveData
    }

    fun sendDialogEvent(dialogEvent: AbsDialogEvent){
        uiEventLiveData.getDialogEvent().postValue(dialogEvent)
    }

    fun sendActivityEvent(activityEvent: AbsActivityEvent) {
        uiEventLiveData.getActivityEvent().postValue(activityEvent)
    }

    fun sendFragmentEvent(fragmentEvent: AbsFragmentEvent) {
        uiEventLiveData.getFragmentEvent().postValue(fragmentEvent)
    }

    fun sendOtherEvent(iEventRequest: IUIEvent) {
        uiEventLiveData.getOtherEvent().postValue(iEventRequest)
    }

    fun finish() {
        uiEventLiveData.getFinishEvent().call()
    }

    fun onBackPressed() {
        uiEventLiveData.getBackPressedEvent().call()
    }

    class UIEventLiveData : SingleLiveEvent<IUIEvent>() {
        private val dialogEvent = SingleLiveEvent<AbsDialogEvent>()
        private var activityEvent = SingleLiveEvent<AbsActivityEvent>()
        private var fragmentEvent = SingleLiveEvent<AbsFragmentEvent>()
        private var finishEvent = SingleLiveEvent<Unit>()
        private var onBackPressedEvent = SingleLiveEvent<Unit>()
        private var otherEvent = SingleLiveEvent<IUIEvent>()

        fun getDialogEvent() : SingleLiveEvent<AbsDialogEvent> {
            return dialogEvent
        }

        fun getActivityEvent() : SingleLiveEvent<AbsActivityEvent> {
            return activityEvent
        }

        fun getFragmentEvent() : SingleLiveEvent<AbsFragmentEvent> {
            return fragmentEvent
        }

        fun getFinishEvent() : SingleLiveEvent<Unit> {
            return finishEvent
        }

        fun getBackPressedEvent() : SingleLiveEvent<Unit> {
            return onBackPressedEvent
        }

        fun getOtherEvent() : SingleLiveEvent<IUIEvent> {
            return otherEvent
        }
    }


    override fun onAny(lifecycleOwner: LifecycleOwner, event: Lifecycle.Event) {

    }

    override fun onCreate() {

    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {

    }
}