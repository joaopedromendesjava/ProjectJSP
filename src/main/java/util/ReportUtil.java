package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ReportUtil implements Serializable{
	
	private static final long serialVersionUID = 1L;
	

	public byte[] geraRelatorioExcel(List listaDados, String nomeRelatorio,HashMap<String, Object> params, ServletContext servletContext) throws Exception {
			
	
		// Cria a lista de dados que vem do nosso SQL da consulta feita
		JRBeanCollectionDataSource JRBCdataSource = new JRBeanCollectionDataSource(listaDados);
		
		String caminhoJasper = servletContext.getRealPath("relatorios") + File.separator + nomeRelatorio + ".jasper";
		
		
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, params, JRBCdataSource);
		
		JRExporter exporter = new JRXlsExporter(); // excel
		
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		
		exporter.exportReport();
		
		return baos.toByteArray();
	
	
	}
	
	

	public byte[] geraRelatorioPDF(List listaDados, String nomeRelatorio, ServletContext servletContext) throws Exception {
		
		
		
		// Cria a lista de dados que vem do nosso SQL da consulta feita
		JRBeanCollectionDataSource JRBCdataSource = new JRBeanCollectionDataSource(listaDados);
		
		String caminhoJasper = servletContext.getRealPath("relatorios") + File.separator + nomeRelatorio + ".jasper";
		
		
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, new HashMap(), JRBCdataSource);
		
		return JasperExportManager.exportReportToPdf(impressoraJasper);
		
	}

	public byte[] geraRelatorioPDF(List listaDados, String nomeRelatorio,HashMap<String, Object> params, ServletContext servletContext) throws Exception {
				
		
		// Cria a lista de dados que vem do nosso SQL da consulta feita
		JRBeanCollectionDataSource JRBCdataSource = new JRBeanCollectionDataSource(listaDados);
		
		String caminhoJasper = servletContext.getRealPath("relatorios") + File.separator + nomeRelatorio + ".jasper";
		
		
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, params, JRBCdataSource);
		
		return JasperExportManager.exportReportToPdf(impressoraJasper);
		
		
	}
	
	

}
