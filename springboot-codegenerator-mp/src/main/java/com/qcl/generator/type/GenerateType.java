package com.qcl.generator.type;

/**
 * 生成策略
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/5/14
 */
public enum GenerateType {
    /**
     * 覆盖
     */
    OVERRIDE,
    /**
     * 新增
     */
    ADD,
    /**
     * 存在则忽略
     */
    IGNORE,
    ;


    public boolean eq(String val) {
        if (this.name().equals(val)) {
            return true;
        }
        return false;
    }

    public boolean eq(GenerateType val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    public boolean neq(GenerateType val) {
        return !eq(val);
    }

}