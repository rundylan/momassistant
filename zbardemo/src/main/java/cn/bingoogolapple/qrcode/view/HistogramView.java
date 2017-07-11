package cn.bingoogolapple.qrcode.view;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cn.bingoogolapple.qrcode.main.MenstruationModel;
import cn.bingoogolapple.qrcode.util.DateChange;
import cn.bingoogolapple.qrcode.zbardemo.R;

/**
 * 大姨妈柱状图
 * @author ZXM
 *
 */
public class HistogramView extends View{
	private Paint paint_text, paint_line, paint_small, paint_big, paint_base;
	private float tb;
	private int characterColor = 0xff6E6E6E; // 字的颜色
	private int lineColor = 0xffBBBBBB; // 线的颜色
	private int smallColor = 0xffFE6895; // 最后一个矩形的颜色
	private int bigColor = 0xffFAB0C7;//矩形的颜色
	private int baseColor = 0xffF8F8F8;//底色
	private float x,y;
	private float rowX = 0;
	private float startX;
	
	private List<MenstruationModel> hm = new ArrayList<MenstruationModel>();
	
	public HistogramView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public HistogramView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public HistogramView(Context context) {
		super(context);
		init(context);
	}
	
	public void setHistogramList(List<MenstruationModel> hm){
		this.hm = hm;
		invalidate();
	}

	private void init(Context context){
		Resources res = getResources();
		tb = res.getDimension(R.dimen.histogram_tb);
		
		x = tb*3.0f;
		y = tb*3.0f;
		
		paint_text = new Paint();
		paint_text.setAntiAlias(true);
		paint_text.setColor(characterColor);
		paint_text.setTextSize(tb * 1.2f);
		paint_text.setStrokeWidth(tb * 0.1f);
		paint_text.setTextAlign(Align.CENTER);
		paint_text.setStyle(Style.FILL);
		
		paint_line = new Paint();
		paint_line.setAntiAlias(true);
		paint_line.setColor(lineColor);
		paint_line.setTextSize(tb * 1.2f);
		paint_line.setStrokeWidth(tb * 0.1f);
		paint_line.setTextAlign(Align.CENTER);
		paint_line.setStyle(Style.FILL);
		
		paint_small = new Paint();
		paint_small.setAntiAlias(true);
		paint_small.setColor(smallColor);
		paint_small.setTextSize(tb * 1.2f);
		paint_small.setStrokeWidth(tb * 0.1f);
		paint_small.setTextAlign(Align.CENTER);
		paint_small.setStyle(Style.FILL);
		
		paint_big = new Paint();
		paint_big.setAntiAlias(true);
		paint_big.setColor(bigColor);
		paint_big.setTextSize(tb * 1.2f);
		paint_big.setStrokeWidth(tb * 0.1f);
		paint_big.setTextAlign(Align.CENTER);
		paint_big.setStyle(Style.FILL);

		paint_base = new Paint();
		paint_base.setAntiAlias(true);
		paint_base.setColor(baseColor);
		paint_base.setTextSize(tb * 1.2f);
		paint_base.setStrokeWidth(tb * 0.1f);
		paint_base.setTextAlign(Align.CENTER);
		paint_base.setStyle(Style.FILL);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float endX = 0;	
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = event.getX();
			break;
		case MotionEvent.ACTION_UP:
			endX = event.getX();
			if(rowX < 0){
//				rowX = 0;
				new thread2();
			}else if(getWidth()-2*hm.size()*x+rowX > 3*x) {
//				rowX = 3*x - (getWidth()-2*hm.size()*x);
				new thread();
			}
//			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			endX = event.getX();
			float f = endX-startX;
			rowX += f;
			startX = event.getX();
			invalidate();
			break;
		}
		return true;
	}
	
	class thread implements Runnable {
		private Thread thread;
		private int statek;

		public thread() {
			thread = new Thread(this);
			thread.start();
		}

		public void run() {
			while (true) {
				switch (statek) {
				case 0:
					try {
						Thread.sleep(10);
						statek = 1;
					} catch (InterruptedException e) {
					}
					break;
				case 1:
					try {
						Thread.sleep(1);
						rowX -= 5f;

						postInvalidate();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(getWidth()-2*hm.size()*x+rowX <= 6*x){
						statek = 2;
					}
					
					break;
				case 2:
					try {
						Thread.sleep(1);
						rowX -= 1f;
						postInvalidate();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					break;
				}
				if (getWidth()-2*hm.size()*x+rowX <= 3*x)
					break;
			}
		}
	}

	class thread2 implements Runnable {
		private Thread thread;
		private int statek;

		public thread2() {
			thread = new Thread(this);
			thread.start();
		}

		public void run() {
			while (true) {
				switch (statek) {
				case 0:
					try {
						Thread.sleep(10);
						statek = 1;
					} catch (InterruptedException e) {
					}
					break;
				case 1:
					try {
						Thread.sleep(1);
						rowX += 5f;
						postInvalidate();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					if(rowX >= -4*x){
						statek = 2;
					}
					
					break;
				case 2:
					try {
						Thread.sleep(1);
						rowX += 1f;
						postInvalidate();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					break;
				}
				if (rowX >= 0)
					break;
			}
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
//		//显示年份
//		canvas.drawText("2015", 3.5f*x, (12+0.5f)*y, paint_text);
		//显示标注
		canvas.drawLine(0.0f, 12*y, getWidth(), 12*y, paint_line);
		canvas.drawLine(3.0f*x, 12.9f*y, 4.0f*x, 12.9f*y, paint_big);
		canvas.drawText("理想周期  （28天）", 6f*x, 13f*y, paint_text);
		canvas.drawText("日/月", 11f*x, 13f*y, paint_big);
		canvas.drawText("来经时间", 12.5f*x, 13f*y, paint_text);

		
		//28天临界线
		canvas.drawLine(0.0f, 8.4f*y, getWidth(), 8.4f*y, paint_big);
		
		//绘制矩形与背景
		if((getWidth()-3*x) / x / 2 <= hm.size()){
			for(int i = hm.size()-1; i >= 0; i--){
				canvas.drawRect(getWidth()-2*(hm.size()-i)*x+rowX, 2*y, getWidth()-2*(hm.size()-i)*x + x+rowX, 11*y, paint_base);
				if(i==hm.size()-1){
					if(hm.get(i).getCycle()<20){
						canvas.drawRect(getWidth()-2*(hm.size()-i)*x+rowX, (11-hm.get(i).getCycle()/20.0f)*y, getWidth()-2*(hm.size()-i)*x+x+rowX, 11*y, paint_small);
						canvas.drawText(""+hm.get(i).getCycle(), getWidth()-2*(hm.size()-i)*x+0.5f*x+rowX, ((11-hm.get(i).getCycle()/20.0f)-0.2f)*y, paint_small);
					}else {
						canvas.drawRect(getWidth()-2*(hm.size()-i)*x+rowX, (11-(hm.get(i).getCycle()-15)/5.0f)*y, getWidth()-2*(hm.size()-i)*x+x+rowX, 11*y, paint_small);
						canvas.drawText(""+hm.get(i).getCycle(), getWidth()-2*(hm.size()-i)*x+0.5f*x+rowX, ((11-(hm.get(i).getCycle()-15)/5.0f)-0.2f)*y, paint_small);
					}
					canvas.drawText(DateChange.timeStamp2Date(hm.get(i).getBeginTime()+"", "MM/dd"), getWidth()-2*(hm.size()-i)*x+0.5f*x+rowX, (11.5f)*y, paint_text);
				}else {
					if(hm.get(i).getCycle()<20){
						canvas.drawRect(getWidth()-2*(hm.size()-i)*x+rowX, (11-hm.get(i).getCycle()/20.0f)*y, getWidth()-2*(hm.size()-i)*x+x+rowX, 11*y, paint_big);
						canvas.drawText(""+hm.get(i).getCycle(), getWidth()-2*(hm.size()-i)*x+0.5f*x+rowX, ((11-hm.get(i).getCycle()/20.0f)-0.2f)*y, paint_small);
					}else {
						canvas.drawRect(getWidth()-2*(hm.size()-i)*x+rowX, (11-(hm.get(i).getCycle()-15)/5.0f)*y, getWidth()-2*(hm.size()-i)*x+x+rowX, 11*y, paint_big);
						canvas.drawText(""+hm.get(i).getCycle(), getWidth()-2*(hm.size()-i)*x+0.5f*x+rowX, ((11-(hm.get(i).getCycle()-15)/5.0f)-0.2f)*y, paint_small);
					}
					canvas.drawText(DateChange.timeStamp2Date(hm.get(i).getBeginTime()+"", "MM/dd"), getWidth()-2*(hm.size()-i)*x+0.5f*x+rowX, (11.5f)*y, paint_text);
				}
				//显示年份
				if(i == 0){
					canvas.drawText(DateChange.timeStamp2Date(hm.get(i).getBeginTime()+"", "yyyy"), getWidth()-2*(hm.size()-i)*x+0.5f*x+rowX, (12+0.5f)*y, paint_text);
					if(Integer.parseInt(DateChange.timeStamp2Date(hm.get(i).getBeginTime()+"", "yyyy"))
							!= Integer.parseInt(DateChange.timeStamp2Date(hm.get(i+1).getBeginTime()+"", "yyyy"))){
						canvas.drawText(DateChange.timeStamp2Date(hm.get(i+1).getBeginTime()+"", "yyyy"), getWidth()-2*(hm.size()-i-1)*x+0.5f*x+rowX, (12+0.5f)*y, paint_text);
					}
				}else if(i != hm.size()-1 && Integer.parseInt(DateChange.timeStamp2Date(hm.get(i).getBeginTime()+"", "yyyy"))
						!= Integer.parseInt(DateChange.timeStamp2Date(hm.get(i+1).getBeginTime()+"", "yyyy"))){
					canvas.drawText(DateChange.timeStamp2Date(hm.get(i+1).getBeginTime()+"", "yyyy"), getWidth()-2*(hm.size()-i-1)*x+0.5f*x+rowX, (12+0.5f)*y, paint_text);
				}
			}
			
		}else {
			for(int i=0; i<hm.size(); i++){
				canvas.drawRect((3+i*2)*x, 2*y, (4+i*2)*x, 11*y, paint_base);
				if(i==hm.size()-1){
					if(hm.get(i).getCycle()<20){
						canvas.drawRect((3+i*2)*x, (11-hm.get(i).getCycle()/20.0f)*y, (4+i*2)*x, 11*y, paint_small);
						canvas.drawText(""+hm.get(i).getCycle(), (3.5f+i*2)*x, ((11-hm.get(i).getCycle()/20.0f)-0.2f)*y, paint_small);
						canvas.drawText(DateChange.timeStamp2Date(hm.get(i).getBeginTime()+"", "MM/dd"), (3.5f+i*2)*x, (11.5f)*y, paint_text);
					}else {
						canvas.drawRect((3+i*2)*x, (11-(hm.get(i).getCycle()-15)/5.0f)*y, (4+i*2)*x, 11*y, paint_small);
						canvas.drawText(""+hm.get(i).getCycle(), (3.5f+i*2)*x, ((11-(hm.get(i).getCycle()-15)/5.0f)-0.2f)*y, paint_small);
						canvas.drawText(DateChange.timeStamp2Date(hm.get(i).getBeginTime()+"", "MM/dd"), (3.5f+i*2)*x, (11.5f)*y, paint_text);
					}
				}else {
					if(hm.get(i).getCycle()<20){
						canvas.drawRect((3+i*2)*x, (11-hm.get(i).getCycle()/20.0f)*y, (4+i*2)*x, 11*y, paint_big);
						canvas.drawText(""+hm.get(i).getCycle(), (3.5f+i*2)*x, ((11-hm.get(i).getCycle()/20.0f)-0.2f)*y, paint_big);
						canvas.drawText(DateChange.timeStamp2Date(hm.get(i).getBeginTime()+"", "MM/dd"), (3.5f+i*2)*x, (11.5f)*y, paint_text);
					}else {
						canvas.drawRect((3+i*2)*x, (11-(hm.get(i).getCycle()-15)/5.0f)*y, (4+i*2)*x, 11*y, paint_big);
						canvas.drawText(""+hm.get(i).getCycle(), (3.5f+i*2)*x, ((11-(hm.get(i).getCycle()-15)/5.0f)-0.2f)*y, paint_big);
						canvas.drawText(DateChange.timeStamp2Date(hm.get(i).getBeginTime()+"", "MM/dd"), (3.5f+i*2)*x, (11.5f)*y, paint_text);
					}
					
				}
				//显示年份
				if(i==0){
					canvas.drawText(DateChange.timeStamp2Date(hm.get(i).getBeginTime()+"", "yyyy"), (3.5f+i*2)*x, (12+0.5f)*y, paint_text);
				}else if(Integer.parseInt(DateChange.timeStamp2Date(hm.get(i).getBeginTime()+"", "yyyy"))!=
						Integer.parseInt(DateChange.timeStamp2Date(hm.get(i-1).getBeginTime()+"", "yyyy"))) {
					canvas.drawText(DateChange.timeStamp2Date(hm.get(i).getBeginTime()+"", "yyyy"), (3.5f+i*2)*x, (12+0.5f)*y, paint_text);
				}
			}
		}
		//y轴坐标
		canvas.drawText("天", x, y, paint_text);
		int j=1;
		for(int i=60; i>=20; i=i-5){
			canvas.drawText(""+i, x, (j+1.1f)*y, paint_text);
			canvas.drawLine(1.5f*x, (j+1)*y, 2*x, (j+1)*y, paint_line);
			j++;
		}
		//x轴y轴
		canvas.drawLine(0.0f, 11*y, getWidth(), 11*y, paint_line);
		canvas.drawLine(2*x, 0.0f, 2*x, 11*y, paint_line);
	}
	
}
