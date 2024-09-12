package br.com.kafka;

import br.com.kafka.consumers.PaymentOrderConsumer;
import br.com.kafka.consumers.SendOrderConsumer;
import br.com.kafka.models.Order;
import br.com.kafka.utils.LoggerUtils;
import br.com.kafka.views.KafkaCommerceView;

import java.util.Properties;

import javax.swing.JFrame;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;

public class Main {

  private static KafkaCommerceView view;

  private static JFrame frame;

  public static void main(String[] args) {
    checkKafkaServer();

    frame = new JFrame("Kafka Commerce");

    view = new KafkaCommerceView(frame);

    frame.setVisible(true);
    frame.setLocationRelativeTo(frame);
    frame.setSize(850, 550);
    frame.setContentPane(view.getMainPanel());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // CONSUMIDOR PARA PAGAMENTO DOS PEDIDOS
    new Thread(() -> {
      PaymentOrderConsumer paymentOrderConsumer = new PaymentOrderConsumer();
      paymentOrderConsumer.readPaymentOrders();
    }).start();

    // CONSUMIDOR PARA ENVIO DOS PEDIDOS
    new Thread(() -> {
      SendOrderConsumer sendOrderConsumer = new SendOrderConsumer();
      sendOrderConsumer.readSendOrders();
    }).start();
  }

  public static void showOrderDetails(Order order) {
    view.showOrderDetails(frame, order);
  }

  private static void checkKafkaServer() {
    Properties properties = new Properties();
    properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

    try (AdminClient adminClient = AdminClient.create(properties)) {
      System.out.println();

      LoggerUtils.warn(
        Main.class.getSimpleName(),
        "Verificando conexão com servidor Kafka...",
        true
      );

      ListTopicsOptions options = new ListTopicsOptions();
      options.timeoutMs(5000);

      ListTopicsResult topics = adminClient.listTopics(options);
      topics.listings().get();

      LoggerUtils.info(
        Main.class.getSimpleName(),
        "Conexão com servidor Kafka bem sucedida.",
        true
      );
    }
    catch (Exception e) {
      LoggerUtils.danger(
        Main.class.getSimpleName(),
        "Não foi possível conectar ao servidor Kafka.",
        true
      );

      throw new RuntimeException(e);
    }
  }
}