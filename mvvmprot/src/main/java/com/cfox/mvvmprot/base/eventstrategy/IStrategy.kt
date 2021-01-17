package com.cfox.mvvmprot.base.eventstrategy

interface IStrategy<T> {
    fun execute(data: T)
}