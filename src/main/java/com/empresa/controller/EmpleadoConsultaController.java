package com.empresa.controller;



import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.empresa.entity.Empleado;
import com.empresa.service.EmpleadoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.apachecommons.CommonsLog;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
@CommonsLog //para error de log (lombok)
public class EmpleadoConsultaController {
	
	//SEMANA 11 --
	@Autowired
	private EmpleadoService service;
	//-------------

	
	@GetMapping("/verConsultaEmpleado")
	public String verInicio() {
		return "consultaEmpleado";
	}
		
	
	//SEMANA 11- CONSULTA
	@GetMapping("/consultaEmpleado")
	@ResponseBody
	public List<Empleado> consulta(int estado, int idPais, 
								   String nomApe, String fecDesde, String fecHasta){
		
		List<Empleado> lstSalida = service.listaConsultaEmpleado(
									estado, 
									idPais, 
									"%"+nomApe+"%", 
									Date.valueOf(fecDesde), 
									Date.valueOf(fecHasta));
		return lstSalida;
	}
	
	//jasper report
	@GetMapping("/reporteEmpleadoPdf")
	@ResponseBody
	public void report(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			//PASO 1: Obtener el dataSource que va generar el reporte
			List<Empleado> lstSalida = service.listaPorNombreApellidoLike("%");
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lstSalida);
			
			//PASO 2: Obtener el archivo que contiene el diseño del reporte
			String fileDirectory = request.getServletContext().getRealPath("/WEB-INF/reportes/reporteEmpleado.jasper");
			log.info(">>> " + fileDirectory);
			FileInputStream stream   = new FileInputStream(new File(fileDirectory));
			
			//PASO 3: Parámetros adicionales
			Map<String,Object> params = new HashMap<String,Object>();
		
			
			//PASO 4: Enviamos dataSource, diseño y parámetros para generar el PDF
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(stream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
			
			//PASO 5: Enviar el PDF generado
			response.setContentType("application/x-pdf");
		    response.addHeader("Content-disposition", "attachment; filename=ReporteEmpleado.pdf");

			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
