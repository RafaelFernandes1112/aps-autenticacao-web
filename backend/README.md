# API de autenticação

Back-end da APS de Domínios de Software. Recebe login ou e-mail e senha, confere o usuário no SQLite e a senha com BCrypt, e devolve a identificação do usuário. Faz somente a verificação de credenciais.

O desenho correspondente no documento da APS está em [`docs/arquitetura/2.2-desenho-detalhado.md`](../docs/arquitetura/2.2-desenho-detalhado.md). O contrato está no item 2.1.5.

## Executar

É preciso Java 21, Maven e a senha do keystore na variável `KEYSTORE_PASSWORD`. O arquivo `src/main/resources/keystore.p12` é o certificado usado pelo HTTPS. A API sobe na porta **8443**.

No PowerShell, a partir desta pasta:

```powershell
$env:KEYSTORE_PASSWORD = "senha-do-keystore"
mvn spring-boot:run
```

O perfil `dev` insere um usuário de exemplo se ele ainda não existir (`login` `maria`, e-mail `maria@exemplo.com`, senha `senha123`):

```powershell
$env:KEYSTORE_PASSWORD = "senha-do-keystore"
mvn spring-boot:run "-Dspring-boot.run.profiles=dev"
```

Para gerar outro keystore de desenvolvimento:

```powershell
keytool -genkeypair -alias auth -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore src/main/resources/keystore.p12 -validity 3650
```

A senha informada ao `keytool` é o valor de `KEYSTORE_PASSWORD`.

## Contrato

`POST /api/login`

Requisição:

```json
{ "identificador": "maria@exemplo.com", "senha": "senha123" }
```

`identificador` aceita o login ou o e-mail. Os dois campos são obrigatórios.

| Situação | Status | Corpo |
| --- | --- | --- |
| Credenciais válidas | 200 | `{ "id": 1, "nome": "Maria Souza" }` |
| JSON inválido ou campo em branco | 400 | `{ "erro": "Requisição inválida" }` |
| Usuário inexistente ou senha errada | 401 | `{ "erro": "Credenciais inválidas" }` |

Exemplo, com o perfil `dev` no ar:

```powershell
'{"identificador":"maria","senha":"senha123"}' | curl.exe -k -X POST https://localhost:8443/api/login -H "Content-Type: application/json" --data-binary "@-"
```

`-k` ignora a validação do certificado autoassinado. No PowerShell, o JSON vai pela entrada padrão para as aspas chegarem inteiras ao `curl`.

## Código

| Classe | Função |
| --- | --- |
| `AuthController` | Atende `POST /api/login`, valida o corpo e traduz sucesso, 400 e 401. |
| `AuthService` | Normaliza o identificador, busca o usuário e confere a senha. |
| `UserRepository` | Consulta `login` ou `email` com `JdbcTemplate` e parâmetro. |
| `PasswordEncoder` | `BCryptPasswordEncoder`, definido em `SecurityConfig`. |
| `DevDataLoader` | Carga do usuário de exemplo, só no perfil `dev`. |

O identificador é comparado depois de `trim` e conversão para minúsculas. A senha fica só como hash BCrypt na coluna `senha_hash`. Usuário inexistente e senha errada produzem a mesma resposta. No caso de usuário inexistente, a comparação BCrypt ainda roda contra um hash fixo, para o tempo de resposta não indicar se o identificador existe.

## Banco

Arquivo `auth.db` no diretório de execução (`jdbc:sqlite:auth.db`). A tabela é criada por `schema.sql` na subida (`spring.sql.init.mode=always`).

```sql
CREATE TABLE IF NOT EXISTS usuario (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    login      TEXT NOT NULL UNIQUE CHECK (login = lower(login) AND instr(login, '@') = 0),
    email      TEXT NOT NULL UNIQUE CHECK (email = lower(email) AND instr(email, '@') > 1),
    nome       TEXT NOT NULL,
    senha_hash TEXT NOT NULL
);
```
