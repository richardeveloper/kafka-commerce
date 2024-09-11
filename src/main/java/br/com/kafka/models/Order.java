package br.com.kafka.models;

import br.com.kafka.enums.OrderStatusEnum;
import br.com.kafka.enums.PaymentMethodEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {

  private String code;

  private LocalDateTime createDate;

  private LocalDateTime confirmationDate;

  private LocalDateTime paymentDate;

  private LocalDateTime sendDate;

  private OrderStatusEnum status;

  private PaymentMethodEnum paymentMethod;

  private BigDecimal totalValue;

  private final List<Product> products = new ArrayList<>();

  public Order() {

  }

  public Order(String code, List<Product> products, PaymentMethodEnum paymentMethod) {
    this.code = code;
    this.products.addAll(products);
    this.paymentMethod = paymentMethod;
    this.createDate = LocalDateTime.now();
    this.status = OrderStatusEnum.CREATED;
    this.calculateTotalValue();
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }

  public LocalDateTime getConfirmationDate() {
    return confirmationDate;
  }

  public void setConfirmationDate(LocalDateTime confirmationDate) {
    this.confirmationDate = confirmationDate;
  }

  public LocalDateTime getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(LocalDateTime paymentDate) {
    this.paymentDate = paymentDate;
  }

  public OrderStatusEnum getStatus() {
    return status;
  }

  public void setStatus(OrderStatusEnum status) {
    this.status = status;
  }

  public PaymentMethodEnum getPaymentMethod() {
    return paymentMethod;
  }

  public LocalDateTime getSendDate() {
    return sendDate;
  }

  public void setSendDate(LocalDateTime sendDate) {
    this.sendDate = sendDate;
  }

  public BigDecimal getTotalValue() {
    return totalValue;
  }

  public List<Product> getProducts() {
    return products;
  }

  private void calculateTotalValue() {
    this.totalValue = products.stream()
      .map(Product::getPrice)
      .reduce(BigDecimal::add)
      .orElse(BigDecimal.ZERO);
  }
}
