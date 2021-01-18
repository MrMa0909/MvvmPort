package com.cfox.mvvmprot.base

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.cfox.mvvmprot.base.eventdata.ActivityEventData
import com.cfox.mvvmprot.base.eventdata.DialogEventData
import com.cfox.mvvmprot.base.eventdata.FragmentEventData
import com.cfox.mvvmprot.base.eventdata.IEventData
import com.cfox.mvvmprot.utils.SingleLiveEvent
import com.trello.rxlifecycle4.LifecycleProvider
import java.lang.ref.WeakReference

open class MpViewModel<M : MpModel>(@NonNull application: Application) : AndroidViewModel(application) , IBaseViewModel {

    private var model : M? = null
    private var uiEventLiveData = UIEventLiveData()
    lateinit var lifecyle : WeakReference<LifecycleProvider<*>>

    constructor(application: Application, m: M): this (application) {
        model = m
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

    fun runDialogEvent(dialogEventData: DialogEventData){
        uiEventLiveData.getDialogEvent().postValue(dialogEventData)
    }

    fun runActivityEvent(activityEventData: ActivityEventData) {
        uiEventLiveData.getActivityEvent().postValue(activityEventData)
    }

    fun runFragmentEvent(fragmentEventData: FragmentEventData) {
        uiEventLiveData.getFragmentEvent().postValue(fragmentEventData)
    }

    fun runOtherEvent(iEventData: IEventData) {
        uiEventLiveData.getOtherEvent().postValue(iEventData)
    }

    fun finish() {
        uiEventLiveData.getFinishEvent().call()
    }

    fun onBackPressed() {
        uiEventLiveData.getBackPressedEvent().call()
    }

    class UIEventLiveData : SingleLiveEvent<IEventData>() {
        private val dialogEvent = SingleLiveEvent<DialogEventData>()
        private var activityEvent = SingleLiveEvent<ActivityEventData>()
        private var fragmentEvent = SingleLiveEvent<FragmentEventData>()
        private var finishEvent = SingleLiveEvent<Unit>()
        private var onBackPressedEvent = SingleLiveEvent<Unit>()
        private var otherEvent = SingleLiveEvent<IEventData>()

        fun getDialogEvent() : SingleLiveEvent<DialogEventData> {
            return dialogEvent
        }

        fun getActivityEvent() : SingleLiveEvent<ActivityEventData> {
            return activityEvent
        }

        fun getFragmentEvent() : SingleLiveEvent<FragmentEventData> {
            return fragmentEvent
        }

        fun getFinishEvent() : SingleLiveEvent<Unit> {
            return finishEvent
        }

        fun getBackPressedEvent() : SingleLiveEvent<Unit> {
            return onBackPressedEvent
        }

        fun getOtherEvent() : SingleLiveEvent<IEventData> {
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