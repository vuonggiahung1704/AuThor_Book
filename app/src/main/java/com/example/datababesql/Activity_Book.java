package com.example.datababesql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Activity_Book extends AppCompatActivity {
    Button btnXem,btnThem,btnXoa,btnCapNhat,btnExit;
    EditText edtMa,edtTen,edtTacGia;
    GridView lv;
    List<Book> list;
    DataBaseHelper db;
//    BookAdapter adapter;
    List<String> list_String;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__book);

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
                Book b = list.get(i);
                valid(b);
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

    public void update(){
        Book b = new Book();
        b.setId_book(Integer.parseInt(edtMa.getText().toString()));
        b.setTitle(edtTen.getText().toString());
        Author author = new Author(Integer.parseInt(edtTacGia.getText().toString()));
        b.setAuthor(author);
        if(db.updateBook(b)>0){
            Toast.makeText(this,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
            clear();
        }else{
            Toast.makeText(this,"Cập nhật không thành công",Toast.LENGTH_SHORT).show();
        }
    }

    public void save(){
        Book b = new Book();
        b.setId_book(Integer.parseInt(edtMa.getText().toString()));
        b.setTitle(edtTen.getText().toString());
        Author author = new Author(Integer.parseInt(edtTacGia.getText().toString()));
        b.setAuthor(author);

        Toast.makeText(this,b.toString(),Toast.LENGTH_SHORT).show();

        if(db.insertBook(b)>0){
            Toast.makeText(this,"Lưu thành công",Toast.LENGTH_SHORT).show();
            clear();
        }else{
            Toast.makeText(this,"Lưu không thành công",Toast.LENGTH_SHORT).show();
        }
    }

    public void AnhXa(){
        edtMa = findViewById(R.id.edtIdBook);
        edtTen = findViewById(R.id.edtTitle);
        edtTacGia = findViewById(R.id.edtId_Author);

        btnXem = findViewById(R.id.btnXem);
        btnThem = findViewById(R.id.btnThem);
        btnXoa = findViewById(R.id.btnXoa);
        btnCapNhat = findViewById(R.id.btnSua);
        btnExit = findViewById(R.id.btnExit);

        lv = findViewById(R.id.gridViewBook);
//        adapter = new AuthorAdapter(this,R.layout.layout_row_author,list);
//        lv.setAdapter(adapter);
        db = new DataBaseHelper(this);
    }

    public void clear(){
        edtMa.setText("");
        edtTen.setText("");
        edtTacGia.setText("");
    }

    public void valid(Book b){
        edtMa.setText(""+b.getId_book());
        edtTen.setText(b.getTitle());
        edtTacGia.setText(""+b.getAuthor().getId_author());
    }

    public void select(){
        list = new ArrayList<>();
        if(edtMa.getText().toString().equals("")){
            list = db.getAllBook();
        }else{
            try {
                list.add(db.getIDBook(Integer.parseInt(edtMa.getText().toString())));
            }catch (Exception e){
                Toast.makeText(this,"Không tìm thấy",Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if(list.size()>0){
//            adapter = new BookAdapter(this,R.layout.layout_row_book,list);
//            lv.setAdapter(adapter);
            clear();
        }else{
            Toast.makeText(this,"CSDL rỗng",Toast.LENGTH_SHORT).show();
        }

//        list_String = new ArrayList<>();
//        for (Author author:list){
//            list_String.add(author.getId_author()+"");
//            list_String.add(author.getName());
//            list_String.add(author.getEmail());
//            list_String.add(author.getAddress());
//        }
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
}