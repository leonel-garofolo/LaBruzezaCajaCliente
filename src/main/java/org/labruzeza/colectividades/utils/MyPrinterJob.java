package org.labruzeza.colectividades.utils;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.print.PrintServiceLookup;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyPrinterJob {
	private static final Logger LOGGER = LoggerFactory.getLogger(MyPrinterJob.class);
	private static SimpleDateFormat formatter; 
	private static File baseDir;
	private static String file;
	private static PrinterJob job;
	
	public static  void preparedPrinter(){
		file = "ticket";		
		formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
		baseDir = new File(System.getProperty("java.io.tmpdir"));
		job = PrinterJob.getPrinterJob();
		try {
			job.setPrintService(PrintServiceLookup.lookupDefaultPrintService());
		} catch (PrinterException e) {
			LOGGER.error("Can't set printer", e);
		}
	}
	
	public static void sendPDF(JasperPrint print){
		LOGGER.info("imprimio");
				
		Date date = new Date(System.currentTimeMillis());
		String dateString = formatter.format(date);
		String pathPdf =baseDir.getAbsolutePath()+file+dateString+".pdf";			
		
		try {
			JasperExportManager.exportReportToPdfFile(print,pathPdf);		
			PDDocument document = PDDocument.load(new File(pathPdf));			
	        job.setPageable(new PDFPageable(document));	        
	        job.print();
	        document.close();
		} catch (IOException|JRException | PrinterException e) {
			LOGGER.error("Ops!", e);
			e.printStackTrace();
		}
	}
}
