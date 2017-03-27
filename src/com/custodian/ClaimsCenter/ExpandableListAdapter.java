package com.custodian.ClaimsCenter;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context _context;
	private List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, List<String>> _listDataChild;
	RelativeLayout rel;
	ImageView img;
	ViewHolder holder;

	public ExpandableListAdapter(Context context, List<String> listDataHeader,
			HashMap<String, List<String>> listChildData, ImageView img) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
		this.img = img;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final String childText = (String) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.custodian_report_claim_weather_child2, null);
		}
		Typeface face = Typeface.createFromAsset(_context.getAssets(),
				CONSTANTS.FONT_NAME);
		TextView txtListChild = (TextView) convertView
				.findViewById(R.id.lblListItem);
		txtListChild.setTypeface(face);

		txtListChild.setText(childText);

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, final boolean isExpanded,
			View convertView, ViewGroup parent) {
		final String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.custodian_report_claim_child, null);
			holder.lblListHeader = (TextView) convertView
					.findViewById(R.id.lblListHeader);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(holder);

			holder.imageArrow = (ImageView) convertView
					.findViewById(R.id.imgArrow);

		} else {
			holder = (ViewHolder) convertView.getTag();

		}
		Typeface face = Typeface.createFromAsset(_context.getAssets(),
				CONSTANTS.FONT_NAME);

		holder.lblListHeader.setTypeface(face);

		holder.lblListHeader.setTypeface(null, Typeface.BOLD);
		holder.lblListHeader.setText(headerTitle);
		if (isExpanded) {
			holder.imageArrow.setBackgroundResource(0);
			holder.imageArrow.setBackgroundResource(R.drawable.arrow_down);
			
		} else {
			holder.imageArrow.setBackgroundResource(R.drawable.arrow);

		}
		// holder. lblListHeader.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// img.setVisibility(View.VISIBLE);
		//
		//
		//
		//
		// }
		// });
		// ImageView imView1 = null;
		// ImageView imView2 = null;
		// ImageView imView3 = null;
		// //
		if (groupPosition == 0) {
			// imView1 = new ImageView(this._context);
			holder.image.setImageResource(R.drawable.icon_weather);
			// linear.addView(imView1);

		}
		if (groupPosition == 1) {
			// imView2 = new ImageView(this._context);
			holder.image.setImageResource(R.drawable.icon_theft_vandalism);
			// linear.addView(imView2);
		}
		if (groupPosition == 2) {
			// image = new ImageView(this._context);
			holder.image.setImageResource(R.drawable.icon_auto_other);
			// linear.addView(imView3);
		}

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	class ViewHolder {
		ImageView image, imageArrow;
		TextView lblListHeader;
		boolean bool = false;

	}
}