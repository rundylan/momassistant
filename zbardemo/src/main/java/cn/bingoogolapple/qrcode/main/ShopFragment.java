package cn.bingoogolapple.qrcode.main;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.bingoogolapple.qrcode.db.MenstruationDao;
import cn.bingoogolapple.qrcode.view.HistogramView;
import cn.bingoogolapple.qrcode.zbardemo.R;


public class ShopFragment extends Fragment{
	private View view;
	private HistogramView hv;
	private MenstruationDao mtDao;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view == null){
			view = inflater.inflate(R.layout.fragment_analyze, container, false);
		}
		
		hv = (HistogramView) view.findViewById(R.id.hv);
		
		mtDao = new MenstruationDao(getActivity());
		List<MenstruationModel> mtmList = mtDao.getMTModelList(0, 0);
		hv.setHistogramList(mtmList);
		return view;
	}
}
