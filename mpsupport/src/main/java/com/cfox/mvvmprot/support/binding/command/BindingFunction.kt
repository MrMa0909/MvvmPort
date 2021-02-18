package com.cfox.mvvmprot.support.binding.command

interface BindingFunction<T> {
    fun call() : T
}