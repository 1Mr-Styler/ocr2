package wui

import grails.gorm.transactions.Transactional

@Transactional
class MainService {

    File toFile(def stuff) {
        String nf = "/apps/wui/grails-app/assets/images/" + stuff.getOriginalFilename()
        File convFile = new File(nf);
        stuff.transferTo(convFile);


        new File('/model/images/' + stuff.getOriginalFilename()).bytes = new File(nf).bytes


        convFile
    }

    ArrayList<HashMap<String, Object>> pdfToFile(def stuff) {

        String pdfLocation = "/model/images/" + stuff.getOriginalFilename().replace(" ", "")

        File pdf = new File(pdfLocation);
        stuff.transferTo(pdf);

        String newNameTemplate = stuff.getOriginalFilename().replace(".pdf", "-pdf").replace(" ", "")

        //xtract PDF
        def proc = "python2.7 /model/pdf2jpg.py ${pdfLocation} /model/images/${newNameTemplate}".execute()
        proc.waitFor()


        ArrayList<HashMap<String, String>> files = new ArrayList<>()
        proc.text.split("\n").each {
            files.add([chequeFilename: it])
        }

        //Copy first image
        String nf = "/apps/wui/grails-app/assets/images/${newNameTemplate}0.jpg"
        new File(nf).bytes = new File(files[0].chequeFilename).bytes


        files
    }
}
