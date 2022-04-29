::%KAFKA_HOME%\bin\windows\kafka-topics.bat --help

%KAFKA_HOME%\bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 -topic JustSimple --partitions 2 --replication-factor 3

%KAFKA_HOME%\bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 -topic Employee --partitions 5 --replication-factor 3