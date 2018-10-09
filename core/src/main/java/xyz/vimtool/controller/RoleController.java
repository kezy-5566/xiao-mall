package xyz.vimtool.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.vimtool.bean.Role;
import xyz.vimtool.response.HttpResponse;
import xyz.vimtool.mapper.RoleMapper;
import xyz.vimtool.response.RoleRes;
import xyz.vimtool.service.RoleService;

import javax.inject.Inject;
import java.util.List;

/**
 * 角色Controller层
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/25
 */
@Api(value = "sys/role", tags = "sys:角色")
@RestController
@RequestMapping("sys/role")
public class RoleController extends BaseController {

    @Inject
    private RoleService roleService;

    @Inject
    private RoleMapper roleMapper;

    @ApiOperation(value = "角色列表")
    @RequiresRoles("admin")
    @GetMapping("list")
    public HttpResponse<List<RoleRes>> list() {
        List<Role> roles = roleService.listAll();
        return HttpResponse.buildSuccess(roleMapper.toRes(roles));
    }
}
