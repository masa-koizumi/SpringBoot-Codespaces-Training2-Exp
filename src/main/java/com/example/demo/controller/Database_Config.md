# データベース構成と移行ガイド

このプロジェクトのデータベース構成と、将来的な本番環境（PostgreSQLなど）への移行手順について解説します。

## 1. 現在の構成 (開発環境)

現在は、開発の容易さを優先し、インメモリデータベースである **H2 Database** を使用しています。

*   **種類**: H2 Database (In-Memory / Embedded)
*   **特徴**:
    *   アプリケーション起動時にデータが初期化される（デフォルト）。
    *   追加のインストール不要で動作する。
    *   Spring Boot DevTools と組み合わせることで、H2 Console (`/h2-console`) からデータを直接確認可能。

## 2. 将来的な構成 (PostgreSQLへの移行)

本番運用やデータの永続化が必要になった場合、**PostgreSQL** への切り替えを推奨します。Spring Bootを使用しているため、コード（Java）の修正は原則不要で、設定ファイルの変更のみで対応可能です。

### 移行手順

#### 手順1: 依存関係の追加 (`pom.xml`)

H2の代わりに、PostgreSQLのJDBCドライバを追加します。

```xml
<!-- PostgreSQL Driver -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

#### 手順2: 接続設定の変更 (`src/main/resources/application.properties`)

H2の設定をコメントアウトし、PostgreSQLの設定を追記します。

```properties
# --- H2 Database 設定 (無効化) ---
# spring.datasource.url=jdbc:h2:mem:testdb
# spring.datasource.driverClassName=org.h2.Driver
# spring.datasource.username=sa
# spring.datasource.password=

# --- PostgreSQL 設定 (有効化) ---
spring.datasource.url=jdbc:postgresql://localhost:5432/asset_db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

※ `spring.jpa.hibernate.ddl-auto=update` に設定することで、Entityクラスの定義に基づいて自動的にテーブルが作成・更新されます。