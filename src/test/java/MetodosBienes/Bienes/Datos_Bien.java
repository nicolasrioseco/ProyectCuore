package MetodosBienes.Bienes;

import java.util.ArrayList;

import utility.ExcelUtils;

public class Datos_Bien {
	
	public static ArrayList<String> nameAtributo = new ArrayList<String>();
	public static ArrayList<String> valorVersion = new ArrayList<String>();
	public static ArrayList<String> nameClase = new ArrayList<String>();
	public static ArrayList<String> nameSubClase = new ArrayList<String>();
	public static ArrayList<String> nameMarca = new ArrayList<String>();
	public static ArrayList<String> nameModelo = new ArrayList<String>();
	
	public String datosBien(String idClase, String idSubClase, String idAsocMarca, String idModelo, String tipificacion, String idTipificacion,
							String patentable, String idPatentable, String semaforo, String idSemaforo, String iva, String idIva, String tipoSeguro, 
							String idTipo, String claseSeguro, String idClasSeg, String bodyAtributo, String idAtributo, int row) throws Exception {
		
		int i = row-3;
		nameAtributo.add(i, ExcelUtils.getCellData(row,0));
		valorVersion.add(i, ExcelUtils.getCellData(row,2));
		nameClase.add(i, ExcelUtils.getCellData(row,3));
		String descriptionClase = ExcelUtils.getCellData(row,4);
		nameSubClase.add(i, ExcelUtils.getCellData(row,5));
		String descriptionSubClase = ExcelUtils.getCellData(row,6);
		nameMarca.add(i, ExcelUtils.getCellData(row,7));
		String descriptionMarca = ExcelUtils.getCellData(row,8);
		nameModelo.add(i, ExcelUtils.getCellData(row,9));
		String descriptionModelo = ExcelUtils.getCellData(row,10);
		String valorTipo = ExcelUtils.getCellData(row,17);
		String valorTipoSeg = ExcelUtils.getCellData(row,21);
		String valorClasSeg= ExcelUtils.getCellData(row,23);
		
		Alta_Bien altaBien = new Alta_Bien();
		altaBien.setaltaClase(idClase, nameClase.get(i), descriptionClase, idSubClase, nameSubClase.get(i), descriptionSubClase,
				idAsocMarca, nameMarca.get(i), descriptionMarca, idModelo, nameModelo.get(i), descriptionModelo,
				nameAtributo.get(i), valorVersion.get(i), tipificacion, idTipificacion, patentable, idPatentable, 
				semaforo, idSemaforo, iva, idIva, tipoSeguro, idTipo, claseSeguro, 
				idClasSeg, bodyAtributo, idAtributo, valorTipo, valorTipoSeg, valorClasSeg);
		
		String post = altaBien.getaltaClase();
		return post;
	}

}
