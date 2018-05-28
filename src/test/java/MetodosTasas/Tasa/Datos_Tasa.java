package MetodosTasas.Tasa;

import java.util.ArrayList;
import utility.ExcelUtils;

public class Datos_Tasa {
	
	public static ArrayList<String> moneda = new ArrayList<String>();
	public static String tipo;
	public static ArrayList<String> nameSubT = new ArrayList<String>();
	public static String multiplicador;  //Si es Fija tiene que ser null
	public static String tope;  //Si es Fija tiene que ser null
	public static ArrayList<Integer> idTipo = new ArrayList<Integer>();
	
	
	public String setTasa(int row) throws Exception{
		
		int i= row-3;
		moneda.add(i, ExcelUtils.getCellData(row,1));
		tipo = ExcelUtils.getCellData(row,0);
		idTipo.add(i, (int) ExcelUtils.getCellDataint(row,27));
		nameSubT.add(i, ExcelUtils.getCellData(row,2));
		multiplicador = ExcelUtils.getCellData(row,3);
		tope = ExcelUtils.getCellData(row,4);
		
		Alta_Tasa tasa = new Alta_Tasa();
		tasa.setAltaT(moneda.get(i), tipo, idTipo.get(i), nameSubT.get(i), multiplicador, tope);
		String post = tasa.getAltaT();
	
		return post;
	}
	
	public String getTipo() {
		return tipo;
	}

}
