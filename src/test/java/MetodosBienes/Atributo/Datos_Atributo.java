package MetodosBienes.Atributo;

import java.util.ArrayList;

import utility.ExcelUtils;

public class Datos_Atributo {

	public static ArrayList<String> nombreAtr = new ArrayList<String>();
	public static String descripcion;


	public String setdatos_atributo(int row) throws Exception{
		
		nombreAtr.add(row-3, ExcelUtils.getCellData(row,0));
		descripcion = ExcelUtils.getCellData(row,1);

		Atributo atributo = new Atributo();
		atributo.setaltaAtributo(nombreAtr.get(row-3), descripcion);
		String post = atributo.getaltaAtributo();		


		return post;

	}

}
