Gigantic Plugin
======
Gigantic Plugin（以下、当プラグイン）は、[ギガンティック☆整地鯖][seichi.click]（以下、当サーバ）のSpigotプラグインです。<br />

Description
---
当プラグインはMySQLを用いて全データベースを管理しています．
デバッグサーバ起動時には”gigantic”データベースを作成済であることを確認してください．


Development
---
* [IntelliJ IDEA 2018.2.3](https://www.jetbrains.com/idea/)
* [java 1.8](http://www.oracle.com/technetwork/java/javase/overview/index.html)
* [spigot 1.13.2-R0.1-SNAPSHOT](https://www.spigotmc.org/)

Requirement
---

* [ProtocolLib 4.4.0-SNAPSHOT](https://dev.bukkit.org/projects/coreprotect)


Licence
---
Copyright &copy; 2018 [unchama](https://github.com/unchama/) by [ギガンティック☆整地鯖][seichi.click]

[No License](https://choosealicense.com/no-license/) : **We are not offering any license.**

当プラグインはライセンスを提供せず、使用を禁じます。<br />
当プラグインの著作権を含む全ての権利は、[unchama](https://github.com/unchama/)及び[ギガンティック☆整地鯖][seichi.click]が所有しています。<br />

[seichi.click]: http://seichi.click/


## Kotlin Style Guide
基本的には[スマートテック・ベンチャーズ Kotlinコーディング規約](https://github.com/SmartTechVentures/kotlin-style-guide)に準拠します。

## Nullable
!!演算子は，原則使用禁止とするが，Nullではないことが明確な場合は使用可能とする．


## JavaDocs
publicなメソッドについては、JavaDocsを記載するよう心がけてください。
その他は各自が必要だと判断した場合のみ記載してください。

## Commit Style
1コミットあたりの情報は最小限としてください。<br />
コミットメッセージは英語の動詞から始めることを推奨しています。

## Branch Model
[Git-flow](https://qiita.com/KosukeSone/items/514dd24828b485c69a05)を簡略化したものを使用します。<br>
新規に機能を開発する際はdevelopブランチからfeatureブランチを作り、そこで作業してください。<br />
開発が終了したらdevelopブランチにマージします。<br>
masterブランチは本番環境に反映されます。<br />
本番環境を更新するタイミングでdevelopブランチをmasterブランチにマージします。