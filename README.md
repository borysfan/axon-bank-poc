# axon-bank-poc
Simple axon and spring application

## Requirements
1. Docker 
2. Java8

## How to build
To build an application just execute command: `mvn clean install`
* There can be some problem with building docker image. Docker maven plugin tries to connect to docker. In Windows 8 some problems can occure with connection details. 
Plugin tries to connecto to `localhost:2375` by default. We can override this value by setting environment variables. To do that run following command:
`docker-machine env`

It gives output like:
```
export DOCKER_TLS_VERIFY="1"
export DOCKER_HOST="tcp://192.168.99.100:2376"
export DOCKER_CERT_PATH="C:\Users\e-dkbs\.docker\machine\machines\default"
export DOCKER_MACHINE_NAME="default"
export COMPOSE_CONVERT_WINDOWS_PATHS="true"
# Run this command to configure your shell:
# eval $("C:\Program Files\Docker Toolbox\docker-machine.exe" env)
```
Then you can set this variables in your system properties settings to have them after your machine restart.

## How to run
1. Make sure that all components has been successfully built. You should find all images in you local images repository. Just type command `docker images` and you should something like that: 
```
REPOSITORY              TAG                 IMAGE ID            CREATED             SIZE
axon-bank-stats         latest              56ab8113b1af        2 days ago          684MB
axon-discovery-server   latest              6308f0048934        2 days ago          685MB
axon-bank               latest              bd54e509ac38        2 days ago          689MB
zuul-proxy              latest              453d44e568c6        2 days ago          683MB
```
2. Execute following command: `docker-compose up`. This command will start all necessary components in proper order

## Scaling up  
Running multiple instances of axon-bank application. When your application is up and ready you can scale it up horizontally. To do that you have to tell docker to start new instance of the axon-bank image. Just type following command: `docker-compose scale axon-bank=2`. If you are running one instance of axon-bank it will start new one. This new instance will be automatically registered in eureka service and in (Axon) distributed event bus.  
 
## How to stop
If you want to stop application and their all components just press CTRL + C and then execute command: `docker-compose down`. 

## Exposed endpoints
CURL commands:
1. Create account
`curl -H "Content-Type: application/json" -X POST -d '{"accountNumber":"123","overdraft":"1000"}' http://localhost:8080/accounts`
2. Deposit money
`curl -H "Content-Type: application/json" -X POST -d '{"accountNumber":"123","amount":"100"}' http://localhost:8080/deposits`
3. Withdraw money
`curl -H "Content-Type: application/json" -X POST -d '{"accountNumber":"123","amount":"100"}' http://localhost:8080/withdraws`
4. Account balance
`curl -X GET http://localhost:8080/accounts/{id}`
5. Transfer money from A account to B account
`curl -H "Content-Type: application/json" -X POST -d '{"fromAccountNumber":"123","toAccountNumber": "321", "amount":"100"}' http://localhost:8080/transactions`

## Development
For development purposes you just need mongo database and rabbit mq server. To run this two components just type:
`docker-compose -f docker-compose-dev.yml up`

If you want to turn them down: `docker-compose -f docker-compose-dev.yml down`
 
