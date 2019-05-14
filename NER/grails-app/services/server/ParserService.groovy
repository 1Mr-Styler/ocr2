package server

import org.grails.web.json.JSONObject

import java.text.SimpleDateFormat

class ParserService {

    HashMap<String, String> runNER(String text, String path, ArrayList<String> bounds, int numOfDocs) {
        HashMap<String, LinkedHashSet<Object>> fields = new HashMap<>()

        File pred = new File("${path}pred.txt")
        ArrayList<String> tabTexts = new ArrayList<>()
        bounds.each {
            tabTexts.add(it)
        }
        pred.text = text

        String dirLoc = "/model"

        def ner = "/bin/bash -c $dirLoc/ner.sh ${path}pred.txt".execute()

        ner.waitFor()

        String nerResult = ner.text

        nerResult += "\n"

        def location = "$dirLoc/location.sh".execute()
        location.waitFor()

        nerResult += location.text

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

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        //~~~~~~~~~~~~~~ Extract Bill Number ~~~~~~~~~~~~~
        String billTemplate = new File("${path}/bill.py").getText('UTF-8')

        String bt = billTemplate.replace("--text--", nerResult)
        File billpy = new File("/tmp/bill.py")
        billpy.text = bt

        def billExtract = "/usr/bin/python2.7 /tmp/bill.py".execute()
        billExtract.waitFor()
        String bill = billExtract.text
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        //~~~~~~~~~~~~~~ Extract Bill Date ~~~~~~~~~~~~~
        billTemplate = new File("${path}/bill-date.py").getText('UTF-8')

        bt = billTemplate.replace("--text--", nerResult)
        billpy = new File("/tmp/bill-date.py")
        billpy.text = bt

        billExtract = "/usr/bin/python2.7 /tmp/bill-date.py".execute()
        billExtract.waitFor()
        String billDate = billExtract.text.trim()
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        //~~~~~~~~~~~~~~ Extract GL ~~~~~~~~~~~~~
        billTemplate = new File("${path}/gl.py").getText('UTF-8')

        bt = billTemplate.replace("--text--", nerResult)
        billpy = new File("/tmp/gl.py")
        billpy.text = bt

        billExtract = "/usr/bin/python2.7 /tmp/gl.py".execute()
        billExtract.waitFor()
        String gl = billExtract.text.trim()
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        //~~~~~~~~~~~~~~ Extract Charge Type ~~~~~~~~~~~~~
        billTemplate = new File("${path}/ct.py").getText('UTF-8')

        bt = billTemplate.replace("--text--", nerResult)
        billpy = new File("/tmp/ct.py")
        billpy.text = bt

        billExtract = "/usr/bin/python2.7 /tmp/ct.py".execute()
        billExtract.waitFor()
        String charge = billExtract.text.trim()
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        //~~~~~~~~~~~~~~ Extract Amounts ~~~~~~~~~~~~~
        billTemplate = new File("${path}/amounts.py").getText('UTF-8')

        bt = billTemplate.replace("--text--", nerResult)
        billpy = new File("/tmp/amounts.py")
        billpy.text = bt

        billExtract = "/usr/bin/python2.7 /tmp/amounts.py".execute()
        billExtract.waitFor()
        String discount = billExtract.text.trim()
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        //~~~~~~~~~~~~~~ Extract Info ~~~~~~~~~~~~~
        tabTexts.each { tabText ->
            String batchTemplate = new File("${path}/batch_process.py").getText('UTF-8')

            String batchPyfileString = batchTemplate.replace("--text--", tabText)

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
                            fields.put("items", new LinkedHashSet<>([[data[0].trim(), subtotal, discountAmt, taxAmt, totalAmt]]))
                        } else fields["items"].add([data[0].trim(), subtotal, discountAmt, taxAmt, totalAmt])

                        println data[0] + "\t--->\t" + subtotal + "\t--->\t" + data[1]
                    }
                } catch (ignored) {
                    println(ignored)

                }
            }
        }
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        fields.put("patient-names", new LinkedHashSet<>([]))
        //~~~~~~~~~~~~~~ Parse Names ~~~~~~~~~~~~~
        tabTexts.each { tabText ->
            String exTemplate = new File("${path}/ex.py").getText('UTF-8')

            String exPyfileString = exTemplate.replace("--text--", tabText)

            File exPyfile = new File("/tmp/ex.py")
            exPyfile.text = exPyfileString

            def exExtract = "/usr/bin/python2.7 /tmp/ex.py".execute()
            exExtract.waitFor()
            String names = exExtract.text
            if (names.length() > 2)
                fields.put("patient-names", new LinkedHashSet<>([names]))
        }
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        //println(result)
        ArrayList<Double> amounts = [0d, 100000d]
        boolean hasAmount = false
        ArrayList<Double> _amounts = new ArrayList<>()
        ArrayList<Date> dates = new ArrayList<>()

        List<String> stuff = result.split(",,,")
        stuff.eachWithIndex { it, i ->
            if ((i + 1) != stuff.size()) {
                List<String> field = it.split("----")
                //println(field)
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
                                fields.put(field[0], new LinkedHashSet<>([f]))
                            }
                        } else {
                            fields.put(field[0], new LinkedHashSet<>([field[1]]))
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
                                //println("Stripped: ${stripped}")
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

//        //println(fields)
        if (hasAmount) {
            Collections.sort(_amounts)

            amounts[0] = _amounts.reverse()[0]
            amounts[1] = _amounts.reverse()[1]
            if (!discount.empty) {
                if (discount.contains(" ")) {
                    def totals = discount.split(" ")
                    fields.put("total-gross", new LinkedHashSet<>([totals[0]]))
                    fields.put("total-discount", new LinkedHashSet<>([totals[1]]))
                    fields.put("total-net", new LinkedHashSet<>([totals[1]]))

                } else {
                    fields.put("total-gross", new LinkedHashSet<>([amounts[0]]))
                    fields.put("total-net", new LinkedHashSet<>([amounts[1]]))
                    fields.put("total-discount", new LinkedHashSet<>([discount]))
                }
            } else {
                fields.put("total-gross", new LinkedHashSet<>([amounts[0]]))
                fields.put("total-net", new LinkedHashSet<>([amounts[1]]))
                fields.put("total-discount", new LinkedHashSet<>([amounts[0] - amounts[1]]))
            }

            fields.remove("amount")
            fields.put("amount", new LinkedHashSet<>(_amounts))
        }

        //Date
        Collections.sort(dates);
        if (dates.size() > 1) {
            fields.put("admission-date", new LinkedHashSet<>([dates[0].format("dd/MM/yyyy")]))
            fields.put("discharge-date", new LinkedHashSet<>([dates[-1].format("dd/MM/yyyy")]))
        }

        if (nric.size() > 0) {
            fields.put("nric", new LinkedHashSet<>([nric.split("\n")[0]]))
        }

        if (bill.size() > 0) {
            fields.put("bill", new LinkedHashSet<>([bill.split("\n")[0]]))
        }
        if (billDate.size() > 0) {
            fields.put("bill-date", new LinkedHashSet<>([billDate.split("\n")[0]]))
        }
        if (gl.size() > 0) {
            fields.put("gl", new LinkedHashSet<>([gl.split("\n")[0]]))
        }
        if (charge.size() > 0) {
            fields.put("charge", new LinkedHashSet<>([charge.split("\n")[0]]))
        }

        fields["date"] = null
        fields["date"] = new LinkedHashSet<>()
        dates.each {
            date ->
                fields["date"].add(date.format("dd/MM/yyyy"))
        }


        Set locations = new LinkedHashSet<>()
        fields["location"].each {
            if (it.matches(".*\\d+.*") || it.length() < 5) {
//                fields["location"].remove(it)
            } else locations.add(it)

        }
        fields.remove("location")
        if (locations.size() > 0) {
            fields.put "location", locations

        }

        Set issuer = new LinkedHashSet<>()
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
                    fields.put("name", new LinkedHashSet<>([it]))
                } else fields["name"].add(it)
            }
        }

        fields.remove("person")

        fields
    }

}
