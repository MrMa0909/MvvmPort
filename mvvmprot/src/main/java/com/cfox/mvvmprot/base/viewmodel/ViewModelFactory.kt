package com.cfox.mvvmprot.base.viewmodel

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.InvocationTargetException

class ViewModelFactory(val vmrMethod : () -> ViewModelRequest) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        fun  checkApplication(activity : Activity) : Application {
            val application = activity.application
            if (application == null) {
                throw  IllegalStateException("Your activity/fragment is not yet attached to "
                        + "Application. You can't request ViewModel before onCreate call.")
            }
            return application
        }

        fun checkActivity(fragment: Fragment): Activity {
            val activity = fragment.activity
            if (activity == null) {
                throw IllegalStateException("Can't create ViewModelProvider for detached fragment")
            }
            return activity
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (AndroidViewModel::class.java.isAssignableFrom(modelClass)) {
            //noinspection TryWithIdenticalCatches
            try {
                return modelClass.getConstructor(ViewModelRequest::class.java).newInstance(vmrMethod.invoke())
            } catch (e : NoSuchMethodException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e : IllegalAccessException ) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e : InstantiationException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e : InvocationTargetException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            }
        }
        return super.create(modelClass)
    }

}