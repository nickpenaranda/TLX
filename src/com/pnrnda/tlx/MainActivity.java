package com.pnrnda.tlx;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private static final String tag = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.button_done).setOnClickListener(this);
		findViewById(R.id.button_clear).setOnClickListener(this);
		
		restoreIds();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.action_export) {
			export();
			return true;
		} else if(item.getItemId() == R.id.action_clear) {
			clear();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.button_done) {
			done();
		} else if(v.getId() == R.id.button_clear) {
			reset();
		}
	}
	
	private void reset() {
		clearSelections();
		findViewById(R.id.edit_eid).requestFocus();
	}
	
	private void done() {
		int mentalId = ((RadioGroup)findViewById(R.id.group_mental)).getCheckedRadioButtonId();
		int physicalId = ((RadioGroup)findViewById(R.id.group_physical)).getCheckedRadioButtonId();
		int temporalId = ((RadioGroup)findViewById(R.id.group_temporal)).getCheckedRadioButtonId();
		int performanceId = ((RadioGroup)findViewById(R.id.group_performance)).getCheckedRadioButtonId();
		int effortId = ((RadioGroup)findViewById(R.id.group_effort)).getCheckedRadioButtonId();
		int frustrationId = ((RadioGroup)findViewById(R.id.group_frustration)).getCheckedRadioButtonId();
		
		int mental, physical, temporal, performance, effort, frustration;
		
		String participantId = ((EditText)findViewById(R.id.edit_pid)).getText().toString();		
		String entryId = ((EditText)findViewById(R.id.edit_eid)).getText().toString();
		
		switch(mentalId) {
		case R.id.mental_1:
			mental = 1;
			break;
		case R.id.mental_2:
			mental = 2;
			break;
		case R.id.mental_3:
			mental = 3;
			break;
		case R.id.mental_4:
			mental = 4;
			break;
		case R.id.mental_5:
			mental = 5;
			break;
		case R.id.mental_6:
			mental = 6;
			break;
		case R.id.mental_7:
			mental = 7;
			break;
		default:
			mental = -1;
			break;
		}

		switch(physicalId) {
		case R.id.physical_1:
			physical = 1;
			break;
		case R.id.physical_2:
			physical = 2;
			break;
		case R.id.physical_3:
			physical = 3;
			break;
		case R.id.physical_4:
			physical = 4;
			break;
		case R.id.physical_5:
			physical = 5;
			break;
		case R.id.physical_6:
			physical = 6;
			break;
		case R.id.physical_7:
			physical = 7;
			break;
		default:
			physical = -1;
			break;
		}

		switch(temporalId) {
		case R.id.temporal_1:
			temporal = 1;
			break;
		case R.id.temporal_2:
			temporal = 2;
			break;
		case R.id.temporal_3:
			temporal = 3;
			break;
		case R.id.temporal_4:
			temporal = 4;
			break;
		case R.id.temporal_5:
			temporal = 5;
			break;
		case R.id.temporal_6:
			temporal = 6;
			break;
		case R.id.temporal_7:
			temporal = 7;
			break;
		default:
			temporal = -1;
			break;
		}
	
		switch(performanceId) {
		case R.id.performance_1:
			performance = 1;
			break;
		case R.id.performance_2:
			performance = 2;
			break;
		case R.id.performance_3:
			performance = 3;
			break;
		case R.id.performance_4:
			performance = 4;
			break;
		case R.id.performance_5:
			performance = 5;
			break;
		case R.id.performance_6:
			performance = 6;
			break;
		case R.id.performance_7:
			performance = 7;
			break;
		default:
			performance = -1;
			break;
		}

		switch(effortId) {
		case R.id.effort_1:
			effort = 1;
			break;
		case R.id.effort_2:
			effort = 2;
			break;
		case R.id.effort_3:
			effort = 3;
			break;
		case R.id.effort_4:
			effort = 4;
			break;
		case R.id.effort_5:
			effort = 5;
			break;
		case R.id.effort_6:
			effort = 6;
			break;
		case R.id.effort_7:
			effort = 7;
			break;
		default:
			effort = -1;
			break;
		}
	
		switch(frustrationId) {
		case R.id.frustration_1:
			frustration = 1;
			break;
		case R.id.frustration_2:
			frustration = 2;
			break;
		case R.id.frustration_3:
			frustration = 3;
			break;
		case R.id.frustration_4:
			frustration = 4;
			break;
		case R.id.frustration_5:
			frustration = 5;
			break;
		case R.id.frustration_6:
			frustration = 6;
			break;
		case R.id.frustration_7:
			frustration = 7;
			break;
		default:
			frustration = -1;
			break;
		}
		
		saveIds(participantId, entryId);
		saveRecord(participantId, entryId, mental, physical, temporal, performance, effort, frustration);
		reset();
		((EditText)findViewById(R.id.edit_eid)).setText("");
	}
	
	private void saveIds(String participantId, String entryId) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor edit = pref.edit();
		if(participantId != null && !participantId.equals(""))
			edit.putString("participantId", participantId);
		else
			edit.remove("participantId");
		if(entryId != null && !entryId.equals(""))
			edit.putString("entryId", entryId);
		else
			edit.remove("entryId");
		
		edit.commit();
	}
	
	private void restoreIds() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		String participantId = pref.getString("participantId", null);
		String entryId = pref.getString("entryId", null);
		
		if(participantId != null)
			((EditText)findViewById(R.id.edit_pid)).setText(participantId);
		
		if(entryId != null)
			((EditText)findViewById(R.id.edit_eid)).setText(entryId);
	}

	private void saveRecord(String participantId, String entryId, int mental, int physical,
			int temporal, int performance, int effort, int frustration) {
		TLXSQLiteHelper helper = new TLXSQLiteHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		String _participantId = participantId.replace(',','_');
		String _entryId = entryId.replace(',','_');
		
		values.put("participant", _participantId);
		values.put("entry", _entryId);
		values.put("mental", mental);
		values.put("physical", physical);
		values.put("temporal", temporal);
		values.put("performance", performance);
		values.put("effort", effort);
		values.put("frustration", frustration);

		db.beginTransaction();
		try {
			db.insert("entries", null, values);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		
		Toast.makeText(this, "Response saved!", Toast.LENGTH_SHORT).show();
	}

	private void clearSelections() {
		((RadioGroup)findViewById(R.id.group_mental)).clearCheck();
		((RadioGroup)findViewById(R.id.group_physical)).clearCheck();
		((RadioGroup)findViewById(R.id.group_temporal)).clearCheck();
		((RadioGroup)findViewById(R.id.group_performance)).clearCheck();
		((RadioGroup)findViewById(R.id.group_effort)).clearCheck();
		((RadioGroup)findViewById(R.id.group_frustration)).clearCheck();
	}
	
	private void clear() {
		ClearDialogFragment f = new ClearDialogFragment();
		f.show(this.getSupportFragmentManager(), "clear_dialog");
	}
	
	private void doClear() {
		TLXSQLiteHelper helper = new TLXSQLiteHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		db.beginTransaction();
		try {
			db.delete("entries", null, null);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		
		Toast.makeText(this, "Responses cleared.", Toast.LENGTH_SHORT).show();
	}
	
	public static class ClearDialogFragment extends DialogFragment {
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			
			builder.setMessage("Clear *ALL* data?  This is irreversible")
				.setPositiveButton("Clear Data", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						((MainActivity) getActivity()).doClear();
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getActivity(), "Cancelled.", Toast.LENGTH_SHORT).show();
					}
				});
			
			return builder.create();
		}
	}
	
	private void export() {
		TLXSQLiteHelper helper = new TLXSQLiteHelper(this);
		SQLiteDatabase db = helper.getReadableDatabase();
		StringBuffer buffer = new StringBuffer();
		buffer.append("timestamp,participant_id,entry_id,mental," +
					"physical,temporal,performance,effort,frustration\n");
		
		Cursor c = db.query("entries", null, null, null, null, null, "timestamp");
		c.moveToFirst();
		while(!c.isAfterLast()) {
			buffer.append(String.format("%s,%s,%s,%d,%d,%d,%d,%d,%d\n",
					c.getString(1), c.getString(2), c.getString(3),
					c.getInt(4), c.getInt(5), c.getInt(6),
					c.getInt(7), c.getInt(8), c.getInt(9)));
			c.moveToNext();
		}
		c.close();
		
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_SUBJECT, "TLX Response Data");
		i.putExtra(Intent.EXTRA_TEXT, buffer.toString());
		try {
		    startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
