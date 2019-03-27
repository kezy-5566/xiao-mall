package xyz.vimtool.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import xyz.vimtool.XiaoMallApplication;

import java.util.Objects;

/**
 * 动态定时任务配置
 *
 * @author qinxiaoqing
 * @date  2019/03/12
 */
@Configuration
@EnableScheduling
@AutoConfigureAfter(XiaoMallApplication.class)
public class ScheduleConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(this::monitorExit, triggerContext -> {
            CronTrigger cronTrigger = new CronTrigger("*/2 * * * * ?");
            return cronTrigger.nextExecutionTime(triggerContext);
        });
    }

    /**
     * 监控系统退出
     */
    private void monitorExit() {
        if (!XiaoMallApplication.pidFile.exists()
                && Objects.nonNull(XiaoMallApplication.context)) {
            SpringApplication.exit(XiaoMallApplication.context);
            System.exit(0);
        }
    }
}
