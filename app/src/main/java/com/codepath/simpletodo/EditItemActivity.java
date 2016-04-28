package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class EditItemActivity extends AppCompatActivity {

    private Item item;
    private int position;
    private ArrayAdapter<Priority> priorityAdapter;
    private ArrayAdapter<Status> statusAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        EditText etSave = (EditText)findViewById(R.id.etSave);
        item = (Item) getIntent().getSerializableExtra("item");
        position = getIntent().getIntExtra("position", -1);
        etSave.setText(item.getName());

        Spinner spPriority = (Spinner)findViewById(R.id.spPriority);
        priorityAdapter = new ArrayAdapter<Priority>(this, android.R.layout.simple_spinner_item, Priority.values());
        spPriority.setAdapter(priorityAdapter);
        spPriority.setSelection(priorityAdapter.getPosition(item.getPriority()));

        Spinner spStatus = (Spinner)findViewById(R.id.spStatus);
        statusAdapter = new ArrayAdapter<Status>(this, android.R.layout.simple_spinner_item, Status.values());
        spStatus.setAdapter(statusAdapter);
        spStatus.setSelection(statusAdapter.getPosition(item.getStatus()));
    }

    public void onSave(View v) {
        EditText etSave = (EditText) findViewById(R.id.etSave);
        Spinner spPriority = (Spinner)findViewById(R.id.spPriority);
        Spinner spStatus = (Spinner)findViewById(R.id.spStatus);
        Intent data = new Intent();
        item.setName(etSave.getText().toString());
        item.setPriority(priorityAdapter.getItem(spPriority.getSelectedItemPosition()));
        item.setStatus(statusAdapter.getItem(spStatus.getSelectedItemPosition()));
        data.putExtra("item", item);
        data.putExtra("position", position);
        setResult(RESULT_OK, data);
        finish();
    }
}
