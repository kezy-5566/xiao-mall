package xyz.vimtool.service;

import org.springframework.stereotype.Service;
import xyz.vimtool.bean.User;
import xyz.vimtool.dao.BaseDao;
import xyz.vimtool.dao.UserDao;

import javax.inject.Inject;

/**
 * 管理员Service层
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @since   jdk1.8
 * @date    2018/8/24
 */
@Service
public class UserService extends BaseService<User, Integer> {

    @Inject
    private UserDao userDao;

    @Override
    public BaseDao<User, Integer> getBaseDao() {
        return this.userDao;
    }

    /**
     * 手机号查找用户
     *
     * @param phone 手机号
     * @return 用户信息
     */
    public User getByPhone(String phone) {
        return userDao.findByPhone(phone);
    }
}
