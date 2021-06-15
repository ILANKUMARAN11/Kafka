package com.ilan.serializer.alternative;

import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

import java.util.Map;

//props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomKafkaAvroSerializer.class);
public class CustomKafkaAvroSerializer extends KafkaAvroSerializer {
    public CustomKafkaAvroSerializer() {
        super();
        super.schemaRegistry = new MockSchemaRegistryClient();
    }

    public CustomKafkaAvroSerializer(SchemaRegistryClient client) {
        super(new MockSchemaRegistryClient());
    }

    public CustomKafkaAvroSerializer(SchemaRegistryClient client, Map<String, ?> props) {
        super(new MockSchemaRegistryClient(), props);
    }
}