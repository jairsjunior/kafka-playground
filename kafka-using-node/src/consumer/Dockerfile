FROM node:11-alpine

RUN mkdir -p /src/app

WORKDIR /src/app

COPY package.json /src/app/package.json

RUN npm install

ADD . /src/app

CMD [ "npm", "start" ]