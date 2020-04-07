#!/bin/bash

[ -z "$XCOOKIE" ] && echo "Need to set XCOOKIE environment variable with the result of \`xauth list\` to connect to your display" && exit 1;

export APPIUM_SERVER="${APPIUM_SERVER:-http://127.0.0.1:4723}"

xauth add $XCOOKIE

adb start-server

cp -R /exercise/.m2 /root/

# Start appium 
appium &

# Start emulator
emulator64-arm -avd nexus -netdelay none -netspeed full &

# Don't exit until emulator is loaded
output=''
while [[ ${output:0:7} != 'stopped' ]]; do
  output=`adb shell getprop init.svc.bootanim`
  sleep 1
done

# Run tests
mvn -o -q -f /exercise/ImageDownloadTest/pom.xml test -P ImageDownloadTest
