package com.qcl.datasource.mapper.test01;

import com.qcl.datasource.bean.Area;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2020/9/11
 */
@Mapper
public interface Test1Mapper {


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
