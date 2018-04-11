package mforv.progs.japan;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Tutoring extends Activity implements OnClickListener{
	static Random rand = new Random();
	protected Button onreadbtn;
	protected Button kunreadbtn;
	protected Button translate;
	protected Button showwriting;
	//protected Button examplebtn; кнопка примеров употребления, пока не введена
	protected EditText mainfield;
	protected ImageView mainimage;
	protected Button next;
	protected Button prev;
	protected Button random;
	protected LinearLayout piclay;
	//Переменные
	int order = 0;
	int border = 0;
	ArrayList<String> onread = new ArrayList<String>();
	ArrayList<String> kunread = new ArrayList<String>();
	ArrayList<String> translation = new ArrayList<String>();
	ArrayList<String> imagebuf = new ArrayList<String>();
	ArrayList<String> examples = new ArrayList<String>();
	boolean n5state = true; //статус словаря n5
	boolean n4state = false; //статус словаря n4
	int transcrip = 1; //выбранная транскрипция
	boolean writing = false;
	Display display; 
	int screenheight;
	double scalek = 2.5; //коэффициент мастштабирования - тоже прикольная шняжка, везде понапихал
	//Функции
	private void ImageLoad()
	{
		if (n5state == true)
		{
			Collections.addAll(imagebuf,getResources().getStringArray(R.array.n5image));
		}
		/*if (n4state == true) //TODO Когда введем n4 - раскомментить
		{
			Collections.addAll(imagebuf, getResources().getStringArray(R.array.n4image));
		}*/
		border = imagebuf.size();
		mainimage.setImageResource(0x7f020000+Integer.parseInt(imagebuf.get(order),16));
		mainimage.setScaleType(ScaleType.FIT_CENTER);
		imagebuf.clear();	
	}
	private void ImageWrite()
	{
		if (n5state == true)
		{
			Collections.addAll(imagebuf,getResources().getStringArray(R.array.n5writing));
		}
		/*if (n4state == true) //TODO Когда введем n4 - раскомментить
		{
			Collections.addAll(imagebuf, getResources().getStringArray(R.array.n4image));
		}*/
		border = imagebuf.size();
		mainimage.setImageResource(0x7f020050+Integer.parseInt(imagebuf.get(order),16));
		mainimage.setScaleType(ScaleType.CENTER_INSIDE);
		imagebuf.clear();	
	}
	private void getPrefs(){
		 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		 n5state = prefs.getBoolean("n5state", true);
		 n4state = prefs.getBoolean("n4state", false);
		 transcrip = Integer.parseInt(prefs.getString("transcrip", "1"));
	}
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.tutoring);
	        //Айди
	        onreadbtn = (Button) findViewById(R.id.onreading);
	        kunreadbtn = (Button) findViewById(R.id.kunreading);   
	        translate = (Button) findViewById(R.id.translatebtn);
	        showwriting = (Button) findViewById(R.id.writebtn);
	        //examplebtn = (Button) findViewById(R.id.examples_button);
	        mainfield = (EditText) findViewById(R.id.mainfield);
	        mainimage = (ImageView) findViewById(R.id.mainpic);
	        next = (Button) findViewById(R.id.nextbtn);
	        prev = (Button) findViewById(R.id.previousbtn);
	        random = (Button) findViewById(R.id.randbtn);
	        piclay = (LinearLayout) findViewById(R.id.pic_lay);
	        //Текст по центру
	        mainfield.setGravity(Gravity.CENTER);
	        //Прослушка
			onreadbtn.setOnClickListener(this);
			kunreadbtn.setOnClickListener(this);
			translate.setOnClickListener(this);
			showwriting.setOnClickListener(this);
			//examplebtn.setOnClickListener(this);
			next.setOnClickListener(this);
			prev.setOnClickListener(this);
			random.setOnClickListener(this);
			getPrefs();
			display = getWindowManager().getDefaultDisplay();
			screenheight = display.getHeight();
			mainimage.setMaxHeight((int)(screenheight/scalek));
			//Выбранный иероглиф
			final Bundle extras = getIntent().getExtras();
			if (extras!=null) {
				order = extras.getInt("order");
				}
			
			ImageLoad();
		}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == next)
		{
			if (border-1 > order)
			{
				mainfield.setText("");
				order = order+1;
				showwriting.setText(R.string.paint);
				mainimage.setMaxHeight((int)(screenheight/scalek));
				writing = false;
				ImageLoad();
			}
		}
		if (v == prev)
		{
			if (order > 0)
			{
				mainfield.setText("");
				order = order-1;
				showwriting.setText(R.string.paint);
				writing = false;
				mainimage.setMaxHeight((int)(screenheight/scalek));
				ImageLoad();
			}
		}
		if (v == onreadbtn)
		{
			if (transcrip == 1)
			{
				if (n5state == true)
				{
					Collections.addAll(onread, getResources().getStringArray(R.array.n5on));
				}
				/*if (n4state == true) //TODO Когда введем n4 - раскомментить
				{
					Collections.addAll(onread, getResources().getStringArray(R.array.n4on));
				}*/
			}
			if (transcrip == 2) 
			{
				if (n5state == true)
				{
					Collections.addAll(onread, getResources().getStringArray(R.array.n5onrom));
				}
				/*if (n4state == true) //TODO Когда введем n4 - раскомментить
				{
					Collections.addAll(onread, getResources().getStringArray(R.array.n4onrom));
				}*/
			}
			if (transcrip == 3)
			{
				if (n5state == true)
				{
					Collections.addAll(onread, getResources().getStringArray(R.array.n5oncyr));
				}
				/*if (n4state == true) //TODO Когда введем n4 - раскомментить
				{
					Collections.addAll(onread, getResources().getStringArray(R.array.n4oncyr));
				}*/
			}
			mainfield.setText(onread.get(order));
			onread.clear();
		}
		if (v == kunreadbtn)
		{
			if (transcrip == 1)
			{
				if (n5state == true)
				{
					Collections.addAll(kunread, getResources().getStringArray(R.array.n5kun));
				}
				/*if (n4state == true) //TODO Когда введем n4 - раскомментить
				{
					Collections.addAll(kunread, getResources().getStringArray(R.array.n4kun));
				}*/
			}
			if (transcrip == 2)
			{
				if (n5state == true)
				{
					Collections.addAll(kunread, getResources().getStringArray(R.array.n5kunrom));
				}
				/*if (n4state == true) //TODO Когда введем n4 - раскомментить
				{
					Collections.addAll(kunread, getResources().getStringArray(R.array.n4kunrom));
				}*/
			}
			if (transcrip == 3)
			{
				if (n5state == true)
				{
					Collections.addAll(kunread, getResources().getStringArray(R.array.n5kuncyr));
				}
				/*if (n4state == true) //TODO Когда введем n4 - раскомментить
				{
					Collections.addAll(kunread, getResources().getStringArray(R.array.n4kuncyr));
				}*/
			}
			mainfield.setText(kunread.get(order));
			kunread.clear();
		}
		if (v == translate)
		{
			if (n5state == true)
			{
				Collections.addAll(translation, getResources().getStringArray(R.array.n5translation));
			}
			/*if (n4state == true) //TODO Когда введем n4 - раскомментить
			{
				Collections.addAll(translation, getResources().getStringArray(R.array.n4translation));
			}*/
			mainfield.setText(translation.get(order));
			translation.clear();
		}
		if (v == random)
		{
			mainfield.setText("");
			order = rand.nextInt(border-1);
			showwriting.setText(R.string.paint);
			mainimage.setMaxHeight((int)(screenheight/scalek));
			writing = false;
			ImageLoad();
		}
		if (v == showwriting)
		{
			if (writing == false)
			{
				ImageWrite();
				writing = true;
				showwriting.setText(R.string.kandji);
				mainimage.setMaxHeight((int)(screenheight*screenheight));
			}
			else 
			{
				ImageLoad();
				showwriting.setText(R.string.paint);
				mainimage.setMaxHeight((int)(screenheight/scalek));
				writing = false;
			}
		}
		/*if (v == examplebtn)
		{
			/*if (n5state == true) //TODO Когда добавим примеры - раскоментить
			{
				Collections.addAll(examples, getResources().getStringArray(R.array.n5examples));
			}
			/*if (n4state == true) //TODO Когда введем n4 - раскомментить
			{
				Collections.addAll(examples, getResources().getStringArray(R.array.n4examples));
			}*/
			//mainfield.setText("Нет примеров"/*examples.get(order)*/);
			//examples.clear();
		//}
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
	    	this.finish();
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
