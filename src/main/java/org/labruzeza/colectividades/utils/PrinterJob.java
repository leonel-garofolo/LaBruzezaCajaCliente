package org.labruzeza.colectividades.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrinterJob {
	 private static final Logger logger = LoggerFactory.getLogger(PrinterJob.class);
	public static void sendPDF(JasperPrint print){
		logger.info("imprimio");
		File baseDir = new File(System.getProperty("java.io.tmpdir"));		
		String file = "ticket";
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
		String dateString = formatter.format(date);
		String pathPdf =baseDir.getAbsolutePath()+file+dateString+".pdf";			
		PrintService service = PrintServiceLookup.lookupDefaultPrintService();
		FileInputStream fis;
		try {
			JasperExportManager.exportReportToPdfFile(print,pathPdf);
			fis = new FileInputStream(pathPdf);
			Doc pdfDoc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
			DocPrintJob printJob = service.createPrintJob();		
			printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
			fis.close();
		} catch (PrintException|IOException|JRException e) {
			logger.error("Ops!", e);
			e.printStackTrace();
		}
	}
}
