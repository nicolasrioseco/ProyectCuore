package Proveedor;

import java.util.ArrayList;

import utility.ExcelUtils;

public class Datos_AcuerdoServicio {
	
	public static ArrayList<String> nombreAcuerdo = new ArrayList<String>();
	public static double costo;
	public static double markup;
	public static String observaciones;

	public String setDatosAcuerdo(int row, String Defoult_URL, String token, String bodyBien, String bodyProveedor) throws Exception {
		int i = row-3;
		nombreAcuerdo.add(i, ExcelUtils.getCellData(row,0));
		costo = ExcelUtils.getCellDataint(row,1);
		markup = ExcelUtils.getCellDataint(row,2);
		observaciones = ExcelUtils.getCellData(row,3);
		
		Alta_AcuerdoServicio acuerdo = new Alta_AcuerdoServicio();
		String post = acuerdo.setAcuerdoServicio(nombreAcuerdo.get(i), bodyBien, bodyProveedor, costo, markup, observaciones);
		
		return post;
		
	}

}
