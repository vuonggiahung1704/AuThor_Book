package com.example.datababesql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnBook :
                Intent intent1 = new Intent(this,Activity_Book.class);
                startActivity(intent1);
                break;
            case R.id.mnAuthor :
                Intent intent2 = new Intent(this,Activity_Author.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}