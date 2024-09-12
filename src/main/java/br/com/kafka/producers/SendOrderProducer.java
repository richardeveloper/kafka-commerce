package br.com.kafka.producers;

import br.com.kafka.converters.OrderConverter;
import br.com.kafka.enums.OrderStatusEnum;
import br.com.kafka.models.Order;
import br.com.kafka.utils.LoggerUtils;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class SendOrderProducer {

  private static final String TOPIC = "send-order";

  private final KafkaProducer<String, String> producer;

  private final OrderConverter orderConverter;

  public SendOrderProducer() {
    this.producer = new KafkaProducer<>(properties());
    this.orderConverter = new OrderConverter();
  }

  public void sendPaymentOrder(Order order)  {
    if (!order.getStatus().equals(OrderStatusEnum.PAYED)) {
      throw new RuntimeException("O pedido informado ainda não teve o pagamento confirmado.");
    }

    order.setStatus(OrderStatusEnum.AWAITING_SHIPMENT);

    String json = orderConverter.convertOrderToJson(order);

    ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, order.getCode(), json);

    producer.send(record);

    LoggerUtils.printOrder(this.getClass().getSimpleName(), order);

    LoggerUtils.warn(
      this.getClass().getSimpleName(),
      "Aguardando confirmação da transportadora...",
      true
    );

    producer.close();
  }

  private static Properties properties() {
    Properties properties = new Properties();
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

    return properties;
  }

}
