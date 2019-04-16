# coding=utf8
# the above tag defines encoding for this document and is for Python 2.x compatibility

import re

regex = (r'''((?=[A-Z])[A-Z\s\n]+)\s+\n?([0-9\.,]+)''')

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
    if len(m) == 2:
        print("{0}---{1}".format(m[0], m[1]))
        # print "<<--------------------------------------------------------------------------->>"
    if len(m) == 3:
        print("{0}---{1}".format(m[0], m[2]))
        # print "<<--------------------------------------------------------------------------->>"
    if len(m) == 5:
        print("{0}---{1}".format(m[2], m[4]))
        # print "<<--------------------------------------------------------------------------->>"

# Note: for Python 2.7 compatibility, use ur"" to prefix the regex and u"" to prefix the test string and substitution.
