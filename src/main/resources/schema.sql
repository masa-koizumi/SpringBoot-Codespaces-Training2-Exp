DROP TABLE IF EXISTS loans;
DROP TABLE IF EXISTS assets;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)
    password VARCHAR(100) -- ★ ここを追加！
);

CREATE TABLE assets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    status VARCHAR(20)
);

CREATE TABLE loans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    asset_id BIGINT,
    user_id BIGINT,
    CONSTRAINT fk_asset FOREIGN KEY (asset_id) REFERENCES assets(id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);
