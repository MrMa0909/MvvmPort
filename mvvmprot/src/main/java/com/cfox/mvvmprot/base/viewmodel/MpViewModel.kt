package com.cfox.mvvmprot.base.viewmodel

import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.cfox.mvvmprot.base.IBaseViewModel
import com.cfox.mvvmprot.base.model.MpModel
import com.cfox.mvvmprot.base.uievent.ActivityEventRequest
import com.cfox.mvvmprot.base.uievent.DialogEventRequest
import com.cfox.mvvmprot.base.uievent.FragmentEventRequest
import com.cfox.mvvmprot.base.uievent.IEventRequest
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

    fun runDialogEvent(dialogEventRequest: DialogEventRequest){
        uiEventLiveData.getDialogEvent().postValue(dialogEventRequest)
    }

    fun runActivityEvent(activityEventRequest: ActivityEventRequest) {
        uiEventLiveData.getActivityEvent().postValue(activityEventRequest)
    }

    fun runFragmentEvent(fragmentEventRequest: FragmentEventRequest) {
        uiEventLiveData.getFragmentEvent().postValue(fragmentEventRequest)
    }

    fun runOtherEvent(iEventRequest: IEventRequest) {
        uiEventLiveData.getOtherEvent().postValue(iEventRequest)
    }

    fun finish() {
        uiEventLiveData.getFinishEvent().call()
    }

    fun onBackPressed() {
        uiEventLiveData.getBackPressedEvent().call()
    }

    class UIEventLiveData : SingleLiveEvent<IEventRequest>() {
        private val dialogEvent = SingleLiveEvent<DialogEventRequest>()
        private var activityEvent = SingleLiveEvent<ActivityEventRequest>()
        private var fragmentEvent = SingleLiveEvent<FragmentEventRequest>()
        private var finishEvent = SingleLiveEvent<Unit>()
        private var onBackPressedEvent = SingleLiveEvent<Unit>()
        private var otherEvent = SingleLiveEvent<IEventRequest>()

        fun getDialogEvent() : SingleLiveEvent<DialogEventRequest> {
            return dialogEvent
        }

        fun getActivityEvent() : SingleLiveEvent<ActivityEventRequest> {
            return activityEvent
        }

        fun getFragmentEvent() : SingleLiveEvent<FragmentEventRequest> {
            return fragmentEvent
        }

        fun getFinishEvent() : SingleLiveEvent<Unit> {
            return finishEvent
        }

        fun getBackPressedEvent() : SingleLiveEvent<Unit> {
            return onBackPressedEvent
        }

        fun getOtherEvent() : SingleLiveEvent<IEventRequest> {
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