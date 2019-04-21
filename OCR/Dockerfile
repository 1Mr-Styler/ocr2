FROM vanilla:latest

LABEL version="1.0"
LABEL description="Nubeslab OCR/NER 2"


RUN ["mkdir", "-p", "/apps/ocr"] 
RUN ["mkdir", "-p", "/model"] 
COPY . /apps/ocr
WORKDIR /apps/ocr
RUN pip2 install -r requirements.txt

EXPOSE 8090
EXPOSE 5005
ENTRYPOINT ["./docker-entry.sh"]


