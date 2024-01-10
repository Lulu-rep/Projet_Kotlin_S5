package org.isen.papernews.view.impl

import org.apache.logging.log4j.kotlin.Logging
import org.isen.papernews.ctrl.PaperController
import org.isen.papernews.model.IPaperModel
import org.isen.papernews.view.IPaperView
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.beans.PropertyChangeEvent
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

class PaperDisplayView(val ctrl: PaperController, title:String = "PaperNews"): IPaperView, ActionListener {

    companion object: Logging

    private val myFrame: JFrame

    init{
        ctrl.register(this, IPaperModel.DATATYPE_SEARCH)
        myFrame= JFrame("Paper display view")
        myFrame.isVisible = false
        myFrame.contentPane.add(MakeGui())
        myFrame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        myFrame.setBounds(100, 100, 450, 300)
    }

    fun MakeGui(): JPanel {
        val panel = JPanel()
        panel.layout = GridLayout(0, 1, 0, 0)
        panel.border = EmptyBorder(5, 5, 5, 5)
        return panel
    }
    override fun display() {
        logger.info("display Displayview")
        myFrame.isVisible = true
    }

    override fun close() {
        logger.info("close Displayview")
        myFrame.isVisible = false
    }

    override fun propertyChange(evt: PropertyChangeEvent?) {
        TODO("Not yet implemented")
    }

    override fun actionPerformed(e: ActionEvent?) {
        TODO("Not yet implemented")
    }
}