SlideSwitch
===========


## About this SlideSwitch
A button they you can slide to open or close something


## How to use it

you can define a slideswitch in xml like the following example


{% highlight java %}

 <com.leaking.slideswitch.SlideSwitch
        android:id="@+id/swit"
		android:layout_height="wrap_content"
		android:layout_width="wrap_content"
		slideswitch:isOpen="true"
        slideswitch:baseColor="#ffff510e"
        slideswitch:moveColor="#ffffff"
        slideswitch:slideHeight="30dip"
        slideswitch:slideWidth="80dip"
        slideswitch:stillColor="#ffa3a3a3" >
    </com.leaking.slideswitch.SlideSwitch>


{% endhighlight %}

you can init the state(open or close) in jave code in this way


{% highlight java %}

	bulletSwitch.setState(true);

{% endhighlight %}


## What does it look like 


<figure>
  <img src="{{ site.url }}/images/fragments.png" alt="search screenshot">
  <figcaption>fragment的设计原理</figcaption>
</figure>