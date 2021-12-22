package com.frommetoyou.baseapp.data.model

/**
 *
 * Class that returns nothing, used for one-time events
 */
class NullModel {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}