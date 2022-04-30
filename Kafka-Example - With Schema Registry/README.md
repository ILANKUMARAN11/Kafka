# Start Spring-boot Consumer with different Server.port and Consumer Group-ID
######As VM Args.
```bash
java -jar -Dserver.port=8183  -Dkafka.simple.group-id=JustSimple_group_id-1 Kafka-Consumer-0.0.1-SNAPSHOT.jar
```

######As Program Args.
```bash
java -jar Kafka-Consumer-0.0.1-SNAPSHOT.jar --server.port=8183  --kafka.simple.group-id=JustSimple_group_id-1
```

## Zookeeper Failed to start

<details>
<summary>When Failed to start</summary>
<p>

If you get error when starting zookeeper
```shell
Classpath is empty. Please build the project first e.g. by running 'gradlew jarAll'
```

Step 1: Go to file in the below location
```shell
confluent-7.1.0\bin\windows\kafka-run-class.bat
```
Step 2: Then find this line
```shell
rem Classpath addition for core
for %%i in ("%BASE_DIR%\core\build\libs\kafka_%SCALA_BINARY_VERSION%*.jar") do (
call :concat "%%i"
)
```
Step 3: Then replace this line
```shell
rem classpath addition for LSB style path
if exist %BASE_DIR%\share\java\kafka\* (
call:concat %BASE_DIR%\share\java\kafka\*
)
```

</p>
</details>

## Multiple Broker configuration
<details>
<summary> Server property file Configuration changes</summary>
<p>

* The id of the broker. This must be set to a unique integer for each broker.
  ```shell
  broker.id=0
    ```

* Port number to be unique when running in same Machine
    ```shell
    listeners=PLAINTEXT://:9092
    ```
  
* Log directory for Each broker
    ```shell
    log.dirs=../tmp/kafka-logs-0
    ```
  
</p>
</details>

##Schema Evolution and Compatibility
See the [COMPATIBILITY](https://docs.confluent.io/platform/current/schema-registry/avro.html) types and more.

######[@Github Ilankumaran11](https://github.com/ILANKUMARAN11/Kafka)