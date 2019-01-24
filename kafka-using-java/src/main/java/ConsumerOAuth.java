
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

public class ConsumerOAuth {

    public static void main(String[] args) {

        Properties properties = configureConsumer();
        Consumer<String, String> consumer = new KafkaConsumer<>(properties);

        consumer.subscribe(Arrays.asList("topic-name"));

        System.out.println("Consuming batch: ");
        while (true) {
            try {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records)
                    System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        consumer.close();
    }

    private static Properties configureConsumer(){
        Properties properties = new Properties();

        // kafka bootstrap server
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getEnvironmentVariables("BOOTSTRAP_SERVERS_CONFIG", "127.0.0.1:9092"));
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // kafka consumer props
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group-id");
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");

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
