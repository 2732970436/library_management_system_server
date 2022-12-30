package com.yzx.server.server;

import com.yzx.server.controller.Result;
import com.yzx.server.dao.BookDao;
import com.yzx.server.dao.BorrowRecordMapper;
import com.yzx.server.dao.UserDao;
import com.yzx.server.domain.MyPage;
import com.yzx.server.domain.book.Book;
import com.yzx.server.domain.borrowRecord.BorrowRecord;
import com.yzx.server.domain.user.User;
import com.yzx.server.exception.BusinessException;
import com.yzx.server.exception.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BorrowService {
  @Autowired
  private BorrowRecordMapper borrowRecordMapper;

  @Autowired
  private UserDao userDao;

  @Autowired
  private BookDao bookDao;

  public void checkBlacklist(User user) {
    if(user.getStatus() == 2) {
      throw new BusinessException(Code.USER_ERROR, "用户为黑名单用户");
    }
  }

  /**
   *
   */

  //这里可以选择使用粒度更小的同步锁，如先锁查询用户借阅数量部分，在数据库中+1后放开锁，但是频繁操作数据库会加大响应时间，所以这里采用锁方法，少操作数据库
  //这里的更优解是在查询用户借阅数量时，按照用户来锁，同一用户才需要被锁阻塞，在书本库存锁模块使同一书本锁，这样可以极大提高响应速度
  @Transactional
  public synchronized Result borrowBook(Long bookId, Long userId) {
    User user = userDao.selectById(userId);
    if (user == null) {
      throw new BusinessException(Code.USER_ERROR,userId + "号用户不存在");
    }
    this.checkBlacklist(user);
//      查询用户
    if (user.getBorrowCount() >= 10) {
      return new Result(Code.ERROR, "您的借阅数量已经到达上限", "Your library number has reached the upper limit");
    }
    user.setBorrowCount(user.getBorrowCount() + 1);

//      查询书本
    Book book = bookDao.selectById(bookId);
    if (book == null) {
      throw new BusinessException(Code.BOOK_ERROR, bookId + "号书本不存在");
    }
    if (book.getRemain() <= 0) {
      return new Result(Code.ERROR, "借阅失败,书本库存不足", "Borrowing fails, the book inventory shortage");
    }

    book.setRemain(book.getRemain() - 1);

//      插入记录
    BorrowRecord br = new BorrowRecord(bookId, userId);
    if (1 == borrowRecordMapper.insert(br)) {
      userDao.updateById(user);
      bookDao.updateById(book);
      return new Result(Code.OK, null, "申请成功，请等待审批", "success");
    }
    return new Result(Code.ERROR, null, "借阅失败,尝试插入借阅数据时失败", "Borrowing fails，Try to insert lending data failed");
  }


  @Transactional
  public synchronized  Result  returnBook(Long borrowId) {
    BorrowRecord br = borrowRecordMapper.selectById(borrowId);
    if (br == null) {
      return new Result(Code.ERROR, null, "还书失败,尝试插入还书数据时失败", "Returning fails，Try to insert lending data failed");
    }
    Long userId = br.getUserId();
    Long bookId = br.getBookId();

    User user = userDao.selectById(userId);
    if (user == null) {
      throw new BusinessException(Code.USER_ERROR,userId + "号用户不存在");
    }
    user.setBorrowCount(user.getBorrowCount() - 1);
//  增加书本可借数
    Book book = bookDao.selectById(bookId);
    if (book == null) {
      throw new BusinessException(Code.BOOK_ERROR, bookId + "号书本不存在");
    }
    book.setRemain(book.getRemain() + 1);

//  删除记录
    br.setReturnTime(new Timestamp(System.currentTimeMillis()));
//  查询是否超期，如果超期则需先缴纳罚款
    if (br.getReturnStatus() == 2) {
      return new Result(Code.RETURN_EXPIRED, br,"此书已超期，请缴纳罚金后再试", "The book has been extended, please try again after pay the fine" );
    }

    if (1 == borrowRecordMapper.updateById(br) && 1 == borrowRecordMapper.deleteById(br.getId())) {
      userDao.updateById(user);
      bookDao.updateById(book);
      return new Result(Code.OK, null, "还书成功", "success");
    }
    return new Result(Code.ERROR, null, "还书失败,尝试插入还书数据时失败", "Returning fails，Try to insert lending data failed");
  }

  public List<BorrowRecord> getBorrowInfo(Long userId) {
    return borrowRecordMapper.getAllRecords(userId);
  }

  public MyPage getBorrowInfoByConditionAndPage(Integer userId, Integer bookId, Integer start, Integer end) {
    return new MyPage(borrowRecordMapper.getRecordsByCondition(userId,bookId,start,end), borrowRecordMapper.getRecordsCount(userId, bookId));
  }

  public boolean changeApproval(BorrowRecord borrowRecord) {
    return 1 == borrowRecordMapper.updateById(borrowRecord);
  }

}
