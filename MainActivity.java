package com.example.a30sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.model.Contact;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    String DATABASE_NAME = "test.sqlite";
    String DB_PATH_SUFFIX = "data/data/com.example.a30sqlite/";
    SQLiteDatabase database = null;
    ListView lvContact;
    ArrayAdapter<Contact>arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        processCopy();
        addControls();
        hienThiToanBoSanPham();

    }

    private void hienThiToanBoSanPham() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
//        Cursor cursor = database.rawQuery("SELECT * FROM Contact",null);
        Cursor cursor = database.query("Contact",null,null,null,null,null,null);
        arrayAdapter.clear();
        while (cursor.moveToNext()){
            int ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            String phone = cursor.getString(2);
            Contact contact = new Contact(ma,ten,phone);
            arrayAdapter.add(contact);
        }
        cursor.close();
    }

    private void addControls() {
        lvContact = findViewById(R.id.lvContact);
        arrayAdapter = new ArrayAdapter<Contact>(MainActivity.this,android.R.layout.simple_list_item_1);
        lvContact.setAdapter(arrayAdapter);
    }

    private void processCopy(){
        try {
            File dbFile = getDatabasePath(DATABASE_NAME);
            // nếu file chưa tồn tại thì mới sao chép còn có rồi thì sao chép làm gì
            if (!dbFile.exists()) {
                copyDatabaseFromAssets();
                Toast.makeText(MainActivity.this,"Sao chép CSDL SQLite vào hệ thống thành công ",Toast.LENGTH_SHORT).show();

            }
        }catch (Exception ex){
            Toast.makeText(MainActivity.this,ex.toString(),Toast.LENGTH_SHORT).show();
            Log.e("loi", ex.toString());
        }

    }
    private String getDatabasePath(){
        return getApplicationInfo().dataDir+DB_PATH_SUFFIX+DATABASE_NAME;
    }
    private void copyDatabaseFromAssets() {
        try{
            InputStream myInput = getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath();
            File f = new File(getApplicationInfo().dataDir+DB_PATH_SUFFIX);
            if(!f.exists()) {
                Toast.makeText(MainActivity.this,"Da tao moi database",Toast.LENGTH_SHORT).show();
                f.mkdir();
            }
                OutputStream myOutput = new FileOutputStream(outFileName);
                byte [] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer))>0){
                    myOutput.write(buffer,0,length);
                }
                myOutput.flush();
                myOutput.close();
                myInput.close();


        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
