package com.cfox.mvvmprot.base.strategy

interface IStrategy<T> {
    fun execute(event: T)
}