package insur

import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse

class DocumentController {
    DocumentService documentService

    static responseFormats = ['json', 'xml']

    def save() {
        print("[${new Date().toString()}]  -->  ")

        def dataMap = params
        dataMap.each {
            println(it)
        }
        println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
        log.info("Request Parameters: ${dataMap.dump()}")

        if (dataMap.token == null || dataMap.token != 'AS87YVN5RFBBVjNCLX0X3pBOEhXU1ZmSi1VY0llMjdpeUdJ') {
            render view: 'index', model: [error: ["Invalid token"]]
            return
        }

        ArrayList<String> base64
        def document

        base64 = new ArrayList<>()
        document = request.getFile('doc1')
        def document2 = request.getFile('doc2')
        def document3 = request.getFile('doc3')
        base64.add("")

        int numOfImg = 1


        String path = grailsApplication.config.fileLocation.toString()
        ArrayList<String> files = new ArrayList<String>()

        String fileNameTemplate = "${System.currentTimeMillis().toString()}"
        String fileName = "${path}_${fileNameTemplate}.jpg"
        String fileName2 = "${path}_${fileNameTemplate}.txt"
        String fileName3 = "${path}_${fileNameTemplate}.txt.bounds"
//        String fileName2 = "${path}_1555369770802.txt"

        documentService.b64ToFile(document, fileName)

        files.add(fileName)

        if (document2 != null) {
            fileName = "${path}_doc2_${System.currentTimeMillis().toString()}.jpg"
            documentService.b64ToFile(document2, fileName)
            files.add(fileName)
        }

        if (document3 != null) {
            fileName = "${path}_doc3_${System.currentTimeMillis().toString()}.jpg"
            documentService.b64ToFile(document3, fileName)
            files.add(fileName)
        }


        ArrayList<String> texts = new ArrayList<String>()
        ArrayList<String> boundedTexts = new ArrayList<String>()
        ArrayList<String> errors = new ArrayList<String>()


        try {
            println("Files: ${files.size()}")
            files.eachWithIndex { file, i ->
                String pred = documentService.predict(file, path)
                String sm2 = documentService.predict(file, path, "2")
//                String pred = new File("${path}/_1555369770802.txt").getText('UTF-8')
                texts.add(pred)
                boundedTexts.add(sm2)
            }

            //~~~~~~~~~~~~~~~ Preprocess plain text ~~~~~~~~~~~~
            File textPred = new File(fileName2)
            String allText = ""
            texts.each { txt ->
                allText += txt
            }
            textPred.text = allText
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            //~~~~~~~~~~~~~~~ Preprocess bounded text ~~~~~~~~~~~~
            boundedTexts.eachWithIndex { txt, i ->
                File boundsPred = new File(fileName3 + i.toString())
                boundsPred.text = txt
            }
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            RestBuilder rest = new RestBuilder()

            RestResponse resp = rest.post("http://ner2:8080") {
                accept('application/json')
                contentType('text/plain')
                json('{ "doc": \'' + fileName2 + '\', "files": ' + files.size() + '}')
            }

            render new HashMap<String, Object>(data: resp.json, text: texts) as JSON
        }
        catch (Exception e) {
            e.getStackTrace().each {
                println(it)
            }
            String err = "Error, " + e.getMessage()
            errors.add(err)
            render view: "index", model: [text: texts, error: errors]
        }

    }


}
