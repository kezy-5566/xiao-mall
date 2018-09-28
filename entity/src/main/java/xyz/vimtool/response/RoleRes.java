package xyz.vimtool.response;

import lombok.Data;

import java.util.Set;

/**
 * 角色
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/27
 */
@Data
public class RoleRes {

    private Integer id;

    private String name;

    private String description;

    private Set<PermissionRes> permissions;
}
