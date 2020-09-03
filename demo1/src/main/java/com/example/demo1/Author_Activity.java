package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Author_Activity extends AppCompatActivity {
    EditText edtMa, edtTen, edtDiaChi, edtEmail;
    Button btnXem, btnThem, btnXoa, btnCapNhat;

    GridView gv;
    ArrayAdapter<String> adapter;
    List<Author> list_A;
    List<String> list_String;
    Database_Book_Author db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_);

        list_A = new ArrayList<>();
        list_String = new ArrayList<>();
        anhXa();

        select_Author();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
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

        btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                find_Author();
            }
        });

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int vitri = i + 1;
                int dong = 0;
                if (vitri % 4 == 0) {
                    dong = vitri / 4;
                } else {
                    dong = vitri / 4 + 1;
                }
                String maTG = list_String.get((dong - 1) * 4);
                Author author = db.getAuthor_Id(maTG);
                valid(author);
            }
        });
    }

    public void anhXa() {
        edtMa = findViewById(R.id.edtMa);
        edtTen = findViewById(R.id.edtTen);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtEmail = findViewById(R.id.edtEmail);

        btnXem = findViewById(R.id.btnXem);
        btnThem = findViewById(R.id.btnThem);
        btnCapNhat = findViewById(R.id.btnCapNhat);
        btnXoa = findViewById(R.id.btnXoa);

        gv = findViewById(R.id.gridView);

        db = new Database_Book_Author(this);
    }

    public void select_Author() {
        list_A = new ArrayList<>();
        String maTG = "";
        if (maTG.equals("")) {
            list_A = db.getAllAuthor();
        } else {
            try {
                Author author = db.getAuthor_Id(maTG);
                list_A.add(author);
            } catch (Exception ex) {
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list_String);
                gv.setAdapter(adapter);
            }
        }
        list_String = new ArrayList<>();
        for (Author author : list_A) {
            list_String.add(author.getMaTG());
            list_String.add(author.getTenTG());
            list_String.add(author.getDiaChi());
            list_String.add(author.getEmail());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list_String);
        gv.setAdapter(adapter);
    }

    public void find_Author() {
        list_A = new ArrayList<>();
        String maTG = edtMa.getText().toString();
        if (maTG.equals("")) {
            select_Author();
        } else {
            try {
                Author author = db.getAuthor_Id(maTG);
                list_A.add(author);
            } catch (Exception ex) {
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list_String);
                gv.setAdapter(adapter);
            }
        }
        list_String = new ArrayList<>();
        for (Author author : list_A) {
            list_String.add(author.getMaTG());
            list_String.add(author.getTenTG());
            list_String.add(author.getDiaChi());
            list_String.add(author.getEmail());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list_String);
        gv.setAdapter(adapter);
    }

    public void save() {
        String maTG = edtMa.getText().toString();
        String tenTG = edtTen.getText().toString();
        String diaChi = edtDiaChi.getText().toString();
        String email = edtEmail.getText().toString();
        if (!(maTG.equals("") || tenTG.equals("") || diaChi.equals("") || email.equals(""))) {
            Author author = new Author(maTG, tenTG, diaChi, email);
            if (db.insertAuthor(author) > 0) {
                clear();
                select_Author();
            } else {
                Toast.makeText(this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void delete() {
        String maTG = edtMa.getText().toString();
        if (!(maTG.equals(""))) {
            if (db.delete(maTG)) {
                clear();
                select_Author();
            } else {
                Toast.makeText(this, "Xóa không thành công", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void update() {
        String maTG = edtMa.getText().toString();
        String tenTG = edtTen.getText().toString();
        String diaChi = edtDiaChi.getText().toString();
        String email = edtEmail.getText().toString();
        if (!(maTG.equals("") || tenTG.equals("") || diaChi.equals("") || email.equals(""))) {
            Author author = new Author(maTG, tenTG, diaChi, email);
            if (db.updateAuthor(author) > 0) {
                clear();
                select_Author();
            } else {
                Toast.makeText(this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void clear() {
        edtMa.setText("");
        edtTen.setText("");
        edtDiaChi.setText("");
        edtEmail.setText("");
    }

    public void valid(Author author) {
        edtMa.setText(author.getMaTG());
        edtTen.setText(author.getTenTG());
        edtDiaChi.setText(author.getDiaChi());
        edtEmail.setText(author.getDiaChi());
    }

}