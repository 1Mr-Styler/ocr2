package insur

import grails.gorm.transactions.Transactional
import sun.misc.BASE64Decoder

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

@Transactional
class DocumentService {

    def b64ToFile(String base64, String file, String ftype) {
        // tokenize the data
        def parts = base64.tokenize(",")
        String imageString = parts[1]

        // create a buffered image
        BufferedImage image
        byte[] imageByte

        BASE64Decoder decoder = new BASE64Decoder();
        imageByte = decoder.decodeBuffer(imageString);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        image = ImageIO.read(bis);
        bis.close();

        // write the image to a file
        try {
            File outputfile = new File(file)
            ImageIO.write(image, ftype, outputfile)

            return [error: null, status: true]
        } catch (Exception e) {
            log.info(e.getMessage())
            return [error: e.getMessage(), status: false]

        }
    }

    def b64ToFile(File image, String file) {

        // write the image to a file
        try {

            new File(file).bytes = image.bytes


            return [error: null, status: true]
        } catch (Exception e) {
            log.info(e.getMessage())
            return [error: e.getMessage(), status: false]

        }
    }


    String predictOCR(String file) {
        StringBuilder pred = new StringBuilder()

        try {
            def proc = "sbs ${file} /tmp/out --oem 1".execute()
            proc.waitFor()
            def ypred = new File("/tmp/out.txt").getText('UTF-8').split("\n")

            for (String line : ypred) {
                pred.append(line)
                pred.append("\n")
            }
            return pred.toString()

        } catch (Exception e) {
            println(e)
            throw new Exception(e)
        }
    }

}
