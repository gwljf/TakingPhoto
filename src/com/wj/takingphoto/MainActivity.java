package com.wj.takingphoto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener{

	private EditText edittext;
	private Button btn_start;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		edittext = (EditText) findViewById(R.id.edittext);
		btn_start = (Button) findViewById(R.id.start);
		btn_start.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.start:
			System.out.println("button works");
			Intent intent = new Intent(MainActivity.this, SurfaceActivity.class);
			intent.putExtra("time", Integer.parseInt(edittext.getText().toString()));
			startActivity(intent);
			break;
		default:
		}
	}

}
