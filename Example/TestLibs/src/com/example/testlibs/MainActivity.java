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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SlideSwitch slide = (SlideSwitch) findViewById(R.id.swit);
        slide.setState(false);
        txt = (TextView) findViewById(R.id.txt);
        slide.setSlideListener(this);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
