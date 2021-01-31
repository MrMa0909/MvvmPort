package com.cfox.mvvmprot.base.viewmodel

import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.cfox.mvvmprot.base.IBaseViewModel
import com.cfox.mvvmprot.base.model.MpModel
import com.cfox.mvvmprot.base.uievent.ActivityEvent
import com.cfox.mvvmprot.base.uievent.DialogEvent
import com.cfox.mvvmprot.base.uievent.FragmentEvent
import com.cfox.mvvmprot.base.uievent.IUIEvent
import com.cfox.mvvmprot.utils.SingleLiveEvent
import com.trello.rxlifecycle4.LifecycleProvider
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType

open class MpViewModel<M : MpModel>(@NonNull val viewModelRequest: ViewModelRequest)
    : AndroidViewModel(viewModelRequest.application) ,
    IBaseViewModel {

    protected var model : M ? = null
    private var uiEventLiveData = UIEventLiveData()
    lateinit var lifecyle : WeakReference<LifecycleProvider<*>>

    init {
        val modelTmp = viewModelRequest.getModel()
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

    fun <SVM : MpViewModel<*>> getShareViewModel() : SVM {
        return viewModelRequest.shareViewMode as SVM
    }

    override fun onCleared() {
        model?.onCleared()
    }

    /**
     * 注入RxLifecycle生命周期
     *
     * @param lifecycle
     */
    fun injectLifecycleProvider( lifecycle : LifecycleProvider<*>) {
        lifecyle =  WeakReference(lifecycle);
    }

    fun getUEvent() : UIEventLiveData {
        return uiEventLiveData
    }

    fun runDialogEvent(dialogEvent: DialogEvent){
        uiEventLiveData.getDialogEvent().postValue(dialogEvent)
    }

    fun runActivityEvent(activityEvent: ActivityEvent) {
        uiEventLiveData.getActivityEvent().postValue(activityEvent)
    }

    fun runFragmentEvent(fragmentEvent: FragmentEvent) {
        uiEventLiveData.getFragmentEvent().postValue(fragmentEvent)
    }

    fun runOtherEvent(iEventRequest: IUIEvent) {
        uiEventLiveData.getOtherEvent().postValue(iEventRequest)
    }

    fun finish() {
        uiEventLiveData.getFinishEvent().call()
    }

    fun onBackPressed() {
        uiEventLiveData.getBackPressedEvent().call()
    }

    class UIEventLiveData : SingleLiveEvent<IUIEvent>() {
        private val dialogEvent = SingleLiveEvent<DialogEvent>()
        private var activityEvent = SingleLiveEvent<ActivityEvent>()
        private var fragmentEvent = SingleLiveEvent<FragmentEvent>()
        private var finishEvent = SingleLiveEvent<Unit>()
        private var onBackPressedEvent = SingleLiveEvent<Unit>()
        private var otherEvent = SingleLiveEvent<IUIEvent>()

        fun getDialogEvent() : SingleLiveEvent<DialogEvent> {
            return dialogEvent
        }

        fun getActivityEvent() : SingleLiveEvent<ActivityEvent> {
            return activityEvent
        }

        fun getFragmentEvent() : SingleLiveEvent<FragmentEvent> {
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