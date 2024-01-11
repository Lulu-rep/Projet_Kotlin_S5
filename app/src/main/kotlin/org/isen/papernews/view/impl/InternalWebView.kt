package org.isen.papernews.view.impl

import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.web.WebView
import javafx.stage.Stage

class InternalWebView() : Application() {
    private val internalWebViewInstances: MutableList<InternalWebView> = mutableListOf()
    private lateinit var webView: WebView

    override fun start(primaryStage: Stage) {
        // Initialize the WebView
        webView = WebView()

        // Create a scene with the WebView
        val scene = Scene(webView, 800.0, 600.0)

        // Set the scene to the primary stage
        primaryStage.title = "My JavaFX App"
        primaryStage.scene = scene

        // Check if program parameters are provided
        parameters.raw.firstOrNull()?.let { url ->
            // If URL is provided, load it into the WebView
            Platform.runLater {
                webView.engine.load(url)
            }
        }

        // Add this instance to the list
        internalWebViewInstances.add(this)

        // Show the primary stage
        primaryStage.show()
    }

    public fun display(url: String) {
        Platform.runLater {
            // Create a new instance of InternalWebView
            val internalWebView = InternalWebView()

            // Create a new stage and set the WebView as its content
            val stage = Stage()
            val newWebView = WebView()
            newWebView.engine.load(url)
            stage.scene = Scene(newWebView, 800.0, 600.0)
            stage.title = "Web Page"
            stage.show()

            // Add the new instance to the list
            internalWebViewInstances.add(internalWebView)
        }
    }
    init {
        Platform.setImplicitExit(false)
    }
    override fun stop() {
        // Remove this instance from the list when the stage is closed
        internalWebViewInstances.remove(this)
    }
}
