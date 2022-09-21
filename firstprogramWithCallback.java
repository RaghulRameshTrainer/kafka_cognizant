import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class firstprogramWithCallback {
    private static final Logger log = LoggerFactory.getLogger(firstprogramWithCallback.class.getSimpleName());

    public static void main(String[] args) {

        log.info("Hello Cognizant!!!");

        //create producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        //create the producer
        KafkaProducer<String,String> producer = new KafkaProducer<>(properties);

        //create a producer record
        ProducerRecord<String,String> producerRecord = new ProducerRecord<>("cts_topic","FINAL MESSAGES!!!");

        //send the data
        producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception e) {

                if (e == null) {
                    //the record was sent successfully
                    log.info("Received the metadata \n" +
                            "Topic: "+metadata.topic() + "\n" +
                            "Partition: "+metadata.partition()+"\n" +
                            "Offset :" +metadata.offset() + "\n"
                            );
                } else {
                    log.error("Error while producing:", e);
                }

            }
        });

        //close the producer
        producer.close();
    }
}
