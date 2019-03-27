package xyz.vimtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.File;

/**
 * 项目启动入口
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @since   jdk1.8
 * @date    2018/8/23
 */
@EnableTransactionManagement
@SpringBootApplication
public class XiaoMallApplication {

    public static ApplicationContext context;
    public static File pidFile = new File("xiaoMallApplication.pid");

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(XiaoMallApplication.class);
        springApplication.addListeners(new ApplicationPidFileWriter(pidFile));
        context = springApplication.run(args);
    }
}
