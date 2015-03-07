package com.leaking.slideswitch;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.example.slideswitch.R;

public class SlideSwitch extends View {

	public static final int SHAPE_RECT = 1;
	public static final int SHAPE_CIRCLE = 2;
	private static final int RIM_SIZE = 6;

	private static final int COLOR_THEME = Color.parseColor("#ff00ee00");

	// 属性
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
	private int shape;

	public interface SlideListener {
		public void open();
		public void close();
	}

	private SlideListener listener;

	public SlideSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		listener = null;
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.slideswitch);
		color_theme = a.getColor(R.styleable.slideswitch_themeColor,
				COLOR_THEME);
		isOpen = a.getBoolean(R.styleable.slideswitch_isOpen, false);
		shape = a.getInt(R.styleable.slideswitch_shape, SHAPE_RECT);

		paint = new Paint();

	}

	public SlideSwitch(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlideSwitch(Context context) {
		this(context, null);
	}

	// 留下的回调接口如果有修改界面其他组件，则每次刷新还会调用这个方法，
	// 一般情况的刷新只会调用onDraw，不会继续调用这个方法
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		System.out.println("onMeasure");
		int width = measureDimension(80, widthMeasureSpec);
		int height = measureDimension(30, heightMeasureSpec);
		setMeasuredDimension(width, height);
		backRect = new Rect(0, 0, width, height);

		
		min_left = RIM_SIZE;
		
		if(shape == SHAPE_RECT)
			max_left = width / 2;
		else
			max_left = width - height - RIM_SIZE;
		
		if (isOpen) {
			frontRect_left = max_left;
			alpha = 255;
		} else {
			frontRect_left = RIM_SIZE;
			alpha = 0;
		}
		frontRect_left_begin = frontRect_left;


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
		if (shape == SHAPE_RECT) {
			paint.setColor(Color.GRAY);
			canvas.drawRect(backRect, paint);
			paint.setColor(color_theme);
			paint.setAlpha(alpha);
			canvas.drawRect(backRect, paint);

			frontRect = new Rect(frontRect_left, RIM_SIZE, frontRect_left
					+ getMeasuredWidth() / 2 - RIM_SIZE, getMeasuredHeight()
					- RIM_SIZE);
			paint.setColor(Color.WHITE);
			canvas.drawRect(frontRect, paint);
		} else {
			// 画圆形
			int radius;
			radius = backRect.height() / 2 - RIM_SIZE;
			paint.setColor(Color.GRAY);
			canvas.drawRoundRect(new RectF(backRect), radius, radius, paint);
			paint.setColor(color_theme);
			paint.setAlpha(alpha);
			canvas.drawRoundRect(new RectF(backRect), radius, radius, paint);

			frontRect = new Rect(frontRect_left, RIM_SIZE, frontRect_left
					+ backRect.height() - 2 * RIM_SIZE, backRect.height() - 2
					* RIM_SIZE);
			paint.setColor(Color.WHITE);
			canvas.drawRoundRect(new RectF(frontRect), radius, radius, paint);
		}
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
			if (Math.abs(wholeX) < 3) {
				toRight = !toRight;
			}
			moveToDest(toRight);
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * 重绘
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
		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					listener.open();
					isOpen = true;
				} else {
					listener.close();
					isOpen = false;
				}
			}

		};

		new Thread(new Runnable() {
			@Override
			public void run() {
				if (toRight) {
					while (frontRect_left <= max_left) {
						alpha = (int) (255 * (float) frontRect_left / (float) max_left);
						invalidateView();
						frontRect_left += 3;
						try {
							Thread.sleep(3);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if(listener != null)
					handler.sendEmptyMessage(1);
					frontRect_left_begin = max_left;
				} else {
					while (frontRect_left >= min_left) {
						alpha = (int) (255 * (float) frontRect_left / (float) max_left);
						invalidateView();
						frontRect_left -= 3;
						try {
							Thread.sleep(3);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if(listener != null)
					handler.sendEmptyMessage(0);
					frontRect_left_begin = min_left;
				}
			}
		}).start();

	}

	public void setState(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public void setShapeType(int shapeType) {
		this.shape = shapeType;
	}
}
