CREATE TABLE IF NOT EXISTS usuario (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    login      TEXT NOT NULL UNIQUE CHECK (login = lower(login) AND instr(login, '@') = 0),
    email      TEXT NOT NULL UNIQUE CHECK (email = lower(email) AND instr(email, '@') > 1),
    nome       TEXT NOT NULL,
    senha_hash TEXT NOT NULL
    );