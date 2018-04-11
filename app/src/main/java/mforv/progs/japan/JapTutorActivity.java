package mforv.progs.japan;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class JapTutorActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	protected Button starttutoring;
	protected Button starttesting;
	protected Button startsetting;
	protected Button startabout;
	public boolean n5state = true;
	public boolean n4state = false;
	boolean useon = true;
	boolean usekun = true;
	boolean usetrans = true;
	private void getPrefs(){
		 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		 n5state = prefs.getBoolean("n5state", true);
		 n4state = prefs.getBoolean("n4state", false);
		 useon = prefs.getBoolean("on_state", true);
		 usekun = prefs.getBoolean("kun_state", true);
		 usetrans = prefs.getBoolean("trans_state", true);
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getPrefs();
        //Связываем элементы с айди
        starttutoring = (Button) findViewById(R.id.starttutoringbtn);
        starttesting = (Button) findViewById(R.id.starttestbtn);
        startsetting = (Button) findViewById(R.id.startsettingsbtn);
        startabout = (Button) findViewById(R.id.startaboutbtn);
        //Прослушка
        starttutoring.setOnClickListener(this);
        starttesting.setOnClickListener(this);
        startsetting.setOnClickListener(this);
        startabout.setOnClickListener(this);
    }

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == starttutoring)
		{
			getPrefs();
			if (n5state == false && n4state == false)
			{
				Toast.makeText(getApplicationContext(), "Необходимо подключить хотя бы один словарь", Toast.LENGTH_SHORT).show();
			}
			else {
				Intent i = new Intent(this,ContentsActivity.class);
				startActivity(i);
			}
		}
		if (v == starttesting)
		{
			getPrefs();
			if (useon == false && usekun == false && usetrans == false)
			{
				Toast.makeText(getApplicationContext(), "Необходимо включить хотя бы один тип заданий", Toast.LENGTH_SHORT).show();
			}
			else {
			if (n5state == false && n4state == false)
			{
				Toast.makeText(getApplicationContext(), "Необходимо подключить хотя бы один словарь", Toast.LENGTH_SHORT).show();
			}
			else {
				Intent j = new Intent(this, TestPrep.class);
				startActivity(j);
			}
			}
		}
		if (v == startsetting)
		{
			Intent p = new Intent(this, PreferencesActivtity.class);
			startActivity(p);
		}
		if (v == startabout)
		{
			Intent l = new Intent(this, AboutActivity.class);
			startActivity(l);
		}
	}
}