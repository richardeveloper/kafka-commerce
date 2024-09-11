package br.com.kafka.utils;

import br.com.kafka.models.Order;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.time.format.DateTimeFormatter;

public class LoggerUtils {

  private static final String HTML_PATH = "src/main/resources/payment-order.html";

  private static final String ANSI_CODE_GREEN = "\u001B[32m";
  private static final String ANSI_CODE_YELLOW = "\u001B[33m";
  private static final String ANSI_CODE_RED = "\u001B[31m";
  private static final String ANSI_CODE_RESET = "\u001B[0m";

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS");

  public static void print(String className, String message, boolean skipLine) {
    System.out.printf("[ %s ] - %s%n", className, message);
    printBlankLine(skipLine);
  }

  public static void info(String className, String message, boolean skipLine) {
    System.out.printf("%s[ %s ] - %s%s%n", ANSI_CODE_GREEN, className, message, ANSI_CODE_RESET);
    printBlankLine(skipLine);
  }

  public static void warn(String className, String message, boolean skipLine) {
    System.out.printf("%s[ %s ] - %s%s%n", ANSI_CODE_YELLOW, className, message, ANSI_CODE_RESET);
    printBlankLine(skipLine);
  }

  public static void danger(String className, String message, boolean skipLine) {
    System.out.printf("%s[ %s ] - %s%s%n", ANSI_CODE_RED, className, message, ANSI_CODE_RESET);
    printBlankLine(skipLine);
  }

  public static void printOrder(String className, Order order) {
    StringBuilder builder = new StringBuilder()
      .append("DETALHES DO PEDIDO").append("\n")
      .append("CÓDIGO: ").append(order.getCode()).append("\n")
      .append("STATUS: ").append(order.getStatus().getDescription()).append("\n")
      .append("VALOR TOTAL: R$ ").append(order.getTotalValue().toString()).append("\n")
      .append("FORMA DE PAGAMENTO: ").append(order.getPaymentMethod().getDescription()).append("\n")
      .append("DATA DO PEDIDO: ").append(order.getCreateDate().format(FORMATTER)).append("\n");

    switch (order.getStatus()) {
      case AWAITING_PAYMENT:
        builder.append("DATA DE CONFIRMAÇÃO: ").append(order.getConfirmationDate().format(FORMATTER)).append("\n");
        break;
      case PAYED:
        builder.append("DATA DE CONFIRMAÇÃO: ").append(order.getConfirmationDate().format(FORMATTER)).append("\n");
        builder.append("DATA DE PAGAMENTO: ").append(order.getPaymentDate().format(FORMATTER)).append("\n");
        break;
      case SEND:
        builder.append("DATA DE CONFIRMAÇÃO: ").append(order.getConfirmationDate().format(FORMATTER)).append("\n");
        builder.append("DATA DE PAGAMENTO: ").append(order.getPaymentDate().format(FORMATTER)).append("\n");
        builder.append("DATA DE ENVIO: ").append(order.getSendDate().format(FORMATTER)).append("\n");
        break;
    }

    String[] lines = builder.toString().split("\n");

    for (String line : lines) {
      print(className, line, false);
    }

    printBlankLine(true);
  }

  public static String printFormattedOrder(Order order) {
    try {
      StringBuilder builder = new StringBuilder();

      BufferedReader reader = new BufferedReader(new FileReader(HTML_PATH));

      reader.lines().forEach(builder::append);

      String html = builder.toString();

      return html
        .replace("CODIGO", order.getCode())
        .replace("STATUS", order.getStatus().getDescription())
        .replace("FORMA_DE_PAGAMENTO", order.getPaymentMethod().getDescription())
        .replace("VALOR_TOTAL", order.getTotalValue().toString())
        .replace("DATA_PEDIDO", order.getCreateDate().format(FORMATTER))
        .replace("DATA_PAGAMENTO", order.getPaymentDate().format(FORMATTER));
    }
    catch (FileNotFoundException e) {
      LoggerUtils.danger(
        LoggerUtils.class.getSimpleName(),
        "O arquivo informado não foi encontrado.",
        true
      );

      throw new RuntimeException(e);
    }
  }

  private static void printBlankLine(boolean nextLine) {
    if (nextLine) {
      System.out.println();
    }
  }

}