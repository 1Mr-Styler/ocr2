from pdf2image import convert_from_path
import argparse

parser = argparse.ArgumentParser()
parser.add_argument('origin', help='PDF to convert.')
parser.add_argument('dests', help='End file name')
args = parser.parse_args()

pages = convert_from_path(args.origin, 500)

i = len(pages)
for page in reversed(pages):
    if i == 2:
        exit()
    page.save(args.dests + str(i) + '.jpg', 'JPEG')
    print args.dests + str(i) + '.jpg'
    i -= 1
