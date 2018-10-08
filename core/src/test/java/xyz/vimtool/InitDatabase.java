package xyz.vimtool;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.vimtool.bean.Permission;
import xyz.vimtool.bean.Role;
import xyz.vimtool.bean.User;
import xyz.vimtool.global.Constant;
import xyz.vimtool.service.PermissionService;
import xyz.vimtool.service.RoleService;
import xyz.vimtool.service.UserService;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 初始化数据库
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Entry.class)
public class InitDatabase {

    @Inject
    private PermissionService permissionService;

    @Inject
    private RoleService roleService;

    @Inject
    private UserService userService;

    @Test
    public void init(){
        ClassPathResource resource = new ClassPathResource("database/permission.json");
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(resource.getURI())))) {
            String line;
            while((line = reader.readLine()) != null) {
                builder.append(line + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 初始化权限
        JSONArray jsonArray = JSONObject.parseArray(builder.toString());
        Iterator<Object> iterator = jsonArray.iterator();
        Set<Permission> permissions = new HashSet<>();
        while (iterator.hasNext()) {
            JSONObject next = (JSONObject) iterator.next();
            Permission permission = new Permission();
            permission.setName(next.getString("name"));
            permission.setDescription(next.getString("description"));
            permissions.add(permission);
        }
        List<Permission> permissionsResult = permissionService.save(permissions);

        // 初始化角色
        Role role = new Role();
        role.setName(Constant.Role.NAME_ADMIN);
        role.setDescription("超级管理员");
        role.setPermissions(new HashSet<>(permissionsResult));
        Role roleResult = roleService.save(role);

        // 初始化超级管理员
        User user = new User();
        user.setName("超级管理员");
        user.setPhone("15109269725");
        user.setPassword("15109269725");
        user.setRoles(new HashSet<>(Arrays.asList(roleResult)));
        userService.save(user);
    }
}
