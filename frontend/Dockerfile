FROM node:20.2.0-alpine3.16 as builder

RUN mkdir /project
WORKDIR /project

RUN npm install -g @angular/cli@13

COPY package.json package-lock.json ./
RUN npm ci

COPY . .
CMD ["ng", "serve", "--prod", "--host", "0.0.0.0", "--disable-host-check"]
