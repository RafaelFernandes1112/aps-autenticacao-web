# Projeto de um software no domínio de aplicações Web.

APS (Atividades Práticas Supervisionadas) da disciplina **Domínios de Software** (Bacharelado em Engenharia de Software, INF/UFG, 2026/2).

## O projeto

O escopo é um **software Web para autenticação de usuários**. Ele recebe credenciais (identificador ou e-mail + senha), verifica se o usuário existe no repositório de dados e compara a credencial fornecida com a registrada. Se as credenciais forem válidas, devolve um objeto com a identificação do usuário. Se não forem, devolve um erro de autenticação. O software faz somente a verificação de credenciais.

## Equipe

Cada item de informação do documento identifica seu autor e seu revisor. Cada membro escreve uma seção e revisa outra, e ninguém revisa o que escreveu.

| Membro           | Usuário GitHub      |
| ---------------- | ------------------- |
| Caique Duarte    | caiqueduart         |
| Rafael Fernandes | RafaelFernandes1112 |
| Vitória Luz      | Vitorialuz229       |

## Stack

| Camada         | Tecnologia                                                       |
| -------------- | ---------------------------------------------------------------- |
| Front-end      | HTML + TypeScript (compilado com `tsc`, requisições com `fetch`) |
| Back-end       | Java 21 com Spring Boot                                          |
| Hash de senha  | BCrypt (`spring-security-crypto`)                                |
| Banco de dados | SQLite                                                           |

## Estrutura

O código é só um esqueleto, já que a APS pede o projeto e não a implementação.

```
backend/   API em Java com Spring Boot e SQLite (projeto Maven)
docs/      definição da APS e artefatos do documento final
```

Para compilar o back-end: `mvn package` dentro de `backend/`.

## Documentação

A pasta [`docs/`](docs/) traz a [definição da APS](docs/2026-2-Domnios_de_Software-Definio_de_APS_v2.pdf). É nela também que está os artefatos necessário para montar o PDF final do trabalho.

Os diagramas são feitos no [draw.io](https://app.diagrams.net/).
