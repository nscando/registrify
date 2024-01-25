package com.e.registrifyv1.Utiles;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.List;
import java.util.Map;

public class ReporteUtil {

   public static void generarReporte(String reporteJasper,
                                     Map<String, Object> parametros,
                                     List<?> datos,
                                     String nombreArchivo) throws JRException {
      try {
         JasperReport reporte = JasperCompileManager.compileReport(reporteJasper);
         JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, new JRBeanCollectionDataSource(datos));
         JasperExportManager.exportReportToPdfFile(jasperPrint, nombreArchivo);
      } catch (JRException e) {
         throw new RuntimeException("Error al generar reporte", e);
      }
   }
}