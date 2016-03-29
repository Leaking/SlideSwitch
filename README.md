SlideSwitch
===========


## About SlideSwitch

A button that you can slide on or off 


## How to import into your project

### Android Studio

use gradle.

Gradle dependency:

Add the below codes in the module gradle file, which module use this widget.

```gradle
compile 'com.leaking.slideswitch:slideswitch:1.0.0'
```

Add the below codes in you project gradle file 

```gradle
allprojects {
    repositories {
        jcenter()
        maven {
            url 'https://dl.bintray.com/leaking/maven'
        }
    }
}
```




### Eclipse

import it as a library project.


## How to use it

you can define a slideswitch in xml like the following example


```xml
    <com.leaking.slideswitch.SlideSwitch
        android:layout_width="100dip"
        android:layout_height="120dip"
        slideswitch:isOpen="false"
        slideswitch:shape="circle"
        slideswitch:themeColor="#f200aa96" >
    </com.leaking.slideswitch.SlideSwitch>
```



you can initial the state(on or off) in jave code in this way


```java
	bulletSwitch.setState(true);
```


and you can listen to the change of the slideswitch like this

```java
    updateSwitch.setSlideListener(new SlideListener() {

            @Override
            public void open() {
                // Do something ,,,
            }

            @Override
            public void close() {
                // Do something ,,,
            }
        });
```

you even can forbid the widget to change its state(open or close) like this

```java
    slide.setSlideable(false);
    slide.setSlideable(true);
```

## What does it look like 


<img src="https://github.com/Leaking/SlideSwitch/blob/master/Example/TestLibs/res/drawable-hdpi/slide_a.png" width="340" />
<img src="https://github.com/Leaking/SlideSwitch/blob/master/Example/TestLibs/res/drawable-hdpi/slide_c.gif" width="340" />


##Author

Quinn Chen  

chenhuazhaoao@gmail.com


## LICENSE

 Copyright 2015 Quinn Chen

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
