package xyz.vimtool.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import xyz.vimtool.bean.Role;
import xyz.vimtool.bean.User;
import xyz.vimtool.commons.response.Assert;
import xyz.vimtool.commons.response.HttpResponse;
import xyz.vimtool.commons.response.MarkCode;
import xyz.vimtool.helper.JwtHelper;
import xyz.vimtool.mapper.UserMapper;
import xyz.vimtool.request.LoginReq;
import xyz.vimtool.response.UserRes;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 管理员Controller层
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @since   jdk1.8
 * @date    2018/8/24
 */
@Api(value = "sys/user", tags = "sys:用户")
@RestController
@RequestMapping("sys/user")
public class UserController extends BaseController {

    @Inject
    private UserMapper userMapper;

    /**
     * 用户登录，请求参数中需要添加图形验证码，在外层校验
     * @param loginReq 登录请求body
     * @return 用户信息及token
     */
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "图形验证码", paramType = "query", required = true),
    })
    @PostMapping("login")
    public HttpResponse<UserRes> login(@RequestBody @Valid LoginReq loginReq) {
        User user = userService.getByPhone(loginReq.getPhone());
        Assert.notNull(MarkCode.build("账号不存在"), user);

        // 密码校验
        Assert.isEquals(MarkCode.build("密码不正确"), loginReq.getPassword(), user.getPassword());

        // 返回结果
        Set<String> roleNames = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        UserRes userRes = userMapper.toRes(user);
        userRes.setToken(JwtHelper.buildToken(user.getId(), roleNames));
        return HttpResponse.buildSuccess(userRes);
    }

    @ApiOperation(value = "用户信息")
    @GetMapping()
    public HttpResponse<UserRes> get() {
        UserRes userRes = userMapper.toRes(getCurrentUser());
        return HttpResponse.buildSuccess(userRes);
    }
}
