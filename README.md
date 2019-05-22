# Tigase RPi

Project for RPi library created specifically for our use at Tigase. 


This is kind of a wrapper over pi4j and wiring low level library. The goal is to expose high level devices and sensors which can be easily used in code and loaded as plugins.


The library supports directly Raspberry Pi board or GrovePi board as well as Pi2Grover and possibly others in the future. The support for a correct board is turned ON transparently based on what is connected to the RPi. From the developer point of view and from the devices/sensors code point of view it does not matter whether the sensors is connected through GrivePi or directly, the same code and the same addressing should work. A number of devices and sensors are already supported and more will come.


The library is based on the  [GrovePi Java library](https://github.com/DexterInd/GrovePi/tree/master/Software/Java)  but is heavily refactored and reimplemented and cleaned up and no longer requires or depends solely on GrovePi board. For this reason the original package names have been changed from **com.dexterind.grovepi** to *tigase.rpi*.
