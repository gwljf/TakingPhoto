package com.wj.takingphoto;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


public class SurfaceActivity extends Activity{

	private Camera mCamera;
    private CameraPreview mPreview;
    
    private boolean isTaking = true;
    private Button btn_stop;
    private TextView text_savefile;
    private int counter;
    
    private FileSaver fileSaver;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
    
    private Handler handler;
    private static int counterWhat = 2;
    private FrameLayout preview;
    
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_surface);
		// Create an instance of Camera
		getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mCamera = getCameraInstance();

        
        mPreview = new CameraPreview(this, mCamera);
        
        preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
		text_savefile = (TextView) findViewById(R.id.savefile);
        btn_stop = (Button) findViewById(R.id.stop_takephotos);
        
        
        //button Ðý×ª 90¶È
        
        btn_stop.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(SurfaceActivity.this, "Processing Data", Toast.LENGTH_LONG).show();
				isTaking = false;
				
				try {
					Thread.sleep((long) (2.1 * 1000));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				releaseCamera();
				finish();
			}
		});
        
        handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				
				if(msg.what == counterWhat){
					counter = msg.arg1;
					text_savefile.setText(counter+" files have been saved");
					preview.removeView(mPreview);
					mPreview = new CameraPreview(SurfaceActivity.this, mCamera);
					preview.addView(mPreview);
				}
				super.handleMessage(msg);
			}
        };
        
        AutoPicturesThread autoPicturesThread = new AutoPicturesThread();
        autoPicturesThread.start(); 
	}
	
	
	private void releaseCamera(){
        if (mCamera != null){
        	mCamera.release();        // release the camera for other applications
        	mCamera = null;
        }
    }
	private Camera getCameraInstance(){
	    try {
	    	mCamera = Camera.open(); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    return mCamera; // returns null if camera is unavailable
	}
	
	class AutoPicturesThread extends Thread{
		int counter = 0;
		@Override
		public void run() {
			
			while(true){
				if(!isTaking){
					break;
				}
				try {
					Thread.sleep((long) (1.5*1000));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				fileSaver = FileSaver.getInstance();
				mCamera.takePicture(null, null, mPicture);
				counter++;
				try {
					Thread.sleep((long) (0.5*1000));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Message msg_counter = handler.obtainMessage();
				msg_counter.what = counterWhat;
				msg_counter.arg1 = counter;
				handler.sendMessage(msg_counter);
				
				if(!isTaking){
					break;
				}
			}
			// TODO Auto-generated method stub
			super.run();
		}

	    private PictureCallback mPicture = new PictureCallback() {
		    public void onPictureTaken(byte[] data, Camera callbackCamera) {
		    	fileSaver.save(data, sdf.format(new Date()) + ".jpg");
		    	System.out.println("file saved!");
		    }
		};
    }
}
