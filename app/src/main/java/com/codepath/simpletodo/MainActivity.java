package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Item> items;
    private ItemAdapter itemsAdapter;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        items = readItems();
        itemsAdapter = new ItemAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);
        if (items.isEmpty()) {
            items.add(new Item("Task 1"));
            items.add(new Item("Task 2"));
        }
        setupListViewListener();
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(new Item(itemText));
        etNewItem.setText("");
        writeItems();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(
                new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                        i.putExtra("item", items.get(position));
                        i.putExtra("position", position);
                        startActivityForResult(i, 0);
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Item item = (Item) data.getExtras().getSerializable("item");
            int position = data.getExtras().getInt("position", 0);
            items.set(position, item);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    private ArrayList<Item> readItems() {
        File todoFile = getTodoFile();
        try {
            ArrayList<Item> todos = new ArrayList<>();
            FileReader reader = new FileReader(todoFile);
            CSVParser parser = CSVFormat.DEFAULT.parse(reader);
            for (CSVRecord record : parser.getRecords()) {
                Item item = new Item();
                item.setName(record.get(0));
                item.setPriority(Priority.valueOf(record.get(1)));
                item.setStatus(Status.valueOf(record.get(2)));
                todos.add(item);
            }
            reader.close();
            return todos;
        }
        catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void writeItems() {
        File todoFile = getTodoFile();
        try {
            CSVPrinter printer = new CSVPrinter(new FileWriter(todoFile), CSVFormat.DEFAULT);
            for (Item item : items) {
                printer.printRecord(item.getName(), item.getPriority().name(), item.getStatus().name());
            }
            printer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private File getTodoFile() {
        File filesDir = getFilesDir();
        return new File(filesDir, "todo.txt");
    }
}
