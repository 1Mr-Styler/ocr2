FROM styl3r/vanilla:latest

LABEL version="1.0"
LABEL description="Nubeslab OCR/NER 2 GUI"


RUN ["mkdir", "-p", "/apps/wui"]
RUN ["mkdir", "-p", "/model"]
COPY . /apps/wui
RUN apt-get update && apt-get install -y zip curl python2.7 python-pip python-argparse poppler-utils
WORKDIR /apps/wui
RUN pip2 install -r requirements.txt

EXPOSE 5005
EXPOSE 8080
ENTRYPOINT ["./docker-entry.sh"]


