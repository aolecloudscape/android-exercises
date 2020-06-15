# Android Appium Grader Exercises

This repository contains Android exercises for the Mobile Cloud Computing course and the scripts required to run each exercise. Inside each exercise folder there is an Android application binary (submission.apk) that represents a valid submission.

There are 4 exercises available:

### 1. Hello World

Inputs random text into a text field and checks whether a greeting message is displayed with that text.

### 2. QRCode

Inputs random text into a text field and checks whether the text is rendered into a QR code.

### 3. Image download

Inputs the URL of an HTTP API that returns a list of images and verifies if the images are loaded in a list.

### 4. Vision API

Selects different pictures from the phone gallery and checks whether the app correctly determines the presence of bar codes, human faces, and features (smiles, and number of people.)

# Running the tests

Each exercise folder contains several run scripts, that can be used for integrating with A+ or for local testing.

### Running with A+ learning management system

  - **run.sh**: script which is run by A+. This script starts a headless x86 Android emulator using KVM. This script captures the standard output and posts it back to A+ using [grading base](https://github.com/apluslms/grading-base.git) convenience scripts. 
  

### Running locally without A+

These scripts are used mostly for testing the grader and developing the Unit Tests. They can be run without access to A+.

  - **run-armeabi-v7a.sh**: runs the tests on a headless armeabi-v7a emulator inside the container. 

```sh
    $ docker run --mount type=bind,source="$(pwd)"/exercise,target=/exercise --mount type=bind,source="$(pwd)"/submission,target=/submission   aaltomcc/android-grader:armeabi-v7a /exercise/run-armeabi-v7a.sh
```

  - **run-x86.sh**: runs the tests on a headless x86 emulator inside the container. Requires access to the KVM device.

```sh
    $ docker run --mount type=bind,source="$(pwd)"/exercise,target=/exercise --mount type=bind,source="$(pwd)"/submission,target=/submission  --device /dev/kvm aaltomcc/android-grader:x86 /exercise/run-x86.sh
```

  - **run-local-armeabi-v7a.sh**: runs the tests using a armeabi-v7a emulator. Runs on the X server of the host machine. This allows seeing the test as it executes on the emulator.

```sh
    $ docker run --net=host -e DISPLAY -e XCOOKIE='cs-114/unix:0  MIT-MAGIC-COOKIE-1  8f5d004294b25170207c92a848d63ba3' --mount type=bind,source="$(pwd)"/exercise,target=/exercise --mount type=bind,source="$(pwd)"/submission,target=/submission aaltomcc/android-grader:armeabi-v7a /exercise/run-local-armeabi-v7a.sh 
```
  - **run-local-x86.sh**: runs the tests using a x86 emulator. Runs on the X server of the host machine. This allows seeing the test as it executes on the emulator. Requires access to the KVM device. 

```sh
    $ docker run --net=host -e DISPLAY -e XCOOKIE='cs-114/unix:0  MIT-MAGIC-COOKIE-1  8f5d004294b25170207c92a848d63ba3' --mount type=bind,source="$(pwd)"/exercise,target=/exercise --mount type=bind,source="$(pwd)"/submission,target=/submission  --device /dev/kvm aaltomcc/android-grader:x86 /exercise/run-local-x86.sh 
```

Note 1: `cd` into the exercise folder before executing the commands.
Note 2: When using the X server of the host machine, set the correct value for the `XCOOKIE` environment variable.

