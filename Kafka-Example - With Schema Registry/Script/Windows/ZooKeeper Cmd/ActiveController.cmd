%KAFKA_HOME%\bin\windows\zookeeper-shell.bat localhost:2181

help

ls /

::Active broker ID's
ls /brokers/ids

::get controller broker ID
get /controller
