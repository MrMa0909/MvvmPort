package com.cfox.mvvmprot.app

import android.content.Context
import androidx.annotation.NonNull

object AppContext {


    private lateinit var context : Context

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    fun init(@NonNull context: Context) {
        AppContext.context = context.applicationContext
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    fun getContext() : Context {
        return context
    }
}