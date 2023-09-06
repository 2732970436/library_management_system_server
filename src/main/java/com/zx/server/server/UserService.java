package com.zx.server.server;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zx.server.dao.UserDao;
import com.zx.server.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
  @Autowired
  private UserDao userDao;

  public User getUser(String account, Integer role) {
    QueryWrapper<User> ar = new QueryWrapper<User>();
    Map<String, Object> armap = new HashMap<>();
    armap.put("account", account);
    armap.put("role", role);
    ar.allEq(armap);
    return userDao.selectOne (ar);
  }
  public boolean enroll(User user) {
    user.setDeleted(0);
    int i = userDao.insert(user);
    
    return  i != 0;
  }

  /**
   * 检查账号是否存在
   * @param account 账户名
   * @param role 用户身份
   * @return 如果存在则返回true,不存在则返回false
   */
  public boolean checkAccountIsExist(String account, Integer role) {
    QueryWrapper<User> ac = new QueryWrapper<User>();
    Map<String, Object> acMap = new HashMap<>();
    acMap.put("account", account);
    acMap.put("role", role);
    ac.allEq(acMap);
    User user = userDao.selectOne(ac);
    return user != null;
  }


  /**
   * 获取所有的用户信息，因为用户较少，所以前端页面采取调用此接口直接获取所有，如业务用户量大于某个值则进行分页处理，这里懒得写了
   * @return
   */
  public List<User> getAllUser() {
    return userDao.selectList(null);
  }
  /**
   *
   * @param page
   * @param pageSize
   * @return
   */
  public IPage<User> getUsersByPage(Integer page, Integer pageSize) {
    IPage<User> mypage = new Page<>(page, pageSize);
//    QueryWrapper<User> qw = new QueryWrapper<User>();
//    qw.eq("role", 0);
     return userDao.selectPage(mypage, null);
  }

  public boolean addUser(User user) {
     int i = userDao.insert(user);
     return i == 1;
  }
  
  public boolean updateUser(User user) {
     int i = userDao.updateById(user);
     return i == 1;
  }
  
  public boolean deleteUser(User user) {
   return userDao.deleteById(user.getId()) == 1;
  }


  @Transactional
  public boolean updateUsers(List<User> users) {
    int i = 0;
    for (User user : users) {
      i += userDao.updateById(user);
    }
    return  i == users.size();
  }

  @Transactional
  public boolean addUsers(List<User> users) {
    int i = 0;
    for (User user : users) {
      i += userDao.insert(user);
    }
    return i==users.size();
  }

  /**
   * 用于删除给定的ids索引数组
   * @param ids 被删除ID数组
   * @return 删除是否成功
   */
  public boolean delUsers(List<Integer> ids) {
    return ids.size() == userDao.deleteBatchIds(ids);
  }

  public List<User> getUsersByAccount(String account) {
    QueryWrapper<User> qw = new QueryWrapper<User>();
    qw.like("account", account);
    return userDao.selectList(qw);
  }

  //根据部门名获取用户
    public List<User> getUsersByDepartment(String department) {
        QueryWrapper<User> qw = new QueryWrapper<User>();
        qw.eq("dept", department);
        return userDao.selectList(qw);
    }
}
