package br.com.kafka.consumers;

import br.com.kafka.Main;
import br.com.kafka.converters.OrderConverter;
import br.com.kafka.enums.OrderStatusEnum;
import br.com.kafka.models.Order;
import br.com.kafka.utils.LoggerUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class SendOrderConsumer {

  private static final String TOPIC = "send-order";

  private final KafkaConsumer<String, String> consumer;

  private final OrderConverter orderConverter;

  public SendOrderConsumer() {
    this.consumer = new KafkaConsumer<>(properties());
    this.orderConverter = new OrderConverter();
  }

  public void readSendOrders() {
    consumer.subscribe(Collections.singleton(TOPIC));

    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

      if (records.isEmpty()) {
        continue;
      }

      for (ConsumerRecord<String, String> record : records) {
        Order order = orderConverter.convertJsonToOrder(record.value());

        order.setStatus(OrderStatusEnum.SEND);
        order.setSendDate(LocalDateTime.now());

        LoggerUtils.info(
          this.getClass().getSimpleName(),
          "Envio aprovado pela transportadora.",
          true
        );

        LoggerUtils.printOrder(this.getClass().getSimpleName(), order);

        LoggerUtils.info(
          this.getClass().getSimpleName(),
          "Pedido finaliado com sucesso.",
          true
        );

        Main.showOrderDetails(order);
      }
    }
  }

  private static Properties properties() {
    Properties properties = new Properties();
    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, SendOrderConsumer.class.getSimpleName());

    return properties;
  }
}
