package com.qcl.mybatisplus;

import com.qcl.mybatisplus.entity.Employee;
import com.qcl.mybatisplus.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * MybatisPlus插入大量数据效率对比：foreach插入、SqlSession批量插入、sql插入
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/11/25
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyBatisPlusApplication.class)
public class InsertEfficiencyTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    public List<Employee> list = new ArrayList<>();

    @Before
    public void generatorEmployeeList() {
        long start = System.currentTimeMillis();
        Employee employee;
        for (int i = 1; i <= 5000; i++) {
            employee = new Employee();
            employee.setId(i);
            employee.setLastName("12345java17");
            employee.setEmail("hhh@qq.com");
            employee.setAge(18);
            employee.setGender(0);
            list.add(employee);
        }
        log.info("拼装数据 耗时：{}", (System.currentTimeMillis() - start));
        log.info("数据长度：{}", list.size());
    }

    @Test
    public void forEachInsert() {
        log.info("forEachInsert 插入开始========");
        long start = System.currentTimeMillis();
        for (int i = 0; i < list.size(); i++) {
            employeeMapper.insert(list.get(i));
        }
        log.info("foreach 插入耗时：{}", (System.currentTimeMillis() - start));
    }

    @Test
    public void sqlInsert() {
        log.info("sql 插入开始========");
        long start = System.currentTimeMillis();
        employeeMapper.sqlInsert(list);
        log.info("sql 插入耗时：{}", (System.currentTimeMillis() - start));
    }

    @Test
    public void xmlInsert() {
        log.info("xmlInsert 批量插入开始========");
        long start = System.currentTimeMillis();
        employeeMapper.xmlBatchInsert(list);
        log.info("xmlInsert 批量插入耗时：{}", (System.currentTimeMillis() - start));
    }

    @Test
    public void batchInsert() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        log.info("batchInsert 插入开始========");
        long start = System.currentTimeMillis();
        for (int i = 0; i < list.size(); i++) {
            mapper.insert(list.get(i));
            if (i % 5000 == 4999) {
                sqlSession.flushStatements();
//                sqlSession.commit();
//                sqlSession.clearCache();
            }
        }
//        sqlSession.commit();
//        sqlSession.clearCache();
        sqlSession.flushStatements();
        log.info("SqlSession 批量插入耗时：" + (System.currentTimeMillis() - start));
    }
}
