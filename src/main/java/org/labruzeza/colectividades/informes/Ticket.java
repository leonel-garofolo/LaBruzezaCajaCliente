package org.labruzeza.colectividades.informes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.labruzeza.colectividades.modelo.Lineadeventa;
import org.labruzeza.colectividades.modelo.Producto;
import org.labruzeza.colectividades.modelo.Venta;
import org.labruzeza.colectividades.utils.PrinterJob;

import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.style.ReportStyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * @author Ricardo Mariaca (dynamicreports@gmail.com)
 */
public class Ticket extends DynamicReports {

	BigDecimal itemCost=new BigDecimal(BigInteger.ZERO,  2);
     
	 
	public JasperPrint generar(Venta ventas) {
		VerticalListBuilder vHeader = cmp.verticalList();
		vHeader.add(cmp.text("SEI ITALIA"));
		HorizontalListBuilder hTicket = buildTicket();		
		vHeader.add(hTicket);
		vHeader.setStyle(stl.style().setLeftPadding(150));
			
		VerticalListBuilder vDetail = cmp.verticalList();
		vDetail.setStyle(stl.style().setBorder(stl.pen2Point()));
		vDetail.setFixedWidth(cm(9.192));	
		HorizontalListBuilder hFrame = null;	
		ReportStyleBuilder totales = stl.style()
				.setHorizontalAlignment(HorizontalAlignment.RIGHT)
				.setRightPadding(5);
		BigDecimal total=new BigDecimal(BigInteger.ZERO,  2);
		for (Lineadeventa linea : ventas.getListOfLineadeventa()) {
			hFrame = cmp.horizontalList();
			hFrame.setStyle(stl.style().setBottomBorder(stl.penDotted()));
			
			hFrame.add(cmp.text(linea.getProducto().getNombre()).setFixedWidth(cm(4.497)));
			hFrame.add(cmp.text(linea.getCantidad()).setStyle(totales).setFixedWidth(cm(1.614)));
			hFrame.add(cmp.text(linea.getPrecio()).setStyle(totales).setFixedWidth(cm(1.614)));
			total =total.add(calculateCost(linea.getCantidad(), linea.getPrecio()));
			vDetail.add(hFrame);
		}
		
		HorizontalListBuilder hFrameTotal = cmp.horizontalList();		
		hFrameTotal.add(cmp.text("Total: " + total)
				.setStyle(stl.style()
						.setHorizontalAlignment(HorizontalAlignment.RIGHT)
						.setRightPadding(5)
						));	
		vDetail.add(hFrameTotal);
		HorizontalListBuilder vDetailPanel = cmp.horizontalList();
		vDetailPanel.setStyle(stl.style().setLeftPadding(150).setBottomPadding(3).setTopPadding(3));			
		vDetailPanel.add(vDetail);
		try {
			return report().setPageFormat(PageType.A4)
					.pageHeader(vHeader)
					.addDetail(vDetailPanel,
							buildFooter())
					.setDataSource(new JREmptyDataSource()).toJasperPrint();
		} catch (DRException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	 private BigDecimal calculateCost(int itemQuantity,BigDecimal itemPrice)
     { 
         return itemPrice.multiply(new BigDecimal(itemQuantity));           
     }
	
	private VerticalListBuilder buildFooter(){		
		
		VerticalListBuilder vFooter = cmp.verticalList();
		vFooter.setFixedWidth(cm(9.192));
		vFooter.add(cmp.line().setPen(stl.penDashed()));
		
		HorizontalListBuilder hTicket = buildTicket();		
		vFooter.add(hTicket);
		
		String sFila1 = "SEI ITALIA agradece su presencia en nuestro predio.\nEste Stand está formado por las siguientes Asoc.\namigas:";
		String sFila2 = "";
		sFila2 += "ASOC. FAMILIA ABRUZZESA\n";
		sFila2 += "ASOC FAMILIA BASILICATA\n";
		sFila2 += "ASOC. FAMILIA MOLISANA\n";
		sFila2 += "CENTRO LIGURE\n";
		sFila2 += "CIRCULO SARDO\n";

		String sFila3 = "";
		sFila3 += "Quienes,  rescatando los valores de solidaridad y de esfuerzo\n";
		sFila3 += "común, se han unido para poder brindarles un poco de\n";
		sFila3 += "nuestras tradiciones ITALIANAS.\n";

		String sFila4 = "";
		sFila4 += "PROMOS:\n";
		sFila4 += "PROMO 1: 1/2 PIZZA MOZZARELLA + GASEOSA O AGUA\n";
		sFila4 += "PROMO 2: PIZZA ENTERA MOZZARELLA + CERVEZA 1 LT\n";
		sFila4 += "PROMO 3: PANINO PORCHETTA + GASEOSA O AGUA\n";

		String sFila5 = "";
		sFila5 += "Ayudemos a mantener limpio nuestro Parque\n";
		sFila5 += "Nacional a la Bandera.\n";
		sFila5 += "Los niños toman nuestro ejemplo\n";

		vFooter.add(cmp.text(sFila1).setHorizontalAlignment(HorizontalAlignment.CENTER).setStyle(stl.style().setFont(stl.font().setFontSize(9).setBold(true))));
		vFooter.add(cmp.text(sFila2).setHorizontalAlignment(HorizontalAlignment.CENTER)
				.setStyle(stl.style().setFont(stl.font().setFontSize(8)).setItalic(true)));
		vFooter.add(cmp.text(sFila3).setHorizontalAlignment(HorizontalAlignment.CENTER).setStyle(stl.style().setFont(stl.font().setFontName("Arial").setFontSize(8))));
		vFooter.add(cmp.text(sFila4).setHorizontalAlignment(HorizontalAlignment.CENTER)
				.setStyle(stl.style().setFont(stl.font().setFontSize(8)).setBold(true)));
		vFooter.add(cmp.text(sFila5).setHorizontalAlignment(HorizontalAlignment.CENTER)
				.setStyle(stl.style().setFont(stl.font().setFontSize(9)).setBold(true)));
		
		VerticalListBuilder vDetailPanel = cmp.verticalList();		
		vDetailPanel.setStyle(stl.style().setLeftPadding(150));
		vDetailPanel.add(vFooter);
		return vDetailPanel;
	}
	
	private HorizontalListBuilder buildTicket(){
		HorizontalListBuilder hTicket = cmp.horizontalList();
		HorizontalListBuilder hCabecera = cmp.horizontalList();
		hCabecera.setFixedWidth(cm(4.917));
		hCabecera.setStyle(stl.style().setBorder(stl.pen1Point()));
		hCabecera.add(cmp.text("Serie:"));
		hCabecera.add(cmp.text("061A-"));
		hCabecera.add(cmp.text("2").setStyle(stl.style().setFontSize(12)));
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM");				
		hTicket.setStyle(stl.style().setTopPadding(10));
		hTicket.add(hCabecera);
		hTicket.add(cmp.text(format.format(new Date())).setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.RIGHT)));
		return hTicket;				
	}

	public static void main(String[] args) {
		Ticket ticket = new Ticket();

		Producto unProd = new Producto();
		unProd.setIdproducto(1);
		unProd.setNombre("Test informe");
		unProd.setPrecio(new BigDecimal("13.5"));

		Venta unVenta = new Venta();
		unVenta.setIdventa(1);
		unVenta.setFecha(new Date());
		unVenta.setCodigo("24A1");

		List<Lineadeventa> lineas = new ArrayList<Lineadeventa>();
		Lineadeventa linea = new Lineadeventa();
		linea.setIdlineadeventa(1);
		linea.setIdproducto(1);
		linea.setProducto(unProd);
		linea.setCantidad(1);
		linea.setPrecio(new BigDecimal("13.5"));
		lineas.add(linea);

		linea = new Lineadeventa();
		linea.setIdlineadeventa(1);
		linea.setIdproducto(1);
		linea.setProducto(unProd);
		linea.setCantidad(2);
		linea.setPrecio(new BigDecimal("14.5"));
		lineas.add(linea);

		linea = new Lineadeventa();
		linea.setIdlineadeventa(1);
		linea.setIdproducto(1);
		linea.setProducto(unProd);
		linea.setCantidad(3);
		linea.setPrecio(new BigDecimal("15.5"));
		lineas.add(linea);
		
		linea = new Lineadeventa();
		linea.setIdlineadeventa(1);
		linea.setIdproducto(1);
		linea.setProducto(unProd);
		linea.setCantidad(3);
		linea.setPrecio(new BigDecimal("115.5"));
		lineas.add(linea);

		unVenta.setListOfLineadeventa(lineas);
		JasperPrint print = ticket.generar(unVenta);
		PrinterJob.sendPDF(print);
	}
}
