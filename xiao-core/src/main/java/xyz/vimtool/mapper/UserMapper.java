package xyz.vimtool.mapper;

import org.mapstruct.Mapper;
import xyz.vimtool.bean.User;
import xyz.vimtool.response.UserRes;

/**
 * 用户Mapper
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/26
 */
@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

    /**
     * 用户信息封装
     *
     * @param user 用户
     * @return 用户信息
     */
    UserRes toRes(User user);
}
