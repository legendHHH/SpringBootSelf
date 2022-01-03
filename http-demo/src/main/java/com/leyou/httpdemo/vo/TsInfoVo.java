package com.leyou.httpdemo.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Ts信息值对象
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/12/28
 */
@Data
public class TsInfoVo {
    /**
     * 部门总人数
     */
    private Integer totalEmployee = 108;
    /**
     * 缺勤人数
     */
    private Integer absentEmployee = 0;

    /**
     * 缺勤名单
     */
    private List<Map> absentDetailList;
}
