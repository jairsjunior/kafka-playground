FROM wurstmeister/kafka:latest

RUN mkdir /opt/kafka/addons
COPY libs/kafka* /opt/kafka/libs/
COPY addons/* /opt/kafka/addons/
COPY config/* /opt/kafka/config/
COPY waitForOAuth.sh /

CMD ["sh", "/waitForOAuth.sh"]
