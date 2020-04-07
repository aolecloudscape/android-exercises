#!/bin/bash

export APPIUM_SERVER="${APPIUM_SERVER:-http://127.0.0.1:4723}"

# Start adb server
adb start-server

cp -R /exercise/.m2 /root/

# Start appium 
appium &

# Start emulator
xvfb-run emulator64-x86 -avd nexus -netdelay none -netspeed full &

# Don't exit until emulator is loaded
output=''
while [[ ${output:0:7} != 'stopped' ]]; do
  output=`adb shell getprop init.svc.bootanim`
  sleep 1
done

# Run tests
mvn -o -q -f /exercise/HelloUserTest/pom.xml test -P HelloUserTest
