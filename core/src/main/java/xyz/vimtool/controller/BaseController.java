package xyz.vimtool.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;
import xyz.vimtool.bean.User;
import xyz.vimtool.response.HttpCode;
import xyz.vimtool.response.ResponseException;
import xyz.vimtool.global.Constant;
import xyz.vimtool.service.UserService;

import javax.inject.Inject;
import java.util.Map;

/**
 * Controller层基类
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @since   jdk1.8
 * @date    2018/8/24
 */
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class BaseController {

    @Inject
    protected UserService userService;

    /**
     * 获取当前系统用户
     */
    protected User getCurrentUser() {
        Integer userId = (Integer)((Map) SecurityUtils.getSubject().getPrincipal()).get(Constant.User.USER_ID);
        User user = userService.getById(userId);
        if (user == null) {
            throw new ResponseException(HttpCode.NO_LOGIN);
        }
        return user;
    }

    /**
     * 获取当前用户id
     */
    protected Integer getCurrentUserId() {
        Integer userId = (Integer)((Map) SecurityUtils.getSubject().getPrincipal()).get(Constant.User.USER_ID);
        if (userId == null) {
            throw new ResponseException(HttpCode.NO_LOGIN);
        }

        return userId;
    }
}
