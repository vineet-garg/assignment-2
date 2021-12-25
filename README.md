1. Clone The repo
2. Build
```
mvn clean
mvn test
mvn package
```
3. Run the server
```
java -jar /home/vgarg1/workspace/crypto.webservice/target/crypto.webservice-0.0.1-SNAPSHOT.jar server server.yaml 
```
4. Sample curl commands and outputs
```
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"num":2}'  http://0.0.0.0:8080/push-and-recalculate
```
```
{"avg":{"num":11.0},"sd":{"num":9.0}}
```
```
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"num":20}'  http://0.0.0.0:8080/push-and-recalculate-encrypt
```
```
{"avg":{"cipherTxt":"vNMohECty6at0P078YGWxA==nnbdbATeYWM1a+55vxKsnA=="},"sd":{"cipherTxt":"aABi54a1Db2tMLQvR1e9VQ==YTD9wGTKBs2nmd1vBKj12g=="}}
```
```
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"cipherTxt":"vNMohECty6at0P078YGWxA==nnbdbATeYWM1a+55vxKsnA=="}'  http://0.0.0.0:8080/decrypt
```
```
{"num":20.0}
```
```
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"cipherTxt":"aABi54a1Db2tMLQvR1e9VQ==YTD9wGTKBs2nmd1vBKj12g=="}'  http://0.0.0.0:8080/decrypt
```
```
{"num":0.0}
```
