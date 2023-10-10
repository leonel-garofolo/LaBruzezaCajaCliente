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
import java.util.Locale;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.labruzeza.colectividades.modelo.Configuracion;
import org.labruzeza.colectividades.modelo.Lineadeventa;
import org.labruzeza.colectividades.modelo.Venta;

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
public class Ticket {
	private static final Logger LOGGER = LoggerFactory.getLogger(Ticket.class);
	BigDecimal itemCost=new BigDecimal(BigInteger.ZERO,  2);
     
	public Ticket(){
		super();
	}
	 
	public JasperPrint generar(Configuracion conf, Venta ventas) {
		VerticalListBuilder vHeader = cmp.verticalList();
		vHeader.add(cmp.text(conf.getTitulo()).setStyle(stl.style().setFont(stl.font().setFontName("Arial")).setFontSize(16).bold()));
		HorizontalListBuilder hTicket = buildTicket(conf, ventas);		
		vHeader.add(hTicket);		
		vHeader.setStyle(stl.style().setLeftPadding(150));
			
		VerticalListBuilder vDetail = cmp.verticalList();
		vDetail.setStyle(stl.style().setBorder(stl.pen2Point()));
		vDetail.setFixedWidth(cm(9.192));	
		HorizontalListBuilder hFrame = null;	
		ReportStyleBuilder totales = stl.style()
				.setHorizontalAlignment(HorizontalAlignment.RIGHT)
				.setRightPadding(5);
		
		double total = 0;		
		for (Lineadeventa linea : ventas.getListOfLineadeventa()) {
			hFrame = cmp.horizontalList();
			hFrame.setStyle(stl.style().setBottomBorder(stl.penDotted()));
			
			hFrame.add(cmp.text(linea.getProducto().getNombre() + ":").setStyle(stl.style().setFont(stl.font().setFontName("Arial")).setFontSize(10).setLeftPadding(5)).setHorizontalAlignment(HorizontalAlignment.LEFT));
			if(linea.getCantidad() > 0){
				hFrame.add(cmp.text(linea.getCantidad()).setStyle(totales).setStyle(stl.style().setLeftPadding(20).setFont(stl.font().setFontName("Arial")).setFontSize(10).bold()).setFixedWidth(cm(1.614)));	
			}else{
				hFrame.add(cmp.text("").setStyle(stl.style().setLeftPadding(20).setFont(stl.font().setFontName("Arial")).setFontSize(10).bold()).setFixedWidth(cm(1.614)));
			}
			if(linea.getCantidad() > 0){
				hFrame.add(cmp.text(linea.getPrecio().doubleValue()).setPattern("#,###.00").setStyle(totales).setStyle(stl.style().setFont(stl.font().setFontName("Arial")).setHorizontalAlignment(HorizontalAlignment.RIGHT).setRightPadding(3).setFontSize(10).bold()).setFixedWidth(cm(1.614)));	
			}else{
				hFrame.add(cmp.text("").setStyle(stl.style().setLeftPadding(20).setFont(stl.font().setFontName("Arial")).setFontSize(10).bold()).setFixedWidth(cm(1.614)));
			}
			
			total += linea.getPrecio().doubleValue();
			vDetail.add(hFrame);
		}
		HorizontalListBuilder hFrameTotal = cmp.horizontalList();		
		hFrameTotal.add(cmp.text("Total: ")
				.setStyle(stl.style().setFont(stl.font().setFontName("Arial"))
						.setHorizontalAlignment(HorizontalAlignment.RIGHT)
						.setRightPadding(5)
						));	
		hFrameTotal.add(cmp.text(total).setPattern("#,###.00").setStyle(stl.style().setFont(stl.font().setFontName("Arial"))
				.setHorizontalAlignment(HorizontalAlignment.RIGHT)
				.setRightPadding(5)
				));	
		vDetail.add(hFrameTotal);
		HorizontalListBuilder vDetailPanel = cmp.horizontalList();
		vDetailPanel.setStyle(stl.style().setLeftPadding(150).setBottomPadding(100).setTopPadding(3));			
		vDetailPanel.add(vDetail);
		
		JasperReportBuilder report = DynamicReports.report();//a new report
		report.setPageFormat(PageType.A4)
		.pageHeader(vHeader)
		.addDetail(vDetailPanel,
				buildFooter(conf, ventas))
		.setDataSource(new JREmptyDataSource());
		try {
	    	JasperPrint print = report.toJasperPrint();
			return print;
			
		} catch (Throwable e) {	
			LOGGER.error("Ops!", e);			
			return null;
		}
	}		
	
	private VerticalListBuilder buildFooter(Configuracion conf, Venta venta){		
		
		VerticalListBuilder vFooter = cmp.verticalList();
		vFooter.setFixedWidth(cm(9.192));
		vFooter.add(cmp.line().setPen(stl.penDashed()));
		vFooter.add(cmp.text(conf.getSubTitulo()).setHorizontalAlignment(HorizontalAlignment.CENTER).setStyle(stl.style().setFont(stl.font().setFontName("Arial").setFontSize(12)).setTopPadding(10).setBold(true)));
		
		HorizontalListBuilder hTicket = buildTicket(conf, venta);		
		vFooter.add(hTicket);
		
		
		vFooter.add(cmp.text(conf.getParrafoDirreccion()).setHorizontalAlignment(HorizontalAlignment.CENTER).setStyle(stl.style().setTopPadding(15).setFont(stl.font().setFontName("Arial").setFontSize(10)).setBold(true)));
		vFooter.add(cmp.text( conf.getParrafoOrganizacion()).setHorizontalAlignment(HorizontalAlignment.CENTER)
				.setStyle(stl.style().setTopPadding(15).setFont(stl.font().setFontName("Arial").setFontSize(9)).setItalic(true)));
		vFooter.add(cmp.text(conf.getParrafoDetalle()).setHorizontalAlignment(HorizontalAlignment.CENTER)
				.setStyle(stl.style().setTopPadding(15).setFont(stl.font().setFontName("Arial").setFontSize(9))));
		vFooter.add(cmp.text(conf.getParrafoPromos()).setHorizontalAlignment(HorizontalAlignment.CENTER).setStyle(stl.style().setTopPadding(15).setFont(stl.font().setFontName("Arial").setFontSize(10)).setBold(true)));
		vFooter.add(cmp.text(conf.getParrafoMensaje()).setHorizontalAlignment(HorizontalAlignment.CENTER).setStyle(stl.style().setTopPadding(15).setFont(stl.font().setFontName("Arial").setFontSize(9)).setBold(true)));
		
		
		VerticalListBuilder vDetailPanel = cmp.verticalList();		
		vDetailPanel.setStyle(stl.style().setLeftPadding(150));
		vDetailPanel.add(vFooter);
		return vDetailPanel;
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
	
	private HorizontalListBuilder buildTicket(Configuracion conf, Venta venta){
		HorizontalListBuilder hTicket = cmp.horizontalList();
		hTicket.setFixedWidth(cm(9.192));	
		HorizontalListBuilder hCabecera = cmp.horizontalList();
		hCabecera.setFixedWidth(cm(4.917));
		hCabecera.setStyle(stl.style().setBorder(stl.pen1Point()));
		hCabecera.add(cmp.text("Serie:").setStyle(stl.style().setLeftPadding(3).setFont(stl.font().setFontName("Arial").setFontSize(12))));
		hCabecera.add(cmp.text(venta.getCodigo() + "-").setStyle(stl.style().setFont(stl.font().setFontName("Arial").setFontSize(11).bold())));
		hCabecera.add(cmp.text(venta.getCodfactura()).setStyle(stl.style().setFont(stl.font().setFontName("Arial").setFontSize(16).bold())));
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM");				
		hTicket.setStyle(stl.style().setTopPadding(10));
		hTicket.add(hCabecera);
		hTicket.add(cmp.text(format.format(conf.getFecha())).setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.RIGHT)));
		return hTicket;				
	}	
}