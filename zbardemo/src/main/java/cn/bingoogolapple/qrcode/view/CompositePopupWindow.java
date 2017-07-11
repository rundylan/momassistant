package cn.bingoogolapple.qrcode.view;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.bingoogolapple.qrcode.util.DateChange;
import cn.bingoogolapple.qrcode.zbardemo.R;


/**
 * 日期选择PopupWindow控件
 * @author Administrator zxm
 *
 */
public class CompositePopupWindow extends PopupWindow {
    private PopupWindow popComposite;
    private View popView;
    private DatePicker datePicker;
    private String title = "取消";
    private TextView tvTitle;
    private int y, m, d;
    public CompositePopupWindow(Context context){
        popView = LayoutInflater.from(context).inflate(R.layout.view_select_combo_pop_1, null);
        initView();
        setListener();
    }
    
    @SuppressWarnings("deprecation")
	private void initView(){
        popComposite = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popComposite.setAnimationStyle(R.style.anim_popup_dir);
        popComposite.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        popComposite.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        popComposite.setFocusable(true);
        
        datePicker = (DatePicker) popView.findViewById(R.id.date_picker);
        tvTitle = (TextView) popView.findViewById(R.id.tv_title);
        tvTitle.setText(title);
    }
    
    private void setListener(){
    	tvTitle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(tvTitle.getText().toString().equals("取消")){
					dismiss();
				}
			}
		});
    	popView.findViewById(R.id.iv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popComposite.isShowing()){
                	selectComposite.onSelectComposite(y, m, d);
                	popComposite.dismiss();
                }
            }
        });
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int monthOfYear=calendar.get(Calendar.MONTH);
        int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
        y = year;
        m = monthOfYear+1;
        d = dayOfMonth;
        datePicker.init(year, monthOfYear, dayOfMonth, new OnDateChangedListener(){

            public void onDateChanged(DatePicker view, int year,
                    int monthOfYear, int dayOfMonth) {
            	y = year;
                m = monthOfYear+1;
                d = dayOfMonth;
            }
        });
    }
    
    //设置�?��日期
    public void setMaxDate(){
        Date d = new Date();
        datePicker.setMaxDate(d.getTime());
    }
    
    //设置�?��日期
    public void setMinDate(){
        datePicker.setMinDate(DateChange.getDate());
    }
    
    public void dismiss(){
        if(popComposite != null){
            popComposite.dismiss();
        }
    }

    public void show(View parent){
        if(!popComposite.isShowing()){
            popComposite.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        }
    }

    public boolean isShow(){
        if(popComposite != null){
            return popComposite.isShowing();
        }
        return false;
    }

    private SelectComposite selectComposite;

    public void setSelectComposite(SelectComposite selectComposite){
        this.selectComposite = selectComposite;
    }

    public interface SelectComposite{
        public void onSelectComposite(int year,int monthOfYear, int dayOfMonth);
    }
}
