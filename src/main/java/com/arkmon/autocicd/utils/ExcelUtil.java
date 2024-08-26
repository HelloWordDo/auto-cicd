package com.arkmon.autocicd.utils;

import Exceptions.MAException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import utils.JsonUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelUtil {
    /**
     * 无模块导出Excel方法，
     * 参数data格式: [
     *      {
     *          "姓名": "张三";
     *          "年龄": "23";
     *          "性别": "男"
     *      },
     *      {
     *          "姓名": "李四";
     *          "年龄": "24";
     *          "性别": "男"
     *      }
     * ]
     *
     * @param data 需要导出的数据
     * @param fileName 导出后保存到本地的文件名
     * @return 创建好的Excel文件，用于前端下载
     * @throws MAException 自定义RunTimeException子类异常
     */
    public static HSSFWorkbook exportExcel(List<Map<String, Object>> data, String fileName) throws MAException {
        // 从参数data中解析出打印的每列标题，放入title中
        List<String> title = Lists.newArrayList();
        for(Map.Entry<String, Object> entry : data.get(0).entrySet()) {
            title.add(entry.getKey());
        }
        // 新建一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // Excel中的sheet
        HSSFSheet sheet = wb.createSheet();
        // sheet中的行，0表示第一行
        HSSFRow row = sheet.createRow(0);
        // 设置标题居中
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // sheet中的单元格
        HSSFCell cell = null;

        // 给第一行赋值，值为我们从参数中解析出的标题，因此需要我们在传参的时候需要严格按照约定
        for(int i = 0; i < title.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(title.get(i));
            cell.setCellStyle(cellStyle);
        }

        // 根据参数内容数量，创建表格行数
        for(int i = 0; i < data.size(); i++) {
            row = sheet.createRow(i + 1);

            Map<String, Object> values = data.get(i);

            // 将参数插入每一个单元格
            for(int k = 0; k < title.size(); k++) {
                Object value = values.get(title.get(k));
                if(null == value) {
                    value = "";
                }
                String val = JsonUtil.jsonObj2Str(value);
                row.createCell(k).setCellValue(val);
            }
        }

        // 将文件写到硬盘中，将来根据需求，或写到服务器中，因此在实际开发中，最好将"E:/Temp/"配置在.properties配置文件中，仪表项目上线事更换方便
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("" + fileName));
            wb.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            log.error("{}", e);
            throw new MAException("文件导出失败");
        }
        return wb;
    }
}
