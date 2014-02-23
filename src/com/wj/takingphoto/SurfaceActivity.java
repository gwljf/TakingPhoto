package com.wj.takingphoto;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


public class SurfaceActivity extends Activity{

	private Camera mCamera;
    private CameraPreview mPreview;
    
    private boolean isTaking = true;
    private Button btn_stop;
    private TextView text_savefile;
    private FileSaver fileSaver;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
    private int counter = 0;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_surface);
		// Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        text_savefile = (TextView) findViewById(R.id.savefile);
        btn_stop = (Button) findViewById(R.id.stop_takephotos);
        //button Ðý×ª 90¶È
        
        btn_stop.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isTaking = false;
				try {
					Thread.sleep((long) (0.2 * 1000));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				releaseCamera();
				finish();
			}
		});
        
        AutoPicturesThread autoPicturesThread = new AutoPicturesThread();
        autoPicturesThread.start();
	}

	
	public Camera getCameraInstance(){
	    try {
	    	mCamera = Camera.open(); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    return mCamera; // returns null if camera is unavailable
	}
	

    private void releaseCamera(){
        if (mCamera != null){
        	mCamera.release();        // release the camera for other applications
        	mCamera = null;
        }
    }
    
    class AutoPicturesThread extends Thread{

		@Override
		public void run() {
			while(true){
				try {
					Thread.sleep(2*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fileSaver = FileSaver.getInstance();
				mCamera.takePicture(null, null, mPicture);
//				text_savefile.setText(String.valueOf(counter)+" photos have been saved.");
				if(isTaking){
					break;
				}
			}
			
			// TODO Auto-generated method stub
			super.run();
		}
		
	    private PictureCallback mPicture = new PictureCallback() {
		    public void onPictureTaken(byte[] data, Camera callbackCamera) {
		    	fileSaver.save(data, sdf.format(new Date()) + ".jpg");
//		    	preview.addView(mPreview);
		    }
		};
    }

}
