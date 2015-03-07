package com.example.testlibs;

import com.leaking.slideswitch.SlideSwitch;
import com.leaking.slideswitch.SlideSwitch.SlideListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity implements SlideListener {

	TextView txt;
	SlideSwitch slide;
	SlideSwitch slide2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		slide = (SlideSwitch) findViewById(R.id.swit);
		slide2 = (SlideSwitch) findViewById(R.id.swit2);

		slide.setState(false);
		txt = (TextView) findViewById(R.id.txt);
		slide.setSlideListener(this);
	}

	@Override
	public void open() {
		// TODO Auto-generated method stub
		txt.setText(" is opend ");
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		txt.setText(" is close ");
	}
}
