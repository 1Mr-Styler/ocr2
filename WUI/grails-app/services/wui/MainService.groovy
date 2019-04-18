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
}
