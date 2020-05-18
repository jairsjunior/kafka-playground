FROM oryd/hydra:v1.0.0-rc.2_oryOS.9-alpine

RUN apk update && \
    apk add curl

COPY ./*.json /
COPY ./*.sh /

WORKDIR /

ENTRYPOINT [ "/bin/sh" ]

CMD [ "start.sh" ]