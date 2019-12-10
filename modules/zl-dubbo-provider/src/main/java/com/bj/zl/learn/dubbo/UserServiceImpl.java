/**
 * create by Administrator
 * 2019-09-24
 */
package com.bj.zl.learn.dubbo;

import com.bj.zl.learn.contract.domain.User;
import com.bj.zl.learn.contract.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(int id) {
        User user = new User();
        user.setId(id);
        user.setAge(id*5);
        user.setName("jack"+id);
        return user;
    }
}
