package xyz.vimtool.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.vimtool.bean.Permission;
import xyz.vimtool.response.HttpResponse;
import xyz.vimtool.mapper.PermissionMapper;
import xyz.vimtool.response.PermissionRes;
import xyz.vimtool.service.PermissionService;

import javax.inject.Inject;
import java.util.List;

/**
 * 权限Controller
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/25
 */
@Api(value = "sys/permission", tags = "sys:权限")
@RestController
@RequestMapping("sys/permission")
public class PermissionController extends BaseController {

    @Inject
    private PermissionService permissionService;

    @Inject
    private PermissionMapper permissionMapper;

    @ApiOperation(value = "权限列表")
    @RequiresRoles("admin")
    @GetMapping("list")
    public HttpResponse<List<PermissionRes>> list() {
        List<Permission> permissions = permissionService.listAll();
        return HttpResponse.buildSuccess(permissionMapper.toRes(permissions));
    }
}
