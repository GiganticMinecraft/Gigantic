Command Package
======
最終更新日時 2018/12/28 執筆 tar0ss

新規コマンド実装方法
---
commandsパッケージ内にTabExecutorを実装したクラスを生成する．
そのクラスをmainクラスに追記する．
plugin.ymlにも追記する

クラス例
----
```kotlin
class TestCommand :TabExecutor{
     override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
         return true
     }

     override fun onTabComplete(p0: CommandSender?, p1: Command?, p2: String?, p3: Array<out String>?): MutableList<String> {
         return mutableListOf()
     }
 }
 ```
 
 第一引数の場合分け
 ```kotlin
 when (args[0].toLowerCase()) {
     "aaa" -> {
     }
     else -> {
     }
 }
 ```

 plugin.yml内追記例
 ---
 ```yaml
 commands:
   test:
       description: This is test command.
       usage: /test <name> <id>
       aliases: [tst,test]
       default: true
       permission: spade.test
       permission-message: You don't have <permission>
   sample:
       description: This is sample command.
       usage: /sample <help>
       aliases: [sam,sa]
       default: op
       permission: spade.sample
       permission-message: You don't have <permission>
       #…(以下に追記する
 ```