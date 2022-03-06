# Wiredcraft Back-end Developer Test


## Features
- add a user
- delete a user 
- query user
- modify user
- add friend
- Find nearby friends

## REQUIREMENT
- Mysql 5.7+
- Redis 3.2+
- JDK 1.8

## Getting started
1. clone  this repository  
2. open in idea
3. create a new database ```CREATE DATABASE `backend_test` CHARACTER SET utf8 COLLATE utf8_general_ci;```
4. modify Run Configuration
5. add & modify  VM options  `-Ddb.address=127.0.0.1 -Ddb.port=3306 -Ddb.name=backend_test -Ddb.username=root -Ddb.password=1q2w3e4r -Dredis.host=127.0.0.1 -Dredis.port=6379 `
   ![](https://s3.bmp.ovh/imgs/2022/03/a06b784468137914.png)
6. Run 
## Documentation & Unit Test
[online documentation](https://documenter.getpostman.com/view/3170707/UVkvHBjM)
or 
Import these two files into postman
- WiredCraft.postman_collection.json
- WiredCraft Tests.postman_collection.json

![](https://s3.bmp.ovh/imgs/2022/03/78aade3e532e2d0c.png)
![](https://s3.bmp.ovh/imgs/2022/03/69cc3a5e01c5f386.png)