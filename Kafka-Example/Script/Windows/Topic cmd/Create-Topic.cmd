%KAFKA_HOME%\bin\windows\kafka-topics.bat --help

%KAFKA_HOME%\bin\windows\kafka-topics.bat --create -zookeeper localhost:2181 -topic JustSimple --partitions 2 --replication-factor 3

%KAFKA_HOME%\bin\windows\kafka-topics.bat --create -zookeeper localhost:2181 -topic Employee --partitions 5 --replication-factor 3