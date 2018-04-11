package mforv.progs.japan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class TestBody extends Activity implements OnClickListener{
	static Random rand = new Random();
	protected Button btn1;
	protected Button btn2;
	protected Button btn3;
	protected Button btn4;
	protected ImageView sign;
	protected LinearLayout testback;
	protected TextView timerlbl;
	//Переменные
	boolean n5state = true;
	boolean n4state = false;
	boolean showright = false;
	boolean firstcall = true;
	boolean useon = true;
	boolean usekun = true;
	boolean usetrans = true;
	int transcrip = 1;
	int count = 0; //количество вопросов в тесте
	int curquest = 0; //номер текущего вопроса
	int order = 0; //номер иероглифа в массиве
	int currentanswer = 0; //выбранный ответ
	int rightanswer = 0; //правильный ответ
	int stats = 0; //статистика правильных ответов
	int tasktype = 0; //это переменная типов заданий (он-ёми, кун-ёми или перевод)
	int rcheck = 0; //эта переменная отвечает за то, чтобы варианты ответа на одно задание не повторялись
	int timer = 550; //это чтобы не напутать с паузами, таймер в миллисекундах (1000 = 1 сек)
	Display display; 
	int screenheight;
	double scalek = 2; //коэффициент мастштабирования - тоже прикольная шняжка, везде понапихал
	//ТАЙМЕР, ВЕЛИКИЙ И УЖАСНЫЙ
	long qtime = 30000;
	CountDownTimer countdown;
	//Конец объявления таймера
	ArrayList<String> signs = new ArrayList<String>();
	ArrayList<String> answers = new ArrayList<String>();
	//Тут переменные для на сбора статистики
	public static final String APP_STATISTICS = "teststats"; //файл статистики
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
	long QuestionAmount = 0;
	long RightAnswers = 0;
	long OnAmount = 0;
	long KunAmount = 0;
	long TransAmount = 0;
	long OnRight = 0;
	long KunRight = 0;
	long TransRight = 0;
	//Функции
	private void addRightAnswerStats(int type){
		userStats = getSharedPreferences(APP_STATISTICS, Context.MODE_PRIVATE);
		editor = userStats.edit();
		RightAnswers = userStats.getLong(APP_STATISTICS_RIGHT_ANSWERS, 0);
		editor.putLong(APP_STATISTICS_RIGHT_ANSWERS, RightAnswers+1);
		if (type == 0)
		{
			OnRight = userStats.getLong(APP_STATISTICS_RIGHT_ON, 0);
			editor.putLong(APP_STATISTICS_RIGHT_ON, OnRight+1);
		}
		if (type == 1)
		{
			KunRight = userStats.getLong(APP_STATISTICS_RIGHT_KUN, 0);
			editor.putLong(APP_STATISTICS_RIGHT_KUN, KunRight+1);
		}
		if (type == 2)
		{
			TransRight = userStats.getLong(APP_STATISTICS_RIGHT_TRANS, 0);
			editor.putLong(APP_STATISTICS_RIGHT_TRANS, TransRight+1);
		}
		editor.commit();
	}
	private void addTaskStats(int type){
		userStats = getSharedPreferences(APP_STATISTICS, Context.MODE_PRIVATE);
		editor = userStats.edit();
		QuestionAmount = userStats.getLong(APP_STATISTICS_QUESTION_AMOUNT, 0);
		editor.putLong(APP_STATISTICS_QUESTION_AMOUNT, QuestionAmount+1);
		if (type == 0)
		{
			OnAmount = userStats.getLong(APP_STATISTICS_ON_TASK_AMOUNT, 0);
			editor.putLong(APP_STATISTICS_ON_TASK_AMOUNT, OnAmount+1);
		}
		if (type == 1)
		{
			KunAmount = userStats.getLong(APP_STATISTICS_KUN_TASK_AMOUNT, 0);
			editor.putLong(APP_STATISTICS_KUN_TASK_AMOUNT, KunAmount+1);
		}
		if (type == 2)
		{
			TransAmount = userStats.getLong(APP_STATISTICS_TRANS_TASK_AMOUNT, 0);
			editor.putLong(APP_STATISTICS_TRANS_TASK_AMOUNT, TransAmount+1);
		}
		editor.commit();
	}
	private void GenerateAnswers(Button v) //генерация неправильных ответов
	{
		int nrcheck = rand.nextInt(signs.size());
		if (nrcheck != order && nrcheck != rcheck)
		{
			v.setText(answers.get(rcheck));
			rcheck = nrcheck;
		}
		else GenerateAnswers(v);
	}
	private void LoadOnTask()
	{
		if (n5state == true)
		{
			Collections.addAll(signs, getResources().getStringArray(R.array.n5image));
			if (transcrip == 1)
			{
				Collections.addAll(answers, getResources().getStringArray(R.array.n5on));
			}
			if (transcrip == 2)
			{
				Collections.addAll(answers, getResources().getStringArray(R.array.n5onrom));
			}
			if (transcrip == 3)
			{
					Collections.addAll(answers, getResources().getStringArray(R.array.n5oncyr));
			}
		}
		order = rand.nextInt(signs.size());
		sign.setImageResource(0x7f020000+Integer.parseInt(signs.get(order),16));
		rightanswer = rand.nextInt(4)+1;
		switch (rightanswer)
		{
		case 1: {btn1.setText(answers.get(order));
				GenerateAnswers(btn2);
				GenerateAnswers(btn3);
				GenerateAnswers(btn4);}
		break;
		case 2: {btn2.setText(answers.get(order));
				GenerateAnswers(btn1);
				GenerateAnswers(btn3);
				GenerateAnswers(btn4);}
		break;
		case 3: {btn3.setText(answers.get(order));
				GenerateAnswers(btn1);
				GenerateAnswers(btn2);
				GenerateAnswers(btn4);}
		break;
		case 4: {btn4.setText(answers.get(order));
				GenerateAnswers(btn1);
				GenerateAnswers(btn2);
				GenerateAnswers(btn3);}
		break;
		}
		signs.clear();
		answers.clear();
	}
	private void LoadKunTask()
	{
		if (n5state == true)
		{
			Collections.addAll(signs, getResources().getStringArray(R.array.n5image));
			if (transcrip == 1)
			{
				
				Collections.addAll(answers, getResources().getStringArray(R.array.n5kun));
			}
			if (transcrip == 2)
			{
				Collections.addAll(answers, getResources().getStringArray(R.array.n5kunrom));
			}
			if (transcrip == 3)
			{
					Collections.addAll(answers, getResources().getStringArray(R.array.n5kuncyr));
			}
		}
		order = rand.nextInt(signs.size());
		sign.setImageResource(0x7f020000+Integer.parseInt(signs.get(order),16));
		rightanswer = rand.nextInt(4)+1;
		switch (rightanswer)
		{
		case 1: {btn1.setText(answers.get(order));
				GenerateAnswers(btn2);
				GenerateAnswers(btn3);
				GenerateAnswers(btn4);}
		break;
		case 2: {btn2.setText(answers.get(order));
				GenerateAnswers(btn1);
				GenerateAnswers(btn3);
				GenerateAnswers(btn4);}
		break;
		case 3: {btn3.setText(answers.get(order));
				GenerateAnswers(btn1);
				GenerateAnswers(btn2);
				GenerateAnswers(btn4);}
		break;
		case 4: {btn4.setText(answers.get(order));
				GenerateAnswers(btn1);
				GenerateAnswers(btn2);
				GenerateAnswers(btn3);}
		break;
		}
		signs.clear();
		answers.clear();
	}
	private void LoadTransTask()
	{
		if (n5state == true)
		{
			Collections.addAll(signs, getResources().getStringArray(R.array.n5image));
			Collections.addAll(answers, getResources().getStringArray(R.array.n5translation));
		}
		order = rand.nextInt(signs.size());
		sign.setImageResource(0x7f020000+Integer.parseInt(signs.get(order),16));
		rightanswer = rand.nextInt(4)+1;
		switch (rightanswer)
		{
		case 1: {btn1.setText(answers.get(order));
				GenerateAnswers(btn2);
				GenerateAnswers(btn3);
				GenerateAnswers(btn4);}
		break;
		case 2: {btn2.setText(answers.get(order));
				GenerateAnswers(btn1);
				GenerateAnswers(btn3);
				GenerateAnswers(btn4);}
		break;
		case 3: {btn3.setText(answers.get(order));
				GenerateAnswers(btn1);
				GenerateAnswers(btn2);
				GenerateAnswers(btn4);}
		break;
		case 4: {btn4.setText(answers.get(order));
				GenerateAnswers(btn1);
				GenerateAnswers(btn2);
				GenerateAnswers(btn3);}
		break;
		}
		signs.clear();
		answers.clear();
	}
	private void LoadTask()
	{
		
		if (useon == true && usekun == false && usetrans == false)
		{
			tasktype = 0;
		}
		else if (useon == false && usekun == true && usetrans == false)
		{
			tasktype = 1;
		}
		else if (useon == false && usekun == false && usetrans == true)
		{
			tasktype = 2;
		}
		else if (useon == false && usekun == true && usetrans == true)
		{
			int temp = rand.nextInt(2);
			if (temp == 0)
			{
				tasktype = 1;
			}
			if (temp == 1)
			{
				tasktype = 2;
			}
		}
		else if (useon == true && usekun == true && usetrans == false)
		{
			tasktype = rand.nextInt(2);
		}
		else if (useon == true && usekun == false && usetrans == true)
		{
			int temp = rand.nextInt(2);
			if (temp == 0)
			{
				tasktype = 0;
			}
			if (temp == 1)
			{
				tasktype = 2;
			}
		}
		else {
			tasktype = rand.nextInt(3);
		}
		
		if (tasktype == 0)
		{
				LoadOnTask();
		}
		if (tasktype == 1)
		{
				LoadKunTask();
		}
		if (tasktype == 2)
		{
				LoadTransTask();
		}
	}
	private void CheckCount()
	{
		curquest = curquest+1;
		btn1.setBackgroundResource(android.R.drawable.btn_default);
		btn2.setBackgroundResource(android.R.drawable.btn_default);
		btn3.setBackgroundResource(android.R.drawable.btn_default);
		btn4.setBackgroundResource(android.R.drawable.btn_default);
		if (curquest >= count)
		{
			Intent l = new Intent(this, TestPrep.class);
			l.putExtra("stat", stats);
			l.putExtra("count", count);
			startActivityFromChild(this, l, 0);
			this.finish();
		}
		else {
			LoadTask();
			countdown.start();
		}
	}
	private void ShowRightAnswer()
	{
		if (showright == true)
		{
			switch (rightanswer)
			{
			case 1: btn1.setBackgroundResource(R.drawable.zbutton_pressed_green);
					break;
			case 2: btn2.setBackgroundResource(R.drawable.zbutton_pressed_green);
					break;
			case 3: btn3.setBackgroundResource(R.drawable.zbutton_pressed_green);
					break;
			case 4: btn4.setBackgroundResource(R.drawable.zbutton_pressed_green);
					break;
			}
		}
	}
	private void getPrefs(){
		 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		 n5state = prefs.getBoolean("n5state", true);
		 n4state = prefs.getBoolean("n4state", false);
		 transcrip = Integer.parseInt(prefs.getString("transcrip", "1"));
		 showright = prefs.getBoolean("show_right", false);
		 useon = prefs.getBoolean("on_state", true);
		 usekun = prefs.getBoolean("kun_state", true);
		 usetrans = prefs.getBoolean("trans_state", true);
	}

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing);
        //Элементы интерфейса
        btn1 = (Button) findViewById(R.id.testbtn1);
        btn2 = (Button) findViewById(R.id.testbtn2);
        btn3 = (Button) findViewById(R.id.testbtn3);
        btn4 = (Button) findViewById(R.id.testbtn4);
        sign = (ImageView) findViewById(R.id.testpic);
        testback = (LinearLayout) findViewById(R.id.test_main_back);
        timerlbl = (TextView) findViewById(R.id.timertext);
        //Прослушка
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        //Берем настройки приложения
        getPrefs();
        //Подгоняем размерчик иероглифа
		display = getWindowManager().getDefaultDisplay();
		screenheight = display.getHeight();
		sign.setMaxHeight((int)(screenheight/scalek));
		//Экстры
		final Bundle extras = getIntent().getExtras();
		if (extras!=null) {
			count=extras.getInt("count");
			qtime=extras.getLong("qtime");
			}
		//Установка обратного отсчета
		countdown = new CountDownTimer(qtime, 1000){
			public void onTick(long millisUntilFinished){
				timerlbl.setText("Осталось секунд: "+millisUntilFinished/1000);
			}
			public void onFinish(){
				CheckCount();
			}
		};
		//Тут она кончается
		
		//Тут наконец грузится первый иероглиф теста
		if (firstcall == true)
		{
			LoadTask();
			countdown.start();
			firstcall = false;
		}
	}

	@Override
	public void onBackPressed(){
		countdown.cancel();
		this.finish();
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btn1)
		{
			countdown.cancel();
			addTaskStats(tasktype);
			currentanswer = 1;
			if (currentanswer == rightanswer)
			{
				stats = stats+1;
				btn1.setBackgroundResource(R.drawable.zbutton_pressed_green);
				addRightAnswerStats(tasktype);
			}
			else {
				btn1.setBackgroundResource(R.drawable.zbutton_pressed_red);
				ShowRightAnswer();
			}
		    Handler handler = new Handler(); 
		    handler.postDelayed(new Runnable() { 
		         public void run() { 
		             CheckCount(); 
		         } 
		    }, timer); 	
		}
		if (v == btn2)
		{
			countdown.cancel();
			addTaskStats(tasktype);
			currentanswer = 2;
			if (currentanswer == rightanswer)
			{
				stats = stats+1;
				btn2.setBackgroundResource(R.drawable.zbutton_pressed_green);
				addRightAnswerStats(tasktype);
			}
			else {
				btn2.setBackgroundResource(R.drawable.zbutton_pressed_red);
				ShowRightAnswer();
			}
		    Handler handler = new Handler(); 
		    handler.postDelayed(new Runnable() { 
		         public void run() { 
		             CheckCount(); 
		         } 
		    }, timer); 
		}
		if (v == btn3)
		{
			countdown.cancel();
			addTaskStats(tasktype);
			currentanswer = 3;
			if (currentanswer == rightanswer)
			{
				stats = stats+1;
				btn3.setBackgroundResource(R.drawable.zbutton_pressed_green);
				addRightAnswerStats(tasktype);
			}
			else {
				btn3.setBackgroundResource(R.drawable.zbutton_pressed_red);
				ShowRightAnswer();
			}
		    Handler handler = new Handler(); 
		    handler.postDelayed(new Runnable() { 
		         public void run() { 
		             CheckCount(); 
		         } 
		    }, timer); 
			
		}
		if (v == btn4)
		{
			countdown.cancel();
			addTaskStats(tasktype);
			currentanswer = 4;
			if (currentanswer == rightanswer)
			{
				stats = stats+1;
				btn4.setBackgroundResource(R.drawable.zbutton_pressed_green);
				addRightAnswerStats(tasktype);
			}
			else {
				btn4.setBackgroundResource(R.drawable.zbutton_pressed_red);
				ShowRightAnswer();
			}
		    Handler handler = new Handler(); 
		    handler.postDelayed(new Runnable() { 
		         public void run() { 
		             CheckCount(); 
		         } 
		    }, timer); 
		}
	}
}
