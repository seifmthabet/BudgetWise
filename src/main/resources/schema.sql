CREATE TABLE IF NOT EXISTS users (
    user_id     INTEGER PRIMARY KEY AUTOINCREMENT,
    name        TEXT    NOT NULL,
    email       TEXT    NOT NULL UNIQUE,
    password    TEXT    NOT NULL,
    currency    TEXT    NOT NULL DEFAULT 'USD',
    language    TEXT    NOT NULL DEFAULT 'en',
    created_at  TEXT    NOT NULL DEFAULT (date('now'))
);

CREATE TABLE IF NOT EXISTS categories (
    category_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id     INTEGER,
    name        TEXT    NOT NULL,
    is_default  INTEGER NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS transactions (
    transaction_id  INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id         INTEGER NOT NULL,
    type            TEXT    NOT NULL CHECK(type IN ('INCOME','EXPENSE')),
    amount          REAL    NOT NULL CHECK(amount > 0),
    category_id     INTEGER,
    description     TEXT,
    payment_method  TEXT,
    date            TEXT    NOT NULL,
    FOREIGN KEY (user_id)     REFERENCES users(user_id)      ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE IF NOT EXISTS budgets (
    budget_id       INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id         INTEGER NOT NULL,
    category_id     INTEGER NOT NULL,
    amount          REAL    NOT NULL CHECK(amount > 0),
    spent_amount    REAL    NOT NULL DEFAULT 0,
    start_date      TEXT    NOT NULL,
    end_date        TEXT    NOT NULL,
    alert_threshold INTEGER NOT NULL DEFAULT 80,
    FOREIGN KEY (user_id)     REFERENCES users(user_id)      ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE IF NOT EXISTS goals (
    goal_id         INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id         INTEGER NOT NULL,
    name            TEXT    NOT NULL,
    target_amount   REAL    NOT NULL CHECK(target_amount > 0),
    current_amount  REAL    NOT NULL DEFAULT 0,
    deadline        TEXT    NOT NULL,
    status          TEXT    NOT NULL DEFAULT 'IN_PROGRESS' CHECK(status IN ('IN_PROGRESS','COMPLETED')),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS notifications (
    notification_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id         INTEGER NOT NULL,
    type            TEXT    NOT NULL,
    message         TEXT    NOT NULL,
    is_read         INTEGER NOT NULL DEFAULT 0,
    timestamp       TEXT    NOT NULL DEFAULT (datetime('now')),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Seed default categories (not tied to any user)
INSERT OR IGNORE INTO categories (user_id, name, is_default) VALUES
    (NULL, 'Food & Dining',    1),
    (NULL, 'Transport',        1),
    (NULL, 'Groceries',        1),
    (NULL, 'Entertainment',    1),
    (NULL, 'Bills & Utilities',1),
    (NULL, 'Healthcare',       1),
    (NULL, 'Education',        1),
    (NULL, 'Salary',           1),
    (NULL, 'Freelance',        1),
    (NULL, 'Other',            1);