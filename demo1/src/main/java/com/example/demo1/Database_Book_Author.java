package com.example.demo1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database_Book_Author extends SQLiteOpenHelper {
    public Database_Book_Author(@Nullable Context context) {
        super(context, "author_book_db1", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table Author(maTG text primary key, tenTG text, diaChi text, email text)");
        sqLiteDatabase.execSQL("create table Book(maS text primary key, tenS text, donGia double, maTG text constraint maTG references Author(maTG))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists Author");
        sqLiteDatabase.execSQL("drop table if exists Book");
    }
    //Author
    public int insertAuthor(Author author){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maTG",author.getMaTG());
        contentValues.put("tenTG",author.getTenTG());
        contentValues.put("diaChi",author.getDiaChi());
        contentValues.put("email",author.getEmail());

        int result = (int) db.insert("Author",null,contentValues);
        db.close();
        return  result;
    }

    public int updateAuthor(Author author){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenTG",author.getTenTG());
        contentValues.put("diaChi",author.getDiaChi());
        contentValues.put("email",author.getEmail());

        int result = db.update("Author",contentValues,"maTG"+"=?",new String[] {String.valueOf(author.getMaTG())});
        db.close();
        return  result;
    }

    public boolean delete(String maTG){
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.delete("Author","maTG"+"=?",new String[]{String.valueOf(maTG)})>0){
            db.close();
            return true;
        }
        return false;
    }

    public Author getAuthor_Id(String maTg){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Author where maTg = '"+ maTg +"'",null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        String maTG = cursor.getString(0);
        String tenTG = cursor.getString(1);
        String diaChi = cursor.getString(2);
        String email = cursor.getString(3);
        Author author = new Author(maTG,tenTG,diaChi,email);
        cursor.moveToNext();
        cursor.close();
        return  author;
    }

    public List<Author> getAllAuthor(){
        List<Author> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Author",null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast() == false){
            String maTG = cursor.getString(0);
            String tenTG = cursor.getString(1);
            String diaChi = cursor.getString(2);
            String email = cursor.getString(3);
            Author author = new Author(maTG,tenTG,diaChi,email);
            list.add(author);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    //Book
    public int insertBook(Book book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maS",book.getMaS());
        contentValues.put("tenS",book.getTenS());
        contentValues.put("donGia",book.getDonGia());
        contentValues.put("maTG",book.getAuthor().getMaTG());
        int result = (int) db.insert("Book",null,contentValues);
        db.close();
        return result;
    }

    public int updateBook(Book book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenS",book.getTenS());
        contentValues.put("donGia",book.getDonGia());
        contentValues.put("maTG",book.getAuthor().getMaTG());
        int result = db.update("Book",contentValues,"maS"+"=?",new String[]{String.valueOf(book.getMaS())});
        db.close();
        return result;
    }

    public boolean deleteBook(String maS){
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.delete("Book","maS"+"=?",new String[]{String.valueOf(maS)})>0){
            db.close();
            return true;
        }
        return false;
    }

    public Book getBook_Id(String maS_tim){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Book where maS = '"+maS_tim+"'",null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        String maS = cursor.getString(0);
        String tenS = cursor.getString(1);
        Double donGia = cursor.getDouble(2);
        String maTG = cursor.getString(3);
        Book book = new Book(maS,tenS,new Author(maTG),donGia);
        cursor.moveToNext();
        cursor.close();
        return book;
    }

    public List<Book> getAllBook(){
        List<Book> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Book",null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast() == false){
            String maS = cursor.getString(0);
            String tenS = cursor.getString(1);
            Double donGia = cursor.getDouble(2);
            String maTG = cursor.getString(3);
            Book book = new Book(maS,tenS,new Author(maTG),donGia);
            list.add(book);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
