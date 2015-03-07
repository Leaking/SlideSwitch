package com.leaking.slideswitch;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Looper;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.slideswitch.R;

public class SlideSwitch extends View {

	private static final int RIM_SIZE = 6;

	private static final int COLOR_THEME = Color.parseColor("#ff00ee00");
	
	//ÊôÐÔ
	private int color_theme;
	private boolean isOpen = false;
	
	
	
	
	private Paint paint;
	private Rect backRect;
	private Rect frontRect;
	private int frontRect_left;
	private int max_left;
	private int min_left;
	private int frontRect_left_begin = RIM_SIZE;

	
	private int eventStartX;
	private int eventLastX;
	private int diffX = 0;
	private int alpha;

	public interface SlideListener {
		public void open();

		public void close();
	}

	private SlideListener listener;

	public SlideSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.slideswitch);
		color_theme = a.getColor(R.styleable.slideswitch_themeColor, COLOR_THEME);
		isOpen = a.getBoolean(R.styleable.slideswitch_isOpen, false);
		paint = new Paint();

	}

	public SlideSwitch(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlideSwitch(Context context) {
		this(context, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = measureDimension(80, widthMeasureSpec);
		int height = measureDimension(30, heightMeasureSpec);
		setMeasuredDimension(width, height);
		backRect = new Rect(0, 0, width, height);
		
		
		if(isOpen){
			frontRect_left = width/2;
			alpha = 255;
		}else{
			frontRect_left = RIM_SIZE;
			alpha = 0;
		}

		frontRect_left_begin = frontRect_left;
		min_left = RIM_SIZE;
		max_left = width / 2;
		


	}



	public int measureDimension(int defaultSize, int measureSpec) {
		int result;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			result = defaultSize; // UNSPECIFIED
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		paint.setColor(Color.GRAY);
		canvas.drawRect(backRect, paint);

		paint.setColor(color_theme);
		paint.setAlpha(alpha);
		canvas.drawRect(backRect, paint);

		frontRect = new Rect(frontRect_left, RIM_SIZE, frontRect_left
				+ getMeasuredWidth() / 2 - RIM_SIZE, getMeasuredHeight() - 2
				* RIM_SIZE);
		paint.setColor(Color.WHITE);
		canvas.drawRect(frontRect, paint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = MotionEventCompat.getActionMasked(event);

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			eventStartX = (int) event.getRawX();
			break;
		case MotionEvent.ACTION_MOVE:
			eventLastX = (int) event.getRawX();
			diffX = eventLastX - eventStartX;
			int tempX = diffX + frontRect_left_begin;
			if (tempX > min_left && tempX < max_left) {
				frontRect_left = tempX;
				alpha = (int) (255 * (float) tempX / (float) max_left);
				invalidateView();
			}
			break;
		case MotionEvent.ACTION_UP:
			int wholeX = (int) (event.getRawX() - eventStartX);
			
			
			frontRect_left_begin = frontRect_left;
			boolean toRight;
			toRight = (frontRect_left_begin > max_left / 2 ? true : false);
			System.out.println("wholeXXXX = " + wholeX);
			if(Math.abs(wholeX) < 3 || wholeX == 0){
				toRight = !toRight;
				System.out.println("toRight =  " + toRight);
			}
			moveToDest(toRight);

			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * ÖØ»æ
	 */
	private void invalidateView() {
		if (Looper.getMainLooper() == Looper.myLooper()) {
			invalidate();
		} else {
			postInvalidate();
		}
	}

	public void setSlideListener(SlideListener listener) {
		this.listener = listener;
	}

	public void moveToDest(final boolean toRight) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("-----111");
				if(toRight){
					while(frontRect_left <= max_left){
						alpha = (int) (255 * (float) frontRect_left / (float) max_left);
						invalidateView();
						frontRect_left += 3;
						try {
							Thread.sleep(3);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					frontRect_left_begin = max_left;
				}else{
					while(frontRect_left >= min_left){
						alpha = (int) (255 * (float) frontRect_left / (float) max_left);
						invalidateView();
						frontRect_left -= 3;
						try {
							Thread.sleep(3);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					frontRect_left_begin = min_left;
				}
			}
		}).start();

	}

	private static class AnimationListenerAdapter implements AnimationListener {

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub

		}

	}

}
