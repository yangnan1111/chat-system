package com.imooc.config;

import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class QuartzConfig {
//    /**
//     * 继承org.springframework.scheduling.quartz.SpringBeanJobFactory
//     * 实现任务实例化方式
//     */
//    public static class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements
//            ApplicationContextAware {
//
//        private transient AutowireCapableBeanFactory beanFactory;
//
//        @Override
//        public void setApplicationContext(final ApplicationContext context) {
//            beanFactory = context.getAutowireCapableBeanFactory();
//        }
//
//        /**
//         * 将job实例交给spring ioc托管
//         * 我们在job实例实现类内可以直接使用spring注入的调用被spring ioc管理的实例
//         * @param bundle
//         * @return
//         * @throws Exception
//         */
//        @Override
//        protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
//            final Object job = super.createJobInstance(bundle);
//            /**
//             * 将job实例交付给spring ioc
//             */
//            beanFactory.autowireBean(job);
//            return job;
//        }
//    }

    /**
     * 配置任务工厂实例
     * @param applicationContext spring上下文实例
     * @return
     */
//    @Bean
//    public JobFactory jobFactory(ApplicationContext applicationContext)
//    {
//        /**
//         * 采用自定义任务工厂 整合spring实例来完成构建任务
//         * see {@link AutowiringSpringBeanJobFactory}
//         */
//        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
//        jobFactory.setApplicationContext(applicationContext);
//        return jobFactory;
//    }
    @Autowired
    SpringJobFactory springJobFactory;

    @Bean(name="SchedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(springJobFactory);
        factory.setQuartzProperties(quartzProperties());
        return factory;
    }

    /*
     * quartz初始化监听器
     */
    @Bean
    public QuartzInitializerListener executorListener() {
        return new QuartzInitializerListener();
    }

    /*
     * 通过SchedulerFactoryBean获取Scheduler的实例
     */
    @Bean(name="Scheduler")
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }

    /**
     * 设置quartz属性,可以从properties配置中读取
     */
    public Properties quartzProperties() throws IOException {
        Properties prop = new Properties();
        prop.put("quartz.scheduler.instanceName", "ServerScheduler");
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        prop.put("org.quartz.scheduler.skipUpdateCheck", "true");
        prop.put("org.quartz.scheduler.instanceId", "NON_CLUSTERED");
        prop.put("org.quartz.scheduler.jobFactory.class", "org.quartz.simpl.SimpleJobFactory");
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "5");
        return prop;
    }
}
