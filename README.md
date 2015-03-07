SlideSwitch
===========


## About SlideSwitch

A button that you can slide on or off 


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
                // TODO Auto-generated method stub
                app.saveAutoUpdateProp(true);
            }

            @Override
            public void close() {
                // TODO Auto-generated method stub
                app.saveAutoUpdateProp(false);
            }
        });
```


## What does it look like 


<img src="https://github.com/Leaking/SlideSwitch/blob/master/Example/TestLibs/res/drawable-hdpi/slide_a.png" width="340" />
<img src="https://github.com/Leaking/SlideSwitch/blob/master/Example/TestLibs/res/drawable-hdpi/slide_b.png" width="340" />


##Author

Quinn Chen  

chenhuazhaoao@gmail.com