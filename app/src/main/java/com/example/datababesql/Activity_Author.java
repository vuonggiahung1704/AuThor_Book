package com.example.datababesql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Activity_Author extends AppCompatActivity {
    Button btnXem,btnThem,btnXoa,btnCapNhat,btnExit;
    EditText edtMa,edtTen,edtDiaChi,edtEmail;
    GridView lv;

    List<Author> list;
    DataBaseHelper db;
    ArrayAdapter<String> adapter;

    List<String> list_String;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__author);


        list = new ArrayList<>();
        list_String = new ArrayList<>();
        AnhXa();

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int dong = 0;
                int vitri = i+1;
                if( vitri % 4 == 0){
                    dong = vitri/4;
                }else{
                    dong = vitri/4 + 1;
                }
                String id_s = list_String.get((dong-1)*4);
                int id = Integer.parseInt(id_s);
                Author author = db.getIDAuthor(id);
                valid(author);
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });
    }

    public  void delete(){
        int id = Integer.parseInt(edtMa.getText().toString());
        if(db.deleteAuthor(id)){
            Toast.makeText(this,"Xóa thành công",Toast.LENGTH_SHORT).show();
            clear();
        }
        else{
            Toast.makeText(this,"Xóa không thành công",Toast.LENGTH_SHORT).show();
        }
    }

    public void update(){
        Author author = new Author();
        author.setId_author(Integer.parseInt(edtMa.getText().toString()));
        author.setName(edtTen.getText().toString());
        author.setEmail(edtEmail.getText().toString());
        author.setAddress(edtDiaChi.getText().toString());
        if(db.updateAuthor(author)>0){
            Toast.makeText(this,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
            clear();
        }else{
            Toast.makeText(this,"Cập nhật không thành công",Toast.LENGTH_SHORT).show();
        }
    }

    public void save(){
        Author author = new Author();
        author.setId_author(Integer.parseInt(edtMa.getText().toString()));
        author.setName(edtTen.getText().toString());
        author.setEmail(edtEmail.getText().toString());
        author.setAddress(edtDiaChi.getText().toString());
        if(db.insertAuthor(author)>0){
            Toast.makeText(this,"Lưu thành công",Toast.LENGTH_SHORT).show();
            clear();
        }else{
            Toast.makeText(this,"Lưu không thành công",Toast.LENGTH_SHORT).show();
        }
    }

    public void AnhXa(){
        edtMa = findViewById(R.id.edtIdAuthor);
        edtTen = findViewById(R.id.edtName);
        edtDiaChi = findViewById(R.id.edtAddres);
        edtEmail = findViewById(R.id.edtEmail);

        btnXem = findViewById(R.id.btnXem);
        btnThem = findViewById(R.id.btnThem);
        btnXoa = findViewById(R.id.btnXoa);
        btnCapNhat = findViewById(R.id.btnSua);
        btnExit = findViewById(R.id.btnExit);

        lv = findViewById(R.id.gridViewAuthor);
        db = new DataBaseHelper(this);
    }

    public void clear(){
        edtMa.setText("");
        edtTen.setText("");
        edtEmail.setText("");
        edtDiaChi.setText("");
    }

    public void valid(Author author){
        edtMa.setText(""+author.getId_author());
        edtTen.setText(author.getName());
        edtEmail.setText(author.getEmail());
        edtDiaChi.setText(author.getAddress());
    }

    public void select(){
        list = new ArrayList<>();
        if(edtMa.getText().toString().equals("")){
            list = db.getAllAuthor();
        }else{
            try {
                Author author = db.getIDAuthor(Integer.parseInt(edtMa.getText().toString()));
                list.add(author);
            }catch (Exception e){
                Toast.makeText(this,"Không tìm thấy",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        list_String = new ArrayList<>();
        for (Author author:list){
            list_String.add(author.getId_author()+"");
            list_String.add(author.getName());
            list_String.add(author.getEmail());
            list_String.add(author.getAddress());
        }

        if(list.size()>0){
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_String);
            lv.setAdapter(adapter);
            clear();
        }else{
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_String);
            lv.setAdapter(adapter);
            clear();
        }

//
    }
}