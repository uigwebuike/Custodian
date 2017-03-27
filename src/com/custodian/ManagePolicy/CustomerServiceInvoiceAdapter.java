package com.custodian.ManagePolicy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.custodian.R;
import com.custodian.ModelView.CustodianClaims;
/**
 *  Adapter class of Invoice of Payment History Module.
 */

public class CustomerServiceInvoiceAdapter  extends BaseAdapter{
	 
	 Context context;
	 LayoutInflater inflater;
	 ArrayList<CustodianServiceInvoice> data;
	 CustodianServiceInvoice cust  = new CustodianServiceInvoice();
	
	 
	 
	 public CustomerServiceInvoiceAdapter(Context context,
	            ArrayList<CustodianServiceInvoice> arraylist){
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
		TextView txtInvoice , txtAmount,txtDate,txtDesc,txtInvoiceDate,txtStatus;
	
		
		
		inflater = (LayoutInflater) context
               .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 View itemView = inflater.inflate(R.layout.custodian_customer_service_invoice_adapter, parent, false);
		 cust = data.get(position);
		 txtInvoiceDate = (TextView)itemView.findViewById(R.id.txt_invoice_date_value);
		 txtInvoice = (TextView)itemView.findViewById(R.id.txt_invoice_value);
		 txtAmount= (TextView)itemView.findViewById(R.id.txt_amount_value);
		 txtDate = (TextView)itemView.findViewById(R.id.txt_date_value);
		 txtDesc = (TextView)itemView.findViewById(R.id.txt_description);
		 txtStatus = (TextView)itemView.findViewById(R.id.txt_status_value);
			/*
			 * Change the milliseconds in date  
			 */ 
		 String x= cust.getDate();
		  SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
		  String parsed = "";
		  if(x!=null){
			  long milliSecondsstart= Long.parseLong(x);
			    System.out.println(milliSecondsstart);  
			    Calendar calendarStart = Calendar.getInstance();
			    calendarStart .setTimeInMillis(milliSecondsstart);
			   parsed = (dateFormat.format(calendarStart .getTime())); 
		  }
		  else{
			  
		  }
		  

		    
		  
		  
			/*
			 * Change the milliseconds in date  
			 */
		  
			 String y= cust.getInvoiceDate();
			  SimpleDateFormat dateFormat1=new SimpleDateFormat("dd/MM/yyyy");
			  String parsed1= "";
			  if(y!=null){
				
				    long milliSecondsstart1= Long.parseLong(y);
				    System.out.println(milliSecondsstart1);

				    Calendar calendarStart1 = Calendar.getInstance();
				    calendarStart1 .setTimeInMillis(milliSecondsstart1);
				parsed1 = (dateFormat1.format(calendarStart1 .getTime()));
			  }
			  else{
				  
			  }
		
		
			  /*
				 * if amount contains .0 it will rouunded off to whole number.
				 */
			  
		String amount = cust.getAmount();
		  String value = null;
		  if(amount!=null){
			  if(amount.contains(".0")){
				  	value = amount.replace(".0", "");
				  }  
		  }
		  else{
			  
		  }
		  
		 txtInvoiceDate.setText(parsed1); 
		 txtInvoice.setText(cust.getInvoice());
		 txtAmount.setText("N "+value);
		 txtDate.setText(parsed);
		 txtDesc.setText(cust.getDescription());
		 txtStatus.setText(cust.getStatus());
		
		return itemView;
	}

}
