package com.custodian.ManagePolicy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;

/**
 * Adapter class of Payment of Payment History Module.
 */
@SuppressLint("SimpleDateFormat")
public class CustodianServicePaymentAdapter extends BaseAdapter {

	Context context;
	LayoutInflater inflater;
	ArrayList<CustodianServicePayment> data;
	CustodianServicePayment cust = new CustodianServicePayment();

	public CustodianServicePaymentAdapter(Context context,
			ArrayList<CustodianServicePayment> arraylist) {
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
		@SuppressWarnings("unused")
		TextView txtpostingDate, txtAmount, txtInvoiceNo, txtDesc, txtpostingDateTitle, txtAmountTitle, txtInvoiceNoTitle;
		@SuppressWarnings("unused")
		Typeface face = Typeface.createFromAsset(context.getAssets(),
				CONSTANTS.FONT_NAME);

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(
				R.layout.custodian_service_center_payment, parent, false);
		cust = data.get(position);

		txtpostingDate = (TextView) itemView
				.findViewById(R.id.txt_postingDate_value);
		txtAmount = (TextView) itemView.findViewById(R.id.txt_amount_value);
		txtInvoiceNo = (TextView) itemView
				.findViewById(R.id.txt_invoiceno_value);
		txtDesc = (TextView) itemView.findViewById(R.id.txt_description);

		/*
		 * Change the milliseconds in date
		 */
		String parsed = "";
		String x = cust.getPostingDate();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if (x != null) {

			long milliSecondsstart = Long.parseLong(x);
			System.out.println(milliSecondsstart);

			Calendar calendarStart = Calendar.getInstance();
			calendarStart.setTimeInMillis(milliSecondsstart);
			parsed = (dateFormat.format(calendarStart.getTime()));
		} else {

		}
		String amount = cust.getAmount();
		String value = null;

		/*
		 * if amount contains .0 it will rouunded off to whole number.
		 */
		if (amount != null) {
			if (amount.contains(".0")) {
				value = amount.replace(".0", "");
			}
		} else {

		}

		txtpostingDate.setText(parsed);
		txtAmount.setText("N " + value);
		txtInvoiceNo.setText(cust.getInvoiceNo());
		txtDesc.setText(cust.getDesc());

		return itemView;
	}

}
