package com.test.poi.extract;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.test.poi.model.User;

public class UserExcelExporter {

	//@Autowired
	 private XSSFWorkbook workbook;
	 
	 private XSSFSheet sheet;
	 
	 private List<User> user;

	public UserExcelExporter(List<User> user) {
		super();
		this.user = user;
		workbook = new XSSFWorkbook();
	}
	 
	 
	private void writeHeaderLine() {
		sheet = workbook.createSheet("Users");
        
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "User ID", style);  
        createCell(row, 1, "Name", style);
        createCell(row, 2, "E-mail", style);       
	}
	
	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
	}
	
	private void writeDataLines() {
		int rowCount = 1;
		 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 
        for (User user : user) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, user.getId(), style);
            createCell(row, columnCount++, user.getName(), style);
            createCell(row, columnCount++, user.getEmail(), style);
            
			/*
			 * createCell(row, columnCount++, user.getRoles().toString(), style);
			 * createCell(row, columnCount++, user.isEnabled(), style);
			 */
             
        }
	}
	
	public void export(HttpServletResponse httpResponse) throws IOException {
		writeHeaderLine();
        writeDataLines();
         
        ServletOutputStream outputStream = httpResponse.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
	}
}
