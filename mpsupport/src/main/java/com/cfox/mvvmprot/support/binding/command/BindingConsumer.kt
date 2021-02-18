package com.cfox.mvvmprot.support.binding.command

interface BindingConsumer<T> {
    fun call(t : T)
}