package cn.bingoogolapple.qrcode.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.bingoogolapple.qrcode.zbardemo.R;

public class SuggestFragment extends Fragment {

	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view == null){
			view = inflater.inflate(R.layout.fragment_suggest, container, false);
		}
		return view;
	}
}
