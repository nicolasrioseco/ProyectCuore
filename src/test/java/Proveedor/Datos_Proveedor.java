package Proveedor;

import java.util.ArrayList;
import utility.ExcelUtils;

public class Datos_Proveedor {
	
	public static ArrayList<String> nombreProv = new ArrayList<String>();
	
	public String setdatosProveedor(int row) throws Exception{
		nombreProv.add((row-3), ExcelUtils.getCellData(row,0));
		String vendor = ExcelUtils.getCellData(row,1);
		String broker = ExcelUtils.getCellData(row,8);
		String bienes = ExcelUtils.getCellData(row,3);
		String servicios = ExcelUtils.getCellData(row,4);
		String importador = ExcelUtils.getCellData(row,7);
		AltaProveedor datos_Proveedor = new AltaProveedor();
		datos_Proveedor.setDatosProveedor(nombreProv.get(row-3), vendor, broker, bienes, servicios, importador);
		String post = datos_Proveedor.getAltaProveedor();
		return post;
		
	}
	
	
}
