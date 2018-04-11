package mforv.progs.japan;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class ContentsActivity extends Activity implements OnItemClickListener{
	protected GridView contentlist;
	//Переменные
	boolean n5state = true;
	boolean n4state = false;
	int order = 0;
	boolean rom = false;
	ArrayAdapter<String> adapt;
	ArrayList<String> signs = new ArrayList<String>();
	private void getPrefs(){
		 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		 n5state = prefs.getBoolean("n5state", true);
		 n4state = prefs.getBoolean("n4state", false);
	}
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contents);
        contentlist = (GridView) findViewById(R.id.contentslist);
        contentlist.setOnItemClickListener(this);
        contentlist.setGravity(Gravity.CENTER);
        getPrefs();
		if (n5state == true)
		{
			Collections.addAll(signs, getResources().getStringArray(R.array.n5signs));
		}
		/*if (n4state == true) //TODO Когда введем n4 - раскомментить
		{
			Collections.addAll(signs, getResources().getStringArray(R.array.n4image));
		}*/
		adapt = new ArrayAdapter<String>(this, R.layout.list_item, signs);
        contentlist.setAdapter(adapt);
		}

	public void onItemClick(AdapterView<?> list, View item, int position, long id) {
		// TODO Auto-generated method stub
		order = position;
		Intent h = new Intent(this,Tutoring.class);
		h.putExtra("order", order);
		startActivity(h);
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
	        return true;
	    case R.id.menu_testing:
	        Intent p = new Intent(this, TestPrep.class);
	        startActivity(p);
	        return true;
	    case R.id.menu_settings:
	        Intent t = new Intent(this, PreferencesActivtity.class);
	        startActivity(t);
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
}
