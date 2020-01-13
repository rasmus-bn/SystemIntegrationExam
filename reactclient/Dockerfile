FROM node:12

WORKDIR /app
COPY . .

RUN npm install --silent
RUN npm install react-scripts@3.0.1 -g --silent

CMD ["npm", "start"]