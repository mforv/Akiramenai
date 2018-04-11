package mforv.progs.japan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.*;

public class TestPrep extends Activity implements OnClickListener{
	//Интерфейс
	protected Button start;
	protected EditText count;
	protected EditText time;
	protected TextView stat;
	protected TextView headline;
	protected Button qplus;
	protected Button qminus;
	protected Button tplus;
	protected Button tminus;
	protected Button statistics;
	protected LinearLayout countlay;
	protected LinearLayout timerlay;
	//Переменные
	int numberofq = 0;
	int stats = 0;
	long seconds = 30000;
	boolean closed = false;
	boolean n5state = true;
	boolean n4state = false;
	boolean testready1 = false;
	boolean testready2 = false;
	//Тут переменные для на сбора статистики
	public static final String APP_STATISTICS = "teststats"; //файл статистики
	public static final String APP_STATISTICS_TEST_AMOUNT = "testamount"; //общее количество тестов
	public static final String APP_STATISTICS_QUESTION_AMOUNT = "questionamount"; //общее количество отвеченных вопросов
	public static final String APP_STATISTICS_RIGHT_ANSWERS = "rightanswers"; //общее количество правильных ответов
	SharedPreferences userStats;
	Editor editor;
	long TestAmount = 0;
	long QuestionAmount = 0;
	long RightAnswers = 0;
	//Функции
	public void StatsOperations(){
		userStats = getSharedPreferences(APP_STATISTICS, Context.MODE_PRIVATE);
		editor = userStats.edit();
		TestAmount = userStats.getLong(APP_STATISTICS_TEST_AMOUNT, 0);
		editor.putLong(APP_STATISTICS_TEST_AMOUNT, TestAmount+1);
		editor.commit();
	}
	private void getPrefs(){
		 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		 numberofq = Integer.parseInt(prefs.getString("amount_of_q", "3"));
		 seconds = 1000*Long.parseLong(prefs.getString("timer_set", "30"));
		 n5state = prefs.getBoolean("n5state", true);
		 n4state = prefs.getBoolean("n4state", false);
	}
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_prepare);
        //Связь с элементами интерфейса
        start = (Button) findViewById(R.id.readybtn);
        statistics = (Button) findViewById(R.id.ststbutton);
        count = (EditText) findViewById(R.id.questnmb);
        time = (EditText) findViewById(R.id.timersec);
        stat = (TextView) findViewById(R.id.statline);
        headline = (TextView) findViewById(R.id.testheadline);
        qplus = (Button) findViewById(R.id.questplus);
        qminus = (Button) findViewById(R.id.questminus);
        tplus = (Button) findViewById(R.id.timerplus);
        tminus = (Button) findViewById(R.id.timerminus);
        countlay = (LinearLayout) findViewById(R.id.countsettings);
        timerlay = (LinearLayout) findViewById(R.id.timersettings);
        //Прослушка
        start.setOnClickListener(this);
        statistics.setOnClickListener(this);
        qplus.setOnClickListener(this);
        qminus.setOnClickListener(this);
        tplus.setOnClickListener(this);
        tminus.setOnClickListener(this);
        count.addTextChangedListener(new TextWatcher(){

			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if (count.getText().length() != 0)
				{
					numberofq = Integer.parseInt(count.getText().toString());
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        time.addTextChangedListener(new TextWatcher(){

			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (time.getText().length() != 0){
					seconds = (Long.parseLong(time.getText().toString()))*1000;
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        //Экстры
		final Bundle extras = getIntent().getExtras();
		if (extras!=null) {
			stats=extras.getInt("stat");
			numberofq=extras.getInt("count");
			}
		//Проверяем, из теста или нет вызвана активность
		if (numberofq > 0)
		{
			closed = true;
			countlay.setVisibility(4); //это такой код INVISIBLE
			timerlay.setVisibility(4);
			statistics.setVisibility(4);
			start.setText("Завершить");
			stat.setText("Тест выполнен. Результат: "+stats+"/"+numberofq);
			stat.setGravity(Gravity.CENTER);
			numberofq = 0;
			stats = 0;
		}
		//Устанавливаем количество вопросов по умолчанию после всего
		getPrefs();//numberofq = 3;
		count.setText(""+numberofq);
		time.setText(""+seconds/1000);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == start)
		{
			if (closed == false)
			{
				if (count.getText().length() == 0 || time.getText().length() == 0)
				{
					Toast.makeText(getApplicationContext(), "Укажите параметры теста или установите параметры по умолчанию на экране настроек", Toast.LENGTH_SHORT).show();
				}
				else
				{
					if (Integer.parseInt(count.getText().toString()) > 0 && Long.parseLong(count.getText().toString()) < 10000)
						{
							numberofq = Integer.parseInt(count.getText().toString());
							testready1 = true;
						}
					else {
						testready1 = false;
						Toast.makeText(getApplicationContext(), "Количество вопросов должно быть больше нуля и меньше 10000", Toast.LENGTH_SHORT).show();
						} 
					if (Long.parseLong(time.getText().toString()) > 0 && Long.parseLong(time.getText().toString()) < 10000)
						{
							seconds = (Long.parseLong(time.getText().toString())*1000);
							testready2 = true;
						}
					else {
						testready2 = false;
						Toast.makeText(getApplicationContext(), "Количество секунд для ответа должно быть больше нуля и меньше 10000", Toast.LENGTH_SHORT).show();
						} 
					
					if (testready1 == true && testready2 == true)
					{
						Intent k = new Intent(this,TestBody.class);
						k.putExtra("count", numberofq);
						k.putExtra("qtime", seconds);
						StatsOperations();
						startActivity(k);
					}
				}
			}
			else 
			{
				this.finish();
			}
		}
		if (v == qplus)
		{
			numberofq = numberofq+1;
			count.setText(""+numberofq);
		}
		if (v == qminus)
		{
			if (numberofq > 1)
			{
			numberofq = numberofq-1;
			count.setText(""+numberofq);
			}
		}
		if (v == tplus)
		{
			seconds = seconds+1000;
			time.setText(""+seconds/1000);
		}
		if (v == tminus)
		{
			if (seconds > 1000)
			{
			seconds = seconds-1000;
			time.setText(""+seconds/1000);
			}
		}
		if (v == statistics)
		{
			Intent y = new Intent(this, StatisticsActivity.class);
			startActivity(y);
		}
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
