package MetodosBienes.Modelo;

import utility.ExcelUtils;

public class Datos_Modelo {

	
	public String setAltaModelo(String bodyAsocMarca, int row) throws Exception {
		
		String name = ExcelUtils.getCellData(row,9);
		String description = ExcelUtils.getCellData(row,10);
		
		Alta_Modelo altaModelo = new Alta_Modelo();
		altaModelo.setModelo(name, description, bodyAsocMarca);
		String post = altaModelo.getModelo();
		
		return post;
	}
	
}
