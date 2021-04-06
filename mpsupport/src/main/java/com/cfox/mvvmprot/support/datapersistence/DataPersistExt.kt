package com.cfox.mvvmprot.support.datapersistence

import com.cfox.mvvmprot.datapersistence.DataPersist
import com.cfox.mvvmprot.datapersistence.Reader
import com.cfox.mvvmprot.datapersistence.Writer

fun getReader(): Reader{
    return DataPersist.getReader(DefaultDataPersistStrategy.NAME)
}

fun getWriter(): Writer {
    return DataPersist.getWriter(DefaultDataPersistStrategy.NAME)
}