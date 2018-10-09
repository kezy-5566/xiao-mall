package xyz.vimtool.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import xyz.vimtool.bean.Role;
import xyz.vimtool.bean.User;
import xyz.vimtool.response.Assert;
import xyz.vimtool.response.ExceptionUtils;
import xyz.vimtool.response.HttpResponse;
import xyz.vimtool.response.MarkCode;
import xyz.vimtool.helper.JwtHelper;
import xyz.vimtool.mapper.UserMapper;
import xyz.vimtool.request.LoginReq;
import xyz.vimtool.response.UserRes;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
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

    @Inject
    private DefaultKaptcha defaultKaptcha;

    @ApiOperation(value = "获取验证码图片")
    @GetMapping("code")
    public void getCodeImage(HttpServletRequest request, HttpServletResponse response) {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        // 生成验证码
        String capText = defaultKaptcha.createText();
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

        // 向客户端写出生成的验证码图片
        try (ServletOutputStream out = response.getOutputStream()) {
            ImageIO.write(defaultKaptcha.createImage(capText), "jpg", out);
            out.flush();
        } catch (IOException e) {
            ExceptionUtils.throwResponseException(MarkCode.build("验证码生成失败"));
        }
    }

    /**
     * 用户登录，请求参数中需要添加图形验证码，在外层校验
     *
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
