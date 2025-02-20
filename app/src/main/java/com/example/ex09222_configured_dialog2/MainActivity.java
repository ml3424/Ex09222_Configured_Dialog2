package com.example.ex09222_configured_dialog2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    AlertDialog.Builder adb;
    LinearLayout inputDialog;
    EditText D_eTa1, D_eTdorq;
    Switch D_sw;

    ListView listV;
    TextView tVa1, tVdq2, tVdOrq, tVn, tVSn2;

    double a1 = 0, dorq = 0, sum = 0;
    Double[] arr_items_sidra = new Double[20];
    boolean isHashbonit = true;
    int n = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listV = findViewById(R.id.listV);
        tVa1 = findViewById(R.id.tVa1);
        tVdq2 = findViewById(R.id.tVdq2);
        tVdOrq = findViewById(R.id.tVdOrq);
        tVn = findViewById(R.id.tVn);
        tVSn2 = findViewById(R.id.tVSn2);

        ArrayAdapter<Double> adp = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, java.util.Arrays.asList(arr_items_sidra));


        listV.setOnItemClickListener(this);
        listV.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }


    public void start (View view)
    {
        inputDialog = (LinearLayout) getLayoutInflater().inflate(R.layout.input_dialog, null);
        D_eTa1 = inputDialog.findViewById(R.id.D_eTa1);
        D_eTdorq = inputDialog.findViewById(R.id.D_eTdorq);
        D_sw = inputDialog.findViewById(R.id.D_sw);

        adb = new AlertDialog.Builder(this);
        adb.setView (inputDialog);
        adb.setTitle("Enter your data:");


        adb.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = D_eTa1.getText().toString();
                String text2 = D_eTdorq.getText().toString();
                if(!isValidInput(text))
                {
                    Toast t = Toast.makeText(view.getContext(), "Invalid a1!", Toast.LENGTH_SHORT);
                    t.show();
                    rest();
                    return;
                }
                else if(!isValidInput(text2))
                {
                    Toast t = Toast.makeText(view.getContext(), "Invalid d/q!", Toast.LENGTH_SHORT);
                    t.show();
                    rest();
                    return;
                }
                a1 = Double.valueOf(text);;
                dorq = Double.valueOf(text2);
                updateResults();

                dialog.cancel();
            }

        });
        adb.setNeutralButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                rest();
                dialog.cancel();
            }
        });
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        adb.show();
    }

    public void swClick(View view) {
        if(D_sw.isChecked())
        {
            isHashbonit = false;
        }
        else
        {
            isHashbonit = true;
        }
    }

    public void onclickInputDialog(View view) {
        start(view);
    }

    public void updateResults()
    {
        // set text in text views:
        tVa1.setText(a1 + "");
        tVdq2.setText(dorq + "");

        if(isHashbonit)
        {
            tVdOrq.setText("d =");
            for(int i = 0; i < 20; i++)
            {
                arr_items_sidra[i] = a1 + dorq*i;
            }
        }
        else //handasit
        {
            tVdOrq.setText("q =");
            for(int i = 0; i < 20; i++)
            {
                arr_items_sidra[i] = a1 * Math.pow(dorq, i);
            }
        }

        // list view context:
        ArrayAdapter<Double> adp = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arr_items_sidra);
        listV.setAdapter(adp);

    }

    public void rest()
    {
        // set text in text views:
        tVa1.setText("");
        tVdq2.setText("");

        for(int i = 0; i < 20; i++)
        {
            arr_items_sidra[i] = 0.0;
        }

        // list view context:
        ArrayAdapter<Double> adp = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arr_items_sidra);
        listV.setAdapter(adp);

    }

    public boolean isValidInput(String input)
    {
        if (input.isEmpty())
        {
            return false;
        }
        // check if input is a single character and is not a digit
        else if ((input.length() == 1 || input.length() == 2) && !Character.isDigit(input.charAt(0)))
        {
            return false;
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        n = i + 1;
        tVn.setText((int)n + "");
        n = i + 1;  // add 1 because n should start at 1


        if (isHashbonit) {
            sum = (n / 2.0) * (2 * a1 + (n - 1) * dorq);
        } else {
            sum = a1 * (Math.pow(dorq, n) - 1) / (dorq - 1);
        }

        tVSn2.setText(String.format("%.2f", sum)); // two digits after .

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}