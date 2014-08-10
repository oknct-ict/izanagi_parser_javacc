izanagi_parser_javacc
=====================

##ビルドに必要なもの
=====================  
*	README
=========
javaccで書かれたizanagi言語のパーサーです

ビルドに必要なもの
============
* ant
* javacc
* python

の3つです

ビルドのやり方
============
* build.propertiesの中の「javacc.dir」をjavaccのパスにします
* auto.pyがあるディレクトリで「python auto.py compile」と入力します
* コンパイル後に「python auto.py run」と入力すると実行する事ができますant
  
例
============
入力:Dim hoge as Integer  

入力:hoge = 1 + 2

