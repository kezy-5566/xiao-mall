package xyz.vimtool.mapper;

import org.mapstruct.Mapper;
import xyz.vimtool.bean.Permission;
import xyz.vimtool.response.PermissionRes;

import java.util.List;

/**
 * 权限Mapper
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/27
 */
@Mapper(componentModel = "spring")
public interface PermissionMapper {

    /**
     * 权限封装
     *
     * @param permission 权限
     * @return 权限信息
     */
    PermissionRes toRes(Permission permission);

    /**
     * 权限封装
     *
     * @param permissions 权限
     * @return 权限信息
     */
    List<PermissionRes> toRes(List<Permission> permissions);
}
