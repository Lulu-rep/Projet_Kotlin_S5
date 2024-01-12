package org.isen.papernews.model

import java.beans.PropertyChangeListener

interface IPaperModel {
    companion object{
        const val DATATYPE_SEARCH = "search"
    }

    //fonction register pour enregistrer les vues auprès du model
    fun register (datatype: String, listener: PropertyChangeListener)

    fun unregister (listener: PropertyChangeListener)

    fun findPaperInformation()

    fun changeCurrentSelection(name: String)
}