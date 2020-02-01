# set-input-source
SmartThings SmartApp to Set the Input Source of a media player when a switch/sensor closes.

I use this to turn on the "Main Zone" and select the "TV Audio" input on my Denon AVR when the connected TV turns on. The Denon AVR device handler types are provided as part of the [upnp-connect](https://github.com/rtyle/upnp-connect) project.

Unfortunately, my old TV, by itself, is not smart enough to tell SmartThings when it has turned on or off. I use a [current switch](https://www.amazon.com/gp/product/B00I9IFJOM) wired to the dry switch contacts of an [open/close Z-Wave sensor](https://www.amazon.com/gp/product/B07PDDX3K6) that is supported by SmartThings to do this. When the TV is turned on, it draws current, the dry switch contacts close, the sensor/device emits a contact.closed event and this SmartApp responds as described above.
