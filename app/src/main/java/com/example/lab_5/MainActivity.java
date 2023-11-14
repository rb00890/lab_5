package com.example.lab_5;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editText1;
    private EditText editText2;
    private Button button;
    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        button = findViewById(R.id.button);
        listView = findViewById(R.id.listView);

        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input1 = editText1.getText().toString();
                String input2 = editText2.getText().toString();

                arrayList.add(input1 + " - " + input2);
                arrayAdapter.notifyDataSetChanged();

                editText1.setText("");
                editText2.setText("");
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> showModifyDialog(position));
    }

    private void showModifyDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.modify_dialog, null);
        builder.setView(dialogView);

        final EditText modifiedInput1 = dialogView.findViewById(R.id.modifiedInput1);
        final EditText modifiedInput2 = dialogView.findViewById(R.id.modifiedInput2);

        modifiedInput1.setText(arrayList.get(position).split(" - ")[0]);
        modifiedInput2.setText(arrayList.get(position).split(" - ")[1]);

        builder.setPositiveButton("Modify", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String modifiedText1 = modifiedInput1.getText().toString();
                String modifiedText2 = modifiedInput2.getText().toString();
                arrayList.set(position, modifiedText1 + " - " + modifiedText2);
                arrayAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }
}

