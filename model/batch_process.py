# coding=utf8
# the above tag defines encoding for this document and is for Python 2.x compatibility

import re

regex = (r'''((?=[A-Z])[A-Z\s\n]+)\s+\n?(?P<valuable>.+)?([0-9\.,]+)''')

test_str = ('''--text--''')

matches = re.finditer(regex, test_str, re.MULTILINE)
data = ""
for matchNum, match in enumerate(matches):
    matchNum = matchNum + 1
    # print match.group(0).strip()
    m = match.group(0).strip().split("\n")
    # print m
    if len(m) == 1:
        m = m[0].split("\t")
        if len(m) == 2:
            print("{0}---{1}".format(m[0], m[1]))
            # print "<<--------------------------------------------------------------------------->>"
        elif len(m) == 1:
            info = re.split("([0-9.,]+)", m[0].strip())
            print("{0}---{1}".format(info[0].strip(), ''.join(info[1:])))
    if len(m) == 2:
        if "doctor" in m[1].lower():
            try:
                doctor = m[1].split(":")
                info = re.split("([0-9.,]+)", doctor[1].strip())
                print("{0}---{1}".format(info[0], info[1]))
            except Exception as e:
                pass
        else:
            print("{0}---{1}".format(m[0], m[1]))
        # print "<<--------------------------------------------------------------------------->>"
    if len(m) == 3:
        if "doctor" in m[2].lower():
            try:
                doctor = m[2].split(":")
                info = re.split("([0-9.,]+)", doctor[1].strip())
                print("{0}---{1}".format(info[0], info[1]))
            except Exception as e:
                pass
        else:
            print("{0}---{1}".format(m[0], m[2]))
        # print "<<--------------------------------------------------------------------------->>"
    if len(m) == 5:
        print("{0}---{1}".format(m[2], m[4]))
        # print "<<--------------------------------------------------------------------------->>"
    if len(m) > 5:
        print("{0}---{1}".format(m[len(m)-2], m[-1]))

# Note: for Python 2.7 compatibility, use ur"" to prefix the regex and u"" to prefix the test string and substitution.
