package com.custodian.ManagePolicy;


import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.ModelView.CertificateOfDocuments;
/**
 *  Adapter class of Certificate of Documents of View your Documents Module.
 */
public class CertificateOfDocumentsAdapter extends BaseAdapter{

	 Context context;
	 LayoutInflater inflater;
	 ArrayList<CertificateOfDocuments> data;
	 CertificateOfDocuments cust  = new CertificateOfDocuments();
	
	 
	 
	 public CertificateOfDocumentsAdapter(Context context,
	            ArrayList<CertificateOfDocuments> arraylist){
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
		TextView txtPolicyNo,txtVehicleNo,txtVehicleMake,txtVehicleModel;
		
		
		
		inflater = (LayoutInflater) context
              .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 View itemView = inflater.inflate(R.layout.custodian_certificate_of_insurance, parent, false);
		 cust = data.get(position);
		 txtPolicyNo = (TextView)itemView.findViewById(R.id.documents_policy_no_value);
		 txtVehicleNo = (TextView)itemView.findViewById(R.id.numeric_value);
		 txtVehicleMake = (TextView)itemView.findViewById(R.id.car_name);
		 txtVehicleModel = (TextView)itemView.findViewById(R.id.car_value);
		 Typeface face = Typeface.createFromAsset(context.getAssets(),CONSTANTS.FONT_NAME);
		 final String policyno = cust.getPolicyNo();
		 txtPolicyNo.setText(policyno);
		 txtPolicyNo.setTypeface(face,Typeface.BOLD);
		 txtVehicleNo.setText(cust.getVehicleNumber()+" - ");
		 txtVehicleNo.setTypeface(face);
		 txtVehicleMake.setText(cust.getVehicleMake()+" - ");
		 txtVehicleMake.setTypeface(face);
		 txtVehicleModel.setText(cust.getVehicleModel());
		 txtVehicleModel.setTypeface(face);
		
		 itemView.setOnClickListener(new OnClickListener() {
			 
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					/* Pass the data to detail screen
					 * i.e Certificate of documents. 
					 */
					Intent intent = new Intent(context,CertificateDocumentsMain.class);
					intent.putExtra("policyno",policyno);
					context.startActivity(intent);
				}
			});
			 
		
		return itemView;
	}

}

	
	

