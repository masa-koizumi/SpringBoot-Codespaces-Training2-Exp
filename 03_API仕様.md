# API仕様書（エンドポイント一覧）

本システムは Spring MVC + Thymeleaf で構築されているため、APIは主に画面遷移とFormデータのPOST処理となります。

## 1. 共通事項

- **ベースURL**: `http://localhost:8080`
- **セッション管理**: ログイン成功時に `HttpSession` を利用してユーザー情報を保持します。

## 2. 認証機能 (LoginController)

| メソッド | パス | 機能概要 | リクエストパラメータ | レスポンス / 遷移先 |
| :--- | :--- | :--- | :--- | :--- |
| GET | `/` | ログイン画面表示 | なし | `login.html` |
| POST | `/login` | ログイン処理 | `name`, `password` | 成功: `/menu` (Redirect)<br>失敗: `login.html` (エラー表示) |
| GET | `/logout` | ログアウト処理 | なし | `/` (Redirect) |
| GET | `/menu` | メニュー画面表示 | なし | `menu.html` |

## 3. 資産管理機能 (AssetController)

| メソッド | パス | 機能概要 | リクエストパラメータ | レスポンス / 遷移先 |
| :--- | :--- | :--- | :--- | :--- |
| GET | `/assets` | 資産一覧画面表示 | なし | `assets/list.html` |
| POST | `/assets/add` | 新規資産登録 | `name` | 成功: `/assets` (Redirect)<br>失敗: `/assets` (エラー表示) |

## 4. 貸出管理機能 (LoanController)

| メソッド | パス | 機能概要 | リクエストパラメータ | レスポンス / 遷移先 |
| :--- | :--- | :--- | :--- | :--- |
| GET | `/loans` | 貸出管理画面表示 | なし | `loans/list.html` |
| POST | `/loans/add` | 貸出登録 | `assetId`, `userId`, <br>`loanDate`, `periodDays` | 成功: `/loans` (Redirect)<br>失敗: `/loans` (エラー表示) |
| POST | `/loans/return` | 返却処理 | `loanId` | `/loans` (Redirect) |

## 5. ユーザー管理機能 (UserController)

| メソッド | パス | 機能概要 | リクエストパラメータ | レスポンス / 遷移先 |
| :--- | :--- | :--- | :--- | :--- |
| GET | `/users` | ユーザー一覧画面表示 | なし | `users/list.html` |
| POST | `/users/add` | 新規ユーザー登録 | `name`, `password`, `role` | `/users` (Redirect) |
| POST | `/users/delete/{id}` | ユーザー削除 | PathVariable: `id` | 成功: `/users` (Redirect)<br>失敗: `/users` (エラー表示) |

## 6. エラーハンドリング

- **バリデーションエラー**: 入力画面に戻り、エラーメッセージを表示する。
- **業務エラー（削除制限など）**: 一覧画面に戻り、フラッシュメッセージ等で理由を表示する。
- **システムエラー**: 共通エラー画面 (`error.html`) へ遷移する（想定）。