# Atividade 01: Consumidores e Produtores

## Instruções
Desenvolva uma aplicação de mensageria utilizando Java e Apache Kafka para simular um sistema simplificado de compras e estoque, que envolva a comunicação entre um cliente e um servidor.

## Requisitos
- O sistema deverá ser dividido em duas partes principais: Cliente e Servidor.
- O Cliente será responsável por iniciar o processo de compra, utilizando um produtor Kafka para enviar os detalhes da compra para o servidor.
- O Servidor atuará como um consumidor Kafka, recebendo as informações da compra. 
- Após processar a compra, o servidor deverá gerar uma resposta contendo o status da compra e enviá-la de volta ao cliente utilizando um produtor Kafka.
- O Cliente também deverá ser um consumidor Kafka, recebendo o status da compra e exibindo o resultado final ao usuário.
- O Cliente deverá implementar uma interface gráfica (UI) para simular a interação com o sistema de compras, onde o usuário possa iniciar uma compra e visualizar o status final da transação.

## Funcionalidades obrigatórias

### Cliente
Interface gráfica para iniciar uma compra e exibir o status da mesma.
Um produtor Kafka que envia a mensagem de compra para o servidor.
Um consumidor Kafka que recebe o status da compra do servidor e o exibe na UI.

### Servidor
Um consumidor Kafka que recebe a mensagem de compra do cliente.
Processamento da compra (verificação de estoque, pagamento, etc.).
Um produtor Kafka que envia o status da compra de volta ao cliente (aprovado, recusado, pendente).

### Observações:

> Utilize tópicos separados no Kafka para cada fluxo de comunicação (por exemplo, um tópico para "compra" e outro para "status").
> O foco da aplicação deve ser a integração entre cliente e servidor via Kafka, garantindo que as mensagens sejam trocadas corretamente entre as partes.
> Certifique-se de que o processamento da compra no servidor seja bem definido, com regras lógicas simples para aprovação ou rejeição.