package com.mxi.krykeyapp.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.mxi.krykeyapp.R;
import com.mxi.krykeyapp.bean.LoadRadio;

/**
 * An array adapter that knows how to render views when given CustomData classes
 */
public class RadioNameAdapter extends ArrayAdapter<LoadRadio> {
	private LayoutInflater mInflater;
	Context mContext;
	ArrayList<LoadRadio> mlist;
	Typeface faceReg;

	public RadioNameAdapter(Context paramContext, int paramInt,
			ArrayList<LoadRadio> arrayList) {
		super(paramContext, paramInt);
		this.mContext = paramContext;
		this.mlist = ((ArrayList) arrayList);
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		 faceReg = Typeface.createFromAsset(mContext.getAssets(),
					"fonts/champagne_limousines.ttf");
	}

	public int getCount() {
		return this.mlist.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;

		if (convertView == null) {
			// Inflate the view since it does not exist
			convertView = mInflater.inflate(R.layout.row_radio, parent,
					false);

			holder = new Holder();

			holder.row_tv_name = (TextView) convertView.findViewById(R.id.row_tv_name);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		holder.row_tv_name.setTypeface(faceReg);
		holder.row_tv_name.setText(mlist.get(position).radio);
		
		return convertView;
	}

	/** View holder for the views we need access to */
	private static class Holder {
		public TextView row_tv_name;
	}
}
