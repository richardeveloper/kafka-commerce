package br.com.kafka.producers;

import br.com.kafka.converters.OrderConverter;
import br.com.kafka.enums.OrderStatusEnum;
import br.com.kafka.models.Order;
import br.com.kafka.utils.LoggerUtils;

import java.time.LocalDateTime;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class PaymentOrderProducer {

  private static final String TOPIC = "payment-order";

  private final KafkaProducer<String, String> producer;

  private final OrderConverter orderConverter;

  public PaymentOrderProducer() {
    this.producer = new KafkaProducer<>(properties());
    this.orderConverter = new OrderConverter();
  }

  public void sendPaymentOrder(Order order)  {
    if (!order.getStatus().equals(OrderStatusEnum.CREATED)) {
      throw new RuntimeException("O pedido informado já foi processado.");
    }

    order.setStatus(OrderStatusEnum.AWAITING_PAYMENT);
    order.setConfirmationDate(LocalDateTime.now());

    String json = orderConverter.convertOrderToJson(order);

    ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, order.getCode(), json);

    this.producer.send(record);

    LoggerUtils.info(
      this.getClass().getSimpleName(),
      "Pedido enviado para pagamento.",
      true
    );

    LoggerUtils.printOrder(this.getClass().getSimpleName(), order);

    LoggerUtils.warn(
      this.getClass().getSimpleName(),
      "Aguardando confirmação da pagamento...",
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
