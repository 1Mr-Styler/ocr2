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
        boolean processDesc = false
        if(params.pd != null && params.pd == "on") {
            processDesc = true;
        }

        ArrayList<HashMap<String, Object>> files = new ArrayList<>()
        ArrayList<HashMap<String, Object>> rfiles = new ArrayList<>()
        request.getFiles("img").each {

            if (!it.getOriginalFilename().endsWith(".pdf")) {

                File chequeFile = mainService.toFile(it)
                String chequeFilename = chequeFile.name

                files.add(new HashMap<String, Object>(chequeFilename: chequeFilename, chequeFile: chequeFile))
            } else {
                isPDF = true
                files = mainService.pdfToFile(it)

                if(processDesc)
                    rfiles = mainService.pdfToFile(it, true)
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

        req = '{ "token": "AS87YVN5RFBBVjNCLX0X3pBOEhXU1ZmSi1VY0llMjdpeUdJ", '
        rfiles.size().times {
            int num = it + 1
            req += "\"doc${num}\":"
            req += "\"${rfiles[it].chequeFilename}\", "

        }
        req += '}'
        println(req)

        resp = rest.post("http://ocr2:8090/descparser") {
            accept('application/json')
            contentType('text/plain')
            json(req)
        }

        JSONObject desc = JSON.parse(resp.text)

        println(desc)

        double sub = 0d
        double total = 0d
        double discs = 0d
        double tax = 0d
        data.data.items.each {
            sub += it[1].toString().replace(",", "").toDouble()
            discs += it[2].toString().replace(",", "").toDouble()
            tax += it[3].toString().replace(",", "").toDouble()
            double mTotal = it[4].toString().replace(",", "").toDouble()
            if (mTotal == 0) {
                it[4] = it[1].toString()
            }
            total += it[4].toString().replace(",", "").toDouble()
        }
        flash.items = data
        flash.description = desc.items
        flash.sub = sub.round(2)
        flash.discount = discs.round(2)
        flash.tax = tax.round(2)
        flash.total = total.round(2)


        chain action: "index"
    }
}
