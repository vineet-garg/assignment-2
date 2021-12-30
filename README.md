## Limits:
1. Input can be a signed 32 bit integer. 
3. No. of requests can be 2^63-1 as count is a long data type
4. Key is provided through a file based configuration file. Needs to be protected through host hardening.
6. Algorithm and Algorithm parameters are hardcoded, but can be made configurable to facilitate quick transition to any other algorithm.
5. System supports only one key. APIs are designed to accomodate possible future requirements like: key rotation, re-keying, crypto agility.
6. Server supports http connection only.
7. Encrypt/Decrypt functionality is not access controlled or rate limited.

## Choices:
Encryption parameters are as per recommendation (NIST SP-800 38D).
The data size is small: 32 bit Float, the size of overhead (extra bits for IV and authentication Tag) is kept small too balancing between bandwith and security. 
Access controlling, rate limiting and monitoring Encryption service along with a keyRoation policy can mitigate the security loss due to small values of IV and authentication Tag. They are in general nice for a production use service. A comprehensive threat modelling of the feature, identifying sensitivity level of data being protected will enable making more informed choices.  
1. Key used is AES-256 bit.
2. Encryption mode used is GCM/NoPadding.
3. CBC/PKCSPadding was considered as well but as Decrypt is not access controlled or rate limited, it would be vulnerable to padding ORACLE attack.
3. IV size is 96 bit
4. Authentication tag size is 96 bit
5. IV is returned in plain along with Encrypted data, with GCM this is considered safe (unlike in CBC mode which is prone to clear IV attacks).
6. Encryption and decryption errors are not propagated to the clients, this can be usability tradeoff.

## References:
Standard deviation calculation:
1. [Welford's online algorithm on wikipedia](https://en.wikipedia.org/wiki/Algorithms_for_calculating_variance).
2. AES-GCM: [NIST SP-800 38D](https://nvlpubs.nist.gov/nistpubs/Legacy/SP/nistspecialpublication800-38d.pdf).



## Steps:
1. Install JDK (jdk-17.0.1) and maven (Apache Maven 3.8.4), set $JAVA_HOME
```
   export JAVA_HOME=<Path to jdk's parent folder>/jdk-17.0.1
```
2. Clone The repo
```
  mkdir -p $HOME/test
  cd $HOME/test
  git clone https://github.com/vineet-garg/assignment-2.git
  cd assignment-2
```
3. Build
```
mvn clean
```
```
mvn test
```
```
   .......
	at org.apache.maven.surefire.booter.ProviderFactory.invokeProvider(ProviderFactory.java:85)
	at org.apache.maven.surefire.booter.ForkedBooter.runSuitesInProcess(ForkedBooter.java:115)
	at org.apache.maven.surefire.booter.ForkedBooter.main(ForkedBooter.java:75)
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.021 sec
Running crypto.webservice.services.StatsSvcImplTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.004 sec

Results :

Tests run: 9, Failures: 0, Errors: 0, Skipped: 0
```
```
mvn package
```
4. Run the server (server properties like port numbers can be changed in server.yaml file)
```
$JAVA_HOME/bin/java -jar $HOME/test/assignment-2/target/crypto.webservice-0.0.1-SNAPSHOT.jar server server.yaml
```
5. Sample curl commands and outputs
```
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"num":2}'  http://0.0.0.0:8080/push-and-recalculate
```
{"avg":{"num":10.0},"sd":{"num":10.0}}

```
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"num":20}'  http://0.0.0.0:8080/push-recalculate-and-encrypt
```
{"avg":{"cipherTxt":"AhDjGm/TwOar80AcickyZfAICokOwTPwTqbJZA==","keyId":"0"},"sd":{"cipherTxt":"5EOD9kTkti7XtTN2q6uhVHnQyYDsC1lAaLhNyg==","keyId":"0"}}

```
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"cipherTxt":"AhDjGm/TwOar80AcickyZfAICokOwTPwTqbJZA==","keyId":"0"}'  http://0.0.0.0:8080/decrypt
```
{"num":13.333333}

```
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"cipherTxt":"5EOD9kTkti7XtTN2q6uhVHnQyYDsC1lAaLhNyg==","keyId":"0"}'  http://0.0.0.0:8080/decrypt
```
{"num":9.428091}
