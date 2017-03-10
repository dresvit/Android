package com.example.owner.lab7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileEditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_editor);

        Button saveBtn = (Button) findViewById(R.id.saveBtn);
        Button loadBtn = (Button) findViewById(R.id.loadBtn);
        Button clearBtn = (Button) findViewById(R.id.clearBtn);
        final EditText editText = (EditText) findViewById(R.id.editText);
        final String FILE_NAME = "mydata.txt";

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try (FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
                    String str = editText.getText().toString();
                    fileOutputStream.write(str.getBytes());
                    Toast.makeText(FileEditorActivity.this, "Save successfully.", Toast.LENGTH_SHORT).show();
                } catch (IOException ex) {
                    Log.e("TAG", "Fail to save file.");
                }
            }
        });

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try (FileInputStream fileInputStream = openFileInput(FILE_NAME)) {
                    byte[] contents = new byte[fileInputStream.available()];
                    fileInputStream.read(contents);
                    editText.setText(new String(contents));
                    Toast.makeText(FileEditorActivity.this, "Load successfully.", Toast.LENGTH_SHORT).show();
                } catch (IOException ex) {
                    Toast.makeText(FileEditorActivity.this, "Fail to load file.", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "Fail to load file.");
                }
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
    }
}
