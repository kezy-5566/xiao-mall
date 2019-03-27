package xyz.vimtool;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import xyz.vimtool.bean.User;
import xyz.vimtool.service.UserService;

import javax.inject.Inject;

/**
 * 用户测试
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @since   jdk1.8
 * @date    2018/8/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {

    @Inject
    private MockMvc mvc;

    @Inject
    private UserService userService;

    @Test
    public void list() throws Exception {
        if (mvc == null) {
            System.out.println("%%%%%%%%%%%%%");
        } else {
            ResultActions perform = mvc.perform(
                    MockMvcRequestBuilders.get("/sys/role/list")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8));
            perform.andDo(MockMvcResultHandlers.print());
        }
    }

    @Test
    public void user() {
        User user = userService.getByPhone("15109269725");
//        String s = JSONObject.toJSONString(user);
        System.out.println(user.getId());
    }
}
