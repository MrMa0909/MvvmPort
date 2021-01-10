package com.cfox.mvvmprot.binding.command

interface BindingFunction<T> {
    fun call() : T
}