package com.yzx.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yzx.server.domain.book.Book;
import com.yzx.server.exception.Code;
import com.yzx.server.server.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/book")
@ResponseBody
public class BookController extends BaseController{

  @Autowired
  BookService bookService;
  /**
   * 获取所有书籍
   */
  @GetMapping
  public Result getAllBook() {
    return new Result(Code.OK ,bookService.getAllBook() , "查询成功", "The query succeeded");
  }

  @GetMapping("/{bookName}")
  public Result getUsersByAccount_admin(@PathVariable String bookName) {
    return new Result(Code.OK, bookService.getBooksByLikeName(bookName), "获取成功", "success");
  }

  /**
   * 分页获取书籍
   */
  @GetMapping("/{page}/{size}")
  public Result getBookByPage(@PathVariable Integer page,@PathVariable Integer size) {
    IPage<Book> pageData =  bookService.getBookByPage(page,size);
    return new Result(Code.OK, pageData, "查询成功", "The query succeeded");
  }

  /**
   * 更新书本信息
   */
  @PatchMapping()
  public Result updateBook_admin(@RequestBody List<Book> books) {
    if (bookService.updateBooks(books)) {
      return new Result(Code.OK, "修改成功", "The modification was successful");
    }
    return new Result(Code.ERROR, "修改失败", "The modification was fail");
  }

  @PostMapping
  public Result addBook_admin(@RequestBody List<Book> books) {
    if (bookService.addBooks(books)) {
      return new Result(Code.OK, books, "添加成功", "The addition succeeded");
    }
    return new Result(Code.ERROR, "添加失败", "Add failed");
  }

  @DeleteMapping()
  public  Result delBook_admin(@RequestBody List<Long> ids) {
    if (bookService.delBooks(ids)) {
      return new Result(Code.OK, "删除成功", "The deletion was successful");
    }
    return new Result(Code.ERROR,"删除失败", "Delete failed");
  }
}
