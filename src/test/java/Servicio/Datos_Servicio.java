package Servicio;

import java.util.ArrayList;

import utility.ExcelUtils;

public class Datos_Servicio {
	
		public static ArrayList<String> nombreServicio = new ArrayList<String>();
	
	public String setdatosServicio(int row, String bodyBien, String bodyAcuerdo) throws Exception{
		int i = row-3;
		nombreServicio.add(i, ExcelUtils.getCellData(row,0));
		int dador = (int) ExcelUtils.getCellDataint(row,17);
		int facturado = (int) ExcelUtils.getCellDataint(row,18);
		
		Alta_Servicio clase = new Alta_Servicio();
		clase.setaltaServicio(nombreServicio.get(i), dador, facturado, bodyBien, bodyAcuerdo);
		String post = clase.getaltaServicio();
		
		return post;
	}
}