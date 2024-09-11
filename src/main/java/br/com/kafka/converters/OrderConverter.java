package br.com.kafka.converters;

import br.com.kafka.models.Order;

import br.com.kafka.utils.LoggerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OrderConverter {

  private final ObjectMapper objectMapper;

  public OrderConverter() {
    objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();
  }

  public String convertOrderToJson(Order order) {
    try {
      String json = objectMapper.writeValueAsString(order);
      return json;
    }
    catch (JsonProcessingException e) {
      LoggerUtils.danger(
        this.getClass().getSimpleName(),
        "Erro ao converter pedido em formato json.",
        true
      );

      throw new RuntimeException(e);
    }
  }

  public Order convertJsonToOrder(String json) {
    try {
      Order order = objectMapper.readValue(json, Order.class);
      return order;
    }
    catch (JsonProcessingException e) {
      LoggerUtils.danger(
        this.getClass().getSimpleName(),
        "Erro ao converter pedido no formato json.",
        true
      );

      throw new RuntimeException(e);
    }
  }

}
