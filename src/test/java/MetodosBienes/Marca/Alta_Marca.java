package MetodosBienes.Marca;

import utility.ExcelUtils;

public class Alta_Marca {
	
	public static String name;
	public static String description;
	
	
	public String setaltaMarca(int row) throws Exception {
		
		name = ExcelUtils.getCellData(row,7);
		description = ExcelUtils.getCellData(row,8);
		
		String altaMarca = ("{" + 
				"\"description\": \"" + description +"\"," + 
				"\"name\": \"" + name + "\"" + 
				"}");
		
		return altaMarca;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getName() {
		return name;
	}

}