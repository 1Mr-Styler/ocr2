FROM vanilla:latest

LABEL version="1.0"
LABEL description="Nubeslab OCR/NER 2"


RUN ["mkdir", "-p", "/apps/ner"]
RUN ["mkdir", "-p", "/model"] 
COPY . /apps/ner

WORKDIR /apps/ner

EXPOSE 8080
EXPOSE 5005
ENTRYPOINT ["./docker-entry.sh"]


