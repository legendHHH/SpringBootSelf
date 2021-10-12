package com.qcl.excel.easyexcel;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;
import java.util.Map;

/**
 * EasyExcel自定义合并策略
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/10/12
 */
public class BizMergeStrategy extends AbstractMergeStrategy {

    private Map<String, List<RowRangeDto>> strategyMap;
    private Sheet sheet;

    public BizMergeStrategy(Map<String, List<RowRangeDto>> strategyMap) {
        this.strategyMap = strategyMap;
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer integer) {
        this.sheet = sheet;
        if (cell.getRowIndex() == 1 && cell.getColumnIndex() == 0) {
            /**
             * 保证每个cell被合并一次，如果不加上面的判断，因为是一个cell一个cell操作的，
             * 例如合并A2:A3,当cell为A2时，合并A2,A3，但是当cell为A3时，又是合并A2,A3，
             * 但此时A2,A3已经是合并的单元格了
             */
            for (Map.Entry<String, List<RowRangeDto>> entry : strategyMap.entrySet()) {
                Integer columnIndex = Integer.valueOf(entry.getKey());
                entry.getValue().forEach(rowRange -> {
                    //添加一个合并请求
                    sheet.addMergedRegionUnsafe(new CellRangeAddress(rowRange.getStart(),
                            rowRange.getEnd(), columnIndex, columnIndex));

                    //这个合并策略很慢
                    /*sheet.addMergedRegion(new CellRangeAddress(rowRange.getStart(),
                            rowRange.getEnd(), columnIndex, columnIndex));*/
                });
            }
        }
    }
}

