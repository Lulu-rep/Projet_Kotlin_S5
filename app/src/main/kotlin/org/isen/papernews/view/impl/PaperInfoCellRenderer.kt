package org.isen.papernews.view.impl

import org.isen.papernews.data.InfoArticle
import java.awt.Color
import java.awt.Component
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.ListCellRenderer

class PaperInfoCellRenderer:JLabel(), ListCellRenderer<InfoArticle> {

    init{
        super.setOpaque(true)
    }

    override fun getListCellRendererComponent(
        list: JList<out InfoArticle>?,
        value: InfoArticle?,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        if(isSelected){
            setBackground(Color.LIGHT_GRAY)
            setForeground(list?.getForeground())
        } else{
            setBackground(list?.getBackground())
            setForeground(list?.getForeground())
        }

        if(value != null){
            setText("${value.title} - ${value.publishedAt}")
        }
        return this
    }

}