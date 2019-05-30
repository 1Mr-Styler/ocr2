package insur

import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import grails.rest.*
import grails.converters.*

class DescparserController {
    static responseFormats = ['json', 'xml']

    DocumentService documentService

    def index() {
        println(request.JSON)
        def files = new ArrayList<String>()
        request.JSON.each {
            if (it.key.toString().startsWith("doc"))
                files.add("/model/images/" + it.value.toString())
        }

        String path = grailsApplication.config.fileLocation.toString()

        String fileNameTemplate = "${System.currentTimeMillis().toString()}"
        String fileName = "${path}_desc_${fileNameTemplate}.jpg"
        String fileName2 = "${path}_desc_${fileNameTemplate}.txt"
        String fileName3 = "${path}_desc_${fileNameTemplate}.txt.bounds"

//        documentService.renameFile(document, fileName)

        ArrayList<String> texts = new ArrayList<String>()
        ArrayList<String> boundedTexts = new ArrayList<String>()
        ArrayList<String> errors = new ArrayList<String>()

        try {
            files.eachWithIndex { file, i ->
                String sm2 = documentService.predictOCR(file)
                boundedTexts.add(sm2)
            }

            //~~~~~~~~~~~~~~~ Preprocess plain text ~~~~~~~~~~~~
            texts = boundedTexts
            File textPred = new File(fileName2)
            String allText = ""
            texts.each { txt ->
                allText += txt
            }
            textPred.text = allText
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            RestBuilder rest = new RestBuilder()

            /*RestResponse resp = rest.post("http://ner2:8080") {
                accept('application/json')
                contentType('text/plain')
                json('{ "doc": \'' + fileName2 + '\', "files": ' + files.size() + '}')
            }

            render new HashMap<String, Object>(data: resp.json, text: texts) as JSON*/
        }
        catch (Exception e) {
            e.getStackTrace().each {
                println(it)
            }
            String err = "Error, " + e.getMessage()
            errors.add(err)
            render view: "index", model: [text: texts, error: errors]
        }

        render new HashMap() as JSON
    }
}
