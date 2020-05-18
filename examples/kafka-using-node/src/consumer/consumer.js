const { Consumer, Offset, KafkaClient } = require('kafka-node-oauth');

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
const options = { 
    autoCommit: false, 
    groupId: process.env.KAFKA_GROUP_ID, 
    fetchMaxWaitMs: 1000, 
    fetchMaxBytes: 1024 * 1024
};
const topics = [
    { topic: topic, partition: 0 }
];

const client = new KafkaClient(clientConfig);
const consumer = new Consumer(client, topics, options);
const offset = new Offset(client);

consumer.on('message', function (message) {
  console.log(message);
});

consumer.on('error', function (err) {
  console.log('error', err);
});

/*
* If consumer get `offsetOutOfRange` event, fetch data from the smallest(oldest) offset
*/
consumer.on('offsetOutOfRange', function (topic) {
  topic.maxNum = 1;
  offset.fetch([topic], function (err, offsets) {
    if (err) {
      return console.error(err);
    }
    const min = Math.min.apply(null, offsets[topic.topic][topic.partition]);
    consumer.setOffset(topic.topic, topic.partition, min);
  });
});
