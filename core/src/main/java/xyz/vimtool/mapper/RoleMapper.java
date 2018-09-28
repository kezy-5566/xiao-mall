package xyz.vimtool.mapper;

import org.mapstruct.Mapper;
import xyz.vimtool.bean.Role;
import xyz.vimtool.response.RoleRes;

import java.util.List;

/**
 * 角色Mapper
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/27
 */
@Mapper(componentModel = "spring", uses = PermissionMapper.class)
public interface RoleMapper {

    /**
     * 角色封装
     *
     * @param role 角色
     * @return 角色信息
     */
    RoleRes toRes(Role role);

    /**
     * 角色封装
     *
     * @param roles 角色
     * @return 角色信息
     */
    List<RoleRes> toRes(List<Role> roles);
}
