package com.wj.takingphoto;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;


/**
 * Handles saving pictures to the SD card.  The OpemCamera class owns this;
 * when the OpemCamera is destroyed, this FileSaver is also destroyed.
 */
public class FileSaver {
  

  /** The directory where all the files will be saved to */
  public File directory;

  private static FileSaver instance = null;
  public static FileSaver getInstance() {
    if (instance == null) {
      instance = new FileSaver();
    }
    return instance;
  }

  public FileSaver() {
    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
      File root = Environment.getExternalStorageDirectory();
      File picturesDir = new File(root, "Pictures");
      picturesDir.mkdirs();
      this.directory = picturesDir;
    } else {
      // can't find the SD card, should error or something.
    }
  }



  /** Saves the data to the file in |directory| */
  public void save(byte[] data, String filename) {

    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state)) {

      File destFile = new File(directory, filename);
      System.out.println("directroy-->"+directory+"   filename-->"+filename);
      try {
        destFile.createNewFile();
        BufferedOutputStream outputStream = new BufferedOutputStream(
            new FileOutputStream(destFile));
        outputStream.write(data);
        System.out.println("成功");
        outputStream.close();
      } catch (IOException e) {
    	  System.out.println("不成功");
      }
    }
  }
}
