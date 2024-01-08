package org.isen.papernews.view.impl

import org.apache.logging.log4j.kotlin.Logging
import org.isen.papernews.ctrl.PaperController
import org.isen.papernews.data.InfoArticle
import org.isen.papernews.data.PaperInformation
import org.isen.papernews.model.IPaperModel
import org.isen.papernews.view.IPaperView
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.beans.PropertyChangeEvent
import javax.swing.DefaultComboBoxModel
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

class PaperSearchView(val ctrl:PaperController, title:String = "PaperNews"): IPaperView,ActionListener {
    companion object: Logging

    private val myFrame:JFrame
    private val labelPaperInformation_title = JLabel()
    private val labelPaperInformation_author = JLabel()
    private val labelPaperInformation_description = JLabel()
    private val labelPaperInformation_url = JLabel()
    private val labelPaperInformation_urlToImage = JLabel()
    private val labelPaperInformation_publishedAt = JLabel()
    private val labelPaperInformation_source_name = JLabel()


    private var PaperList:JComboBox<InfoArticle> = JComboBox<InfoArticle>().apply { addActionListener(this@PaperSearchView)
        renderer = PaperInfoCellRenderer()
    }

    init{
        ctrl.register(this, IPaperModel.DATATYPE_SEARCH)
        myFrame= JFrame("Paper search view")
        myFrame.isVisible = false
        myFrame.contentPane.add(MakeGui())
        myFrame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        myFrame.setBounds(100, 100, 450, 300)
    }


    private fun createPaperComboBox(): JPanel{
        val contentPane = JPanel()
        contentPane.layout = BorderLayout()
        contentPane.add(JLabel("Paper list"), BorderLayout.WEST)
        PaperList.border = EmptyBorder(5, 5, 5, 5)
        contentPane.add(PaperList, BorderLayout.CENTER)
        return contentPane
    }

    private fun createPaperInformationPanel():JPanel{
        val contentPane = JPanel()
        contentPane.layout = GridLayout(7,2)
        contentPane.preferredSize = Dimension(250,100)

        contentPane.add(JLabel("Titre : "))
        contentPane.add(labelPaperInformation_title)
        contentPane.add(JLabel("Auteur : "))
        contentPane.add(labelPaperInformation_author)
        contentPane.add(JLabel("Description : "))
        contentPane.add(labelPaperInformation_description)
        contentPane.add(JLabel("Url : "))
        contentPane.add(labelPaperInformation_url)
        contentPane.add(JLabel("Url pour image : "))
        contentPane.add(labelPaperInformation_urlToImage)
        contentPane.add(JLabel("Publié le : "))
        contentPane.add(labelPaperInformation_publishedAt)
        contentPane.add(JLabel("Source : "))
        contentPane.add(labelPaperInformation_source_name)
        return contentPane
    }

    private fun MakeGui(): JPanel {
        val contentPane = JPanel()
        contentPane.layout = BorderLayout()
        contentPane.add(createPaperComboBox(), BorderLayout.NORTH)
        contentPane.add(createPaperInformationPanel(), BorderLayout.WEST)
        return contentPane
    }


    override fun display() {
        logger.info("PaperSearchView : display")
        myFrame.isVisible = true
    }

    override fun close() {
        logger.info("PaperSearchView : close")
        this.ctrl.closeView()
    }

    override fun propertyChange(evt: PropertyChangeEvent?) {
        println(evt?.newValue)
        if(evt?.newValue is PaperInformation){
            logger.info("receive PaperInformation data")
            PaperList.model = DefaultComboBoxModel<InfoArticle>((evt.newValue as PaperInformation).articles.toTypedArray())
        }
        else if(evt?.newValue is InfoArticle){
            logger.info("receive InfoArticle data")
            (evt.newValue as InfoArticle).let{
                labelPaperInformation_title.text = it.title
                labelPaperInformation_author.text = it.author
                labelPaperInformation_description.text = it.description
                labelPaperInformation_url.text = it.url.toString()
                labelPaperInformation_urlToImage.text = it.urlToImage.toString()
                labelPaperInformation_publishedAt.text = it.publishedAt.toString()
                labelPaperInformation_source_name.text = it.source.name
            }
        }
        else {
            logger.info("unknown data")
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        if (e?.source is JComboBox<*>){
            println("Click on combo with index ${PaperList.selectedIndex} and\" +\" value ${PaperList.selectedItem}")
            logger.info("Click on combo with index ${PaperList.selectedIndex} and" +" value ${PaperList.selectedItem}")

            this.ctrl.selectPaper(PaperList.model.getElementAt(PaperList.selectedIndex).title)
        }

    }

}