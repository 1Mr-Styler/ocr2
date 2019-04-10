package insur


import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

class DocumentController {
    DocumentService documentService

    static responseFormats = ['json', 'xml']

    def save() {
        print("[${new Date().toString()}]  -->  ")
        println(request.JSON.dump())
        log.info("Request Parameters: ${request.JSON.dump()}")

        if (request.JSON.token == null || request.JSON.token != 'AS87YVN5RFBBVjNCLX0X3pBOEhXU1ZmSi1VY0llMjdpeUdJ') {
            render view: 'index', model: [error: ["Invalid token"]]
            return
        }
        if (request.JSON.type == null) {
            render view: 'index', model: [error: ["No Insurance model specified"]]
            return
        }

        String view = request.JSON.type
        view = view.toLowerCase()

        if (request.JSON.base64 == null) {
            render view: view, model: [error: ["No document specified"]]
            return
        }
        ArrayList<String> base64 = request.JSON.base64
        int numOfImg = base64.size()

        if (request.JSON.ftype == null) {
            render view: view, model: [error: ["File type not specified"]]
            log.info("Base64 Image without specified filetype")
            return
        }
        String ftype = request.JSON.ftype.toString()

        boolean ext = ['jpeg', 'jpg', 'png'].any { extension ->
            ftype == extension
        }
        if (!ext) {
            log.warn("File extension no supported")
            render view: 'index', model: [error: ['File extension not supported']]
        }

        if (request.JSON.conf == null) {
            render view: view, model: [error: ["No configuration file specified"]]
            return
        }

        ArrayList<String> pattern = new ArrayList<String>()
        if (numOfImg > 1) {
            (1..numOfImg).each {
                String conf = request.JSON.conf.toString() + it.toString()
                pattern.add(loadConfig(conf, view).toString())
            }
        } else
            pattern.add(loadConfig(request.JSON.conf.toString(), view).toString())

        if (pattern.contains(false)) {
            render view: view, model: [error: ["Invalid Config file specified"]]
            return
        }

        String path = grailsApplication.config.fileLocation.toString()
        ArrayList<String> files = new ArrayList<String>()

        base64.each { b64 ->
            String file = "${path}${request.JSON.conf.toString()}_${System.currentTimeMillis().toString()}.${ftype}"

            def convert = documentService.b64ToFile(b64, file, ftype)
            if (!convert.status) {
                render view: view, model: [error: convert.error]
            }
            files.add(file)

//            println("[Filename]  -->  " + file)
//            log.info("[Filename]  -->  " + file)
        }

        ArrayList<String> errors = new ArrayList<String>()
        Map<String, String> fields = new HashMap<String, String>()
        ArrayList<String> texts = new ArrayList<String>()

        ArrayList<String> populate = documentService.populate(view)

        try {

            files.eachWithIndex { file, i ->

                String template = new File("${path}config/${view}/template.py").getText('UTF-8')
                String pred = documentService.predict(file, path)
                String result = documentService.prepareFiles(template, path, pred, pattern[i])
                texts.add(pred)

                if (result.empty) {
                    errors.add("no-match")
                }
//                println(result)
                def csv = result.split(",,,")

                println("-----> ${i}")

                populate.eachWithIndex { field, index ->
                    println("POP---> ${field} --> ${csv[index]}")

                    if (fields.containsKey(field) && fields[field].empty) {
                        fields.replace(field, csv[index])
                    } else if (!fields.containsKey(field)) {
                        fields.put(field, csv[index])
                    }
                }

            }

            log.info(texts)
            println(fields.dump())

            switch (view) {
                case 'motor': render view: 'motor', model: [
                        text           : texts,
                        name           : fields['name'],
                        policy_number  : fields['policy_number'],
                        address        : fields['address'],
                        nric           : fields['nric'],
                        occupation     : fields['occupation'],
                        expiry_date    : fields['expiry_date'],
                        reg_date       : fields['reg_date'],
                        year           : fields['year'],
                        make           : fields['make'],
                        seater         : fields['seater'],
                        coverage_amount: fields['coverage_amount'],
                        premium        : fields['premium'],
                ]
                    break
                case 'life': render view: 'life', model: [
                        text           : texts,
                        name           : fields['name'],
                        policy_number  : fields['policy_number'],
                        address        : fields['address'],
                        nric           : fields['nric'],
                        occupation     : fields['occupation'],
                        expiry_date    : fields['expiry_date'],
                        reg_date       : fields['reg_date'],
                        beneficiary    : fields['beneficiary'],
                        mop            : fields['mop'],
                        type           : fields['type'],
                        coverage_amount: fields['coverage_amount'],
                        premium        : fields['premium'],
                ]
                    break
                case 'health': render view: 'health', model: [
                        text         : texts,
                        name         : fields['name'],
                        policy_number: fields['policy_number'],
                        address      : fields['address'],
                        occupation   : fields['occupation'],
                        reg_date     : fields['reg_date'],
                        expiry_date  : fields['expiry_date'],
                        beneficiary  : fields['beneficiary'],
                        coverage_type: fields['coverage_type'],
                        premium      : fields['premium'],
                ]
                    break
                default: render view: 'index', model: [
                        text           : texts,
                        name           : fields['name'],
                        policy_number  : fields['policy_number'],
                        expiry_date    : fields['expiry_date'],
                        coverage_amount: fields['coverage_amount'],
                        premium        : fields['premium'],
                ]
            }

        }
        catch (Exception e) {
            String err = "Error, " + e.getMessage()
            errors.add(err)
            render view: view, model: [text: texts, error: errors]
        }

    }

    def loadConfig(String configName, String type) {
        String path = grailsApplication.config.fileLocation.toString()

        try {
            List<String> conf = Files.lines(Paths.get(path + "config/${type}/" + configName + '.rgx'))
                    .collect(Collectors.toList())

            return conf[0]

        } catch (Exception e) {
            log.warn(e.getMessage())
            return false
        }

    }

}
