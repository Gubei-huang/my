package com.hwj.service.impl;

import com.hwj.beans.Book;
import com.hwj.dao.BookDao;
import com.hwj.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Resource
    private BookDao bookDao;

    @Override
    public List<Book> listBook() {
        List<Book> books = bookDao.listBook();
        return books;
    }

    @Override
    public void insertBook(Book book) {
        bookDao.insertBook(book);
    }

    @Override
    public void deleteBook(String isbn) {
        bookDao.deleteBook(isbn);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    @Override
    public List<Book> listBookByIsbn(String isbn) {
        List<Book> books = bookDao.listBookByIsbn(isbn);
        return books;
    }

    @Override
    public List<Book> listBookByBookName(String bookName) {
        List<Book> books = bookDao.listBookByBookName(bookName);
        return books;
    }

    @Override
    public List<Book> listBookByAuthor(String author) {
        List<Book> books = bookDao.listBookByAuthor(author);
        return books;
    }
}
