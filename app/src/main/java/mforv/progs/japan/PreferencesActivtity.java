package mforv.progs.japan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.*;
import android.preference.Preference.OnPreferenceChangeListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class PreferencesActivtity extends PreferenceActivity implements OnPreferenceChangeListener{
	private EditTextPreference check_q;
	private EditTextPreference check_t;
	private String ad_title = "Неверное значение";
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.preferences);
	    check_q = (EditTextPreference) getPreferenceScreen().findPreference("amount_of_q");
	    check_q.setOnPreferenceChangeListener(this);
	    check_t = (EditTextPreference) getPreferenceScreen().findPreference("timer_set");
	    check_t.setOnPreferenceChangeListener(this);
	}
	private void createAlert(String message)
	{
		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		ad.setTitle(ad_title);
		ad.setMessage(message);
		ad.setCancelable(true);
		ad.setNeutralButton("Закрыть", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		ad.show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Операции для выбранного пункта меню
	    switch (item.getItemId()) 
		{
	    case R.id.menu_tutoring:
	        Intent g = new Intent(this, ContentsActivity.class);
	        startActivity(g);
	        return true;
	    case R.id.menu_testing:
	        Intent p = new Intent(this, TestPrep.class);
	        startActivity(p);
	        return true;
	    case R.id.menu_settings:
	        return true;
	    case R.id.menu_stat:
	        Intent s = new Intent(this, StatisticsActivity.class);
	        startActivity(s);
	        return true;
	    case R.id.menu_about:
	    	Intent a = new Intent(this, AboutActivity.class);
	    	startActivity(a);
	    	return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
		public boolean onPreferenceChange(Preference preference, Object newValue) {
		// TODO Auto-generated method stub
		if(preference == check_q)
		{
			if (newValue.toString().length() > 0)
			{
				if (Long.parseLong(newValue.toString()) > 9999 || Long.parseLong(newValue.toString()) <= 0)
				{
					createAlert("Укажите число в промежутке от 0 до 9999");
					return false;
					}
				}
			else
			{
				createAlert("Укажите число в промежутке от 0 до 9999");
				return false;
				}
			}
		if (preference == check_t)
		{
			if (newValue.toString().length() > 0)
			{
				if (Long.parseLong(newValue.toString()) > 9999 || Long.parseLong(newValue.toString()) <= 0)
				{
					createAlert("Укажите число в промежутке от 0 до 9999");
					return false;
					}
				}
			else
			{
				createAlert("Укажите число в промежутке от 0 до 9999");
				return false;
				}
			}
		return true;
	}
}
