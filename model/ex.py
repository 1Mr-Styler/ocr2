# coding=utf8
# the above tag defines encoding for this document and is for Python 2.x compatibility

import re

regex = (r'''.*(?:Patient)\s((?:[Nn]ame)?[\s\t:;]+?([A-Z]{2,}\s[A-Z]{2,}\s?[A-Z]{2,}?))[\s\r\n\t]''')

test_str = ('''--text--''')

matches = re.finditer(regex, test_str, re.MULTILINE | re.IGNORECASE)
data = ""
for matchNum, match in enumerate(matches):
    matchNum = matchNum + 1
    print match.group(2).strip()


# Note: for Python 2.7 compatibility, use ur"" to prefix the regex and u"" to prefix the test string and substitution.
