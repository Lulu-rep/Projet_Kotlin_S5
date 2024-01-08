package org.isen.papernews.model.impl

import org.apache.logging.log4j.kotlin.Logging
import org.isen.papernews.data.PaperInformation
import org.isen.papernews.model.IPaperModel
import java.beans.PropertyChangeListener
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PaperModelJUnitTest {
    companion object: Logging

    @Test
    fun findPaperInformation(){
        var passObserver = false
        var data:Any?= null
        val myObserver = PropertyChangeListener{
            passObserver = true
            data = it.newValue
        }

        val myModel = PaperModel()
        myModel.register(IPaperModel.DATATYPE_SEARCH, myObserver)
        myModel.findPaperInformation()

        logger.info("Waiting data...")
        Thread.sleep(5000)
        assertTrue(passObserver)
        assertNotNull(data)
        data?.let{d:Any ->
            assertEquals(PaperInformation::class, d::class)
            (d as PaperInformation).let{
                assertEquals("ok",it.status)
                assertEquals(100, it.articles.size)
            }
        }

    }


}