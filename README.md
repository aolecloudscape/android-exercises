# Android Appium Grader Exercises

This repository contains Android exercises for the MCC course nd the run scripts for each exercise.
There are 4 exercises available:

1. Hello World
2. QRCode
3. Image download
4. Vision API

### Running the tests locally

Each exercise contains 3 run scripts. That can be called as follows:
  - **run.sh**: script which is run by A+, identical to run-armeabi.v7a.sh except that it captures output and posts it back to A+ using [grading base](https://github.com/apluslms/grading-base.git) convenience scripts.
  - **run-armeabi-v7a.sh**: runs using the armeabi-v7a emulator, runs a xvfb session inside the container.

```sh
    $ docker run --mount type=bind,source="$(pwd)"/exercise,target=/exercise --mount type=bind,source="$(pwd)"/submission,target=/submission   aaltomcc/android-grader:armeabi-v7a /exercise/run-armeabi-v7a.sh
```

  - **run-x86.sh**: runs using the x86 emulator, runs a xvfb session inside the container, requires access to the KVM device.

```sh
    $ docker run --mount type=bind,source="$(pwd)"/exercise,target=/exercise --mount type=bind,source="$(pwd)"/submission,target=/submission  --device /dev/kvm aaltomcc/android-grader:x86 /exercise/run-x86.sh
```

  - **run-local-armeabi-v7a.sh**: runs using the armeabi-v7a emulator, runs on the X server of the host machine.

```sh
    $ docker run --net=host -e DISPLAY -e XCOOKIE='cs-114/unix:0  MIT-MAGIC-COOKIE-1  8f5d004294b25170207c92a848d63ba3' --mount type=bind,source="$(pwd)"/exercise,target=/exercise --mount type=bind,source="$(pwd)"/submission,target=/submission aaltomcc/android-grader:armeabi-v7a /exercise/run-local-armeabi-v7a.sh 
```
  - **run-local-x86.sh**: runs using the x86 emulator, runs on the X server of the host machine, requires access to the KVM device.

```sh
    $ docker run --net=host -e DISPLAY -e XCOOKIE='cs-114/unix:0  MIT-MAGIC-COOKIE-1  8f5d004294b25170207c92a848d63ba3' --mount type=bind,source="$(pwd)"/exercise,target=/exercise --mount type=bind,source="$(pwd)"/submission,target=/submission  --device /dev/kvm aaltomcc/android-grader:x86 /exercise/run-local-x86.sh 
```

Note: `cd` into the exercise folder before executing the commands.

The environment variable `APPIUM_SERVER` can be specified when running the Docker container to indicate where the Appium server is running. Default value is `http://127.0.0.1:4723`.

