package com.example.listview2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class results extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    TextView first, jump, chosenItem, sumToItem;
    ListView lv;
    double startingVal, jumper;
    double[] arrVal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        first = findViewById(R.id.tv1);
        jump = findViewById(R.id.tv2);
        chosenItem = findViewById(R.id.tv3);
        sumToItem = findViewById(R.id.tv4);
        lv = findViewById(R.id.lv);

        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setOnItemClickListener(this);

        //getting values from intent
        Intent gi = getIntent();
        startingVal = gi.getDoubleExtra("startVal", 1);
        jumper = gi.getDoubleExtra("jump", 1);
        arrVal = gi.getDoubleArrayExtra("valArr");

        //setting info to textviews
        first.setText("" + startingVal);
        jump.setText("" + jumper);

        //converting from double array to string array in order to use the list view
        String[] arr = new String[20];
        for(int i = 0; i < 20; i++)
        {
            arr[i] = "" + arrVal[i];
            if(arr[i].contains("E"))
            {
                arr[i] = getFinalNumber(arr[i]);
            }
        }
        //creating an adapter for my listview
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item , arr);
        lv.setAdapter(adp);
    }

    public String getFinalNumber(String num)
    {
        String finalStr;
        String part1, part2;
        int pointIndex = Integer.valueOf(num.substring(num.indexOf("E") + 1));
        num = num.substring(0, num.indexOf("E"));
        num = num.replace(".", "");
        if(pointIndex < num.length())
        {
            part1 = num.substring(0, pointIndex + 1);
            part2 = num.substring(pointIndex + 1);
            finalStr = part1 + "." + part2;
            if(finalStr.endsWith("."))
            {
                finalStr += "0";
            }
        }
        else
        {
            finalStr = num;
            pointIndex = pointIndex - num.length();
            for(int i = 0; i < pointIndex; i++)
            {
                finalStr += "0";
            }
            finalStr += ".0";
        }

        return finalStr;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        chosenItem.setText("" + (i+1));
        double sum = 0;
        for(int j = 0; j <= i; j++)
        {
            sum += arrVal[j];
        }
        String sumStr = "" + sum;
        sumStr = getFinalNumber(sumStr);
        sumToItem.setText(sumStr);
    }
}