# axon-bank-poc
Simple axon and spring application

## Requirements
1. Docker 
2. Java8

## How to build
To build an application just execute command: `mvn clean install`
* There can be some problem with building docker image. Docker maven plugin tries to connect to docker. In Windows 8 some problems can occure with connection details. 
Plugin tries to connecto to `localhost:2375` by default. We can ovveride this value by setting environment variables. To do that run following command:
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
1. Run MongoDB

Execute following command: `docker run --name axon-bank-mongo -p 27017:27017 -d mongo`

2. Run Rabbit-MQ
Exchanging events
Execute following command: `docker run -d -p 5672:5672 -p 15672:15672 -e RABBITMQ_PASS="mypass" tutum/rabbitmq`

3. Run the application

To run docker image you have to execute following command: 

`docker run -it --name axon-bank-poc -p 8080:8080 axon-bank-poc`

* To connect to docker image container run command: `docker exec mongo bash`

CURL commands:
1. Create account
`curl -H "Content-Type: application/json" -X POST -d '{"accountNumber":"123","overdraft":"1000"}' http://localhost:8080/accounts`
2. Deposit money
`curl -H "Content-Type: application/json" -X POST -d '{"accountNumber":"123","amount":"100"}' http://localhost:8080/deposits`
3. Withdraw money
`curl -H "Content-Type: application/json" -X POST -d '{"accountNumber":"123","amount":"100"}' http://localhost:8080/withdraws`
4. Account balance
`curl -X GET http://localhost:8080/accounts/{id}`