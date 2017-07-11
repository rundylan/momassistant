package cn.bingoogolapple.qrcode.main;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import cn.bingoogolapple.qrcode.db.MenstruationDao;
import cn.bingoogolapple.qrcode.zbardemo.R;


public class DetailFragment extends Fragment{
	private ListView slvSuggest;
	private View view;
	private MenstruationDao mtDao;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view == null){
			view = inflater.inflate(R.layout.fragment_detail, container, false);
		}
		slvSuggest = (ListView) view.findViewById(R.id.slv_suggest);
		
		mtDao = new MenstruationDao(getActivity());
		List<MenstruationModel> mtmList = mtDao.getMTModelList(0,0);
		slvSuggest.setAdapter(new SlvSuggestAdapter(getActivity(), mtmList));
		return view;
	}
}
