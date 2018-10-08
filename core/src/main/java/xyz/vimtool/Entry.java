package xyz.vimtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.File;
import java.util.concurrent.*;

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
public class Entry {

    private static ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Entry.class);
        springApplication.addListeners(new ApplicationPidFileWriter("flag.pid"));
        context = springApplication.run(args);

        // 控制程序安全退出
        Executors.newSingleThreadExecutor().execute(() -> {
            File file = new File("flag.exit");
            while (!file.exists()) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            SpringApplication.exit(context);
            System.exit(0);
        });
    }
}
