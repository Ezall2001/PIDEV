package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class File_helpers {

  public static void copy_file(File src, File dest) {
    InputStream is = null;
    OutputStream os = null;
    try {
      is = new FileInputStream(src);
      os = new FileOutputStream(dest); // buffer size 1K 
      byte[] buf = new byte[1024];
      int bytesRead;
      while ((bytesRead = is.read(buf)) > 0)
        os.write(buf, 0, bytesRead);

      is.close();
      os.close();
    } catch (Exception e) {
      Log.file(e.getMessage());
    }

  }

}
