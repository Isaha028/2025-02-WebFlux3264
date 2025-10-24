-- Eliminar tabla si existe
DROP TABLE IF EXISTS accounts;

-- Crear tabla accounts
CREATE TABLE accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(255) UNIQUE NOT NULL,
    owner_name VARCHAR(255) NOT NULL,
    balance DECIMAL(19,2) NOT NULL,
    active BOOLEAN DEFAULT TRUE
);

-- Índice para búsquedas rápidas
CREATE INDEX idx_account_number ON accounts(account_number);