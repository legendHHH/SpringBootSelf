//package com.legend.springbootmybatis.datasource2;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.MutablePropertyValues;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.GenericBeanDefinition;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
//import org.springframework.core.env.Environment;
//import org.springframework.core.type.AnnotationMetadata;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 动态数据源注册
// * 启动动态数据源在启动类中添加 @Import(DynamicDataSourceRegister.class)
// *
// * @author legend
// * @version 1.0
// * @description
// * @date 2022/1/8
// */
//public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {
//
//    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegister.class);
//
//    /**
//     * 如配置文件中未指定数据源类型，使用该默认值
//     */
//    private static final Object DATASOURCE_TYPE_DEFAULT = "com.zaxxer.hikari.HikariDataSource";
//
//    /**
//     * 数据源
//     */
//    private DataSource defaultDataSource;
//    private Map<String, DataSource> customDataSources = new HashMap<>();
//
//    @Override
//    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
//        Map<Object, Object> targetDataSources = new HashMap<>(16);
//        //添加默认数据源
//        targetDataSources.put("dataSource", this.defaultDataSource);
//        DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
//        //添加其他数据源
//        targetDataSources.putAll(customDataSources);
//        for (String key : customDataSources.keySet()) {
//            DynamicDataSourceContextHolder.dataSourceIds.add(key);
//        }
//
//        //创建DynamicDataSource
//        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
//        beanDefinition.setBeanClass(DynamicDataSource.class);
//        beanDefinition.setSynthetic(true);
//        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
//        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
//        mpv.addPropertyValue("targetDataSources", targetDataSources);
//        //注册 - BeanDefinitionRegistry
//        beanDefinitionRegistry.registerBeanDefinition("dataSource", beanDefinition);
//
//        logger.info("Dynamic DataSource Registry Success!!!!");
//    }
//
//    /**
//     * 创建DataSource
//     */
//    @SuppressWarnings("unchecked")
//    public DataSource buildDataSource(Map<String, Object> dsMap) {
//        try {
//            Object type = dsMap.get("type");
//            if (type == null) {
//                // 默认DataSource
//                type = DATASOURCE_TYPE_DEFAULT;
//            }
//
//            Class<? extends DataSource> dataSourceType;
//            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
//
//            String driverClassName = dsMap.get("driver-class-name").toString();
//            String url = dsMap.get("url").toString();
//            String username = dsMap.get("username").toString();
//            String password = dsMap.get("password").toString();
//
//            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
//                    .username(username).password(password).type(dataSourceType);
//            return factory.build();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 加载多数据源配置
//     */
//    @Override
//    public void setEnvironment(Environment env) {
//        logger.warn("正在初始化默认数据源/////");
//        initDefaultDataSource(env);
//        logger.warn("正在初始化定制数据源....");
//        initCustomDataSources(env);
//    }
//
//    /**
//     * 初始化主数据源
//     */
//    private void initDefaultDataSource(Environment env) {
//        // 读取主数据源
//        Map<String, Object> dsMap = new HashMap<>();
//        dsMap.put("driver-class-name", env.getProperty("spring.datasource.driver-class-name"));
//        dsMap.put("url", env.getProperty("spring.datasource.url"));
//        dsMap.put("username", env.getProperty("spring.datasource.username"));
//        dsMap.put("password", env.getProperty("spring.datasource.password"));
//        defaultDataSource = buildDataSource(dsMap);
//
//        logger.info("初始化initDefaultDataSource完成");
//    }
//
//    /**
//     * 初始化更多数据源
//     */
//    private void initCustomDataSources(Environment env) {
//        // 读取配置文件获取更多数据源
//        String dsPrefixs = env.getProperty("custom.datasource.names");
//        if (StringUtils.isNotBlank(dsPrefixs)) {
//            for (String dsPrefix : dsPrefixs.split(",")) {
//                // 多个数据源
//                Map<String, Object> dsMap = new HashMap<>();
//                dsMap.put("driver-class-name", env.getProperty("custom.datasource." + dsPrefix + ".driver-class-name"));
//                dsMap.put("url", env.getProperty("custom.datasource." + dsPrefix + ".url"));
//                dsMap.put("username", env.getProperty("custom.datasource." + dsPrefix + ".username"));
//                dsMap.put("password", env.getProperty("custom.datasource." + dsPrefix + ".password"));
//                DataSource ds = buildDataSource(dsMap);
//                customDataSources.put(dsPrefix, ds);
//            }
//        }
//        logger.info("初始化initCustomDataSources完成");
//    }
//
//}