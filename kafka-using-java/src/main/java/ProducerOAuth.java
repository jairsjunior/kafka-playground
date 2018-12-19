import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class ProducerOAuth {

    public static void main(String[] args) {

        Properties properties = configureProducer();
        Producer<String, String> producer = new KafkaProducer<>(properties);

        int i = 0;
        while (i < 10) {
            System.out.println("Producing batch: " + i);
            try {
                producer.send(newEvent("Person 1"));
                Thread.sleep(100);
                producer.send(newEvent("Person 2"));
                Thread.sleep(100);
                producer.send(newEvent("Person 3"));
                Thread.sleep(100);
                i += 1;
            } catch (InterruptedException e) {
                break;
            }
        }
        producer.close();
    }

    private static ProducerRecord<String, String> newEvent(String name) {
        //Create an JSON Object
        ObjectNode transaction = JsonNodeFactory.instance.objectNode();
        ObjectNode singleTran = JsonNodeFactory.instance.objectNode();
        ObjectNode labels = JsonNodeFactory.instance.objectNode();

        singleTran.put("id", ThreadLocalRandom.current().nextInt(0, 9000));
        labels.put("account", ThreadLocalRandom.current().nextInt(0, 5));
        Integer amount = ThreadLocalRandom.current().nextInt(0, 100);
        transaction.put("transaction", singleTran);
        transaction.put("id", ThreadLocalRandom.current().nextInt(0, 900000));
        transaction.put("balance", amount);
        transaction.put("labels",labels);

        System.out.println(transaction);

        return new ProducerRecord<>("topic-name", name, transaction.toString());
    }

    private static Properties configureProducer(){
        Properties properties = new Properties();

        // kafka bootstrap server
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getEnvironmentVariables("BOOTSTRAP_SERVERS_CONFIG", "127.0.0.1:9092"));
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // producer acks
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, "3");
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1");

        //kafka oauth configuration
        properties.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.oauthbearer.OAuthBearerLoginModule required ;");
        properties.setProperty("security.protocol", "SASL_PLAINTEXT");
        properties.setProperty("sasl.mechanism", "OAUTHBEARER");
        properties.setProperty("sasl.login.callback.handler.class", "br.com.jairsjunior.security.oauthbearer.OauthAuthenticateLoginCallbackHandler");

        return properties;
    }

    private static String getEnvironmentVariables(String envName, String defaultValue) {
        String result= System.getenv(envName);
        if(result == null){
            result = defaultValue;
        }
        return result;
    }

}
