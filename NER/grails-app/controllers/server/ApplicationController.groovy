package server

import grails.converters.JSON
import grails.core.GrailsApplication
import grails.plugins.GrailsPluginManager
import grails.plugins.PluginManagerAware

class ApplicationController implements PluginManagerAware {

    GrailsApplication grailsApplication
    GrailsPluginManager pluginManager
    ParserService parserService

    def index() {
        println("Path's")
        String path = "/model/"

        String fl = request.JSON.doc ?: params.doc
        println("FileLocation: $fl")

        //Load Doc
        String pred = new File(fl).getText('UTF-8')
        println("Pred: ${pred.take(20)}")

        render parserService.runNER(pred, path) as JSON
        return
    }
}
