package com.yzx.server;

import com.yzx.server.domain.user.User;
import com.yzx.server.server.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
  @Autowired
  UserService userService;
  @Test
  public void checkUser() {
    System.out.println(userService.checkAccountIsExist("yzxyzx", 1));
  }
  @Test
  public void setUser() {
    User user = new User();
    user.setId(1L);
  }
}
