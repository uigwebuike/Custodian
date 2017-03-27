package com.custodian.ManagePolicy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.ModelView.CustodianViewYourPolicy;
/**
 *  Adapter class of View Your Policy Basic Class of View your policy module.
 */

public class ViewYourPolicyAdapter  extends BaseAdapter{
	
	Date parsed =null;
		 Context context;
		 LayoutInflater inflater;
		 ArrayList<CustodianViewYourPolicy> data;
		 CustodianViewYourPolicy cust  = new CustodianViewYourPolicy();
		
		 
		 
		 public ViewYourPolicyAdapter (Context context,
		            ArrayList<CustodianViewYourPolicy> arraylist){
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
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView mPolicyNumber , mStatus,mStartDate,mEndDate,lbStartDate,lbEndDate;
			ImageView ActiveImage;
			
			
			inflater = (LayoutInflater) context
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 View itemView = inflater.inflate(R.layout.custodian_view_policy_main_adapter, parent, false);
			 cust = data.get(position);
			 ActiveImage = (ImageView)itemView.findViewById(R.id.active);
			 String ActiveORnot = cust.getStatus();
			 if(ActiveORnot.equalsIgnoreCase("ACTIVE")){
				ActiveImage.setImageResource(R.drawable.policy_active);
			 }
			 else{
					ActiveImage.setImageResource(R.drawable.policy_inactive);
			 }
			 Typeface face = Typeface.createFromAsset(context.getAssets(),CONSTANTS.FONT_NAME);
			 mPolicyNumber = (TextView)itemView.findViewById(R.id.viewMangeMain_PolicyNumber);
			 mPolicyNumber.setTypeface(face);
			 
			 lbStartDate = (TextView)itemView.findViewById(R.id.viewMangeMain_StartDate);
			 lbStartDate.setTypeface(face);
			 lbEndDate = (TextView)itemView.findViewById(R.id.viewMangeMain_EndDate_Value);
			 lbEndDate.setTypeface(face);
			 mStartDate= (TextView)itemView.findViewById(R.id.viewMangeMain_StartDate_Value);
			 mStartDate.setTypeface(face);
			 mEndDate = (TextView)itemView.findViewById(R.id.viewMangeMain_EndDate_Value);
			 mEndDate.setTypeface(face);
		// Convert milliseconds into date.
			 String x= cust.getStartDate();
			  SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");

			    long milliSecondsstart= Long.parseLong(x);
		

			    Calendar calendarStart = Calendar.getInstance();
			    calendarStart .setTimeInMillis(milliSecondsstart);
			   final String parsed = (dateFormat.format(calendarStart .getTime())); 
			   
			   String y = cust.getEndDate();
	

			    long milliSecondsend= Long.parseLong(y);
			    System.out.println(milliSecondsend);

			    Calendar calendar = Calendar.getInstance();
			    calendar.setTimeInMillis(milliSecondsend);
			   final String parsedend = (dateFormat.format(calendar.getTime())); 

			  final String policyType = cust.getPolicyType();
			  Log.e("policy",policyType);
			  final String ID = cust.getID();
			 
			 mPolicyNumber.setText(cust.getPolicyNumber());
			

			 mStartDate.setText(parsed.toString());
			 mEndDate.setText(parsedend.toString());
			 itemView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*
					 * Send Data to View your policy detail screen using 
					 * intent putextra
					 */
					cust = data.get(position);
					Intent mIntent  = new Intent(context,ViewYourPolicyDetailScreen.class);
					mIntent.putExtra("id",ID);
					mIntent.putExtra("policyNo",cust.getPolicyNumber());
					mIntent.putExtra("policyName",cust.getPolicyName());
					mIntent.putExtra("vehicleType",cust.getVehicleType());
					mIntent.putExtra("policyType",policyType);
					mIntent.putExtra("startDate", parsed.toString());
					mIntent.putExtra("endDate",parsedend.toString());
					
					context.startActivity(mIntent);
				}
			});
			
			return itemView;
		}


		

	}



