package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        EditText etSave = (EditText)findViewById(R.id.etSave);
        String text = getIntent().getStringExtra("text");
        position = getIntent().getIntExtra("position", -1);
        etSave.setText(text);
    }

    public void onSave(View v) {
        EditText etSave = (EditText) findViewById(R.id.etSave);
        Intent data = new Intent();
        data.putExtra("text", etSave.getText().toString());
        data.putExtra("position", position);
        setResult(RESULT_OK, data);
        finish();
    }
}
