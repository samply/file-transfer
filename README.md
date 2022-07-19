# File Transfer

This service transfers files from one bridgehead to another bridgehead.

It serves for both sides of the connection. If we transfer the files from bridgehead-1 to
bridgehead-2:

- Configuration of bridgehead-1: TARGET_BRIDGEHEAD_URL and TARGET_BRIDEHEAD_APIKEY of bridgehead-2
  
  
- Configuration of bridgehead-2: TRANSFER_FILES_CRON_EXPR=- 
With this configuration, no files will be transferred to any additional bridgehead.
  
Of course, TARGET_BRIDGEHEAD_URL and TARGET_BRIDGEHEAD_APIKEY don't need to be specified.
  

## Building
mvn clean package

## Running
java -jar file-transfer.jar 
-DTRANSFER_FILES_DIRECTORY=
-DTRANSFER_FILES_CRON_EXPR=
-DCLIENT_API_KEY=
-DAPPLICATION_PORT=
-DTARGET_BRIDGEHEAD_URL=
-DTARGET_BRIDGEHEAD_APIKEY=


## Docker
docker-compose up

## Environment variables
TRANSFER_FILES_DIRECTORY: Directory to save files sent to the API.  
TRANSFER_FILES_CRON_EXPR: How often the directory is checked to transfer the files 
to bridgehead-2.  
CLIENT_API_KEY: API key  
APPLICATION_PORT: Port  
TARGET_BRIDGEHEAD_URL: URL of bridgehead-2  
TARGET_BRIDGEHEAD_APIKEY: API key of bridgehead-2 
LOG_LEVEL: Spring log level (INFO by default)


## Environment configuration example
bridgehead-1:

TRANSFER_FILES_DIRECTORY=.\files-bk1  
TRANSFER_FILES_CRON_EXPR=0 * * * * *  
CLIENT_API_KEY=abc123  
APPLICATION_PORT=8181  
TARGET_BRIDGEHEAD_URL=http://localhost:8282/transfer  
TARGET_BRIDGEHEAD_APIKEY=def456  

bridgehead-2:

TRANSFER_FILES_DIRECTORY=.\files-bk2  
TRANSFER_FILES_CRON_EXPR=-  
CLIENT_API_KEY=def456  
APPLICATION_PORT=8282  

## Send file to the API

POST http://<bridgehead-1 server>:<bridgehead-1 port>/transfer  
Headers:   
- apiKey=\<api key of bridgehead-1>  
- Content-Type=multipart/form-data  
Body:  
- file=\<file to be transferred>  
