# SpringBoot Codespaces Training1
# 資産管理システム（研修用）

## 📌 概要

本アプリケーションは、会社の資産（PC・タブレット等）を管理するWebシステムです。
Spring Boot + Thymeleaf を用いて、CRUD処理・セッション管理・業務ロジックの基礎を学習することを目的としています。

---

## 🧰 使用技術

| 分類       | 技術                       |
| -------- | ------------------------ |
| 言語       | Java 21                  |
| フレームワーク  | Spring Boot 3            |
| テンプレート   | Thymeleaf                |
| DB（開発）   | H2 Database              |
| DB（本番想定） | PostgreSQL               |
| ビルド      | Maven                    |
| 実行環境     | GitHub Codespaces / ローカル |

---

## 🚀 起動方法

### ① ビルド

```
mvn clean install
```

### ② 起動

```
mvn spring-boot:run
```

### ③ アクセス

```
http://localhost:8080
```

---

## 🗄️ データベース

### H2コンソール

```
http://localhost:8080/h2-console
```

| 項目       | 値                  |
| -------- | ------------------ |
| JDBC URL | jdbc:h2:mem:testdb |
| User     | sa                 |
| Password | (空)                |

---

## 🧑‍💻 主な機能

### 🔐 ログイン

* ユーザ名のみでログイン
* セッションに保存
* 未ログイン時はアクセス制御

---

### 📦 資産管理

* 資産一覧表示
* 資産登録
* 状態管理

  * AVAILABLE（利用可能）
  * LOANED（貸出中）

---

### 🔄 貸出管理

* 資産をユーザに貸出
* 貸出中は再貸出不可
* 返却処理で状態を戻す

---

### 👤 ユーザ管理

* ユーザ登録
* ユーザ削除
* 貸出中ユーザは削除不可

---

## 📺 画面一覧

| 画面    | URL       | 説明      |
| ----- | --------- | ------- |
| ログイン  | `/`       | ユーザ名入力  |
| メニュー  | `/menu`   | 機能選択    |
| 資産一覧  | `/assets` | 資産表示・登録 |
| 貸出管理  | `/loans`  | 貸出・返却   |
| ユーザ管理 | `/users`  | ユーザ管理   |

---

## 🏗️ システム構成

```
Controller → Service → Repository → DB
                ↓
            Entity
                ↓
           Thymeleaf
```

---

## 🧩 クラス構成

### Controller

* LoginController：ログイン処理
* AssetController：資産管理
* LoanController：貸出管理
* UserController：ユーザ管理

### Service

* LoginService：認証処理
* AssetService：資産操作
* LoanService：貸出ロジック

### Entity

* User：ユーザ
* Asset：資産
* Loan：貸出

---

## 🔗 ER図（関係）

```
User 1 --- n Loan n --- 1 Asset
```

---

## ⚠️ 仕様上のポイント

* 資産は1つの貸出のみ
* 貸出中の資産は再貸出不可
* 貸出中のユーザは削除不可
* DBは schema.sql / data.sql で初期化

---

## 🔄 PostgreSQLへの切替

`application.properties` を変更：

```
spring.datasource.url=jdbc:postgresql://localhost:5432/test
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.database-platform=org.hibernate.d
```

