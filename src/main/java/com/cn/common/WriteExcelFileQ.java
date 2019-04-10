package com.cn.common;


import jxl.write.WritableCellFormat;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 处理excel---卡券系统
 * Created by cuixiaowei on 2015/12/22.
 */
public class WriteExcelFileQ {
    private static WritableCellFormat dateFormat;
    private static WritableCellFormat menuTitleFormat;
    private static WritableCellFormat titleFormat;

    public static void doRspExcel(HttpServletResponse response, String fileName)
            throws UnsupportedEncodingException {
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Type", "text/html;charset=UTF-8");

        response.setHeader("content-disposition", "attachment;filename="
                + URLEncoder.encode(fileName, "UTF-8"));

        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(fileName);
            int lenExcel = 0;
            byte[] buffer = new byte[1024];
            out = response.getOutputStream();
            while ((lenExcel = in.read(buffer)) > 0) {
                out.write(buffer, 0, lenExcel);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }






    public static void createCell(Workbook wb, Row row, int column,
                                  String value, CellStyle cellStyle) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }
    public static Font createFonts(Workbook wb, short bold, String fontName,
                                   boolean isItalic, short hight) {
        Font font = wb.createFont();
        font.setFontName(fontName);
        font.setBoldweight(bold);
        font.setItalic(isItalic);
        font.setFontHeight(hight);
        return font;
    }

}
