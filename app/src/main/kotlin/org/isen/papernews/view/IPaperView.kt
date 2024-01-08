package org.isen.papernews.view

import java.beans.PropertyChangeListener

interface IPaperView:PropertyChangeListener {
    fun display()
    fun close()
}