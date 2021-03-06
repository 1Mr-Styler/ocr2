# coding=utf8
# the above tag defines encoding for this document and is for Python 2.x compatibility

import re

regex = (r'''(?:Bill)?\s?Date[\s/and]+(?:Time)?\s?:?\s?(?P<date>\d{2}[-/](?:[a-zA-Z]{3}|\d{2})[-/]20\d{2}\s?[0-9-/\s:APM]+)\n''')

test_str = ('''--text--''')

matches = re.finditer(regex, test_str, re.MULTILINE  | re.IGNORECASE)
data = ""
for matchNum, match in enumerate(matches):
    matchNum = matchNum + 1
    print("{0}".format(match.group('date')))


# Note: for Python 2.7 compatibility, use ur"" to prefix the regex and u"" to prefix the test string and substitution.
