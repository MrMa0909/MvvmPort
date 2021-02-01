package com.cfox.mvvmprot.base.strategy

internal sealed class StrategyType(private val type: Int) {

    open fun getType() : Int {
        return type
    }

    companion object {
        private const val TYPE_ACTIVITY = 1
        private const val TYPE_FRAGMENT = 2
        private const val TYPE_DIALOG = 3
        private const val TYPE_OTHER = 4
    }

    object ACTIVITY : StrategyType(
        TYPE_ACTIVITY
    )
    object FRAGMENT : StrategyType(
        TYPE_FRAGMENT
    )
    object DIALOG : StrategyType(
        TYPE_DIALOG
    )
    object OTHER : StrategyType(
        TYPE_OTHER
    )

}