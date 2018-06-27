#!/bin/bash

find build/libs -name "*.jar" | grep build/libs/Gigantic | xargs -i cp {} ./server/plugins

exit