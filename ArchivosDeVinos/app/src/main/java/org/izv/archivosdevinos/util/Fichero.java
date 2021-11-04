package org.izv.archivosdevinos.util;

import android.util.Log;

import org.izv.archivosdevinos.MainActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Fichero {

    public static final String TAG = MainActivity.class.getName() + "xyzyx";

    public static boolean writeFile(File file, String fileName, String string) {
        File f = new File(file, fileName);
        FileWriter fw = null;
        boolean ok = true;
        try {
            fw = new FileWriter(f, true);
            fw.write(string);
            fw.write("\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            ok = false;
            Log.v(TAG, e.toString());
        }
        return ok;
    }

    public static String readFile(File file, String fileName) {
        File f = new File(file, fileName);
        String texto = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            while ((linea = br.readLine()) != null) {
                texto += linea + "\n";
            }
            br.close();
        } catch (IOException e) {
            texto = null;
            Log.v(TAG, e.toString());
        }
        return texto;
    }

    public static String[] getFileLines(File file, String fileName){
        File f = new File(file, fileName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            String cache = "";
            while ((linea = br.readLine()) != null) {
                cache += linea + "%";
            }
            br.close();
            return cache.split("%");
        } catch (IOException e) {
            Log.v(TAG, e.toString());
        }
        return null;
    }

    public static boolean writeLine(File file, String filename, String line){
        File f = new File(file, filename);
        FileWriter fw;
        try {
            fw = new FileWriter(f, true);
            fw.write(line);
            fw.flush();
            fw.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean deleteLine(File file, String fileName, String id) {
        File f = new File(file, fileName);
        File f2 = new File(file, "temp.tmp");
        String string[];
        String tmp;
        try {
            FileWriter fw = new FileWriter(f2);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            while ((linea = br.readLine()) != null) {
                string = linea.split(";");
                if (!id.equals(string[0])) {
                    tmp = linea;
                    fw.write(tmp);
                    fw.write("\n");
                    fw.flush();
                }
            }
            fw.close();
            br.close();

            f.delete();
            f2.renameTo(f);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
