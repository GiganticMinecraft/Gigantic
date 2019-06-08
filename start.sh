#!/bin/bash

SERVER=''

# コンフィグファイルを読み込み
. ./server.conf

while true;do

# サーバー起動
cd ./server
java -Dfile.encording=UTF-8 -verbose:gc -server -Xms2G -Xmx2G -XX:MetaspaceSize=512M -XX:+UseG1GC -XX:+UseStringDeduplication -XX:+DisableExplicitGC -XX:+UseCompressedOops -XX:+OptimizeStringConcat -XX:+UseTLAB -jar ${SERVER}.jar
cd ../

echo "Restarting in 10 seconds..."
sleep 10s

done