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

        ArrayList<HashMap<String, Object>> files = new ArrayList<>()
        request.getFiles("img").each {
            File chequeFile = mainService.toFile(it)
            String chequeFilename = chequeFile.name

            files.add(new HashMap<String, Object>(chequeFilename: chequeFilename, chequeFile: chequeFile))
        }


        flash.hasUpload = true
        flash.image = files[0].chequeFilename

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


        chain action: "index"
    }
}
