# BuildItBigger

App that is used to display jokes retrieved from a Google Cloud Endpoints module. It has a free and paid product flavour. Free version has banner and interstitial ads. There a number of modules:

* app - Contains the source of the joke telling app
* jokelib - Joke provider Java library
* jokebackend - Google cloud endpoints module that retrieves jokes from jokelib
* jokeview - Android module that displays the joke in a separate activity

Also includes a gradle task that automatically runs appengine module in daemon mode, executes android tests and turns off the appengine module.

Developed as part of coursework for [Udacity Android Nanodegree](https://www.udacity.com/course/android-developer-nanodegree--nd801).

## Screencast
Screencast can be found [here](https://youtu.be/nfQHzJr-qHo).

## How to use
Either clone this repository using git or download as zip (and unzip). Import in Android Studio.

### Testing on emulator
* Change run configuration to 'jokebackend' and run. Verify that the appengine module has been successfully started by opening http://localhost:8080/ in a browser.
* Change run configuration to 'app' and launch app in emulator.

### Testing on a real Android device
* Ensure the device running appengine module and your android device are connected to the same network.
* In BuildItBigger/jokebackend/build.gradle, uncomment the line httpAddress in appengine closure.
* In BuildItBigger/app/build.gradle, replace the GCE_ROOT_URL string with the ip address of the machine on which appengine module is running. You can get this by running ifconfig. Basically "http://10.0.2.2:8080/_ah/api/" will be replaced by "http://YOUR_IP_ADDRESS:8080/_ah/api/".
* Perform a gradle sync.
* Change run configuration to 'jokebackend' and run. Verify that the appengine module has been successfully started by opening http://localhost:8080/ in a browser.
* Change run configuration to 'app' and launch app in your device.

## License
    Copyright 2017 Darshan Dorai.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

