# ECE-464 Databases Final Project
## Localization Packet Simulator

### Description

This is the Final Project for ECE-464 Databases course by Theo Song and I.
It simulates the backend architecture of our Senior Project, which creates
a network of Tags and Anchors and simulates Packet generation between Tag
and Anchors.

Users can log-in/register, and depending on their roles, can manange new
Tags, Anchors and their assignments.

### Technical requirements

This project is built on Java Spring Boot, PostgreSQL and InfluxDB.
Due to InfluxDB dependencies, Java version 15 is required.

### Installation Requirements

```sh
$ git clone https://github.com/dhyunp/databases-finalproject-ece464.git
$ cd indoor
$ ./gradlew bootRun
```

This will launch the project on `http://localhost:8080/` and the landing
page will be the login form.
