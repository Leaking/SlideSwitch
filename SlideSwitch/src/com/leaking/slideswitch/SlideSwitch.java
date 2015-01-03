package com.leaking.slideswitch;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.slideswitch.R;

public class SlideSwitch extends FrameLayout {
	
	public interface SlideListener{
		public void open();
		public void close();
	}
	
	private static final int MOVETIME = 150;

	private static final int LP_MATCH = LayoutParams.MATCH_PARENT;
	private static final int LP_WRAPP = LayoutParams.WRAP_CONTENT;

	
	private SlideListener listener;


	boolean isOpen = false;
	int moveLength;
	
	int baseColor;
	int stillColor;
	int moveColor;

	int rimSize;
	int baseHeight;
	int baseWidth;

	RelativeLayout baseView;
	RelativeLayout stillView;
	RelativeLayout moveableView;

	public SlideSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public SlideSwitch(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context
				.obtainStyledAttributes(attrs, R.styleable.slideswitch);
		baseColor = a.getColor(R.styleable.slideswitch_baseColor, baseColor);
		stillColor = a.getColor(R.styleable.slideswitch_stillColor, stillColor);
		moveColor = a.getColor(R.styleable.slideswitch_moveColor, moveColor);
		baseWidth = a.getDimensionPixelSize(R.styleable.slideswitch_slideWidth, baseWidth);
		//baseWidth = px2dip(baseWidth);
		baseHeight = a.getDimensionPixelSize(R.styleable.slideswitch_slideHeight, baseHeight);
		//baseHeight = px2dip(baseHeight);
		isOpen = a.getBoolean(R.styleable.slideswitch_isOpen, isOpen);
		rimSize = 6;
		init();
	}

	public SlideSwitch(Context context) {
		super(context);
		init();
	}

	/**
	 * 
	 */
	public void init() {
		this.setBackgroundColor(this.baseColor);
		
		baseView = new RelativeLayout(getContext());
		stillView = new RelativeLayout(getContext());
		moveableView = new RelativeLayout(getContext());
		LayoutParams baseParams = new LayoutParams(LP_WRAPP,LP_WRAPP);
		LayoutParams stillParams = new LayoutParams(LP_WRAPP, LP_WRAPP);
		LayoutParams moveParams = new LayoutParams(LP_WRAPP, LP_WRAPP);
		baseParams.gravity = Gravity.CENTER;
		stillParams.gravity = Gravity.RIGHT;


		
		baseParams.width = baseWidth - rimSize*2;
		baseParams.height = baseHeight - rimSize*2;
		
		stillParams.width = (baseWidth - 2*rimSize) / 2;
		stillParams.height = baseHeight - rimSize*2;
		moveParams.width = (baseWidth - 2*rimSize) / 2;
		moveParams.height = baseHeight - rimSize*2;
		
		moveLength = stillParams.width;
		
		baseParams.bottomMargin = rimSize;
		baseParams.topMargin = rimSize;
		baseParams.leftMargin = rimSize;
		baseParams.rightMargin = rimSize;
		
		
		moveParams.bottomMargin = rimSize;
		moveParams.topMargin = rimSize;
		
		
		stillParams.rightMargin = rimSize;
		stillParams.topMargin = rimSize;
		stillParams.bottomMargin = rimSize;
		
		moveableView.setBackgroundColor(this.moveColor);
		stillView.setBackgroundColor(this.stillColor);
		
		baseView.setLayoutParams(baseParams);
		stillView.setLayoutParams(stillParams);
		moveableView.setLayoutParams(moveParams);
		
		System.out.println("isOpen == " + isOpen);
		
		if(isOpen == false){
			moveParams.gravity = Gravity.LEFT;
			this.setBackgroundColor(this.stillColor);
			moveParams.leftMargin = rimSize;
		}else{
			moveParams.gravity = Gravity.RIGHT;
			this.setBackgroundColor(this.baseColor);
			moveParams.rightMargin = rimSize;
		}

		
		
		addView(baseView, baseParams);
		addView(stillView, stillParams);
		addView(moveableView, moveParams);
		

	}

	public int px2dip(float pxValue) { 
        final float scale = this.getResources().getDisplayMetrics().density; 
        return (int) (pxValue / scale + 0.5f); 
    } 
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if(!isOpen){
			TranslateAnimation tAnim = new TranslateAnimation(0,moveLength,0,0);
			tAnim.setDuration(MOVETIME);
			moveableView.startAnimation(tAnim);
			tAnim.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					SlideSwitch.this.setBackgroundColor(baseColor);
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					FrameLayout.LayoutParams flp = (LayoutParams) moveableView.getLayoutParams();
					flp.gravity = Gravity.RIGHT;
					flp.rightMargin = rimSize;
					moveableView.setLayoutParams(flp);
					moveableView.clearAnimation();
					listener.open();
					isOpen = !isOpen;
				}
			});
		}else{
			TranslateAnimation tAnim = new TranslateAnimation(0,-moveLength,0,0);
			tAnim.setDuration(MOVETIME);
			moveableView.startAnimation(tAnim);
			tAnim.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					

				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					FrameLayout.LayoutParams flp = (LayoutParams) moveableView.getLayoutParams();
					flp.gravity = Gravity.LEFT;
					flp.leftMargin = rimSize;
					moveableView.setLayoutParams(flp);
					moveableView.clearAnimation();
					isOpen = !isOpen;
					listener.close();
					SlideSwitch.this.setBackgroundColor(stillColor);
				}
			});
			
		}
		return super.dispatchTouchEvent(ev);
	}
	
	public void setSlideListener(SlideListener listener) {
		this.listener = listener;
	}
	
    public void setState(boolean isOpen){
    	if(isOpen){
    		FrameLayout.LayoutParams flp = (LayoutParams) moveableView.getLayoutParams();
			flp.gravity = Gravity.RIGHT;
			flp.rightMargin = rimSize;
			moveableView.setLayoutParams(flp);
			SlideSwitch.this.setBackgroundColor(baseColor);
    	}else{
    		FrameLayout.LayoutParams flp = (LayoutParams) moveableView.getLayoutParams();
			flp.gravity = Gravity.LEFT;
			flp.leftMargin = rimSize;
			moveableView.setLayoutParams(flp);
			
			SlideSwitch.this.setBackgroundColor(stillColor);
    	}
		this.isOpen = isOpen;

    }
}
