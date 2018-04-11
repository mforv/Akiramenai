package mforv.progs.japan;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends Activity implements OnClickListener {
	protected TextView link1;
	protected TextView link2;
	protected TextView blog_ref;
	protected Button license1;
	protected Button license2;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);
        link1 = (TextView) findViewById(R.id.lists_link_view);
        link2 = (TextView) findViewById(R.id.stroke_link_view);
        license1 = (Button) findViewById(R.id.license_button_1);
        license2 = (Button) findViewById(R.id.license_button_2);
        blog_ref = (TextView) findViewById(R.id.blog_link);
        link1.setOnClickListener(this);
        link2.setOnClickListener(this);
        license1.setOnClickListener(this);
        license2.setOnClickListener(this);
        blog_ref.setOnClickListener(this);
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
	        Intent s = new Intent(this, StatisticsActivity.class);
	        startActivity(s);
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == link1)
		{
			 Intent inte = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://en.wikibooks.org/wiki/JLPT_Guide"));
             this.startActivity(inte);
		}
		if (v == link2)
		{
			 Intent inte = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://commons.wikimedia.org/wiki/Commons:Stroke_Order_Project/Kangxi_radicals"));
             this.startActivity(inte);
		}
		if (v == license1)
		{
			 Intent inte = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://creativecommons.org/licenses/by-nc-sa/3.0/legalcode"));
             this.startActivity(inte);
		}
		if (v == license2)
		{
			 Intent inte = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://creativecommons.org/licenses/by-sa/3.0/legalcode"));
             this.startActivity(inte);
		}
		if (v == blog_ref)
		{
			 Intent inte = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://akiramenai.ru"));
             this.startActivity(inte);
		}
	}
}
