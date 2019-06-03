package com.neelkanthjdabhi.appdigger;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



public class APKBackup {

    Context context;


    public APKBackup(Context context) {
        this.context = context;
        File sdcard = Environment.getExternalStorageDirectory();
        File f = new File(sdcard + Constants.directory);
        f.mkdir();

    }


    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public void extractapk(String packagename) {
        PackageManager packageManager = context.getPackageManager();
        try {
            File file = new File(packageManager.getApplicationInfo(packagename, PackageManager.GET_META_DATA).publicSourceDir);
            File output = new File(Environment.getExternalStorageDirectory().getPath() + Constants.directory + packagename + ".apk");
            try {
                output.createNewFile();
                FileOutputStream fos;
                try {

                    InputStream assetFile = new FileInputStream(file);
                    fos = new FileOutputStream(output);
                    copyFile(assetFile, fos);
                    fos.close();
                    assetFile.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Toast.makeText(context, "Extracted to Folder.", Toast.LENGTH_LONG).show();
    }


}