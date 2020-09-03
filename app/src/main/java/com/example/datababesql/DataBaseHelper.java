package com.example.datababesql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(@Nullable Context context) {
        super(context, "db1.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table Author(id integer primary key, name text, address text, email text)");
        sqLiteDatabase.execSQL("create table Book(id integer primary key, tittle text, id_author integer constraint id references Author(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists Author");
        sqLiteDatabase.execSQL("drop table if exists Book");
        onCreate(sqLiteDatabase);
    }
    //Author
    public int insertAuthor(Author author){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",author.getId_author());
        contentValues.put("name",author.getName());
        contentValues.put("address",author.getAddress());
        contentValues.put("email",author.getEmail());

        int result = (int) db.insert("Author",null,contentValues);
        db.close();
        return result;
    }

    public int updateAuthor(Author author) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", author.getName());
        values.put("address", author.getAddress());
        values.put("email", author.getEmail());

        int result = db.update("Author", values,   "id" +"=?", new String[] { String.valueOf(author.getId_author())});
        db.close();
        return result;
    }

    public Author getIDAuthor(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Author where id=" + id,null);
        if(cursor !=null ){
            cursor.moveToFirst();
        }
        Author author = new Author( cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        cursor.moveToNext();
        cursor.close();
        return author;
    }

    public ArrayList<Author> getAllAuthor(){
        ArrayList<Author> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Author",null);
        if(cursor !=null ){
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast()==false){
            list.add(new Author( cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public boolean deleteAuthor(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.delete("Author","id"+"=?",new String[]{String.valueOf(id)})>0){
            db.close();
            return true;
        }
        return false;
    }
    //Book
    public int insertBook(Book book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",book.getId_book());
        contentValues.put("tittle",book.getId_book());
        contentValues.put("id_author",book.getAuthor().getId_author());

        int result = (int) db.insert("Book",null,contentValues);
        db.close();
        return result;
    }

    public int updateBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tittle",book.getTitle());
        contentValues.put("id_author",book.getAuthor().getId_author());

        int result = db.update("Book", contentValues,   "id" +"=?", new String[] { String.valueOf(book.getId_book())});
        db.close();
        return result;
    }

    public Book getIDBook(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Book where id=" + id,null);
        if(cursor !=null ){
            cursor.moveToFirst();
        }
        Author author = new Author(cursor.getInt(2));
        Book book = new Book( cursor.getInt(0),cursor.getString(1),author);
        cursor.moveToNext();
        cursor.close();
        return book;
    }

    public ArrayList<Book> getAllBook(){
        ArrayList<Book> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Book",null);
        if(cursor !=null ){
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast()==false){
            list.add(new Book( cursor.getInt(0),cursor.getString(1),new Author(cursor.getInt(2))));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public boolean deleteBook(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.delete("Book","id"+"=?",new String[]{String.valueOf(id)})>0){
            db.close();
            return true;
        }
        return false;
    }

}
