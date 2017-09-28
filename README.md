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
To run docker image you have to execute following command: 

`docker run -it --name axon-bank-poc -p 8080:8080 axon-bank-poc`