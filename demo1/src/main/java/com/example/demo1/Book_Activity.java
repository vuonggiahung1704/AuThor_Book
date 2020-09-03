package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Book_Activity extends AppCompatActivity {
    EditText edtMa, edtTen, edtDonGia;
    Spinner spnTacGia;

    Button btnXem, btnThem, btnXoa, btnCapNhat;
    GridView gv;
    ArrayAdapter<String> adapter_B;
    AuthorAdapter adapter_Author;

    List<Author> list_A;

    List<Book> list_B;
    List<String> list_String_book;
    Database_Book_Author db;

    Author author = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_);

        list_A = new ArrayList<>();
        list_B = new ArrayList<>();
        list_String_book = new ArrayList<>();
        anhXa();

        select_Author();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        spnTacGia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                author = list_A.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                find_Book();
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
                String maS = list_String_book.get((dong - 1) * 4);
                Book b = db.getBook_Id(maS);
                valid(b);
            }
        });
    }

    public void anhXa() {
        edtMa = findViewById(R.id.edtMa);
        edtTen = findViewById(R.id.edtTen);
        edtDonGia = findViewById(R.id.edtDonGia);
        spnTacGia = findViewById(R.id.spnAuthor);

        btnXem = findViewById(R.id.btnXem);
        btnThem = findViewById(R.id.btnThem);
        btnCapNhat = findViewById(R.id.btnCapNhat);
        btnXoa = findViewById(R.id.btnXoa);

        gv = findViewById(R.id.gridView);

        db = new Database_Book_Author(this);
    }

    public void select_Author() {
        list_A = new ArrayList<>();
        list_A = db.getAllAuthor();
        if(list_A.size()>0) {
            adapter_Author = new AuthorAdapter(this, R.layout.row_author, list_A);
            spnTacGia.setAdapter(adapter_Author);
        }
    }

    public void select_Book() {
        list_B = new ArrayList<>();
        list_B = db.getAllBook();
        list_String_book = new ArrayList<>();
        for (Book b : list_B) {
            list_String_book.add(b.getMaS());
            list_String_book.add(b.getTenS());
            Author author = db.getAuthor_Id(b.getAuthor().getMaTG());
            list_String_book.add(author.getTenTG());
            list_String_book.add("" + b.getDonGia());
        }
        adapter_B = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list_String_book);
        gv.setAdapter(adapter_B);
    }

    public void find_Book() {
        list_B = new ArrayList<>();
        String maS = edtMa.getText().toString();
        if (maS.equals("")) {
            select_Book();
        } else {
            try {
                Book book = db.getBook_Id(maS);
                list_B.add(book);
            } catch (Exception ex) {
                adapter_B = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list_String_book);
                gv.setAdapter(adapter_B);
            }
        }
        list_String_book = new ArrayList<>();
        for (Book b : list_B) {
            list_String_book.add(b.getMaS());
            list_String_book.add(b.getTenS());
            Author author = db.getAuthor_Id(b.getAuthor().getMaTG());
            list_String_book.add(author.getTenTG());
            list_String_book.add("" + b.getDonGia());
        }
        adapter_B = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list_String_book);
        gv.setAdapter(adapter_B);
    }

    public void save() {
        String maTG = edtMa.getText().toString();
        String tenTG = edtTen.getText().toString();
        String donGia_S = edtDonGia.getText().toString();
        if (!(maTG.equals("") || tenTG.equals("") || donGia_S.equals(""))) {
            Double donGia = Double.parseDouble(donGia_S);
            Book b = new Book(maTG, tenTG, author, donGia);
            if (db.insertBook(b) > 0) {
                clear();
                select_Book();
            } else {
                Toast.makeText(this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void delete() {
        String maS = edtMa.getText().toString();
        if (!(maS.equals(""))) {
            if (db.deleteBook(maS)) {
                clear();
                select_Book();
            } else {
                Toast.makeText(this, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                select_Book();
                clear();
            }
        }
    }

    public void update() {
        String maTG = edtMa.getText().toString();
        String tenTG = edtTen.getText().toString();
        String donGia_S = edtDonGia.getText().toString();
        if (!(maTG.equals("") || tenTG.equals("") || donGia_S.equals(""))) {
            Double donGia = Double.parseDouble(donGia_S);
            Book b = new Book(maTG, tenTG, author, donGia);
            if (db.updateBook(b) > 0) {
                clear();
                select_Book();
            } else {
                Toast.makeText(this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void clear() {
        edtMa.setText("");
        edtTen.setText("");
        edtDonGia.setText("");
        spnTacGia.setAdapter(adapter_Author);
        author = null;
    }

    public void valid(Book b) {
        edtMa.setText(b.getMaS());
        edtTen.setText(b.getTenS());
        edtDonGia.setText("" + b.getDonGia());
        for (int i = 0; i < list_A.size(); i++) {
            if (list_A.get(i).getMaTG().equalsIgnoreCase(b.getAuthor().getMaTG()))
                spnTacGia.setSelection(i);
        }

    }
}