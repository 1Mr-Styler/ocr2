package server

import grails.converters.JSON
import org.grails.web.json.JSONObject

import java.text.SimpleDateFormat
import java.util.regex.Matcher

class ParserService {

    HashMap<String, String> runNER(String text, String path, ArrayList<String> bounds, int numOfDocs) {
        println("Run NER.sh")
        HashMap<String, HashSet<Object>> fields = new HashMap<>()

        ArrayList<JSONObject> json = new ArrayList<>()
        bounds.each {
            json.add(JSON.parse(it))
        }
        File pred = new File("${path}pred.txt")
        ArrayList<String> tabTexts = new ArrayList<>()
        json.each {
            tabTexts.add(it.ParsedResults[0].ParsedText)
        }
        pred.text = text

        String dirLoc = "/model"

        def ner = "/bin/bash -c $dirLoc/ner.sh ${path}pred.txt".execute()
        println("Before NER wait")
        ner.waitFor()
        println("After NER wait")
        String nerResult = ner.text
        println("ner Size: ${nerResult.size()}")

        /*def outputStream = new StringBuffer();
        ner.waitForProcessOutput(outputStream, System.err);
        println(outputStream.toString());*/

        nerResult += "\n"

        println("Before Location wait")
        def location = "$dirLoc/location.sh".execute()
        location.waitFor()
        println("After Location wait")
        nerResult += location.text
        println("+Loc Size: ${nerResult.size()}")

//        println(nerResult)

        String template = new File("${path}/ner_template.py").getText('UTF-8')

        String g = template.replace("--text--", nerResult)
        File nerpy = new File("${path}ner.py")
        nerpy.text = g

        def reg = "python2.7 ${path}ner.py".execute()
        String result = reg.text

        //~~~~~~~~~~~~~~ Extract NRIC ~~~~~~~~~~~~~
        String nricTemplate = new File("${path}/nric.py").getText('UTF-8')

        String nt = nricTemplate.replace("--text--", nerResult)
        File nricpy = new File("/tmp/nric.py")
        nricpy.text = nt

        def nircExtract = "/usr/bin/python2.7 /tmp/nric.py".execute()
        nircExtract.waitFor()
        String nric = nircExtract.text
        println("NRIC: ${nric}")

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        //~~~~~~~~~~~~~~ Extract Info ~~~~~~~~~~~~~
        tabTexts.each {tabText ->
            String batchTemplate = new File("${path}/batch_process.py").getText('UTF-8')

            String batchPyfileString = batchTemplate.replace("--text--", tabText)

            File batchPyfile = new File("/tmp/bpc.py")
            batchPyfile.text = batchPyfileString

            def batchExtract = "/usr/bin/python2.7 /tmp/bpc.py".execute()
            batchExtract.waitFor()
            String batch = batchExtract.text
            batch.split("\n").each { desc ->
                def data = desc.split("---")

                if (data[1].contains(".") && data[0].size() > 2 && data[1].size() > 2) {
                    println("${data[0].trim()} = ${data[1]}")
                    if (fields["items"] == null) {
                        fields.put("items", new HashSet<>([[data[0].trim(), data[1]]]))
                    } else fields["items"].add([data[0].trim(), data[1]])
                }
            }
        }
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        //~~~~~~~~~~~~~~ Parse Names ~~~~~~~~~~~~~
        tabTexts.each {tabText ->
            String exTemplate = new File("${path}/ex.py").getText('UTF-8')

            String exPyfileString = exTemplate.replace("--text--", tabText)

            File exPyfile = new File("/tmp/ex.py")
            exPyfile .text = exPyfileString

            def exExtract = "/usr/bin/python2.7 /tmp/ex.py".execute()
            exExtract.waitFor()
            String names = exExtract.text
            fields.put("patient-names", new HashSet<>([names]))
        }
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        println(result)
        ArrayList<Double> amounts = [0d, 100000d]
        boolean hasAmount = false
        ArrayList<Double> _amounts = new ArrayList<>()
        ArrayList<Date> dates = new ArrayList<>()

        List<String> stuff = result.split(",,,")
        stuff.eachWithIndex { it, i ->
            if ((i + 1) != stuff.size()) {
                List<String> field = it.split("----")
                println(field)
                field[0] = field[0].replace(",", '').toLowerCase()

                if (field[1] != null) {
                    if (fields.containsKey(field[0])) {
                        if (field[1].contains("\n")) {
                            field[1].split("\n").each { f ->
                                fields[field[0]].add(f)
                            }
                        } else {
                            fields[field[0]].add(field[1])
                        }
                    } else {
                        if (field[1].contains("\n")) {
                            field[1].split("\n").each { f ->
                                fields.put(field[0], new HashSet<>([f]))
                            }
                        } else {
                            fields.put(field[0], new HashSet<>([field[1]]))
                        }
                    }

                    if (field[0] == "amount") {
                        hasAmount = true
                        field[1].split("\n").each { amount ->
                            String stripped = amount.replace("RM", "")
                                    .replace(" ", "")
                                    .replace(",", "")
                                    .replace("MYR", "")
                            Double amt
                            try {
                                amt = Double.parseDouble(stripped)
                                _amounts.add(amt)
                            } catch (NumberFormatException e) {
                                println("Stripped: ${stripped}")
                            }
                        }
                    }

                    if (field[0] == "date") {
                        try {
                            if (field[1].contains("-"))
                                dates.add(new SimpleDateFormat("dd-MM-yyyy").parse(field[1]))
                            else dates.add(new SimpleDateFormat("dd/MM/yyyy").parse(field[1]))
                        } catch (e) {
                            try {
                                dates.add(new SimpleDateFormat("E dd MMM yyyy").parse(field[1]))
                            } catch (ignored) {


                            }
                        }

                    }
                }
            }
        }

//        println(fields)
        if (hasAmount) {
            Collections.sort(_amounts)

            amounts[0] = _amounts.reverse()[0]
            amounts[1] = _amounts.reverse()[1]
            fields.put("total-gross", new HashSet<>([amounts[0]]))
            fields.put("total-net", new HashSet<>([amounts[1]]))
            fields.put("total-discount", new HashSet<>([amounts[0] - amounts[1]]))

            fields.remove("amount")
            fields.put("amount", new HashSet<>(_amounts))
        }

        //Date
        Collections.sort(dates);
        if (dates.size() > 1) {
            fields.put("admission-date", new HashSet<>([dates[0].format("dd/MM/yyyy")]))
            fields.put("discharge-date", new HashSet<>([dates[-1].format("dd/MM/yyyy")]))
        }

        if (nric.size() > 0) {
            fields.put("nric", new HashSet<>([nric.split("\n")[0]]))
        }

        fields["date"] = null
        fields["date"] = new HashSet<>()
        dates.each {
            date ->
                fields["date"].add(date.format("dd/MM/yyyy"))
        }


        Set locations = new HashSet<>()
        fields["location"].each {
            if (it.matches(".*\\d+.*") || it.length() < 5) {
//                fields["location"].remove(it)
            } else locations.add(it)

        }
        fields.remove("location")
        if (locations.size() > 0) {
            fields.put "location", locations

        }

        Set issuer = new HashSet<>()
        fields["organization"].each {
            if (it.toLowerCase().contains("berhad") || it.toLowerCase().contains("bhd")) {
                issuer.add(it)
            }
        }

        fields.remove("organization")
        fields.remove("issuer")
        if (issuer.size() > 0) {
            fields.put "organization", issuer
        }

        fields["person"].each {
            if (!it.contains("\n")) {
                if (fields["name"] == null) {
                    fields.put("name", new HashSet<>([it]))
                } else fields["name"].add(it)
            }
        }

        fields.remove("person")

        fields
    }

}
