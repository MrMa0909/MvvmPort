package com.cfox.mvvmprot.base

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

class MpShareViewModel(@NonNull application: Application) : AndroidViewModel(application) , IBaseViewModel {
    override fun onAny(lifecycleOwner: LifecycleOwner, event: Lifecycle.Event) {}

    override fun onCreate() {}

    override fun onStart() {}

    override fun onResume() {}

    override fun onPause() {}

    override fun onStop() {}

    override fun onDestroy() {}
}