FROM vanilla:latest

LABEL version="1.0"
LABEL description="Nubeslab OCR/NER 2 GUI"


RUN ["mkdir", "-p", "/apps/wui"] 
RUN ["mkdir", "-p", "/model"] 
COPY . /apps/wui
WORKDIR /apps/wui
RUN apt-get update && apt-get install -y python-argparse poppler-utils
RUN pip2 install -r requirements.txt

EXPOSE 8080
EXPOSE 5005
ENTRYPOINT ["./docker-entry.sh"]


