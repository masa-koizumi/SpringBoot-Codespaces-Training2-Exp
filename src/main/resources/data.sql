-- 1. ユーザーデータの登録
INSERT INTO users (name, password) VALUES ('Alice', 'a123');
INSERT INTO users (name, password) VALUES ('Bob', 'b456');
INSERT INTO users (name, password) VALUES ('Charlie', 'c789');

-- 2. 資産データの登録
INSERT INTO assets (name, status) VALUES ('MacBook Pro', 'LOANED');
INSERT INTO assets (name, status) VALUES ('iPad Air', 'LOANED');
INSERT INTO assets (name, status) VALUES ('Dell Monitor 24inch', 'AVAILABLE');
INSERT INTO assets (name, status) VALUES ('iPhone 15', 'AVAILABLE');
INSERT INTO assets (name, status) VALUES ('Web Camera', 'LOANED');

-- 3. 貸出情報の登録
-- 期限内
INSERT INTO loans (asset_id, user_id, loan_date, period_days) 
VALUES (1, 1, '2026-03-20', 7);

-- 期限切れ（赤字テスト用）
INSERT INTO loans (asset_id, user_id, loan_date, period_days) 
VALUES (2, 2, '2026-03-01', 7);

-- 最近の貸出
INSERT INTO loans (asset_id, user_id, loan_date, period_days) 
VALUES (5, 3, '2026-03-21', 14);
