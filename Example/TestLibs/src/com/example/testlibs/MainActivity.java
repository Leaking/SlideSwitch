/*
 * Copyright (C) 2015 Quinn Chen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
		txt.setText("first switch is opend, and set the second one is 'slideable'");
		slide2.setSlideable(true);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		txt.setText("first switch is closed,and set the second one is 'unslideable'");
		slide2.setSlideable(false);
	}
}
