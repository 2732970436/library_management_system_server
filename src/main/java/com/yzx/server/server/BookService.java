package com.yzx.server.server;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.server.dao.BookDao;
import com.yzx.server.dao.BorrowRecordMapper;
import com.yzx.server.domain.book.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {
  private final BookDao bookDao;

  private final BorrowRecordMapper borrowRecordMapper;

  public BookService(BookDao bookDao, BorrowRecordMapper borrowRecordMapper) {
    this.bookDao = bookDao;
    this.borrowRecordMapper = borrowRecordMapper;
  }

  /**
   * 根据id获取单本书信息
   *
   * @param id 书本id
   * @return Book
   */
  public Book getBookById(Long id) {
    return bookDao.selectById(id);
  }

  /**
   * 根据书名获取
   *
   * @param bookName 书名
   * @return List<Book>
   */
  public List<Book> getBookByName(String bookName) {
    QueryWrapper<Book> qw = new QueryWrapper<Book>();
    qw.eq("book_name", bookName);
    return bookDao.selectList(qw);
  }

  /**
   * 仅为测试接口，请勿调用！！！
   *
   * @return 所有的书本信息
   */
  public List<Book> getAllBook() {
    return bookDao.selectList(null);
  }

  public IPage<Book> getBookByPage(Integer page, Integer size) {
    IPage<Book> myPage = new Page<Book>(page, size);
    return bookDao.selectPage(myPage, null);
  }


  @Transactional
  public boolean updateBooks(List<Book> books) {
    int i = 0;
    for (Book book : books) {
      i += bookDao.updateById(book);
    }
    return i == books.size();
  }

  @Transactional
  public boolean updateBook(Book book) {
    return bookDao.updateById(book) == 1;
  }

  @Transactional
  public boolean addBooks(List<Book> books) {
    int i = 0;
    for (Book book : books) {
      i += bookDao.insert(book);
    }
    return i == books.size();
  }

  /**
   * 用于删除给定的ids索引数组
   *
   * @param ids 被删除ID数组
   * @return 删除是否成功
   */
  @Transactional
  public boolean delBooks(List<Long> ids) {
    return ids.size() == bookDao.deleteBatchIds(ids);
  }

  public List<Book> getBooksByLikeName(String bookName) {
    QueryWrapper<Book> qw = new QueryWrapper<Book>();
    qw.like("book_name", bookName);
    return bookDao.selectList(qw);
  }
}
