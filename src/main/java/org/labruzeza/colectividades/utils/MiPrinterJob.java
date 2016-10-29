package org.labruzeza.colectividades.utils;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

public class MiPrinterJob {
	 private static final Logger logger = LogManager.getLogger(MiPrinterJob.class);
	public static void sendPDF(JasperPrint print){
		logger.info("imprimio");
		File baseDir = new File(System.getProperty("java.io.tmpdir"));		
		String file = "ticket";
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
		String dateString = formatter.format(date);
		String pathPdf =baseDir.getAbsolutePath()+file+dateString+".pdf";			
		PrintService service = PrintServiceLookup.lookupDefaultPrintService();
		
		try {
			JasperExportManager.exportReportToPdfFile(print,pathPdf);
		
			PDDocument document = PDDocument.load(new File(pathPdf));
			PrinterJob job = PrinterJob.getPrinterJob();
	        job.setPageable(new PDFPageable(document));
	        job.setPrintService(service);
	        job.print();
	        document.close();
		} catch (IOException|JRException | PrinterException e) {
			logger.error("Ops!", e);
			e.printStackTrace();
		}
	}
}
