package com.custodian;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DisplayMessageAlert {


		@SuppressWarnings("deprecation")
		public DisplayMessageAlert(Context context, 
				String message, String buttonText) 
		{
//			Log.i("VIAMO","in single option alert class");
			//alert dialog functionality
			AlertDialog alertDialog = new AlertDialog.Builder(context).create();
			//hide title bar
			//alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			alertDialog.setTitle("Custodian");
			//set the icon.
			//alertDialog.setIcon(context.getResources().getDrawable(R.drawable.ic_launcher));
			//set the message			
			alertDialog.setMessage(message);
			//set button1 functionality
			alertDialog.setButton(buttonText, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					//close dialog				
					dialog.dismiss();	
					
					
				}
			});
			//show the alert dialog
			alertDialog.show();
		} 
	}


