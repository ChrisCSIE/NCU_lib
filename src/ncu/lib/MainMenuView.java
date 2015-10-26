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

@SuppressLint("WrongCall") public class MainMenuView extends SurfaceView implements SurfaceHolder.Callback
{
//	RootActivity activity;
	MenuActivity activity;
	Paint paint;
	Bitmap scaled;
	Bitmap bitmapSearch;
	Bitmap bitmapBorrowed;
	Bitmap bitmapRequested;
	Bitmap bitmapNotice;
	Bitmap bitmapNews;
	Bitmap bitmapNewBook;
	Bitmap bitmapOpeningHours;
//	Bitmap logout;
	
	public MainMenuView(MenuActivity activity) {
		super(activity);
		this.activity=activity;
		this.getHolder().addCallback(this);
		paint=new Paint();
		paint.setAntiAlias(true);
		initBitmap();
	}
	public void initBitmap()
	{
		bitmapSearch=BitmapFactory.decodeResource(getResources(), R.drawable.search);
		bitmapBorrowed=BitmapFactory.decodeResource(getResources(), R.drawable.borrowed);
		bitmapRequested=BitmapFactory.decodeResource(getResources(), R.drawable.requested);
//		bitmapNotice=BitmapFactory.decodeResource(getResources(), R.drawable.notice);
		bitmapNews=BitmapFactory.decodeResource(getResources(), R.drawable.news);
		bitmapNewBook=BitmapFactory.decodeResource(getResources(), R.drawable.new_book);
		bitmapOpeningHours=BitmapFactory.decodeResource(getResources(), R.drawable.opening_hours);
//		logout=BitmapFactory.decodeResource(getResources(), R.drawable.logout);						
	}
	@Override
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		// draw the background
		canvas.drawColor(Color.WHITE);
//		canvas.drawBitmap(RescaledBackground(bitmap), 0, 0, null);
		
		float scale = getWidth()/4;
		float xGap = (getWidth() - scale*2)/3;
		float yGap = (getHeight() - scale*3)/4;
		
		float BUTTON_SEARCH_XOFFSET = xGap;
		float BUTTON_SEARCH_YOFFSET = yGap;
		float BUTTON_BORROWED_XOFFSET = xGap*2 + scale;
		float BUTTON_BORROWED_YOFFSET = yGap;
		float BUTTON_REQUESTED_XOFFSET = xGap;
		float BUTTON_REQUESTED_YOFFSET = yGap*2 + scale;
//		float BUTTON_NOTICE_XOFFSET = xGap*2 + scale;
//		float BUTTON_NOTICE_YOFFSET = yGap*2 + scale;
		float BUTTON_NEWS_XOFFSET = xGap*2 + scale;
		float BUTTON_NEWS_YOFFSET = yGap*2 + scale;
		float BUTTON_NEWBOOK_XOFFSET = xGap;
		float BUTTON_NEWBOOK_YOFFSET = yGap*3 + scale*2;
		float BUTTON_OPENING_XOFFSET = xGap*2 + scale;
		float BUTTON_OPENING_YOFFSET = yGap*3 + scale*2;
		
		//館藏目錄
		canvas.drawBitmap(Rescaled(bitmapSearch), BUTTON_SEARCH_XOFFSET, BUTTON_SEARCH_YOFFSET, null);
    	//借書記錄
    	canvas.drawBitmap(Rescaled(bitmapBorrowed), BUTTON_BORROWED_XOFFSET, BUTTON_BORROWED_YOFFSET, null);
    	//預約書目
    	canvas.drawBitmap(Rescaled(bitmapRequested), BUTTON_REQUESTED_XOFFSET, BUTTON_REQUESTED_YOFFSET, null);
//    	//通知
//    	canvas.drawBitmap(Rescaled(bitmapNotice), BUTTON_NOTICE_XOFFSET, BUTTON_NOTICE_YOFFSET, null);
//    	//最新消息
    	canvas.drawBitmap(Rescaled(bitmapNews), BUTTON_NEWS_XOFFSET, BUTTON_NEWS_YOFFSET, null);
//    	//新書推薦
    	canvas.drawBitmap(Rescaled(bitmapNewBook), BUTTON_NEWBOOK_XOFFSET, BUTTON_NEWBOOK_YOFFSET, null);
//		//開館時間
		canvas.drawBitmap(Rescaled(bitmapOpeningHours), BUTTON_OPENING_XOFFSET, BUTTON_OPENING_YOFFSET, null);
	}
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
//		
//		int x=(int)(e.getX());
//		int y=(int) (e.getY());
//		
//		switch(e.getAction())
//		{
//		    case MotionEvent.ACTION_DOWN:
//		    	if(x>BUTTON_SEARCH_XOFFSET&&x<BUTTON_SEARCH_XOFFSET+BUTTON_SEARCH_WIDTH
//		    	   &&y>BUTTON_SEARCH_YOFFSET&&y<BUTTON_SEARCH_YOFFSET+BUTTON_SEARCH_HEIGHT)
//				{
//		    		activity.hd.sendEmptyMessage(1);
//				}
//		        if(x>BUTTON_NEWS_XOFFSET&&x<BUTTON_NEWS_XOFFSET+BUTTON_NEWS_WIDTH
//		           &&y>BUTTON_NEWS_YOFFSET&&y<BUTTON_NEWS_YOFFSET+BUTTON_NEWS_HEIGHT)
//		        {
//		        	activity.hd.sendEmptyMessage(3);
//				}
//				if(x>BUTTON_NEWBOOK_XOFFSET&&x<BUTTON_NEWBOOK_XOFFSET+BUTTON_NEWBOOK_WIDTH
//				   &&y>BUTTON_NEWBOOK_YOFFSET&&y<BUTTON_NEWBOOK_YOFFSET+BUTTON_NEWBOOK_HEIGHT)
//				{
//					activity.hd.sendEmptyMessage(2);
//				}
//
//				if(x>BUTTON_NOTICE_XOFFSET&&x<BUTTON_NOTICE_XOFFSET+BUTTON_NOTICE_WIDTH
//				  &&y>BUTTON_NOTICE_YOFFSET&&y<BUTTON_NOTICE_YOFFSET+BUTTON_NOTICE_HEIGHT)
//			   {					
//					System.exit(0);
//			   }
////			   if(x>BUTTON_LOGOUT_XOFFSET&&x<BUTTON_LOGOUT_XOFFSET+BUTTON_LOGOUT_WIDTH
////				 &&y>BUTTON_LOGOUT_YOFFSET&&y<BUTTON_LOGOUT_YOFFSET+BUTTON_LOGOUT_HEIGHT)
////			   {					
////				  activity.hd.sendEmptyMessage(0);
////			   }
//			  if(x>BUTTON_REQUESTED_XOFFSET&&x<BUTTON_REQUESTED_XOFFSET+BUTTON_REQUESTED_WIDTH
//				&&y>BUTTON_REQUESTED_YOFFSET&&y<BUTTON_REQUESTED_YOFFSET+BUTTON_REQUESTED_HEIGHT)
//			  {
//				   activity.hd.sendEmptyMessage(4);
//			  }
//			  if(x>BUTTON_BORROWED_XOFFSET&&x<BUTTON_NEWBOOK_XOFFSET+BUTTON_BORROWED_WIDTH
//				 &&y>BUTTON_BORROWED_YOFFSET&&y<BUTTON_BORROWED_YOFFSET+BUTTON_BORROWED_HEIGHT)
//			  {
//				   activity.hd.sendEmptyMessage(5);
//			  }
//		    break;
//		}
		return true;
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) 
	{	
		
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) 
	{
		repaint();
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) 
	{
		
	}
	
	public void repaint()
	{
		SurfaceHolder holder=this.getHolder();
		Canvas canvas=holder.lockCanvas();
		try{
			synchronized(holder){
				onDraw(canvas);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(canvas!=null){
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}
	
	public Bitmap Rescaled(Bitmap bitmap) {
		Bitmap scaled = null;
		int newWidth = getWidth()/4;
		int newHeight = newWidth;
	    scaled = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
		return scaled;
	}
	
	public Bitmap RescaledBackground(Bitmap bitmap) {
		Bitmap scaled = null;
	    float scale = (float)bitmap.getHeight()/(float)getHeight();
	    int newWidth = Math.round(bitmap.getWidth()/scale);
	    int newHeight = Math.round(bitmap.getHeight()/scale);
	    scaled = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
		return scaled;
	}
}