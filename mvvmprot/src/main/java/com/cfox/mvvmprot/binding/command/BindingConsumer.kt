package com.cfox.mvvmprot.binding.command

interface BindingConsumer<T> {
    fun call(t : T)
}