/*
 * 
 * 
 */
package mp4.utils.files;

/**
 *
 * @author Galileo Computing
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import mp4.logs.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import mp4.utils.text.RandomText;

public class FileDirectoryHandler {

    public static void deleteTree(File path) throws IOException {
        for (File file : path.listFiles()) {
            if (file.isDirectory()) {
                deleteTree(file);
            } else {
                Log.Debug("Delete: " + file.getCanonicalPath(), true);
                file.delete();
            }
        }
        path.delete();
    }

    /**
     * 
     * @param sourceLocation
     * @param targetLocation
     * @throws java.io.IOException
     */
    public static void copyDirectory(File sourceLocation, File targetLocation)
            throws IOException {

        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }

            String[] children = sourceLocation.list();
            for (int i = 0; i < children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {

            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
      
    }

    public static URI copyFile(File sourceFile, File targetDirectory, String targetFilename)
            throws IOException {

            InputStream in = new FileInputStream(sourceFile);
            File outp = new File(targetDirectory + File.separator + targetFilename);
            OutputStream out = new FileOutputStream(targetDirectory + File.separator + targetFilename);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
       
            return outp.toURI();
            
   
    }
    
    public static File[] getFilesOfDirectory(String dir, String identifier){
        File src;
    
        try {
            lstFiles = new ArrayList();
            src = new File(dir);
            Log.Debug("Verzeichnis: " + src, true);
            File[] files = src.listFiles();
            Log.Debug("Dateien analysieren...", true);
            lstFiles = new ArrayList();

            for (int i = 0,   k = 0; i < files.length; i++) {
//                Log.Debug("Datei analysieren: " + files[i].getName());
                if (files[i].isFile() && files[i].toString().contains(identifier)) {
                    try {
//                        String[] fileinfo = new String[3];
//                        fileinfo[0] = String.valueOf(k);
//                        fileinfo[1] = files[i].getName();
//                        fileinfo[2] = files[i].getCanonicalPath();
                        lstFiles.add(files[i]);
                        Log.Debug("Datei gefunden: " + files[i].getName(), true);
                        k++;
                    } catch (Exception ex) {
                        Log.Debug(ex.getMessage(), true);
                    }
                }
            }
            if (files.length == 0) {
//                String[] fileinfo = new String[3];
//                fileinfo [2] = "Keine Datei vorhanden";
//                lstFiles.add(fileinfo);
                 Log.Debug("Keine Datei gefunden.");
            }
        } catch (Exception exception) {
            Log.Debug(exception);
            Log.Debug(exception.getMessage(), true);
        }
    
        return (File[]) lstFiles.toArray(new File[0]);
        
    }
    private static ArrayList<File> lstFiles;

    public static File tempFileClone(File file) {

        return tempFileClone(file, new RandomText().getString());
    }

    public static File tempFileClone(File file, String suffix) {
        try {
            File fil = new File(copyFile(file, new File(System.getProperty("java.io.tmpdir")), new RandomText().getString() + "." + suffix));
            fil.deleteOnExit();
            return fil;
        } catch (IOException ex) {
            Log.Debug(ex);
        }
        return null;
    }
}
    



