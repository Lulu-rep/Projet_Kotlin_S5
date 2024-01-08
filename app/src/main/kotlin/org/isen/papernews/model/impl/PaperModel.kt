package org.isen.papernews.model.impl

import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.logging.log4j.kotlin.Logging
import org.isen.papernews.data.PaperInformation
import org.isen.papernews.model.IPaperModel
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import kotlin.properties.Delegates

class PaperModel:IPaperModel {
    companion object: Logging

    private val pcs = PropertyChangeSupport(this)
    private var paperInformation: PaperInformation? by Delegates.observable(null){_, oldValue, newValue ->
        logger.info("paperInformation property changed, notify observer")
        pcs.firePropertyChange(IPaperModel.DATATYPE_SEARCH, oldValue, newValue )
    }

    private var paperDescriptionlist = listOf<InfoArticle>()

    private var selectedPaper: InfoArticle? by Delegates.observable(null){property, oldValue, newValue ->
        logger.info("selectedPaper property changed, notify observer")
        pcs.firePropertyChange(IPaperModel.DATATYPE_DISPLAY, oldValue, newValue )
    }

    override fun register(datatype: String, listener: PropertyChangeListener) {
        logger.info("register new observer $listener with datatype $datatype")
        pcs.addPropertyChangeListener(datatype, listener)
    }

    override fun unregister(listener: PropertyChangeListener) {
        logger.info("unregister observer $listener")
        pcs.removePropertyChangeListener(listener)
    }

    public override fun findPaperInformation() {
        GlobalScope.launch{
            downloadPaperInformation()
        }
    }

    private suspend fun downloadPaperInformation(){
        logger.info("download paper information")
        val(request,response,result) = "https://newsapi.org/v2/everything?q=tesla&apiKey=092f8ef56e6648d3a291ab8004879564".httpGet().responseObject(PaperInformation.Deserializer())

        logger.info("Status Code : ${response.statusCode}")
        result.let { (si,error) ->
            if (si!=null){
                logger.info("receive data $si")
                this.paperInformation = si
            } else {
                logger.error("error when downloading data (paper information): $error")
            }
        }
    }

    public override fun changeCurrentSelection(name:String){
        println("changeCurrentSelection $name")
        if(paperDescriptionlist.isEmpty()) {
            "https://newsapi.org/v2/everything?q=tesla&apiKey=092f8ef56e6648d3a291ab8004879564".httpGet()
                .responseObject(PaperInformation.Deserializer()) { request, response, result ->
                    logger.info("Status Code : ${response.statusCode}")
                    result.let{(si,error)->
                        paperDescriptionlist = si?.articles ?: listOf()
                    }
                }
        }
        selectedPaper = paperDescriptionlist.find { it.title == name }
    }
}