# coding=utf8
# the above tag defines encoding for this document and is for Python 2.x compatibility

import re

regex = (r'''(?:GL|Ref|Reference)\s?(?:No\.?|number)\s?:?-?\s?(?P<gl>[A-Z0-9/-]+)''')

test_str = ('''--text--''')

matches = re.finditer(regex, test_str, re.MULTILINE | re.IGNORECASE)
data = ""
for matchNum, match in enumerate(matches):
    matchNum = matchNum + 1
    print("{0}".format(match.group('gl')))


# Note: for Python 2.7 compatibility, use ur"" to prefix the regex and u"" to prefix the test string and substitution.
