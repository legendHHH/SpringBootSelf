package com.legend.spring.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

/**
 * 导入选择器
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/3
 */
public class MyImportSelector implements ImportSelector {

    /**
     * @param importingClassMetadata 当前标注@Import注解的类的所有注解信息
     * @return 就是要导入到容器中的组件全类名
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Set<String> annotationTypes = importingClassMetadata.getAnnotationTypes();
        //方法不要返回null
        return new String[]{"com.legend.spring.bean.Blue","com.legend.spring.bean.Yellow"};
    }
}
