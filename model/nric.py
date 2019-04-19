# coding=utf8
# the above tag defines encoding for this document and is for Python 2.x compatibility

import re

regex = (r'''(?P<nric>\d{6}-?\d{2}-?\d{4})''')

test_str = ('''--text--''')

matches = re.finditer(regex, test_str, re.MULTILINE)
data = ""
for matchNum, match in enumerate(matches):
    matchNum = matchNum + 1
    print("{0}".format(match.group('nric')))


# Note: for Python 2.7 compatibility, use ur"" to prefix the regex and u"" to prefix the test string and substitution.
