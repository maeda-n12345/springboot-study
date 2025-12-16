# 個人学習 あっち向いてほい Web アプリ

## 概要
本アプリは、ブラウザ上で「あっち向いてほい」ゲームをプレイできる Web アプリである。

ユーザーが方向（上・下・左・右）を選択してCPUと対戦する。
その結果をデータベースに保存し、過去の対戦履歴を一覧で確認できる。


## 主な機能
- あっち向いてほいゲームの実行
- CPUとの対戦および勝敗判定
- 対戦結果のデータベース保存
- 対戦履歴の一覧表示

## アプリ構成
- Spring Boot + MyBatis 構成
- 外部Tomcatにデプロイする構成である



## 画面表示
- 画面表示には JSP を使用している
- 一部の処理では JSONレスポンス を返す実装も含まれている

## 使用技術

### Spring Boot

#### 技術概要
Spring Boot は、Springアプリケーションの起動および設定を簡素化するための基盤技術である。  
依存関係の管理や自動設定を提供し、開発者はアプリケーション本体の実装に集中できる。

#### 本アプリでの使用箇所
- 起動クラス  
  - `jp.co.sfrontier.ss3.game.init`
    - `GameWebAppInitializer.java`
      - `@SpringBootApplication`
      - `SpringBootServletInitializer`（アプリを外部のServlet コンテナでWARとして動かくすときに使用する）

#### 特徴
- starterによる依存関係管理
- 自動設定によるSpring MVC環境の構築
- 外部Tomcatにデプロイ可能な構成

---
### Spring MVC

#### 技術概要
Spring MVC は、HTTPリクエストを受け取り、処理を振り分けるWebフレームワークである。  
画面遷移（JSP）およびJSONレスポンスの両方を扱える。

#### 本アプリでの使用箇所
- Controller
  - `jp.co.sfrontier.ss3.game.controller`
    - `LookOverThereController.java`
      - `@Controller`
      - `@RequestMapping`
      - `@GetMapping`
      - `@PostMapping`
      - `@ResponseBody`

- MVC設定
  - `jp.co.sfrontier.ss3.game.config`
    - `WebConfig.java`
      - `@EnableWebMvc`
      - `InternalResourceViewResolver`


---
### BOM（Bill of Materials）

#### 技術概要
BOM（Bill of Materials）は、複数のライブラリのバージョンを一括で管理する仕組みである。  
Spring Boot では、動作確認済みのライブラリ組み合わせを BOM として提供している。

#### 本アプリでの使用箇所
- `pom.xml`
  - `spring-boot-dependencies` を BOM として利用

#### 特徴
- 依存関係に個別の version を記述する必要がない
- Spring関連ライブラリ間のバージョン不整合を防止できる
- Spring Boot のバージョン変更だけで一括更新が可能である

---
### MyBatis

#### 技術概要
MyBatis は、SQLをXMLで管理し、Javaから呼び出すためのDBアクセスフレームワークである。  
SQLを明示的に記述できる点が特徴である。

#### 本アプリでの使用箇所
- Mapperインターフェース
  - `jp.co.sfrontier.ss3.game.mapper`
    - `MatchResultMapper.java`

- SQL定義
  - `src/main/resources/mapper/MatchResultMapper.xml`

- 設定ファイル
  - `src/main/resources/mybatis-config.xml`

---
### JSP / JSTL

#### 技術概要
JSP（JavaServer Pages）は、サーバー側でHTMLを生成するための技術である。  
JSTLを用いることで、ループや条件分岐などを簡潔に記述できる。

#### 本アプリでの使用箇所
- JSPファイル
  - `src/main/webapp/WEB-INF/jsp/lookoverthere/play.jsp`

- JSTLタグ
  - `c:out`
  - `c:forEach`

---
### JSPカスタムタグ

#### 技術概要
JSPカスタムタグは、JSP内の共通処理や表示ロジックを再利用するための仕組みである。  
JSPの可読性と保守性を向上させる。

#### 本アプリでの使用箇所
- カスタムタグクラス
  - `jp.co.sfrontier.ss3.game.tag`
    - `JoinTag.java`

---
### JSONレスポンス（@ResponseBody）

#### 技術概要
画面（HTML）ではなく、処理結果のデータを JSON 形式で返却する方式である。  
画面遷移を伴わない通信（Ajax等）を想定した実装が可能である。

#### 本アプリでの使用箇所
- Controller
  - `LookOverThereController.java`
    - `@PostMapping`
    - `@ResponseBody`
    - 戻り値：`LookOverThereMatchResult`

---
### Lombok

#### 技術概要
Lombok は、getter / setter / toString などをアノテーションにより自動生成するライブラリである。

#### 本アプリでの使用箇所
- 値オブジェクト
  - `jp.co.sfrontier.ss3.game.value`
    - `LookOverThereMatchResult.java`
      - `@Data`

#### 利用目的
- JSONレスポンス用オブジェクトを簡潔に記述するため
- コード量削減および可読性向上のため

---
### JUnit 5（JUnit Jupiter）
- Javaの標準的なテストフレームワークである
- テストケースの定義および実行を担当する

---
### AssertJ
- テスト結果を検証するためのアサーションライブラリである
- テストコードを読みやすくすることができる。

---
### Spring Test

#### 技術概要
Spring Test は、Spring Framework が提供するテスト支援ライブラリである。  
Spring の DI コンテナや各種設定を読み込んだ状態でテストを実行できる。

#### 利用目的
- Spring Boot の設定を含めた状態でテストを実行するため
- Controller や DBアクセスを含む処理を、実行環境に近い形で検証するため

#### 本アプリでの位置づけ
- Spring Boot アプリケーションコンテキストを起動した状態でテストを実行
- DBアクセスやトランザクションを含む処理の検証に利用


---
### DBUnit

#### 技術概要
DBUnit は、データベースを利用する処理をテストするためのライブラリである。  
テスト実行前後でデータベースの状態を制御し、  
常に同一条件でテストを実行できるようにする。

#### 利用目的
- テスト実行前にデータベースを既知の状態に初期化するため
- テスト後にデータをクリーンな状態に戻すため
- DBアクセスを含む処理の再現性を確保するため

#### 本アプリでの位置づけ
- DBアクセスを含む処理のテストに使用
- テスト実行順や過去の実行結果に依存しないテストを実現

