# API仕様

## ログイン
POST /login

| パラメータ | 型 | 説明 |
|-----------|----|------|
| name | String | ユーザ名 |

---

## 資産一覧
GET /assets

---

## 資産登録
POST /assets

| パラメータ | 型 |
|-----------|----|
| name | String |

---

## 貸出
POST /loans

| パラメータ | 型 |
|-----------|----|
| assetId | Long |
| userId | Long |

---

## 返却
POST /loans/return

| パラメータ | 型 |
|-----------|----|
| loanId | Long |

---

## ユーザ一覧
GET /users

---

## ユーザ登録
POST /users

| パラメータ | 型 | 説明 |
|-----------|----|------|
| name | String | ユーザ名 |

---

## ユーザ削除
POST /users/delete

| パラメータ | 型 |
|-----------|----|
| id | Long |

---

## パスワード設定
GET /user/setup-password (画面表示)
POST /user/setup-password (更新処理)

| パラメータ | 型 | 説明 |
|-----------|----|------|
| userId | Long | 対象ユーザID |
| password | String | 新しいパスワード |

---

## 権限変更
POST /users/update-role

| パラメータ | 型 | 説明 |
|-----------|----|------|
| id | Long | 対象ユーザID |
