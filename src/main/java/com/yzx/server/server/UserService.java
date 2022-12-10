package com.yzx.server.server;

import com.yzx.server.dao.UserDao;
import com.yzx.server.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  private UserDao userDao;

  public User getUser(String account,Integer role) {
    return userDao.getUser(account,role);
  }
}
