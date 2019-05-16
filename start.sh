#!/bin/bash

while true;do

# サーバー起動
cd ./server
java -Dfile.encording=UTF-8 -verbose:gc -server -Xms2G -Xmx2G -XX:MetaspaceSize=512M -XX:+UseG1GC -XX:+UseStringDeduplication -XX:+DisableExplicitGC -XX:+UseCompressedOops -XX:+OptimizeStringConcat -XX:+UseTLAB -jar spigot-1.14.1.jar
cd ../

echo "Restarting in 10 seconds..."
sleep 10s

done