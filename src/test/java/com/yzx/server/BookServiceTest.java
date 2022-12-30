package com.yzx.server;

import com.yzx.server.server.BookService;
import com.yzx.server.server.BorrowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class BookServiceTest {
  @Autowired
  BookService bookService;
  @Autowired
  BorrowService borrowService;

//  @Test
//  public void getAllBook() {
//    System.out.println(bookService.getAllBook());
//  }
//
//  public void deleteBook() {
//    ArrayList<Long> delIds = new ArrayList<>();
//    bookService.delBooks(delIds);
//  }

  @Test
  public void getBookById() {
    System.out.println(bookService.getBookById(767L));
  }
  @Test
  public void borrowBook() {
    System.out.println(borrowService.borrowBook(799L,1L));
  }
}
