package br.com.kafka.enums;

public enum OrderStatusEnum {

  CREATED("CRIADO"),
  AWAITING_PAYMENT("AGUARDANDO PAGAMENTO"),
  PAYED("PAGO"),
  AWAITING_SHIPMENT("AGUARDANDO ENVIO"),
  SEND("ENVIADO");

  private final String description;

  OrderStatusEnum(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

}
