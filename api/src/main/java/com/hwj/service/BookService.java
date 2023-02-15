package com.hwj.service;

import com.hwj.beans.Book;

import java.util.List;

public interface BookService {
    public List<Book> listBook();
    public void insertBook(Book book);
    public void deleteBook(String isbn);
    public void updateBook(Book book);
    public List<Book> listBookByIsbn(String isbn);
    public List<Book> listBookByBookName(String bookName);
    public List<Book> listBookByAuthor(String author);
}
