package com.qcl.datasource.mapper;

import com.qcl.datasource.bean.Area;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/11
 */
@Deprecated
@Mapper
public interface TestMapper {


    /**
     * 获取最顶层的菜单
     *
     * @return
     */
    List<Area> getFirstParentNode();

    /**
     * 获取其他的节点数据根据parentCode
     *
     * @param areaTemp
     * @return
     */
    List<Area> getOtherNode(Area areaTemp);

    /**
     * 获取所有数据
     *
     * @return
     */
    List<Area> selectAll();
}
