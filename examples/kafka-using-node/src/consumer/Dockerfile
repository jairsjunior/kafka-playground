FROM node:11-alpine

RUN apk add --update --no-cache busybox busybox-extras && \
    mkdir -p /src/app

WORKDIR /src/app

COPY package.json /src/app/package.json

RUN npm install

ADD . /src/app

CMD [ "sh", "./checkIsUp.sh" ]