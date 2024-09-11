package br.com.kafka.views;

import br.com.kafka.enums.PaymentMethodEnum;
import br.com.kafka.models.Order;
import br.com.kafka.models.Product;
import br.com.kafka.producers.PaymentOrderProducer;
import br.com.kafka.utils.LoggerUtils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class KafkaCommerceView {

  private JPanel mainPanel;
  private JPanel contentPanel;

  private JTextField curso1;
  private JTextField nivel1;
  private JTextField valor1;
  private JCheckBox certificado1;

  private JTextField curso2;
  private JTextField nivel2;
  private JTextField valor2;
  private JCheckBox certificado2;

  private JTextField curso3;
  private JTextField nivel3;
  private JTextField valor3;
  private JCheckBox certificado3;

  private JLabel total;

  private JPanel paymentPanel;
  private JButton confirmButton;

  private JCheckBox creditoButton;
  private JCheckBox debitoButton;
  private JCheckBox pixButton;
  private JCheckBox boletoButton;

  private JLabel espaco1;
  private JLabel espaco3;
  private JLabel espaco4;
  private JLabel espaco5;

  private final JDialog progressDialog;
  private final JProgressBar progressBar;

  private final PaymentOrderProducer producer;

  public KafkaCommerceView(JFrame frame) {
    this.actionConfirmButton(frame);
    this.actionDebitoButton(frame);
    this.actionCreditoButton(frame);
    this.actionBoletoButton(frame);
    this.actionPixButton(frame);

    this.calculateTotalValue();

    progressBar = new JProgressBar();
    progressDialog = new JDialog();

    producer = new PaymentOrderProducer();
  }

  public void actionConfirmButton(JFrame frame) {
    confirmButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        if (isAllButtonsUnchecked()) {
          JOptionPane.showMessageDialog(
            frame,
            "Favor informar a forma de pagamento.",
            "AVISO",
            JOptionPane.WARNING_MESSAGE
          );
          return;
        }

        LoggerUtils.warn(
          KafkaCommerceView.class.getSimpleName(),
          "Iniciando cadastro do pedido...",
          true
        );

        String code = UUID.randomUUID().toString()
          .substring(0, 16)
          .replace("-", "")
          .toUpperCase();

        List<Product> products = new ArrayList<>();

        products.add(new Product(curso1.getText(), new BigDecimal(formatMoney(valor1.getText()))));
        products.add(new Product(curso2.getText(), new BigDecimal(formatMoney(valor2.getText()))));
        products.add(new Product(curso3.getText(), new BigDecimal(formatMoney(valor3.getText()))));

        PaymentMethodEnum paymentMethod = getPaymentMethod();

        Order order = new Order(code, products, paymentMethod);

        LoggerUtils.info(
          KafkaCommerceView.class.getSimpleName(),
          "Pedido cadastrado com sucesso.",
          true
        );

        LoggerUtils.printOrder(KafkaCommerceView.class.getSimpleName(), order);

        producer.sendPaymentOrder(order);

        showProgressBar(frame);
      }
    });
  }

  public void actionDebitoButton(JFrame frame) {
    debitoButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        disableCreditoButton();
        disablePixButton();
        disableBoletoButton();
      }
    });
  }

  public void actionCreditoButton(JFrame frame) {
    creditoButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        disableDebitoButton();
        disablePixButton();
        disableBoletoButton();
      }
    });
  }

  public void actionPixButton(JFrame frame) {
    pixButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        disableCreditoButton();
        disableDebitoButton();
        disableBoletoButton();
      }
    });
  }

  public void actionBoletoButton(JFrame frame) {
    boletoButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        disableCreditoButton();
        disableDebitoButton();
        disablePixButton();
      }
    });
  }

  public void showOrderDetails(JFrame frame, Order order) {
    progressDialog.dispose();

    SwingUtilities.invokeLater(() -> {
      JOptionPane.showMessageDialog(
        frame,
        LoggerUtils.printFormattedOrder(order),
        "PEDIDO APROVADO",
        JOptionPane.INFORMATION_MESSAGE,
        null
      );
    });

    activateButtons();
  }

  private void showProgressBar(JFrame frame) {
    progressBar.setIndeterminate(true);
    progressBar.setPreferredSize(new java.awt.Dimension(150, 30));

    progressDialog.add(progressBar, BorderLayout.CENTER);

    progressDialog.setSize(400, 80);
    progressDialog.setLocationRelativeTo(frame);
    progressDialog.setTitle("Aguardando confirmação do pagamento...");
    progressDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

    progressDialog.setVisible(true);

    disableButtons();
  }

  private void calculateTotalValue() {
    List<JTextField> fields = List.of(valor1, valor2, valor3);

    double totalValue = 0.0;

    for (JTextField field : fields) {
      totalValue += Double.parseDouble(formatMoney(field.getText()));
    }

    this.total.setText(String.format("R$ %.2f", totalValue));
  }

  private boolean isAllButtonsUnchecked() {
    return !creditoButton.isSelected()
      && !debitoButton.isSelected()
      && !pixButton.isSelected()
      && !boletoButton.isSelected();
  }

  private void activateButtons() {
    creditoButton.setEnabled(true);
    debitoButton.setEnabled(true);
    pixButton.setEnabled(true);
    boletoButton.setEnabled(true);

    this.confirmButton.setEnabled(true);
  }

  private void disableButtons() {
    creditoButton.setSelected(false);
    debitoButton.setSelected(false);
    pixButton.setSelected(false);
    boletoButton.setSelected(false);

    creditoButton.setEnabled(false);
    debitoButton.setEnabled(false);
    pixButton.setEnabled(false);
    boletoButton.setEnabled(false);

    this.confirmButton.setEnabled(false);
  }

  private PaymentMethodEnum getPaymentMethod() {
    String paymentMethod;

    if (creditoButton.isSelected()) {
      paymentMethod = creditoButton.getText();
    }
    else if (debitoButton.isSelected()) {
      paymentMethod = debitoButton.getText();
    }
    else if (pixButton.isSelected()) {
      paymentMethod = pixButton.getText();
    }
    else {
      paymentMethod = boletoButton.getText();
    }

    return PaymentMethodEnum.parse(paymentMethod.toUpperCase());
  }

  private String formatMoney(String string) {
    return string.replace("R$ ", "");
  }

  private void disableDebitoButton() {
    if (debitoButton.isSelected()) {
      debitoButton.setSelected(false);
    }
  }

  private void disableCreditoButton() {
    if (creditoButton.isSelected()) {
      creditoButton.setSelected(false);
    }
  }

  private void disablePixButton() {
    if (pixButton.isSelected()) {
      pixButton.setSelected(false);
    }
  }

  private void disableBoletoButton() {
    if (boletoButton.isSelected()) {
      boletoButton.setSelected(false);
    }
  }

  public JPanel getMainPanel() {
    return mainPanel;
  }
}
