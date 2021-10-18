package com.qcl.excel.easyexcel;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * excel导出数据内容单元格合并规则
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/8/28
 */
public class ExcelFileCellMergeStrategy implements CellWriteHandler {
    /**
     * 合并列的范围索引
     */
    private int[] mergeColumnIndex;
    /**
     * 合并起始行索引
     */
    private int mergeRowIndex;

    public ExcelFileCellMergeStrategy(int[] mergeColumnIndex, int mergeRowIndex) {
        this.mergeColumnIndex = mergeColumnIndex;
        this.mergeRowIndex = mergeRowIndex;
    }

    public ExcelFileCellMergeStrategy() {
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer integer, Integer integer1, Boolean aBoolean) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> list, Cell cell, Head head, Integer integer, Boolean aBoolean) {
        //当前行
        int curRowIndex = cell.getRowIndex();
        //当前列
        int curColIndex = cell.getColumnIndex();
        if (curRowIndex > mergeRowIndex) {
            for (int columnIndex : mergeColumnIndex) {
                if (curColIndex == columnIndex) {
                    mergeWithPrevRow(writeSheetHolder, cell, curRowIndex, curColIndex);
                    break;
                }
            }
        }
    }

    /**
     * 当前单元格向上合并
     *
     * @param writeSheetHolder
     * @param cell
     * @param curRowIndex
     * @param curColIndex
     */
    private void mergeWithPrevRow(WriteSheetHolder writeSheetHolder, Cell cell, int curRowIndex, int curColIndex) {
        //获取当前行的当前列的数据和上一行的当前列数据，通过上一行数据是否相同进行合并
        int idCol= mergeColumnIndex[0];
        //获取当前行的当前列的数据和上一行的当前列列数据，通过上一行数据是否相同进行合并
        Cell curCell = cell.getSheet().getRow(curRowIndex).getCell(idCol);
        Object curData = curCell.getCellTypeEnum() == CellType.STRING ? curCell.getStringCellValue() :
                curCell.getNumericCellValue();
        Cell preCell = cell.getSheet().getRow(curRowIndex - 1).getCell(idCol);
        Object preData = preCell.getCellTypeEnum() == CellType.STRING ? preCell.getStringCellValue() :
                preCell.getNumericCellValue();

        //比较当前行的第一列的单元格与上一行是否相同，相同合并当前单元格与上一行
        if (curData.equals(preData)) {
            Sheet sheet = writeSheetHolder.getSheet();
            List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
            boolean isMerged = false;
            for (int i = 0; i < mergedRegions.size() && !isMerged; i++) {
                CellRangeAddress cellAddresses = mergedRegions.get(i);
                //若上 一个单元格已经被合并，则先移出原有的合并单元，再重新添加合并单元
                if (cellAddresses.isInRange(curRowIndex - 1, curColIndex)) {
                    sheet.removeMergedRegion(i);
                    cellAddresses.setLastRow(curRowIndex);
                    sheet.addMergedRegion(cellAddresses);
                    isMerged = true;
                }
            }
            //若上一个单元格未被合并，则新增合并单元
            if (!isMerged) {
                CellRangeAddress cellAddresses = new CellRangeAddress(curRowIndex - 1, curRowIndex, curColIndex, curColIndex);
                sheet.addMergedRegion(cellAddresses);
            }
        }
    }
}
