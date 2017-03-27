package com.custodian.ManagePolicy;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.ModelView.PolicyDetailsValues;

/**
 * View your Policy Detail Screen Adapter.
 *
 */
public class ViewDetailsAdapter  extends BaseAdapter{
	
		Date parsed =null;
		 Context context;
		 LayoutInflater inflater;
		 ArrayList<PolicyDetailsValues> data;
		 PolicyDetailsValues cust  = new PolicyDetailsValues();
		
		 
		 
		 public ViewDetailsAdapter (Context context,
		            ArrayList<PolicyDetailsValues> arraylist){
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
			TextView mVehicleUsage, mModelYear,
			mVehicleColor, mVehicleMake, mVehicleMileage, mVehicleNumber,
			mVehicleValue, mEngineNumber, mChassisNumber, mNotes,lbUage,
			lbModelYear, lbcolor, lbMake, lbMil, lbValue,
			lbEng, lbChass, lbNotes, lbNo,mVehicleModel;
			
			
			inflater = (LayoutInflater) context
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 View itemView = inflater.inflate(R.layout.view_policy_detail_adapter, parent, false);
			 cust = data.get(position);
			 
			 /*
			  *  Initialisations of view ,typeface.
			  */
			 //************************************************************************
			 	Typeface face = Typeface.createFromAsset(context.getAssets(),
						CONSTANTS.FONT_NAME);
			 	mVehicleModel = (TextView)itemView.findViewById(R.id.txt_Vehicle_Name);
			 	mVehicleModel.setTypeface(face,Typeface.BOLD);
				lbUage = (TextView)itemView.findViewById(R.id.txt_Vehicle_Usage);
				lbUage.setTypeface(face);
				mVehicleUsage = (TextView)itemView.findViewById(R.id.txt_Vehicle_Usage_Value);
				mVehicleUsage.setTypeface(face, Typeface.BOLD);
				lbModelYear = (TextView) itemView.findViewById(R.id.txt_Model_Year);
				lbModelYear.setTypeface(face);
				mModelYear = (TextView) itemView.findViewById(R.id.txt_Model_Year_Value);
				mModelYear.setTypeface(face, Typeface.BOLD);
				lbcolor = (TextView)itemView. findViewById(R.id.txt_Vehicle_Color);
				lbcolor.setTypeface(face);
				mVehicleColor = (TextView)itemView. findViewById(R.id.txt_Vehicle_Color_Value);
				mVehicleColor.setTypeface(face, Typeface.BOLD);
				lbMake = (TextView)itemView.findViewById(R.id.txt_Vehicle_Make);
				lbMake.setTypeface(face);
				mVehicleMake = (TextView)itemView.findViewById(R.id.txt_Vehicle_Make_Value);
				mVehicleMake.setTypeface(face, Typeface.BOLD);
				lbMil = (TextView)itemView.findViewById(R.id.txt_Vehicle_Mileage);
				lbMil.setTypeface(face);
				mVehicleMileage = (TextView)itemView.findViewById(R.id.txt_Vehicle_Mileage_Value);
				mVehicleMileage.setTypeface(face, Typeface.BOLD);
				lbNo = (TextView)itemView.findViewById(R.id.txt_Vehicle_Number);
				lbNo.setTypeface(face);
				mVehicleNumber = (TextView)itemView.findViewById(R.id.txt_Vehicle_Number_Value);
				mVehicleNumber.setTypeface(face, Typeface.BOLD);
				lbValue = (TextView)itemView.findViewById(R.id.txt_Vehicle_Value);
				lbValue.setTypeface(face);
				mVehicleValue = (TextView)itemView.findViewById(R.id.txt_Vehicle_Value_Value);
				mVehicleValue.setTypeface(face, Typeface.BOLD);
				lbEng = (TextView)itemView.findViewById(R.id.txt_Engine_Number);
				lbEng.setTypeface(face);
				mEngineNumber = (TextView) itemView.findViewById(R.id.txt_Engine_Number_Value);
				mEngineNumber.setTypeface(face, Typeface.BOLD);
				lbChass = (TextView)itemView.findViewById(R.id.txt_Chassis_Number);
				lbChass.setTypeface(face);
				mChassisNumber = (TextView)itemView.findViewById(R.id.txt_Chassis_Number_Value);
				mChassisNumber.setTypeface(face, Typeface.BOLD);
				lbNotes = (TextView)itemView.findViewById(R.id.txt_Notes);
				lbNotes.setTypeface(face);
				mNotes = (TextView)itemView.findViewById(R.id.txt_Notes_Value);
				mNotes.setTypeface(face, Typeface.BOLD);
          //**************************************************************************************************
			
				/* Display  the data in fields after getting the same 
				 * from its Bean class. 			
				 */
				mVehicleModel.setText(cust.getmVehicleMake()+" "+" - "+cust.getmVehicleModel());
				mVehicleUsage.setText(cust.getmVehicleUsage());
				mModelYear.setText(cust.getmModelYear());
				mVehicleColor.setText(cust.getmVehicleColor());
				mVehicleMake.setText(cust.getmVehicleMake());
				mVehicleMileage.setText(cust.getmVehicleMileage());
				mVehicleNumber.setText(cust.getmVehicleNumber());
			
				mVehicleValue.setText(cust.getmVehicleValue());
				mEngineNumber.setText(cust.getmEngineNumber());
				mChassisNumber.setText(cust.getmChassisNumber());
				mNotes.setText(cust.getmNotes());
				
				
			return itemView;
		}


		

	 
}
