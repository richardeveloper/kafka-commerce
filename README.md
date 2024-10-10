# ADS1242 - Atividade 01: Consumidores e Produtores

<div align="center">

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-000?style=for-the-badge&logo=apachekafka)

</div>

## Instruções
<p align="justify">Desenvolva uma aplicação de mensageria utilizando <b>Java</b> e <b>Apache Kafka</b> para simular um sistema simplificado de compras e estoque, que envolva a comunicação entre um cliente e um servidor.</p>

## Requisitos
> - <p align="justify">O sistema deverá ser dividido em duas partes principais: Cliente e Servidor.</p>
> - <p align="justify">O Cliente será responsável por iniciar o processo de compra, utilizando um produtor Kafka para enviar os detalhes da compra para o servidor.</p>
> - <p align="justify">O Servidor atuará como um consumidor Kafka, recebendo as informações da compra. Após processar a compra (como verificar o estoque ou o pagamento), o servidor deverá gerar uma resposta contendo o status da compra (aprovada, recusada, etc.) e enviá-la de volta ao cliente utilizando um produtor Kafka.</p>
> - <p align="justify">O Cliente também deverá ser um consumidor Kafka, recebendo o status da compra e exibindo o resultado final ao usuário.</p>
> - <p align="justify">O Cliente deverá implementar uma interface gráfica (UI) para simular a interação com o sistema de compras, onde o usuário possa iniciar uma compra e visualizar o status final da transação.</p>

## Funcionalidades obrigatórias

### Cliente
> - <p align="justify">Interface gráfica para iniciar uma compra e exibir o status da mesma.</p>
> - <p align="justify">Um produtor Kafka que envia a mensagem de compra para o servidor.</p>
> - <p align="justify">Um consumidor Kafka que recebe o status da compra do servidor e o exibe na UI.</p>

### Servidor
> - <p align="justify">Um consumidor Kafka que recebe a mensagem de compra do cliente.</p>
> - <p align="justify">Processamento da compra (verificação de estoque, pagamento, etc.).</p>
> - <p align="justify">Um produtor Kafka que envia o status da compra de volta ao cliente (aprovado, recusado, pendente).</p>

### Observações:

> - <p align="justify">Utilize tópicos separados no Kafka para cada fluxo de comunicação (por exemplo, um tópico para "compra" e outro para "status").</p>
> - <p align="justify">O foco da aplicação deve ser a integração entre cliente e servidor via Kafka, garantindo que as mensagens sejam trocadas corretamente entre as partes.</p>
> - <p align="justify">Certifique-se de que o processamento da compra no servidor seja bem definido, com regras lógicas simples para aprovação ou rejeição.</p>
