package wui

import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import org.grails.web.json.JSONObject

class MainController {

    MainService mainService

    def index() {
        render view: '/index'
    }

    def upload() {
        if (params.img == null) {
            redirect action: 'index'
            return
        }

        boolean isPDF = false

        ArrayList<HashMap<String, Object>> files = new ArrayList<>()
        request.getFiles("img").each {

            if (!it.getOriginalFilename().endsWith(".pdf")) {

                File chequeFile = mainService.toFile(it)
                String chequeFilename = chequeFile.name

                files.add(new HashMap<String, Object>(chequeFilename: chequeFilename, chequeFile: chequeFile))
            } else {
                isPDF = true
                files = mainService.pdfToFile(it)
            }
        }


        flash.hasUpload = true
        def image = new ArrayList<String>()

        files.each {
            image.add(it.chequeFilename)
        }

        flash.image = image

        RestBuilder rest = new RestBuilder()

        String req = '{ "token": "AS87YVN5RFBBVjNCLX0X3pBOEhXU1ZmSi1VY0llMjdpeUdJ", '
        files.size().times {
            int num = it + 1
            req += "\"doc${num}\":"
            req += "\"${files[it].chequeFilename}\", "

        }

        req += '}'

        println(req)

        RestResponse resp = rest.post("http://ocr2:8090/document") {
            accept('application/json')
            contentType('text/plain')
            json(req)
        }

        JSONObject data = JSON.parse(resp.text)

        println(resp.json.data)

        flash.items = data
        double sub = 0d
        data.data.items.each{
            sub += it[1].toString().replace(",", "").toDouble()
        }
        flash.sub = sub.round(2)


        chain action: "index"
    }
}
