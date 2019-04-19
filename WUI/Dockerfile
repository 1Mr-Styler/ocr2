FROM ubuntu:16.04

LABEL version="1.0"
LABEL description="Nubeslab OCR/NER 2 GUI"

ENV SDKMAN_DIR /root/.sdkman
ENV GRAILS_VERSION 3.3.6
ENV JAVA_VERSION 8.0.212-zulu

RUN ["mkdir", "-p", "/apps/wui"] 
RUN ["mkdir", "-p", "/model"] 
COPY . /apps/ocr
RUN apt-get update && apt-get install -y zip curl python2.7 python-pip python-argparse poppler-utils
WORKDIR /apps/wui
RUN pip2 install -r requirements.txt
RUN rm /bin/sh && ln -s /bin/bash /bin/sh
RUN curl -s "https://get.sdkman.io" | bash
RUN chmod a+x "$SDKMAN_DIR/bin/sdkman-init.sh"

RUN set -x \
    && echo "sdkman_auto_answer=true" > $SDKMAN_DIR/etc/config \
    && echo "sdkman_auto_selfupdate=false" >> $SDKMAN_DIR/etc/config \
    && echo "sdkman_insecure_ssl=false" >> $SDKMAN_DIR/etc/config

WORKDIR $SDKMAN_DIR
RUN [[ -s "$SDKMAN_DIR/bin/sdkman-init.sh" ]] && source "$SDKMAN_DIR/bin/sdkman-init.sh" && exec "$@"

RUN source /root/.bashrc
RUN source "$SDKMAN_DIR/bin/sdkman-init.sh" && sdk install java $JAVA_VERSION
WORKDIR /apps/wui

EXPOSE 8080
EXPOSE 5005
ENTRYPOINT ["./docker-entry.sh"]


