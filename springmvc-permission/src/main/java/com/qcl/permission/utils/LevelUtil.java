package com.qcl.permission.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 层级工具类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/26
 */
public class LevelUtil {

    /**
     * 层级之间的分隔符
     */
    public final static String SEPARATOR = ".";

    /**
     * root的id
     */
    public final static String ROOT = "0";

    /**
     * 部门层级计算规则
     *
     * @param parentLevel
     * @param parentId
     * @return
     */
    public static String calculateLevel(String parentLevel, int parentId) {
        //首层
        // 0
        if (StringUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            // 0.1
            // 0.1.2
            // 0.1.3
            // 0.4
            return StringUtils.join(parentLevel, SEPARATOR, parentId);
        }
    }
}
