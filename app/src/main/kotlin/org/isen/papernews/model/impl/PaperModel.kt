package org.isen.papernews.model.impl

import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.logging.log4j.kotlin.Logging
import org.isen.papernews.data.PaperInformation
import org.isen.papernews.data.Source
import org.isen.papernews.data.SourceInformation

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

    private var paperDescriptionlist = listOf<Source>()

    private var selectedPaper: Source? by Delegates.observable(null){property, oldValue, newValue ->
        logger.info("selectedPaper property changed, notify observer")
        pcs.firePropertyChange(IPaperModel.DATATYPE_SEARCH, oldValue, newValue )
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
        val(request,response,result) = "https://newsapi.org/v2/everything?domains=techcrunch.com,thenextweb.com,engadget.com&apiKey=092f8ef56e6648d3a291ab8004879564".httpGet().responseObject(PaperInformation.Deserializer())
        if(manageError(response)){
            logger.info("Status Code : ${response.statusCode}")
            result.let { (si,error) ->
                if (si!=null){
                    logger.info("receive data $si")
                    this.paperInformation = si
                }
            }
        }
    }

    public override fun changeCurrentSelection(name:String){
        if(paperDescriptionlist.isEmpty()) {
            "https://newsapi.org/v2/top-headlines/sources?apiKey=092f8ef56e6648d3a291ab8004879564".httpGet()
                .responseObject(SourceInformation.Deserializer()) { request, response, result ->
                    if (manageError(response)) {
                        logger.info("Status Code : ${response.statusCode}")
                        result.let { (data, error) ->
                            paperDescriptionlist = data?.sources ?: listOf()
                            selectedPaper = paperDescriptionlist.find { it.name == name }
                        }
                    }
                }
        }
        selectedPaper = paperDescriptionlist.find { it.name == name }
    }

    private fun manageError(res: Response) : Boolean{
        if (res.statusCode != 200) {
            logger.error("error when downloading data (paper information): ${res.statusCode}")
            if(res.statusCode== 429){
                logger.error("too many request, please wait 12 hours")
                return false
            }
            else if(res.statusCode == 401){
                logger.error("invalid api key")
                return false
            }
            else if(res.statusCode == 400){
                logger.error("bad request")
                return false
            }
            else if(res.statusCode == 500){
                logger.error("internal server error")
                return false
            }
            return false
        }
        return true
    }
}