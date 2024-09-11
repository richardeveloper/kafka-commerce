# Atividade 01: Consumidores e Produtores

## Instruções
<p style="text-align: justify">Desenvolva uma aplicação de mensageria utilizando <b>Java</b> e <b>Apache Kafka</b> para simular um sistema simplificado de compras e estoque, que envolva a comunicação entre um cliente e um servidor.</p>

## Requisitos
> - <p style="text-align: justify">O sistema deverá ser dividido em duas partes principais: Cliente e Servidor.</p>
> - <p style="text-align: justify">O Cliente será responsável por iniciar o processo de compra, utilizando um produtor Kafka para enviar os detalhes da compra para o servidor.</p>
> - <p style="text-align: justify">O Servidor atuará como um consumidor Kafka, recebendo as informações da compra. Após processar a compra, o servidor deverá gerar uma resposta contendo o status da compra e enviá-la de volta ao cliente utilizando um produtor Kafka.</p>
> - <p style="text-align: justify">O Cliente também deverá ser um consumidor Kafka, recebendo o status da compra e exibindo o resultado final ao usuário.</p>
> - <p style="text-align: justify">O Cliente deverá implementar uma interface gráfica (UI) para simular a interação com o sistema de compras, onde o usuário possa iniciar uma compra e visualizar o status final da transação.</p>

## Funcionalidades obrigatórias

### Cliente
> - <p style="text-align: justify">Interface gráfica para iniciar uma compra e exibir o status da mesma.</p>
> - <p style="text-align: justify">Um produtor Kafka que envia a mensagem de compra para o servidor.</p>
> - <p style="text-align: justify">Um consumidor Kafka que recebe o status da compra do servidor e o exibe na UI.</p>

### Servidor
> - <p style="text-align: justify">Um consumidor Kafka que recebe a mensagem de compra do cliente.</p>
> - <p style="text-align: justify">Processamento da compra (verificação de estoque, pagamento, etc.).</p>
> - <p style="text-align: justify">Um produtor Kafka que envia o status da compra de volta ao cliente (aprovado, recusado, pendente).</p>

### Observações:

> - <p style="text-align: justify">Utilize tópicos separados no Kafka para cada fluxo de comunicação (por exemplo, um tópico para "compra" e outro para "status").</p>
> - <p style="text-align: justify">O foco da aplicação deve ser a integração entre cliente e servidor via Kafka, garantindo que as mensagens sejam trocadas corretamente entre as partes.</p>
> - <p style="text-align: justify">Certifique-se de que o processamento da compra no servidor seja bem definido, com regras lógicas simples para aprovação ou rejeição.</p>