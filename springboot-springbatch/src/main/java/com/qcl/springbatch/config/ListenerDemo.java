package com.qcl.springbatch.config;

import com.qcl.springbatch.listener.MyChunkListener;
import com.qcl.springbatch.listener.MyJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * 监听器
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/25
 */
@Configuration
public class ListenerDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job listenerJob() {
        return jobBuilderFactory.get("listenerJob")
                .start(step1())
                .listener(new MyJobListener())
                .build();

    }

    private Step step1() {

        return stepBuilderFactory.get("step1")
                //读取完200个数据在处理  read process write
                .<String, String>chunk(2)
                //容错
                .faultTolerant()
                //chunk的使用
                .listener(new MyChunkListener())
                //读数据
                .reader(read())
                .writer(write())
                .build();
    }

    @Bean
    public ItemWriter<String> write() {
        //进行数据输出
        return new ItemWriter<String>() {
            @Override
            public void write(List<? extends String> list) throws Exception {
                for (String str : list) {
                    System.out.println(str);
                }
            }
        };
    }

    /**
     * 读取数据
     *
     * @return
     */
    @Bean
    public ItemReader<String> read() {
        //进行数据读取
        return new ListItemReader<>(Arrays.asList("java", "spring,", "mybatis", "mybatis plus", "Hibernate"));
    }

}
