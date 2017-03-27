package com.custodian.ClaimsCenter;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.ModelView.CustodianClaims;

/**
 * 
 * Adapter class of view and manage claims 
 *
 */

public class ViewAndManageClaimAdapter  extends BaseAdapter{
	 
	 Context context;
	 LayoutInflater inflater;
	 ArrayList<CustodianClaims> data;
	 CustodianClaims cust  = new CustodianClaims();
	
	 
	 
	 public ViewAndManageClaimAdapter(Context context,
	            ArrayList<CustodianClaims> arraylist){
		 this.context = context;
	     data = arraylist;
	     
		 
	 }
	 
	
	 
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	
		ImageView arrow;
		TextView mdateOfClaims , mAdjuster,mDescription,mClaimNo,mClaimText;
		
		
		
		inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View   itemView = inflater.inflate(R.layout.custodian_view_manage_adapter, parent, false);
		 cust = data.get(position);
		 arrow = (ImageView)itemView.findViewById(R.id.layout_arrow);
		
		 Typeface face = Typeface.createFromAsset(context.getAssets(),CONSTANTS.FONT_NAME);
		 mClaimNo = (TextView)itemView.findViewById(R.id.claim_no_value);
		 mClaimNo.setTypeface(face,Typeface.BOLD);
		
		 mClaimText = (TextView)itemView.findViewById(R.id.claim_no);
		 mClaimText.setTypeface(face,Typeface.BOLD);
		 mdateOfClaims = (TextView)itemView.findViewById(R.id.date_of_Incident);
		 mdateOfClaims.setTypeface(face,Typeface.BOLD);
		 mAdjuster = (TextView)itemView.findViewById(R.id.adjuster_claims);
		 mAdjuster.setTypeface(face,Typeface.BOLD);
		 mDescription = (TextView)itemView.findViewById(R.id.descriptions);
		 mDescription.setTypeface(face);

		 mClaimNo.setText(cust.getClaimNo());
		 Log.e("1",""+cust.getClaimNo());
		 
		 mdateOfClaims.setText(cust.getDateOfIncident() +"  " + "-" );
		
		 mAdjuster.setText(cust.getAdjuster());
		 Log.e("3",""+cust.getAdjuster());
		 mDescription.setText(cust.getDescription());
		 Log.e("4",""+cust.getDescription());
		
		  
		 itemView.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				cust = data.get(position);
				Intent mIntent = new Intent (context,ViewAndManageClaimsDetail.class);
				mIntent.putExtra("cust",cust);
				context.startActivity(mIntent);
			}
		});
		 
		 
		 
		 
		 return itemView;
		
	}
	
}


