package org.isen.papernews.ctrl

import org.isen.papernews.model.IPaperModel
import org.isen.papernews.view.IPaperView

class PaperController(val model: IPaperModel) {
    private val views = mutableListOf<IPaperView>()

    fun register(v:IPaperView, datatype:String=""){
        this.views.add(v)
        if(datatype.isEmpty()){
            this.model.register(IPaperModel.DATATYPE_SEARCH,v)
        } else {
            this.model.register(datatype, v)
        }
    }

    fun displayView(){
        views.forEach { it.display() }
    }

    fun closeView(){
        views.forEach{it.close()}
    }

    fun loadPaperInformation(){
        model.findPaperInformation()
    }

    fun selectPaper(name: String){
        model.changeCurrentSelection(name)
    }
}