package com.cfox.mvvmprot.datapersistence

import com.cfox.mvvmprot.app.MPort

object DataPersist  {
    private val writer = Writer()
    private val reader = Reader()

    fun getWriter(name : String) : Writer {
        writer.setStrategy(getStrategy(name))
        return writer
    }

    fun getReader(name : String) : Reader {
        reader.setStrategy(getStrategy(name))
        return reader
    }

    private fun getStrategy(name: String): DataPersistStrategy {
        return MPort.getConfig().getDataPersistManager().getStrategy(name)
    }
}