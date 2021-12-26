## Limits:
1. Input can be a 32 bit integer. 
3. No. of requests can be 2^63-1 as count is a long data type
4. Key is provided through a file based configuration file. Needs to be protected through host hardening.
5. System supports only one key. APIs are designed to accomodate possible future requirements like: key rotation, re-keying, crypto agility.
6. Server supports http connection only, Should be enhanced to support https.

## Decisions:
Not much about the sesitivity of the data is known, Encryption parameters are chosen to be fulfilling NIST recommendation and based on industry practices.
The data size is small too.
1. Key used is AES-256 bit.
2. Encryption mode used is CBC/PKCS5Padding.
3. IV size is 128 bit

## References:
Standard deviation calculation:
[Welford's online algorithm on wikipedia](https://en.wikipedia.org/wiki/Algorithms_for_calculating_variance) .



## Steps:
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
