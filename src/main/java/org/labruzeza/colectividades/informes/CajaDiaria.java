package org.labruzeza.colectividades.informes;


import static net.sf.dynamicreports.report.builder.DynamicReports.cm;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.labruzeza.colectividades.modelo.Vcaja;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.style.ReportStyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.DefaultFormatFactory;

/**
 * @author Ricardo Mariaca (dynamicreports@gmail.com)
 */
public class CajaDiaria {
	private static final Logger logger = LogManager.getLogger(CajaDiaria.class);
	BigDecimal itemCost=new BigDecimal(BigInteger.ZERO,  2);
     
	public CajaDiaria(){
		super();
	}
	 
	public JasperPrint generar(List<Vcaja> caja, int cantVentas) {
		SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yy");
		VerticalListBuilder vHeader = cmp.verticalList();
		vHeader.add(cmp.text("Totales Diarios - Caja \"" + caja.get(1).getCodigo() + "\" " + formatDate.format(new Date())).setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER).setFont(stl.font().setFontName("Arial")).setFontSize(16).bold()));
			
		VerticalListBuilder vDetail = cmp.verticalList();
		vDetail.setFixedWidth(cm(9.192));	
		HorizontalListBuilder hFrame = null;	
		ReportStyleBuilder totales = stl.style()
				.setHorizontalAlignment(HorizontalAlignment.RIGHT)
				.setRightPadding(5);
		
		
		double total = 0;
		for(Vcaja lineaCaja:caja){
		
				hFrame = cmp.horizontalList();
				hFrame.setStyle(stl.style().setBorder(stl.pen()).setTopPadding(20));
				
				hFrame.add(cmp.text(lineaCaja.getNombre() + ":").setStyle(stl.style().setFont(stl.font().setFontName("Arial")).setFontSize(10)).setHorizontalAlignment(HorizontalAlignment.RIGHT));
				hFrame.add(cmp.text(lineaCaja.getCantidad()).setPattern("#,###").setStyle(totales).setStyle(stl.style().setLeftPadding(20).setFont(stl.font().setFontName("Arial")).setFontSize(10).bold()).setFixedWidth(cm(1.614)));
				hFrame.add(cmp.text(lineaCaja.getSubtotal().doubleValue()).setPattern("#,###.00").setStyle(totales).setStyle(stl.style().setFont(stl.font().setFontName("Arial")).setHorizontalAlignment(HorizontalAlignment.RIGHT).setRightPadding(3).setFontSize(10).bold()).setFixedWidth(cm(1.614)));
				total += lineaCaja.getSubtotal().doubleValue();
				vDetail.add(hFrame);			
		}
		
		VerticalListBuilder vTotales = cmp.verticalList();
		vTotales.setStyle(stl.style().setTopBorder(stl.pen()).setTopPadding(30));		
		HorizontalListBuilder hFrameTotal = cmp.horizontalList();		
		hFrameTotal.add(cmp.text("Total: ")
				.setStyle(stl.style().setFont(stl.font().setFontName("Arial")).setFontSize(10).bold()
						.setHorizontalAlignment(HorizontalAlignment.RIGHT)
						.setRightPadding(5)
						));	
		hFrameTotal.add(cmp.text(total).setPattern("#,###.00").setStyle(stl.style().setFont(stl.font().setFontName("Arial")).setFontSize(10)
				.setHorizontalAlignment(HorizontalAlignment.RIGHT)
				.setRightPadding(5)
				));	
		vTotales.add(hFrameTotal);
		HorizontalListBuilder hFrameCantidadTotal = cmp.horizontalList();		
		hFrameCantidadTotal.add(cmp.text("Cantidad Ticket: ")
				.setStyle(stl.style().setFont(stl.font().setFontName("Arial")).bold()
						.setHorizontalAlignment(HorizontalAlignment.RIGHT).setFontSize(10)
						.setRightPadding(5)
						));	
		hFrameCantidadTotal.add(cmp.text(cantVentas).setPattern("#,###").setStyle(stl.style().setFont(stl.font().setFontName("Arial")).setFontSize(10)
				.setHorizontalAlignment(HorizontalAlignment.RIGHT)
				.setRightPadding(5)
				));	
		vTotales.add(hFrameCantidadTotal);		
		vDetail.add(vTotales);
		
		HorizontalListBuilder vDetailPanel = cmp.horizontalList();
		vDetailPanel.setStyle(stl.style().setLeftPadding(130).setBottomPadding(3).setTopPadding(3));			
		vDetailPanel.add(vDetail);
		
		JasperReportBuilder report = DynamicReports.report();//a new report
		report.setPageFormat(PageType.A4)
		.pageHeader(vHeader)
		.addDetail(vDetailPanel)
		.setDataSource(new JREmptyDataSource());
		try {
	    	JasperPrint print = report.toJasperPrint();
			return print;
			
		} catch (Throwable e) {	
			logger.error("Ops!", e);			
			return null;
		}
	}		
		
	@SuppressWarnings("unused")
	private class FormatFactory extends DefaultFormatFactory {
		  public NumberFormat createNumberFormat(String pattern, Locale locale) {
		    DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
		    symbols.setNaN("NaN");
		    symbols.setInfinity("Infinity");
		    NumberFormat nf = super.createNumberFormat(pattern, locale);
		    if (nf instanceof DecimalFormat) {
		      ((DecimalFormat) nf).setDecimalFormatSymbols(symbols);
		    }
		    return nf;
		  }
		}
}