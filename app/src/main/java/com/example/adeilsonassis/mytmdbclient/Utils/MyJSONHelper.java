package com.example.adeilsonassis.mytmdbclient.Utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.FileObserver;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by adeilson.assis on 21/10/2017.
 */

public class MyJSONHelper
{
    static String fileName = "PopularMovies.json";

    public static void objectToFile(Object object, Context context) throws IOException
    {
        //String path = Environment.getExternalStorageDirectory() + File.separator + "/AppName/App_cache" + File.separator;
        String path = context.getFilesDir().getPath();
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        path +=  "/" + fileName;
        File data = new File(path);
        if (!data.createNewFile()) {
            data.delete();
            data.createNewFile();
        }
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(data));
        objectOutputStream.writeObject(object);
        objectOutputStream.close();

    }

    public static Object objectFromFile(Context context) throws IOException, ClassNotFoundException {
        Object object = null;
        String path = context.getFilesDir().getPath() + "/" + fileName;
        File data = new File(path);
        if(data.exists()) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(data));
            object = objectInputStream.readObject();
            objectInputStream.close();
        }
        return object;
    }

    public static void saveData(Context context, String mJsonResponse)
    {
        try
        {

            /*FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(mJsonResponse.getBytes(),0,mJsonResponse.length());
            fos.close();*/

            FileWriter file = new FileWriter(context.getFilesDir().getPath() + "/" + fileName);
            file.write(mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    public static String getData(InputStream is)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null)
            {
                sb.append(line).append("\n");
            }

            reader.close();

            return sb.toString();

            /*
            File f = new File(context.getFilesDir().getPath() + "/" + fileName);
            //check whether file exists
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);*/
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
