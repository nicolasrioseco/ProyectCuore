package MetodosBienes.Marca;

import utility.ExcelUtils;

public class AsociarMarca {
	
	public String asocMarca;
	
	public String setAsocMarca(String bodySubClase, String bodyMarca, int row) throws Exception {
		
		String nameMarca = ExcelUtils.getCellData(row,7);
		String descriptionMarca = ExcelUtils.getCellData(row,8);
		String nameSubClase = ExcelUtils.getCellData(row,5);
		
		this.asocMarca = ("{" + 
				"\"name\": \"" + nameSubClase + "-" + nameMarca + "\"," + 
				"\"description\": \"" + descriptionMarca + "\"," + 
				"\"parent\": " + bodySubClase + "," + 
				"\"elements\": []," + 
				"\"status\": \"Alta\"," + 
				"\"brand\": " + bodyMarca +  
				"}");
		
		return asocMarca;
	}
	
}
