#!/bin/bash

export APPIUM_SERVER="${APPIUM_SERVER:-http://127.0.0.1:4723}"

# Start adb server
adb start-server

# Start appium 
appium &

# Start emulator
xvfb-run emulator64-arm -avd nexus -netdelay none -netspeed full > /dev/null 2>&1 &

# Don't exit until emulator is loaded
output=''
while [[ ${output:0:7} != 'stopped' ]]; do
  output=`adb shell getprop init.svc.bootanim`
  sleep 1
done


# Copy dependencies
cp -R /exercise/.m2 /root/

# Make writeable directory for compiling test
mkdir /exercise-run/
cp -R /exercise/* /exercise-run/
 
touch /feedback/out
touch /feedback/err

# is android running
echo "Android adb devices:" >> /feedback/out
adb devices >>/feedback/out

# Run tests
mvn -o -q -f /exercise-run/HelloUserTest/pom.xml test -P HelloUserTest

