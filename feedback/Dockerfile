FROM node:8

WORKDIR /home/node/app

COPY ./src ./src
COPY ./env_helper.js ./env_helper.js
COPY ./db ./db
COPY ./package.json ./package.json

RUN npm install

CMD ["npm", "start"]