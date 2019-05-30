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
        HashMap<String, LinkedHashSet<Object>> fields = new HashMap<>()


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

            //~~~~~~~~~~~~~~ Extract Info ~~~~~~~~~~~~~

            String batchTemplate = new File("/model/batch_process.py").getText('UTF-8')

            String batchPyfileString = batchTemplate.replace("--text--", allText)

            File batchPyfile = new File("/tmp/bpc.py")
            batchPyfile.text = batchPyfileString

            def batchExtract = "/usr/bin/python2.7 /tmp/bpc.py".execute()
            batchExtract.waitFor()
            String batch = batchExtract.text
            batch.split("\n").each { desc ->
                def data = desc.split("---")

                try {
                    ArrayList<String> _poi = data[1]
                            .replace("~", "")
                            .replace("_", "")
                            .replace("\\", "")
                            .replace("/", "")
                            .replaceFirst("[a-zA-Z]", "")
                            .trim()
                            .split(" ")
                    String subtotal = _poi[0]
                    String discountAmt = _poi[1] ?: "0.00"
                    String taxAmt = _poi[2] ?: "0.00"
                    String totalAmt = _poi[3] ?: "0.00"


                    if (subtotal.contains(".") && data[0].size() > 2 && subtotal.size() > 2 && !subtotal.matches(".*[a-zA-Z]+.*")) {
                        if (fields["items"] == null) {
                            if(!data[0].contains("("))
                                fields.put("items", new LinkedHashSet<>([[data[0].trim(), subtotal, discountAmt, taxAmt, totalAmt]]))
                        } else fields["items"].add([data[0].trim(), subtotal, discountAmt, taxAmt, totalAmt])

                        println data[0] + "\t--->\t" + subtotal + "\t--->\t" + data[1]
                    }
                } catch (ignored) {
                    println(ignored)
                    render ignored
                    return

                }
            }
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

//            render new HashMap<String, Object>(data: resp.json, text: texts) as JSON
        }
        catch (Exception e) {
            e.getStackTrace().each {
                println(it)
            }
            String err = "Error, " + e.getMessage()
            errors.add(err)
            render view: "index", model: [text: texts, error: errors]
        }
        render fields as JSON
    }
}
