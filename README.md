# Android Exercises

This repository contains the Android exercises used in the Fall 2017 edition of course [CS-E4100 Mobile Cloud Computing](https://oodi.aalto.fi/a/opintjakstied.jsp?html=1&kieli=6&Tunniste=CS-E4100&Ajankohta=13-09-2017) at Aalto University. This repository contains the *standalone* content with minimal dependencies on the [A+ Learning Management System](https://apluslms.github.io/). See also <https://cloudscape.aalto.fi> for more details.

Four exercises are available:

1. *Hello User*: inputs random text into a text field and checks whether a greeting message is displayed with that text.
2. *QR Code*: Inputs random text into a text field and checks whether the text is rendered into a QR code.
3. *Image List*: Inputs the URL of an HTTP API that returns a list of images and verifies if the images are loaded in a list.
4. *Image Detection*: Selects different pictures from the phone gallery and checks whether the app correctly determines the presence of bar codes, human faces, and features (smiles, and number of people.)

Exercises run unit tests on a binary Android binary application package by using the [Appium](http://appium.io/) mobile app testing framework.

The repository also contains sample Android applications that passes all tests for a given exercise. They can be found in the `submission/user` subfolders.


# Running the tests

Each exercise folder contains several run scripts, that can be used for integration with the [A+ learning management system](https://apluslms.github.io/) or for local testing.

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

