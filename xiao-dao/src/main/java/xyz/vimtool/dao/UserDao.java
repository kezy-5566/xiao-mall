package xyz.vimtool.dao;

import org.springframework.stereotype.Repository;
import xyz.vimtool.bean.User;

/**
 * 管理员Dao层
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @since   jdk1.8
 * @date    2018/8/24
 */
@Repository
public interface UserDao extends BaseDao<User, Integer> {

    /**
     * 手机号查找用户
     *
     * @param phone 手机号
     * @return 用户信息
     */
    User findByPhone(String phone);
}
