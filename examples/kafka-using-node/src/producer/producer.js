const { Producer, KeyedMessage, KafkaClient } = require('kafka-node-oauth');

//Configuration of Topic and KafkaClient
const topic = process.env.KAFKA_TOPIC;
const clientConfig = {
    kafkaHost: process.env.KAFKA_HOST,
    sasl: {
      mechanism: 'OAUTHBEARER',
      protocol: process.env.OAUTH_PROTOCOL,
      grantType: process.env.OAUTH_GRANT_TYPE,
      scope: process.env.OAUTH_SCOPE,
      url: process.env.OAUTH_HOST,
      endpoint: process.env.OAUTH_ENDPOINT,
      token: process.env.OAUTH_TOKEN
    },
    connectRetryOptions: {
      retries: 0
    },
    noAckBatchOptions: null
};
console.log(clientConfig);

const partition = 0;
const attribute = 0;

const client = new KafkaClient(clientConfig);
const producer = new Producer(client, { requireAcks: 1 });

producer.on('ready', function () {
  let max = 10000;
  let min = 0;
  console.log('Kafka client is connected!');
  setInterval(() => {
    const message = `a message ${Math.floor(Math.random() * (max - min + 1)) + min}`;
    const keyedMessage = new KeyedMessage('keyed', `a keyed message ${Math.floor(Math.random() * (max - min + 1)) + min}`);
    producer.send([
      { topic: topic, partitions: partition, messages: [message, keyedMessage], attributes: attribute }
    ], function (err, result) {
      console.log(err || result);
    });
  }, 3000);
});

producer.on('error', function (err) {
  console.log('error', err);
});
