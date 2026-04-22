# SpringBoot Codespaces Training2
# 資産管理システム（研修用）

## 📌 概要

本アプリケーションは、会社の資産（PC・タブレット等）を管理するWebシステムです。
Spring Boot + Thymeleaf を用いて、CRUD処理・セッション管理・業務ロジックの基礎を学習することを目的としています。

---

## 📚 ドキュメント

詳細な仕様や設計については、以下のドキュメントを参照してください。

* [01. システム概要](docs/01_システム概要.md)
* [02. 機能仕様](docs/02_機能仕様.md)
* [03. API仕様](docs/03_API仕様.md)
* [04. クラス設計](docs/04_クラス設計.md)
* [05. 画面遷移図](docs/05_画面遷移図.md)
* [06. DB設計](docs/06_DB設計.md)

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

## 🧪 テスト実行

以下のコマンドで単体テストを実行できます。

```
mvn test
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

* ユーザー名とパスワードによる認証
* セッション管理（HTTPSession）
* 未ログイン時のアクセス制御（ログイン画面へリダイレクト）

---

### 📦 資産管理

* 資産一覧表示
* 新規資産登録
* ステータス管理
  * AVAILABLE（利用可能）
  * LOANED（貸出中）

---

### 🔄 貸出管理

* 資産の貸出処理（トランザクション管理）
* 貸出制約（貸出中の資産は選択不可）
* 返却処理（ステータス更新）

---

### 👤 ユーザー管理

* ユーザー一覧表示
* 新規ユーザー登録
* ユーザー削除（貸出中の資産があるユーザーは削除不可）

---

## 🛠️ 研修課題

本プロジェクトを利用した機能追加の実習課題です。3時間程度の作業を想定しています。

*   **課題：資産の保管場所管理機能の追加**
    *   内容: DBカラム追加から画面表示までの一通りの改修（フルスタック開発体験）
    *   目安時間: 3時間

---

## � 画面一覧

| 画面    | URL       | 説明      |
| ----- | --------- | ------- |
| ログイン  | `/`       | ユーザー名入力  |
| メニュー  | `/menu`   | 機能選択    |
| 資産一覧  | `/assets` | 資産表示・登録 |
| 貸出管理  | `/loans`  | 貸出・返却   |
| ユーザー管理 | `/users`  | ユーザー管理   |

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
* UserController：ユーザー管理

### Service

* LoginService：認証処理
* AssetService：資産操作
* LoanService：貸出ロジック

### Entity

* User：ユーザー
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
* 貸出中のユーザーは削除不可
* DBは schema.sql / data.sql で初期化

---

## 🔄 PostgreSQLへの切替

`application.properties` を変更：

```
spring.datasource.url=jdbc:postgresql://localhost:5432/test
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```
