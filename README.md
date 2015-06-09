SlideSwitch
===========


## About SlideSwitch

A button that you can slide on or off 


## How to import into your project

### Android Studio

use gradle.

Gradle dependency:

```groovy

dependencies {
    compile 'com.leaking.slideswitch:app:1.0.0'
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