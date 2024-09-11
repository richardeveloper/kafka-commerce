package br.com.kafka.enums;

public enum PaymentMethodEnum {

  CREDIT("CARTÃO DE CRÉDITO"),
  DEBIT("CARTÃO DE DÉBITO"),
  PIX("PIX"),
  BILLET("BOLETO BANCÁRIO");

  private final String description;

  PaymentMethodEnum(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public static PaymentMethodEnum parse(String description) {
    if (description == null) {
      return null;
    }
    for (PaymentMethodEnum paymentMethod : PaymentMethodEnum.values()) {
      if (paymentMethod.getDescription().equalsIgnoreCase(description)) {
        return paymentMethod;
      }
    }
    throw new IllegalArgumentException("A forma de pagamento informada não foi identificada.");
  }

}
