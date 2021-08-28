package com.bmp.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Declare some variables
    private ListView listView;
    private ArrayList<String> items; //Declared out array
    private ArrayAdapter<String> itemAdapter;  //Declare our arrayadapter


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize vaiables
        listView = findViewById(R.id.lstView);
        items = new ArrayList<String>() ;
        onReadItems();
        itemAdapter = new ArrayAdapter<String>(this,R.layout.individual_item,R.id.task_txt,items);

        listView.setAdapter(itemAdapter);
        onDeleteItem(); //Calling onDelete function so that data will be refreshed when we delete something

    }

    public void onAddItem(View v){
        //Declare and Initalize my edittext variable
        EditText editText = findViewById(R.id.et_add_task);
        //Catching the content of edittext
        String itemText = editText.getText().toString();
        itemAdapter.add(itemText);
        editText.setText("");
        onWriteItems();
    }

    public void onDeleteItem(){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                items.remove(pos);
                itemAdapter.notifyDataSetChanged();
                onWriteItems();
                return true;
            }
        });
    }

    private void onReadItems(){
        File filesDir = getFilesDir();
        File taskFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(taskFile));
        }catch(IOException error){
            items = new ArrayList<String>();
        }
    }

    private void onWriteItems(){
        File filesDir = getFilesDir(); /// Getting directory
        File taskFile = new File(filesDir, "todo.txt");        // Checking if file exists or not
        try {
            FileUtils.writeLines(taskFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}