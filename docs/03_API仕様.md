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
