package com.yeyintlwin.rfunny;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yeyintlwin.rfunny.utils.Rabbit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String assetPath = "file:///android_asset/";

    String fuckPath = "data";
    List<String> tempAnswer;

    TextView textView, finalAnswerText;
    Button button_0, button_1, button_2;

    LinearLayout linearLayout, linearLayout2;
    private long back_press;

    @Override
    public void onClick(View v) {
        button_2.setVisibility(View.GONE);
        switch (v.getId()) {
            case R.id.answer_0:
                fuckPath += "/" + tempAnswer.get(0) + ".a";
                break;
            case R.id.answer_1:
                fuckPath += "/" + tempAnswer.get(1) + ".a";
                break;
            case R.id.answer_2:
                fuckPath += "/" + tempAnswer.get(2) + ".a";
                break;
        }
        loadAssets(fuckPath);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_0 = findViewById(R.id.answer_0);
        button_1 = findViewById(R.id.answer_1);
        button_2 = findViewById(R.id.answer_2);
        button_0.setOnClickListener(this);
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        textView = findViewById(R.id.questionText);
        linearLayout = findViewById(R.id.fuckLayout);
        linearLayout2 = findViewById(R.id.finalAnswer);
        finalAnswerText = findViewById(R.id.finalAnswerText);
        tempAnswer = new ArrayList<>();

        loadAssets(fuckPath);

    }

    @SuppressLint("SetTextI18n")
    private void loadAssets(String path) {
        tempAnswer.clear();
        try {
            String[] fileNames = getAssets().list(path);
            assert fileNames != null;
            for (String fileName : fileNames) {

                File f = new File(assetPath + fileName);

                System.out.println(f.getAbsolutePath());
                String name = f.getName();

                String format = name.substring(name.lastIndexOf('.'));

                if (format.equals(".fa")) {
                    System.out.println("target: " + removeFormat(name));
                    linearLayout.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.VISIBLE);
                    finalAnswerText.setText("သင်က\n" + removeFormat(name));
                    finalAnswerText.setText(Rabbit.uni2zg(finalAnswerText.getText().toString()));
                    return;
                }

                if (format.equals(".q")) {
                    textView.setText(removeFormat(name));
                }

                if (format.equals(".a")) {
                    tempAnswer.add(removeFormat(name));
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Fuck", e.toString());
        }

        button_0.setText(tempAnswer.get(0));
        button_1.setText(tempAnswer.get(1));
        if (tempAnswer.size() == 3) {
            button_2.setText(tempAnswer.get(2));
        }

        textView.setText(Rabbit.uni2zg(textView.getText().toString()));
        button_0.setText(Rabbit.uni2zg(button_0.getText().toString()));
        button_1.setText(Rabbit.uni2zg(button_1.getText().toString()));
        button_2.setText(Rabbit.uni2zg(button_2.getText().toString()));

    }

    private String removeFormat(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, AboutsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (back_press + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        }
        Toast.makeText(this, "Press back bottom again to exist!", Toast.LENGTH_SHORT).show();
        back_press = System.currentTimeMillis();


    }
}
