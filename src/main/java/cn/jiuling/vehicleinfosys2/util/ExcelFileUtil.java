package cn.jiuling.vehicleinfosys2.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CreationHelper;

import cn.jiuling.vehicleinfosys2.vo.ResultVo;
import cn.jiuling.vehicleinfosys2.vo.SearchCarByImageVo;

public class ExcelFileUtil {

	/**
	 * 生成excel表格
	 * 
	 * @param title sheet名
	 * @param headers sheet属性列名数组
	 * @param dataset 插入的数据
	 * @param out  与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param url  图片目录
	 */
	@SuppressWarnings("deprecation")
	public static void exportExcel(String title, String[] headers,List<ResultVo> dataset, OutputStream out) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook(); 
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		CreationHelper createHelper = workbook.getCreationHelper();
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
				0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("leno");

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		//填充数据
		int index = 0;
		for(ResultVo resultVo:dataset){
			index++;
			row = sheet.createRow(index);
			
			//车牌
			HSSFCell cell_00 = row.createCell(0);
			cell_00.setCellStyle(style2);
			HSSFRichTextString richString = new HSSFRichTextString(resultVo.getLicense());
			cell_00.setCellValue(richString);
			
			//车牌类型 
			HSSFCell cell_01 = row.createCell(1);
			cell_01.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getPlateType());
			cell_01.setCellValue(richString);
			
			//车身颜色 
			HSSFCell cell_02 = row.createCell(2);
			cell_02.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getCarColor());
			cell_02.setCellValue(richString);
			
			//车型 
			HSSFCell cell_03 = row.createCell(3);
			cell_03.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getVehicleKind());
			cell_03.setCellValue(richString);
			
			//品牌 
			HSSFCell cell_04 = row.createCell(4);
			cell_04.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getVehicleBrand());
			cell_04.setCellValue(richString);
			
			//车系
			HSSFCell cell_05 = row.createCell(5);
			cell_05.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getVehicleSeries());
			cell_05.setCellValue(richString);
			
			//款型 
			HSSFCell cell_06 = row.createCell(6);
			cell_06.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getVehicleStyle());
			cell_06.setCellValue(richString);
			
			//车牌归属地
			HSSFCell cell_07 = row.createCell(7);
			cell_07.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getLicenseAttribution());
			cell_07.setCellValue(richString);
			
			//车牌颜色 
			HSSFCell cell_08 = row.createCell(8);
			cell_08.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getPlateColor());
			cell_08.setCellValue(richString);
			
			//年检标
			HSSFCell cell_09 = row.createCell(9);
			cell_09.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getTag()==1?"有":"无");
			cell_09.setCellValue(richString);
			
			//纸巾盒
			HSSFCell cell_10 = row.createCell(10);
			cell_10.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getPaper()==1?"有":"无");
			cell_10.setCellValue(richString);
			
			//遮阳板 
			HSSFCell cell_11 = row.createCell(11);
			cell_11.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getSun()==1?"有":"无");
			cell_11.setCellValue(richString);
			
			//挂饰 
			HSSFCell cell_12 = row.createCell(12);
			cell_12.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getDrop()==1?"有":"无");
			cell_12.setCellValue(richString);
			
			//可信度
			HSSFCell cell_13 = row.createCell(13);
			cell_13.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getConfidence()+"");
			cell_13.setCellValue(richString);
			
			//方向 
			HSSFCell cell_14 = row.createCell(14);
			cell_14.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getDirection());
			cell_14.setCellValue(richString);
			
			//地点 
			HSSFCell cell_15 = row.createCell(15);
			cell_15.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getLocation());
			cell_15.setCellValue(richString);
			
			//过车时间 
			HSSFCell cell_16 = row.createCell(16);
			cell_16.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getResultTime().toString());
			cell_16.setCellValue(richString);
			
			//结果图片
			HSSFCellStyle linkStyle = workbook.createCellStyle();
			HSSFFont cellFont= workbook.createFont();
			cellFont.setUnderline((byte) 1);
			cellFont.setColor(HSSFColor.BLUE.index);
			linkStyle.setFont(cellFont);
			
			HSSFCell cell_17 = row.createCell(17);
			cell_17.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			cell_17.setCellStyle(linkStyle);
			
			
			String imageUrl ="图片"+File.separator+new File(resultVo.getImagePath()).getName();
			cell_17.setCellFormula("HYPERLINK(\"" + imageUrl+ "\",\"" + "图片超链接"+ "\")");		
			
			/*String imageUrl ="原图"+File.separator+new File(resultVo.getImagePath()).getName();
			Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_FILE);
			cell_17.setCellValue("图片超链接");
			link.setAddress(imageUrl); 
			cell_17.setHyperlink(link);
			*/
								
		}
		
		try {
			workbook.write(out);
			out.flush();
			out.close();
			workbook.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}
	
	/**
	 * 生成excel表格(以图搜车)
	 * 
	 * @param title sheet名
	 * @param headers sheet属性列名数组
	 * @param dataset 插入的数据
	 * @param out  与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param url  图片目录
	 */
	@SuppressWarnings("deprecation")
	public static void exportScarExcel(String title, String[] headers,List<SearchCarByImageVo> dataset, OutputStream out,String originalImageDir) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook(); 
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		CreationHelper createHelper = workbook.getCreationHelper();
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
				0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("leno");

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		//填充数据
		int index = 0;
		for(SearchCarByImageVo resultVo:dataset){
			index++;
			row = sheet.createRow(index);
			
			//车牌
			HSSFCell cell_00 = row.createCell(0);
			cell_00.setCellStyle(style2);
			HSSFRichTextString richString = new HSSFRichTextString(resultVo.getLicense());
			cell_00.setCellValue(richString);
			
			//车牌类型 
			HSSFCell cell_01 = row.createCell(1);
			cell_01.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getPlateType());
			cell_01.setCellValue(richString);
			
			//车身颜色 
			HSSFCell cell_02 = row.createCell(2);
			cell_02.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getCarColor());
			cell_02.setCellValue(richString);
			
			//车型 
			HSSFCell cell_03 = row.createCell(3);
			cell_03.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getVehicleKind());
			cell_03.setCellValue(richString);
			
			//品牌 
			HSSFCell cell_04 = row.createCell(4);
			cell_04.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getVehicleBrand());
			cell_04.setCellValue(richString);
			
			//车系
			HSSFCell cell_05 = row.createCell(5);
			cell_05.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getVehicleSeries());
			cell_05.setCellValue(richString);
			
			//款型 
			HSSFCell cell_06 = row.createCell(6);
			cell_06.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getVehicleStyle());
			cell_06.setCellValue(richString);
			
			//车牌归属地
			HSSFCell cell_07 = row.createCell(7);
			cell_07.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getLicenseAttribution());
			cell_07.setCellValue(richString);
			
			//车牌颜色 
			HSSFCell cell_08 = row.createCell(8);
			cell_08.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getPlateColor());
			cell_08.setCellValue(richString);
			
			//年检标
			HSSFCell cell_09 = row.createCell(9);
			cell_09.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getTag()==1?"有":"无");
			cell_09.setCellValue(richString);
			
			//纸巾盒
			HSSFCell cell_10 = row.createCell(10);
			cell_10.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getPaper()==1?"有":"无");
			cell_10.setCellValue(richString);
			
			//遮阳板 
			HSSFCell cell_11 = row.createCell(11);
			cell_11.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getSun()==1?"有":"无");
			cell_11.setCellValue(richString);
			
			//挂饰 
			HSSFCell cell_12 = row.createCell(12);
			cell_12.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getDrop()==1?"有":"无");
			cell_12.setCellValue(richString);
			
			//地点 
			HSSFCell cell_13 = row.createCell(13);
			cell_13.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getLocation());
			cell_13.setCellValue(richString);
			
			//过车时间 
			HSSFCell cell_14 = row.createCell(14);
			cell_14.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getResultTime().toString());
			cell_14.setCellValue(richString);
			
			//可信度
			HSSFCell cell_15 = row.createCell(15);
			cell_15.setCellStyle(style2);
			richString = new HSSFRichTextString(resultVo.getVfmScore()+"");
			cell_15.setCellValue(richString);
			
			//结果图片
			HSSFCellStyle linkStyle = workbook.createCellStyle();
			HSSFFont cellFont= workbook.createFont();
			cellFont.setUnderline((byte) 1);
			cellFont.setColor(HSSFColor.BLUE.index);
			linkStyle.setFont(cellFont);
			
			HSSFCell cell_16 = row.createCell(16);
			cell_16.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			cell_16.setCellStyle(linkStyle);
			
			
			String originalImageUrl ="原图"+File.separator+new File(originalImageDir).getName();
			cell_16.setCellFormula("HYPERLINK(\"" + originalImageUrl+ "\",\"" + "原图超链接"+ "\")");
			
			HSSFCell cell_17 = row.createCell(17);
			cell_17.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			cell_17.setCellStyle(linkStyle);
			
			
			String imageUrl ="搜车结果图片"+File.separator+new File(resultVo.getImagePath()).getName();
			cell_17.setCellFormula("HYPERLINK(\"" + imageUrl+ "\",\"" + "结果图片超链接"+ "\")");
		}
		
		try {
			workbook.write(out);
			out.flush();
			out.close();
			workbook.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}
}
