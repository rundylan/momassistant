package cn.bingoogolapple.qrcode.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.bingoogolapple.qrcode.zbardemo.R;


public class WFYTitle extends RelativeLayout {
	private Context mContext;
	private TextView tvTitle, tvLeft, tvRight;
	private ImageView ivRight, ivTitle, ivLeft;
	private int titleColor, rightTextColor;

	public WFYTitle(Context context) {
		super(context);
		mContext = context;
	}

	public WFYTitle(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WFYTitle(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WFYTitle, defStyle, 0);
		String title = ta.getString(R.styleable.WFYTitle_centerText);
		
		int titleImage = ta.getResourceId(R.styleable.WFYTitle_centerImage, -1);
		if (titleImage != -1) {
			addTitleImage(titleImage);
		}
		titleColor = ta.getColor(R.styleable.WFYTitle_centerTextColor, Color.parseColor("#ffffff"));
		if (!TextUtils.isEmpty(title)) {
			addTitleText(title, titleColor);
		}
		
		int leftImage = ta.getResourceId(R.styleable.WFYTitle_leftImage, -1);
		if (leftImage != -1) {
			addLeftImage(leftImage);
		}
		String leftText = ta.getString(R.styleable.WFYTitle_leftText);
		if (!TextUtils.isEmpty(leftText)) {
			int leftTextColor = ta.getColor(R.styleable.WFYTitle_leftTextColor, Color.parseColor("#ffffff"));
			addLeftText(leftText, leftTextColor);
		}
		
		String rightText = ta.getString(R.styleable.WFYTitle_rightText);
		rightTextColor = ta.getColor(R.styleable.WFYTitle_rightTextColor, Color.parseColor("#ffffff"));
		if (!TextUtils.isEmpty(rightText)) {
			addRightText(rightText, rightTextColor);
		}
		int rightImage = ta.getResourceId(R.styleable.WFYTitle_rightImage, -1);
		if (rightImage != -1) {
			addRightImage(rightImage);
		}
		if (ta.getBoolean(R.styleable.WFYTitle_leftIsBack, false)) {
			onBackPressed();
		}
		ta.recycle();
	}

	private void onBackPressed() {
		ivLeft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				((Activity)mContext).onBackPressed();
			}
		});
	}

	private void addTitleImage(int titleImage) {
		ivTitle = new ImageView(mContext);
		LayoutParams lpTitle = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		lpTitle.addRule(RelativeLayout.CENTER_IN_PARENT);
		ivTitle.setLayoutParams(lpTitle);
		ivTitle.setImageResource(titleImage);
		ivTitle.setScaleType(ScaleType.FIT_CENTER);
		ivTitle.setPadding(0, dp2px(mContext, 3), 0, dp2px(mContext, 3));
		addView(ivTitle);
	}
	
	public void addTitleText(String title, int titleColor) {
		tvTitle = new TextView(mContext);
		LayoutParams lpTitle = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		lpTitle.addRule(RelativeLayout.CENTER_IN_PARENT);
		tvTitle.setLayoutParams(lpTitle);
		tvTitle.setGravity(Gravity.CENTER);
		tvTitle.setText(title);
		tvTitle.setTextColor(titleColor);
		tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		tvTitle.setTypeface(Typeface.MONOSPACE);
		tvTitle.setEllipsize(TruncateAt.END);
		tvTitle.setSingleLine();
		tvTitle.setEms(10);
		addView(tvTitle);
	}

	private void addLeftImage(int leftImage) {
		ivLeft = new ImageView(mContext);
		LayoutParams lpLeft = new LayoutParams(dp2px(mContext, 50), LayoutParams.MATCH_PARENT);
		lpLeft.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		ivLeft.setLayoutParams(lpLeft);
		ivLeft.setImageResource(leftImage);
		ivLeft.setScaleType(ScaleType.FIT_CENTER);
		ivLeft.setPadding(dp2px(mContext, 10), dp2px(mContext, 10), dp2px(mContext, 10), dp2px(mContext, 10));
		addView(ivLeft);
	}

	private void addLeftText(String leftText, int leftTextColor) {
		tvLeft = new TextView(mContext);
		LayoutParams lpLeft = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		lpLeft.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		tvLeft.setLayoutParams(lpLeft);
		tvLeft.setGravity(Gravity.CENTER_VERTICAL);
		tvLeft.setText(leftText);
		tvLeft.setTextColor(leftTextColor);
		tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		tvLeft.setTypeface(Typeface.MONOSPACE);
		tvLeft.setLines(1);
		tvLeft.setPadding(dp2px(mContext, 8), 0, 0, 0);
		addView(tvLeft);
	}

	private void addRightText(String rightText, int rightTextColor) {
		tvRight = new TextView(mContext);
		LayoutParams lpRight = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		lpRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		tvRight.setLayoutParams(lpRight);
		tvRight.setGravity(Gravity.CENTER_VERTICAL);
		tvRight.setText(rightText);
		tvRight.setTextColor(rightTextColor);
		tvRight.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		tvRight.setTypeface(Typeface.MONOSPACE);
		tvRight.setLines(1);
		tvRight.setPadding(0, 0, dp2px(mContext, 8), 0);
		addView(tvRight);
	}
	
	private void addRightImage(int rightImage) {
		ivRight = new ImageView(mContext);
		LayoutParams lpLeft = new LayoutParams(dp2px(mContext, 40), LayoutParams.MATCH_PARENT);
		lpLeft.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		ivRight.setLayoutParams(lpLeft);
		ivRight.setImageResource(rightImage);
		ivRight.setScaleType(ScaleType.FIT_CENTER);
		ivRight.setPadding(dp2px(mContext, 10), dp2px(mContext, 10), dp2px(mContext, 10), dp2px(mContext, 10));
		addView(ivRight);
	}
	
	public void setOnLeftClickListener(OnClickListener clickListener){
		if (ivLeft != null) {
			ivLeft.setOnClickListener(clickListener);
		}
		if (tvLeft != null) {
			tvLeft.setOnClickListener(clickListener);
		}
	}
	
	public void setOnRightClickListener(OnClickListener clickListener){
		if (ivRight != null) {
			ivRight.setOnClickListener(clickListener);
		}
		if (tvRight != null) {
			tvRight.setOnClickListener(clickListener);
		}
	}
	
	public void setOnTitleClickListener(OnClickListener clickListener){
		if (ivTitle != null) {
			ivTitle.setOnClickListener(clickListener);
		}
		if (tvTitle != null) {
			tvTitle.setOnClickListener(clickListener);
		}
	}
	
	private int dp2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	public void  hideLeft(){
		if(ivLeft != null){
			ivLeft.setVisibility(View.GONE);
		}
		if(tvLeft != null){
			tvLeft.setVisibility(View.GONE);
		}
	}
	
	public void setTitleText(String title){
		if (tvTitle == null) {
			addTitleText(title, titleColor);
		} else {
			tvTitle.setText(title);
		}
	}
	
	public void setTitleText(int resId){
		if (tvTitle == null) {
			addTitleText(mContext.getString(resId), titleColor);
		} else {
			tvTitle.setText(mContext.getString(resId));
		}
	}
	
	public void setRightImage(int resId){
		if (ivRight == null) {
			addRightImage(resId);
		} else {
			ivRight.setImageResource(resId);
		}
	}
	
	public void setRightTextColor(int color){
		rightTextColor = color;
	}
	
	public void setRightText(String right){
		if (tvRight == null) {
			addRightText(right, rightTextColor);
		} else {
			tvRight.setText(right);
		}
	}
	
	public void setRightText(int resId){
		if (tvRight == null) {
			addRightText(mContext.getString(resId), titleColor);
		} else {
			tvRight.setText(mContext.getString(resId));
		}
	}
}
