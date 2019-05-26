FROM styl3r/vanilla:latest

LABEL version="1.0"
LABEL description="Nubeslab OCR/NER 2"


RUN ["mkdir", "-p", "/apps/ocr"] 
RUN ["mkdir", "-p", "/model"] 
COPY . /apps/ocr
WORKDIR /apps/ocr
RUN pip2 install -r requirements.txt
RUN apt-get install software-properties-common
RUN add-apt-repository -y ppa:alex-p/tesseract-ocr
RUN apt-get update && apt-get install -y tesseract-ocr
WORKDIR /usr/bin/
ln -s tesseract sbs
WORKDIR /apps/ocr
EXPOSE 8090
EXPOSE 5005
ENTRYPOINT ["./docker-entry.sh"]


