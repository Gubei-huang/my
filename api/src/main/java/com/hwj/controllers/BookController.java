package com.hwj.controllers;

import com.hwj.beans.Book;
import com.hwj.service.BookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    private BookService bookService;

    @PostMapping("/listBook")
    public List<Book> listBook(){
        List<Book> books = bookService.listBook();
        return books;
    }

    @PostMapping("/addBook")
    public boolean addBoo(@RequestBody Book book){
        bookService.insertBook(book);
        return true;
    }

    @PostMapping("/deleteBook")
    public boolean deleteBook(@RequestBody String isbn){
        bookService.deleteBook(isbn);
        return true;
    }

    @PostMapping("/updateBook")
    public boolean updateBook(@RequestBody Book book){
        bookService.updateBook(book);
        return true;
    }

    @PostMapping("/findBook")
    public List<Book> findBook(@RequestBody Map<String,String> params){
        String input = params.get("input");
        String radio = params.get("radio");
        List<Book> books = new ArrayList<>();
        if(radio.equals("isbn")){
            books = bookService.listBookByIsbn(input);
        }
        if(radio.equals("bookName")){
            books = bookService.listBookByBookName(input);
        }
        if(radio.equals("author")){
            books = bookService.listBookByAuthor(input);
        }
        return books;
    }
}
