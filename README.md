# File Transfer

This service transfers files from one bridghead to another bridgehead.

It serves for both sides of the connection. If we transfer the files from bridghead-1 to
bridgehead-2:

- Configuration of bridgehead-1: TARGET_BRIDGEHEAD_URL and TARGET_BRIDEHEAD_APIKEY of bridgehead-2
  
  
- Configuration of bridgehead-2: TRANSFER_FILES_CRON_EXPR=- 
With this configuration, no files will be transferred to any additional bridghead.
  
Of course, TARGET_BRIDGEHEAD_URL and TARGET_BRIDGEHEAD_APIKEY don't need to be specified.
  

## Building
mvn clean package

## Running
java -jar file-transfer.jar 
-DTRANSFER_FILES_DIRECTORY=
-DTRANSFER_FILES_CRON_EXPR=
-DCLIENT_API_KEY=
-DAPPLICATION.PORT=
-DTARGET_BRIDGEHEAD_URL=
-DTARGET_BRIDGEHEAD_APIKEY=


## Docker
docker-compose up

## Environment variables
TRANSFER_FILES_DIRECTORY: Directory to save files sent to the API.  
TRANSFER_FILES_CRON_EXPR: How often the directory is checked to transfer the files 
to bridgehead-2.  
CLIENT_API_KEY: API key  
APPLICATION.PORT: Port  
TARGET_BRIDGEHEAD_URL: URL of brideghead-2  
TARGET_BRIDGEHEAD_APIKEY: API key of bridghead-2  


## Environment configuration example
bridghead-1:

TRANSFER_FILES_DIRECTORY=C:\tmp\hki-files-bk1  
TRANSFER_FILES_CRON_EXPR=0 * * * * *  
CLIENT_API_KEY=abc123  
APPLICATION.PORT=8181  
TARGET_BRIDGEHEAD_URL=http://localhost:8282/transfer  
TARGET_BRIDGEHEAD_APIKEY=def456  

bridgehead-2:

TRANSFER_FILES_DIRECTORY=C:\tmp\hki-files-bk2  
TRANSFER_FILES_CRON_EXPR=-  
CLIENT_API_KEY=def456  
APPLICATION.PORT=8282  

## Send file to the API

POST http://<bridgehead-1 server>:<bridghead-1 port>/transfer  
Headers:   
- apiKey=\<api key of bridghead-1>  
- Content-Type=multipart/form-data  
Body:  
- file=\<file to be transferred>  
