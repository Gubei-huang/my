package com.hwj.service.impl;

import com.hwj.beans.Book;
import com.hwj.beans.User;
import com.hwj.dao.BookDao;
import com.hwj.service.BookService;
import com.hwj.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml","classpath:spring-context.xml","classpath:spring-mvc.xml"})
public class UserServiceImplTest {
    @Resource
    private UserService userService;
    private BookService bookService;
    @Resource
    private BookDao bookDao;

    @Test
    public void listUser() {

        List<User> users = userService.listUser();
        for (User u:users) {
            System.out.println(u);
        }
        Book book = new Book();
        book.setIsbn("123");
//        bookService.insertBook(book);
        bookDao.insertBook(book);
    }
}