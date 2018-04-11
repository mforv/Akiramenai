package mforv.progs.japan;

import java.math.BigDecimal;
import java.math.RoundingMode;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class StatisticsActivity extends Activity implements OnClickListener {
	//Интерфейс
	protected Button clean_stats;
	protected TextView test_amount;
	protected TextView question_amount;
	protected TextView right_answers;
	protected TextView r_a_percent;
	protected TextView on_amount;
	protected TextView on_right;
	protected TextView on_percent;
	protected TextView kun_amount;
	protected TextView kun_right;
	protected TextView kun_percent;
	protected TextView trans_amount;
	protected TextView trans_right;
	protected TextView trans_percent;
	//Переменные
	//Тут переменные для на сбора статистики
	public static final String APP_STATISTICS = "teststats"; //файл статистики
	public static final String APP_STATISTICS_TEST_AMOUNT = "testamount"; //общее количество тестов
	public static final String APP_STATISTICS_QUESTION_AMOUNT = "questionamount"; //общее количество отвеченных вопросов
	public static final String APP_STATISTICS_RIGHT_ANSWERS = "rightanswers"; //общее количество правильных ответов
	public static final String APP_STATISTICS_ON_TASK_AMOUNT = "onamount"; //общее количество заданий на он-ёми
	public static final String APP_STATISTICS_KUN_TASK_AMOUNT = "kunamount"; //общее количество заданий на кун-ёми
	public static final String APP_STATISTICS_TRANS_TASK_AMOUNT = "transamount"; //общее количество заданий на перевод
	public static final String APP_STATISTICS_RIGHT_ON = "righton"; //количество правильных ответов на он-ёми
	public static final String APP_STATISTICS_RIGHT_KUN = "rightkun"; //количество правильных ответов на кун-ёми
	public static final String APP_STATISTICS_RIGHT_TRANS = "righttrans"; //количество правильных ответов на перевод
	SharedPreferences userStats; 
	Editor editor;
	long TestAmount = 0;
	long QuestionAmount = 0;
	long RightAnswers = 0;
	long OnAmount = 0;
	long KunAmount = 0;
	long TransAmount = 0;
	long OnRight = 0;
	long KunRight = 0;
	long TransRight = 0;
	double Percent = 0;
	double OnPercent = 0;
	double KunPercent = 0;
	double TransPercent = 0;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);
        //Связь с лэйаутом
        clean_stats = (Button) findViewById(R.id.stats_clean_btn);
        test_amount = (TextView) findViewById(R.id.test_amount);
        question_amount = (TextView) findViewById(R.id.question_amount);
        right_answers = (TextView) findViewById(R.id.right_answers);
        r_a_percent = (TextView) findViewById(R.id.right_answers_percent);
        on_amount = (TextView) findViewById(R.id.on_task_amount);
        on_right = (TextView) findViewById(R.id.on_right_amount);
        on_percent = (TextView) findViewById(R.id.on_percent);
        kun_amount = (TextView) findViewById(R.id.kun_task_amount);
        kun_right = (TextView) findViewById(R.id.kun_right_amount);
        kun_percent = (TextView) findViewById(R.id.kun_percent);
        trans_amount = (TextView) findViewById(R.id.trans_task_amount);
        trans_right = (TextView) findViewById(R.id.trans_right_amount);
        trans_percent = (TextView) findViewById(R.id.trans_percent);
        clean_stats.setOnClickListener(this);
        //Непосредственно операции со статистикой
        userStats = getSharedPreferences(APP_STATISTICS, Context.MODE_PRIVATE);
        TestAmount = userStats.getLong(APP_STATISTICS_TEST_AMOUNT, 0);
        QuestionAmount = userStats.getLong(APP_STATISTICS_QUESTION_AMOUNT, 0);
        RightAnswers = userStats.getLong(APP_STATISTICS_RIGHT_ANSWERS, 0);
        OnAmount = userStats.getLong(APP_STATISTICS_ON_TASK_AMOUNT, 0);
        KunAmount = userStats.getLong(APP_STATISTICS_KUN_TASK_AMOUNT, 0);
        TransAmount = userStats.getLong(APP_STATISTICS_TRANS_TASK_AMOUNT, 0);
        OnRight = userStats.getLong(APP_STATISTICS_RIGHT_ON, 0);
        KunRight = userStats.getLong(APP_STATISTICS_RIGHT_KUN, 0);
        TransRight = userStats.getLong(APP_STATISTICS_RIGHT_TRANS, 0);
        if (QuestionAmount != 0)
        {
        	Percent = (double)RightAnswers / (double)QuestionAmount;
        }
        if (OnAmount != 0)
        {
        	OnPercent = (double)OnRight / (double)OnAmount;
        }
        if (KunAmount != 0)
        {
        	KunPercent = (double)KunRight / (double)KunAmount;
        }
        if (TransAmount != 0)
        {
        	TransPercent = (double)TransRight / (double)TransAmount;
        }
        //Вывод
        test_amount.setText(""+TestAmount);
        question_amount.setText(""+QuestionAmount);
        right_answers.setText(""+RightAnswers);
        r_a_percent.setText((new BigDecimal(Percent*100).setScale(2, RoundingMode.HALF_UP))+"%");
        on_amount.setText(""+OnAmount);
        on_right.setText(""+OnRight);
        on_percent.setText((new BigDecimal(OnPercent*100).setScale(2, RoundingMode.HALF_UP))+"%");
        kun_amount.setText(""+KunAmount);
        kun_right.setText(""+KunRight);
        kun_percent.setText((new BigDecimal(KunPercent*100).setScale(2, RoundingMode.HALF_UP))+"%");
        trans_amount.setText(""+TransAmount);
        trans_right.setText(""+TransRight);
        trans_percent.setText((new BigDecimal(TransPercent*100).setScale(2, RoundingMode.HALF_UP))+"%"); 
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
	        Intent t = new Intent(this, PreferencesActivtity.class);
	        startActivity(t);
	        return true;
	    case R.id.menu_stat:
	        return true;
	    case R.id.menu_about:
	    	Intent a = new Intent(this, AboutActivity.class);
	    	startActivity(a);
	    	return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		showDialog(110);
	}
	protected Dialog onCreateDialog(int id)
	{
    	switch (id) 
		{
		case 110:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Обнулить статистику?")
			        .setCancelable(false)
			        .setPositiveButton("OK", new DialogInterface.OnClickListener() 
				    {
			            public void onClick(DialogInterface dialog, int id) 
			            {
			            	editor = userStats.edit();
			        		editor.putLong(APP_STATISTICS_TEST_AMOUNT, 0);
			        		editor.putLong(APP_STATISTICS_QUESTION_AMOUNT, 0);
			        		editor.putLong(APP_STATISTICS_RIGHT_ANSWERS, 0);
			        		editor.putLong(APP_STATISTICS_ON_TASK_AMOUNT, 0);
			        		editor.putLong(APP_STATISTICS_KUN_TASK_AMOUNT, 0);
			        		editor.putLong(APP_STATISTICS_TRANS_TASK_AMOUNT, 0);
			        		editor.putLong(APP_STATISTICS_RIGHT_ON, 0);
			        		editor.putLong(APP_STATISTICS_RIGHT_KUN, 0);
			        		editor.putLong(APP_STATISTICS_RIGHT_TRANS, 0);
			        		editor.commit(); 
			        		StatisticsActivity.this.finish();
			            }
			        })
					.setNegativeButton("Отмена", new DialogInterface.OnClickListener() 
					{
			           public void onClick(DialogInterface dialog, int id) 
					    {
			               dialog.cancel(); 
			            }  
			        });
			//AlertDialog alert = builder.create();
            return builder.create();
		default:
			return null;
		}
	}
}
