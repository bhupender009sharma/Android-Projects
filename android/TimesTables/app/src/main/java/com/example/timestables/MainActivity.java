package com.example.timestables;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView tableListView;

    public void generateTable(int tableNumber){

        ArrayList<String> tableContent = new ArrayList<String >();

        for(int i=1 ; i<=10; i++){
            tableContent.add( Integer.toString(i * tableNumber));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tableContent);

        tableListView.setAdapter(arrayAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int currentTable;

        tableListView = (ListView) findViewById(R.id.tableListView);
        SeekBar tableSeekBar = (SeekBar) findViewById(R.id.tableSeekBar);

        int maxTable =20;
        int minTable =1;

        tableSeekBar.setMax(maxTable);
        tableSeekBar.setMin(minTable);
        tableSeekBar.setProgress(minTable);

        generateTable(minTable);

        tableSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int min=1;
                int tableNumber = progress;

                Log.i("Seekbar value", Integer.toString(progress));

                generateTable(tableNumber);

                Toast.makeText(getApplicationContext(),Integer.toString(tableNumber),L).show();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }
}