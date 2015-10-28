package ncu.lib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("WrongCall")
public class MainMenuView extends SurfaceView implements SurfaceHolder.Callback {
	// RootActivity activity;
	MenuActivity activity;
	Paint paint;
	Bitmap scaled;
	Bitmap bitmapSearch;
	Bitmap bitmapBorrowed;
	Bitmap bitmapRequested;
//	Bitmap bitmapNotice;
	Bitmap bitmapNews;
	Bitmap bitmapNewBook;
	Bitmap bitmapOpeningHours;
//	Bitmap bitmapLogout;

	float BUTTON_SEARCH_XOFFSET;
	float BUTTON_SEARCH_YOFFSET;
	float BUTTON_BORROWED_XOFFSET;
	float BUTTON_BORROWED_YOFFSET;
	float BUTTON_REQUESTED_XOFFSET;
	float BUTTON_REQUESTED_YOFFSET;
	// float BUTTON_NOTICE_XOFFSET = xGap*2 + scale;
	// float BUTTON_NOTICE_YOFFSET = yGap*2 + scale;
	float BUTTON_NEWS_XOFFSET;
	float BUTTON_NEWS_YOFFSET;
	float BUTTON_NEWBOOK_XOFFSET;
	float BUTTON_NEWBOOK_YOFFSET;
	float BUTTON_OPENING_XOFFSET;
	float BUTTON_OPENING_YOFFSET;
	// Bitmap logout;

	float scale;
	float xGap;
	float yGap;
	float textGap;
	int size = 4;

	public MainMenuView(MenuActivity activity) {
		super(activity);
		this.activity = activity;
		this.getHolder().addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		initBitmap();
	}

	public void initBitmap() {
		bitmapSearch = BitmapFactory.decodeResource(getResources(), R.drawable.search);
		bitmapBorrowed = BitmapFactory.decodeResource(getResources(), R.drawable.borrowed);
		bitmapRequested = BitmapFactory.decodeResource(getResources(), R.drawable.requested);
		// bitmapNotice=BitmapFactory.decodeResource(getResources(),
		// R.drawable.notice);
		bitmapNews = BitmapFactory.decodeResource(getResources(), R.drawable.news);
		bitmapNewBook = BitmapFactory.decodeResource(getResources(), R.drawable.new_book);
		bitmapOpeningHours = BitmapFactory.decodeResource(getResources(), R.drawable.opening_hours);
		// logout=BitmapFactory.decodeResource(getResources(),
		// R.drawable.logout);
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// draw the background
		canvas.drawColor(Color.WHITE);
		// canvas.drawBitmap(RescaledBackground(bitmap), 0, 0, null);

		scale = getWidth() / size;
		xGap = (getWidth() - scale * 2) / 3;
		yGap = (getHeight() - scale * 3) / 5;
		textGap = scale + yGap / 2;

		BUTTON_SEARCH_XOFFSET = xGap;
		BUTTON_SEARCH_YOFFSET = yGap;
		BUTTON_BORROWED_XOFFSET = xGap * 2 + scale;
		BUTTON_BORROWED_YOFFSET = yGap;
		BUTTON_REQUESTED_XOFFSET = xGap;
		BUTTON_REQUESTED_YOFFSET = yGap * 2 + scale;
		// float BUTTON_NOTICE_XOFFSET = xGap*2 + scale;
		// float BUTTON_NOTICE_YOFFSET = yGap*2 + scale;
		BUTTON_NEWS_XOFFSET = xGap * 2 + scale;
		BUTTON_NEWS_YOFFSET = yGap * 2 + scale;
		BUTTON_NEWBOOK_XOFFSET = xGap;
		BUTTON_NEWBOOK_YOFFSET = yGap * 3 + scale * 2;
		BUTTON_OPENING_XOFFSET = xGap * 2 + scale;
		BUTTON_OPENING_YOFFSET = yGap * 3 + scale * 2;

		paint.setColor(Color.BLACK);
		paint.setTextSize(scale / 4);

		// �]�åؿ�
		canvas.drawBitmap(Rescaled(bitmapSearch), BUTTON_SEARCH_XOFFSET, BUTTON_SEARCH_YOFFSET, null);
		canvas.drawText("�]�åؿ�", BUTTON_SEARCH_XOFFSET, BUTTON_SEARCH_YOFFSET + textGap, paint);
		// �ɮѰO��
		canvas.drawBitmap(Rescaled(bitmapBorrowed), BUTTON_BORROWED_XOFFSET, BUTTON_BORROWED_YOFFSET, null);
		canvas.drawText("�ɮѰO��", BUTTON_BORROWED_XOFFSET, BUTTON_BORROWED_YOFFSET + textGap, paint);
		// �w���ѥ�
		canvas.drawBitmap(Rescaled(bitmapRequested), BUTTON_REQUESTED_XOFFSET, BUTTON_REQUESTED_YOFFSET, null);
		canvas.drawText("�w���ѥ�", BUTTON_REQUESTED_XOFFSET, BUTTON_REQUESTED_YOFFSET + textGap, paint);
		// //�q��
		// canvas.drawBitmap(Rescaled(bitmapNotice), BUTTON_NOTICE_XOFFSET,
		// BUTTON_NOTICE_YOFFSET, null);
		// //�̷s����
		canvas.drawBitmap(Rescaled(bitmapNews), BUTTON_NEWS_XOFFSET, BUTTON_NEWS_YOFFSET, null);
		canvas.drawText("�̷s����", BUTTON_NEWS_XOFFSET, BUTTON_NEWS_YOFFSET + textGap, paint);
		// //�s�ѱ���
		canvas.drawBitmap(Rescaled(bitmapNewBook), BUTTON_NEWBOOK_XOFFSET, BUTTON_NEWBOOK_YOFFSET, null);
		canvas.drawText("�s�ѱ���", BUTTON_NEWBOOK_XOFFSET, BUTTON_NEWBOOK_YOFFSET + textGap, paint);
		// //�}�]�ɶ�
		canvas.drawBitmap(Rescaled(bitmapOpeningHours), BUTTON_OPENING_XOFFSET, BUTTON_OPENING_YOFFSET, null);
		canvas.drawText("�}�]�ɶ�", BUTTON_OPENING_XOFFSET, BUTTON_OPENING_YOFFSET + textGap, paint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		//
		int x = (int) (e.getX());
		int y = (int) (e.getY());

		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (x > BUTTON_SEARCH_XOFFSET && x < BUTTON_SEARCH_XOFFSET + scale && y > BUTTON_SEARCH_YOFFSET
					&& y < BUTTON_SEARCH_YOFFSET + scale) {
				// activity.hd.sendEmptyMessage(1);
				Log.d("SEARCH", "SEARCH Button");
			}
			if (x > BUTTON_BORROWED_XOFFSET && x < BUTTON_BORROWED_XOFFSET + scale && y > BUTTON_BORROWED_YOFFSET
					&& y < BUTTON_BORROWED_YOFFSET + scale) {
				Log.d("BORROWED", "BORROWED Button");
			}
			if (x > BUTTON_REQUESTED_XOFFSET && x < BUTTON_REQUESTED_XOFFSET + scale && y > BUTTON_REQUESTED_YOFFSET
					&& y < BUTTON_REQUESTED_YOFFSET + scale) {
				Log.d("REQUESTED", "REQUESTED Button");
			}
			if (x > BUTTON_NEWS_XOFFSET && x < BUTTON_NEWS_XOFFSET + scale && y > BUTTON_NEWS_YOFFSET
					&& y < BUTTON_NEWS_YOFFSET + scale) {
				Log.d("NEWS", "NEWS Button");
			}
			if (x > BUTTON_NEWBOOK_XOFFSET && x < BUTTON_NEWBOOK_XOFFSET + scale && y > BUTTON_NEWBOOK_YOFFSET
					&& y < BUTTON_NEWBOOK_YOFFSET + scale) {
				Log.d("NEWBOOK", "NEWBOOK Button");
			}
			// if(x>BUTTON_LOGOUT_XOFFSET&&x<BUTTON_LOGOUT_XOFFSET+BUTTON_LOGOUT_WIDTH
			// &&y>BUTTON_LOGOUT_YOFFSET&&y<BUTTON_LOGOUT_YOFFSET+BUTTON_LOGOUT_HEIGHT)
			// {
			// activity.hd.sendEmptyMessage(0);
			// }
			if (x > BUTTON_OPENING_XOFFSET && x < BUTTON_OPENING_XOFFSET + scale && y > BUTTON_OPENING_YOFFSET
					&& y < BUTTON_OPENING_YOFFSET + scale) {
				Log.d("OPENING", "OPENING Button");
			}
			break;
		}
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		repaint();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

	public void repaint() {
		SurfaceHolder holder = this.getHolder();
		Canvas canvas = holder.lockCanvas();
		try {
			synchronized (holder) {
				onDraw(canvas);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (canvas != null) {
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	public Bitmap Rescaled(Bitmap bitmap) {
		Bitmap scaled = null;
		int newWidth = getWidth() / size;
		int newHeight = newWidth;
		scaled = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
		return scaled;
	}

	public Bitmap RescaledBackground(Bitmap bitmap) {
		Bitmap scaled = null;
		float scale = (float) bitmap.getHeight() / (float) getHeight();
		int newWidth = Math.round(bitmap.getWidth() / scale);
		int newHeight = Math.round(bitmap.getHeight() / scale);
		scaled = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
		return scaled;
	}
}