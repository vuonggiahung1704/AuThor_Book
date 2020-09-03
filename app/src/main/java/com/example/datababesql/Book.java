package com.example.datababesql;

import java.io.Serializable;

public class Book implements Serializable {
    private int id_book;
    private String title;
    private Author author;

    public Book(int id_book, String title, Author author) {
        this.id_book = id_book;
        this.title = title;
        this.author = author;
    }

    public Book() {
    }

    public int getId_book() {
        return id_book;
    }

    public void setId_book(int id_book) {
        this.id_book = id_book;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return id_book + "-" + title + "-" + author.toString();
    }
}
