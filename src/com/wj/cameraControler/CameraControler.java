package com.wj.cameraControler;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

public class CameraControler {
	
	private Camera camera = null;
	
	
	
	private boolean checkCameraHardware(Context context) {
	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
	        // this device has a camera
	        return true;
	    } else {
	        // no camera on this device
	        return false;
	    }
	}
	
	public Camera getCameraInstance(){
	    try {
	        camera = Camera.open(); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    return camera; // returns null if camera is unavailable
	}
	

    private void releaseCamera(){
        if (camera != null){
            camera.release();        // release the camera for other applications
            camera = null;
        }
    }
}
