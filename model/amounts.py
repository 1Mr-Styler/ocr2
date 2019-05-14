# coding=utf8
# the above tag defines encoding for this document and is for Python 2.x compatibility

import re

regex = r'''Discount(?:ed)?(?:[\s:RMY\(\)]+)(?P<money>[0-9\.]+)'''
r2 = r'''Total\s(?P<money>[0-9\.\s,]+)'''

test_str = ('''--text--''')

data = ""
disc = ""
matches = re.finditer(regex, test_str, re.MULTILINE)
for matchNum, match in enumerate(matches):
    matchNum = matchNum + 1
    disc = "{0}".format(match.group('money'))

if disc == "":
    matches = re.finditer(r2, test_str, re.MULTILINE)
    for matchNum, match in enumerate(matches):
        matchNum = matchNum + 1
        disc = "{0}".format(match.group('money'))

print disc
# Note: for Python 2.7 compatibility, use ur"" to prefix the regex and u"" to prefix the test string and substitution.
