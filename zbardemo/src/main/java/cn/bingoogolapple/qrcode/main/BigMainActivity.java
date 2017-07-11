package cn.bingoogolapple.qrcode.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.bingoogolapple.qrcode.db.MenstruationDao;
import cn.bingoogolapple.qrcode.util.DateChange;
import cn.bingoogolapple.qrcode.view.DateView;
import cn.bingoogolapple.qrcode.view.DegreeView;
import cn.bingoogolapple.qrcode.view.WFYTitle;
import cn.bingoogolapple.qrcode.zbardemo.R;


public class BigMainActivity extends Activity {
	private WFYTitle title;
	private TextView tvDate;
	private DateView dateView;
	private DegreeView dvFlow, dvPain;
	private LinearLayout llMtCome, llMtBack;
	private MenstruationDao mtDao;
	private MenstruationCycle mCycle;
	private Date curDate; // ��ǰ������ʾ����
	private Calendar calendar;
	private MenstruationModel mtmBass;//Ԥ�������Ļ�������
	private long nowTime = 0;//���������
	private DateCardModel dcm;//������·�
	private List<MenstruationModel> list;
	private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_bigaunt);
		
		mContext = this;
		initView();
		initData();
		setListener();
	}
	private void initView() {
		tvDate = (TextView) findViewById(R.id.tv_date);
		dateView = (DateView) findViewById(R.id.date_view);
		dateView.setOnItemClickListener(new DateView.OnItemListener() {

			@Override
			public void onClick(long time, DateCardModel d) {
				nowTime = time;
				dcm = d;
				if(time> DateChange.getDate()){
					llMtCome.setVisibility(View.GONE);
					llMtBack.setVisibility(View.GONE);
					dvFlow.setVisibility(View.GONE);
					dvPain.setVisibility(View.GONE);
					return;
				}else if(dcm.type == 1){
					MenstruationMt mt = mtDao.getMTMT(nowTime);
					llMtCome.setVisibility(View.GONE);
					llMtBack.setVisibility(View.VISIBLE);
					dvFlow.setVisibility(View.VISIBLE);
					dvPain.setVisibility(View.VISIBLE);
					if(mt != null){
						dvFlow.setNumber(mt.getQuantity());
						dvPain.setNumber(mt.getPain());
					}else {
						dvFlow.setNumber(0);
						dvPain.setNumber(0);
					}
				}else if (mtDao.getEndTimeNumber(nowTime) < 6) {
					llMtCome.setVisibility(View.GONE);
					llMtBack.setVisibility(View.VISIBLE);
					dvFlow.setVisibility(View.GONE);
					dvPain.setVisibility(View.GONE);
				}else if (dcm.type != 1) {
					llMtCome.setVisibility(View.VISIBLE);
					llMtBack.setVisibility(View.GONE);
					dvFlow.setVisibility(View.GONE);
					dvPain.setVisibility(View.GONE);
				}
			}
		});
		tvDate.setText(dateView.getYearAndmonth());
		dvFlow = (DegreeView) findViewById(R.id.dv_flow);
		dvPain = (DegreeView) findViewById(R.id.dv_pain);
		title = (WFYTitle) findViewById(R.id.wfy_title);
		llMtCome = (LinearLayout) findViewById(R.id.ll_mt_come);
		llMtBack = (LinearLayout) findViewById(R.id.ll_mt_back);
		
		
	}
	
	/**
	 * ��ʼ������������
	 */
	private void initData(){
		calendar = Calendar.getInstance();
		curDate = new Date();
		calendar.setTime(curDate);
		mtDao = new MenstruationDao(mContext);
		mCycle= mtDao.getMTCycle();
		long nowDate = DateChange.dateTimeStamp(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-1","yyyy-MM-dd");
		long nextDate = DateChange.dateTimeStamp(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+2)+"-1","yyyy-MM-dd");
		//��ȡ��������
		List<MenstruationModel> mtmList = mtDao.getMTModelList(nowDate, nextDate);
		//��ȡȫ������
		list = mtDao.getMTModelList(0, 0);
		//�����ݿ���û�б��¼�¼ʱ��������һ�εļ�¼Ԥ�Ȿ�¼�¼
		for(int i=0; i<mtmList.size(); i++){
			mtmList.get(i).setCon(true);
		}
		if(mtmList.size()==0){
			MenstruationModel mtm = new MenstruationModel();
			mtm.setDate(nowDate);
			mtm.setBeginTime(list.get(list.size()-1).getBeginTime()+intervalTime(list.get(list.size()-1).getBeginTime(), nowDate));
			mtm.setEndTime(list.get(list.size()-1).getBeginTime()+intervalTime(list.get(list.size()-1).getBeginTime(), nowDate)+86400000l*(mCycle.getNumber()-1));
			mtm.setCon(false);
			//�������û�м�¼���͸���֮ǰ��������Ԥ�����ڵ����¾�ʱ�䣬�������֮ǰԤ���ʱ��С�ڵ���ʱ��ʹ����ڿ�ʼ��¼
			if(mtm.getBeginTime() > DateChange.getDate()){
				mtmList.add(mtm);
			}else {
				mtm.setBeginTime(DateChange.getDate());
				mtm.setEndTime(DateChange.getDate() + 86400000l*4);
				mtmList.add(mtm);
			}
			//��¼Ԥ��Ļ�׼
			mtmBass = mtm;
		}else {
			//��¼Ԥ��Ļ�׼
			mtmBass = mtmList.get(mtmList.size()-1);
		}
		//��һ�ε��¾��Ƿ��ڵ���
		MenstruationModel mtm = new MenstruationModel();
		mtm.setBeginTime(mtmBass.getBeginTime()+86400000l*28);
		mtm.setEndTime(mtmBass.getBeginTime()+86400000l*28+86400000l*(mCycle.getNumber()-1));
		mtm.setDate(nowDate);
		mtm.setCon(false);
		if(nextDate > mtm.getBeginTime()){
			if(mtm.getBeginTime() > DateChange.getDate()){
				mtmList.add(mtm);
			}else {
				mtm.setBeginTime(DateChange.getDate());
				mtm.setEndTime(DateChange.getDate() + 86400000l*4);
				mtmList.add(mtm);
			}
			mtmBass = mtm;
		}
		dateView.initData(mtmList);
	}

	/**
	 * ��ȡ��Ԥ�������
	 * @param nowDate ����ʱ��
	 * @param nextDate ����ʱ��
	 * @return 
	 */
	private List<MenstruationModel> calculateMt(long nowDate, long nextDate){
		//��ȡ���´���������
		List<MenstruationModel> mtmList = mtDao.getMTModelList(nowDate, nextDate);//�����ݿ��еĵ��´���������ȡ����
		for(int i=0; i<mtmList.size(); i++){
			mtmList.get(i).setCon(true);
		}
		//����ʱ��С�ڻ���ʱ�䣬���ü���������
		if(nowDate < mtmBass.getDate()){
			return mtmList;
		}
		//�������û�д��������ݣ��͸�����һ���µ�����Ԥ������µ���������
		if(nowDate == mtmBass.getDate()){
			//����ʱ�������ʱ����ͬ
			if(!mtmBass.isCon()){
				mtmList.add(mtmBass);
			}
		}else {
			//��ͬ�͸��ݻ���ʱ��Ԥ��
			MenstruationModel mtm1 = new MenstruationModel();
			mtm1.setBeginTime(mtmBass.getBeginTime()+intervalTime(mtmBass.getDate(), nowDate));
			mtm1.setEndTime(mtmBass.getBeginTime()+intervalTime(mtmBass.getDate(), nowDate)+86400000l*(mCycle.getNumber()-1));
			mtm1.setCon(false);
			mtmList.add(mtm1);
			//�ж���һ�εĴ������Ƿ��ڱ���
			MenstruationModel mtm = new MenstruationModel();
			mtm.setBeginTime(mtmBass.getBeginTime()+intervalTime(mtmBass.getDate(), nowDate)+86400000l*28);
			mtm.setEndTime(mtmBass.getBeginTime()+intervalTime(mtmBass.getDate(), nowDate)+86400000l*28+86400000l*(mCycle.getNumber()-1));
			mtm.setCon(false);
			if(nextDate > mtm.getBeginTime()){
				if(mtm.getBeginTime() > DateChange.getDate()){
					mtmList.add(mtm);
				}else {
					mtm.setBeginTime(DateChange.getDate());
					mtm.setEndTime(DateChange.getDate() + 86400000l*4);
					mtmList.add(mtm);
				}
			}
		}
		//�ж���һ�εĴ������Ƿ����ڱ���
		MenstruationModel mtm1 = new MenstruationModel();
		mtm1.setBeginTime(mtmBass.getBeginTime()+intervalTime(mtmBass.getDate(), nowDate)-86400000l*28);
		mtm1.setEndTime(mtmBass.getBeginTime()+intervalTime(mtmBass.getDate(), nowDate)-86400000l*28+86400000l*(mCycle.getNumber()-1));
		mtm1.setCon(false);
		if(nowDate <= mtm1.getEndTime()){
			mtmList.add(mtm1);
		}
		return mtmList;
	}
	
	
	private void setListener() {
		/**
		 * ��һ��
		 */
		findViewById(R.id.iv_click_left_month).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				calendar.setTime(curDate);
				calendar.add(Calendar.MONTH, -1);
				curDate = calendar.getTime();
				long nowDate = DateChange.dateTimeStamp(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-1","yyyy-MM-dd");
				long nextDate = DateChange.dateTimeStamp(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+2)+"-1","yyyy-MM-dd");
				List<MenstruationModel> mtmList = calculateMt(nowDate, nextDate);
				tvDate.setText(dateView.clickLeftMonth(mtmList));
			}
		});
		/**
		 * ��һ��
		 */
		findViewById(R.id.iv_click_right_month).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				calendar.setTime(curDate);
				calendar.add(Calendar.MONTH, 1);
				curDate = calendar.getTime();
				long nowDate = DateChange.dateTimeStamp(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-1","yyyy-MM-dd");
				long nextDate = DateChange.dateTimeStamp(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+2)+"-1","yyyy-MM-dd");
				List<MenstruationModel> mtmList = calculateMt(nowDate, nextDate);
				tvDate.setText(dateView.clickRightMonth(mtmList));
			}
		});
		/**
		 * �ص�����
		 */
		findViewById(R.id.tv_today).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				calendar.setTime(curDate);
				calendar.add(Calendar.MONTH, getNowTime("yyyy")*12 + getNowTime("MM") - (calendar.get(Calendar.MONTH)+1)-calendar.get(Calendar.YEAR)*12);
				curDate = calendar.getTime();
				long nowDate = DateChange.dateTimeStamp(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-1","yyyy-MM-dd");
				long nextDate = DateChange.dateTimeStamp(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+2)+"-1","yyyy-MM-dd");
				List<MenstruationModel> mtmList = mtDao.getMTModelList(nowDate, nextDate);
				for(int i=0; i<mtmList.size(); i++){
					mtmList.get(i).setCon(true);
				}
				if(mtmList.size()==0){
					MenstruationModel mtm = new MenstruationModel();
					mtm.setBeginTime(list.get(list.size()-1).getBeginTime()+intervalTime(list.get(list.size()-1).getBeginTime(), nowDate));
					mtm.setEndTime(list.get(list.size()-1).getBeginTime()+intervalTime(list.get(list.size()-1).getBeginTime(), nowDate)+86400000l*(mCycle.getNumber()-1));
					mtm.setCon(false);
					if(mtm.getBeginTime() > DateChange.getDate()){
						mtmList.add(mtm);
					}else {
						mtm.setBeginTime(DateChange.getDate());
						mtm.setEndTime(DateChange.getDate() + 86400000l*4);
						mtm.setCon(false);
						mtmList.add(mtm);
					}
				}
				mtmList.add(mtmBass);
				tvDate.setText(dateView.recurToday(mtmList));
			}
		});
		/**
		 * �¾�����
		 */
		title.setOnRightClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(mContext, MenstruationAnalyze.class));
			}
		});
		/**
		 * ��������
		 */
		llMtCome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				long startTime = mtDao.getStartTimeNumber(nowTime);
				if((startTime-nowTime)/86400000<9 && (startTime-nowTime)/86400000>0){
					mtDao.updateMTStartTime(nowTime, startTime);
				}else {
					MenstruationModel mtm = new MenstruationModel();
					mtm.setDate(DateChange.dateTimeStamp(DateChange.timeStamp2Date(nowTime+"", "yyyy-MM")+"-1", "yyyy-MM-dd"));
					mtm.setBeginTime(nowTime);
					mtm.setEndTime(nowTime+86400000l*(mCycle.getNumber()-1));
					mtm.setCycle(mCycle.getCycle());
					mtm.setDurationDay(mCycle.getNumber());
					mtDao.setMTModel(mtm);
				}
				refreshUI();
			}
		});
		
		/**
		 * ��������
		 */
		llMtBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mtDao.updateMTEndTime(nowTime);
				refreshUI();
			}
		});
		
		/**
		 * �����̶Ȼص�
		 */
		dvFlow.setOnNumberListener(new DegreeView.OnNumberListener() {
			
			@Override
			public void onClick(int position) {
				MenstruationMt mt = mtDao.getMTMT(nowTime);
				if(mt != null){
					mt.setQuantity(position);
					mtDao.updateMTM(mt);
				}else {
					mt = new MenstruationMt();
					mt.setDate(nowTime);
					mt.setQuantity(position);
					mt.setPain(0);
					mtDao.setMTMT(mt);
				}
			}
		});
		
		/**
		 * ʹ���̶Ȼص�
		 */
		dvPain.setOnNumberListener(new DegreeView.OnNumberListener() {
			
			@Override
			public void onClick(int position) {
				MenstruationMt mt = mtDao.getMTMT(nowTime);
				if(mt != null){
					mt.setPain(position);
					mtDao.updateMTM(mt);
				}else {
					mt = new MenstruationMt();
					mt.setDate(nowTime);
					mt.setQuantity(0);
					mt.setPain(position);
					mtDao.setMTMT(mt);
				}
			}
		});
	}
	
	/**
	 * ˢ��UI
	 */
	private void refreshUI(){
		long nowDate = DateChange.dateTimeStamp(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-1","yyyy-MM-dd");
		long nextDate = DateChange.dateTimeStamp(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+2)+"-1","yyyy-MM-dd");
		//��ȡ��������
		List<MenstruationModel> mtmList = mtDao.getMTModelList(nowDate, nextDate);
		//��ȡȫ������
		list = mtDao.getMTModelList(0, 0);
		for(int i=0; i<mtmList.size(); i++){
			mtmList.get(i).setCon(true);
		}
		//��һ�ε��¾��Ƿ��ڵ���
		MenstruationModel mtm = new MenstruationModel();
		mtm.setBeginTime(mtmBass.getBeginTime()+86400000l*28);
		mtm.setEndTime(mtmBass.getBeginTime()+86400000l*28+86400000l*(mCycle.getNumber()-1));
		mtm.setDate(nowDate);
		mtm.setCon(false);
		if(nextDate > mtm.getBeginTime()){
			if(mtm.getBeginTime() > DateChange.getDate()){
				mtmList.add(mtm);
			}else {
				mtm.setBeginTime(DateChange.getDate());
				mtm.setEndTime(DateChange.getDate() + 86400000l*4);
				mtmList.add(mtm);
			}
		}
		dateView.refreshUI(mtmList);
	}

	/**
	 * ������ʱ��
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private long intervalTime(long startTime, long endTime){
		int i = (int) ((endTime-startTime)/86400000/mCycle.getCycle());
		i = (endTime-startTime)/86400000%mCycle.getCycle()==0 ? i-1 : i;
		return   i*86400000l*mCycle.getCycle();
	}
	
	/**
	 * ��ȡ��������
	 * @param format
	 * @return
	 */
	public int getNowTime(String format) { 
        Date now = new Date(); 
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);// ���Է�����޸����ڸ�ʽ  
        String hehe = dateFormat.format(now); 
        return Integer.parseInt(hehe); 
    } 
}
