# easypay

We have 2 types of users, regular ``users`` and ``merchants``; both have a wallet with money and can transfer funds between them. Let's focus exclusively on the transfer flow between two users.

Requirements:

* For both types of users, we need Full Name, CPF (Brazilian Individual Taxpayer Registry), email, and Password. CPF/CNPJ and emails must be unique in the system. Therefore, your system should allow only one registration with the same CPF or email address.

* Users can send money (make transfers) to merchants and other users.

* Merchants only receive transfers; they do not send money to anyone.

* Validate if the user has a balance before the transfer.

* Before completing the transfer, it should consult an external authorization service; [use this mock to simulate](https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc).

* The transfer operation should be a transaction (i.e., reversible in any case of inconsistency), and the money should return to the wallet of the user who initiated the transfer.

* Upon receiving payment, the user or merchant needs to receive a notification (email, SMS) sent by a third-party service, and this service may occasionally be unavailable/unstable. [Use this mock to simulate the sending](https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6).

* This service should be RESTful.

## Requirements
1. Java 17
2. Maven 3.6.3 (or above)

## Build project
```
mvn clean install
```

## Run project
```
cd .\impl\
mvn spring-boot:run
```