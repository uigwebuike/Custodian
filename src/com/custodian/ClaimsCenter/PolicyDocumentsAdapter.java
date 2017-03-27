package com.custodian.ClaimsCenter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.ManagePolicy.Documents;

import com.custodian.ModelView.CertificateOfDocuments;
import com.custodian.ModelView.PolicyDocuments;

public class PolicyDocumentsAdapter  extends BaseAdapter{
	Context context;
	 LayoutInflater inflater;
	 ArrayList<PolicyDocuments> data;
	 PolicyDocuments cust  = new PolicyDocuments();
	
	 
	 
	 public PolicyDocumentsAdapter(Context context,
	            ArrayList<PolicyDocuments> arraylist){
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
		TextView txtPolicyno,txtstartDate,txtendDate;
		
		
		
		inflater = (LayoutInflater) context
             .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 View itemView = inflater.inflate(R.layout.custodian_policy_documents, parent, false);
		 cust = data.get(position);
		 Typeface face = Typeface.createFromAsset(context.getAssets(),CONSTANTS.FONT_NAME);
		 txtPolicyno = (TextView)itemView.findViewById(R.id.documents_policy_no_value);
		 txtstartDate = (TextView)itemView.findViewById(R.id.start_date);
		 txtendDate = (TextView)itemView.findViewById(R.id.end_date);
		 
		 final String policy=cust.getPolicyNo();
		 txtPolicyno.setText(policy);
		 Log.e("getPolicyNo",cust.getPolicyNo());
		 
		 
		 txtPolicyno.setTypeface(face,Typeface.BOLD);
		 txtstartDate.setText(cust.getStartDate());
		 txtPolicyno.setTypeface(face);
		 txtendDate.setText(cust.getEndDate());
		 txtPolicyno.setTypeface(face);
		
		 itemView.setOnClickListener(new OnClickListener() {
			 
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Intent intent = new Intent(context,Documents.class);
					
					intent.putExtra("policyno",policy);
					intent.putExtra("covertype",cust.getCoverType());
					context.startActivity(intent);
					
					
					
				}
			});
			 
		
		return itemView;
	}

}
