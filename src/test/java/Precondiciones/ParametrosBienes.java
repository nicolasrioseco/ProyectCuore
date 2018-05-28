package Precondiciones;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.Arrays;
import MetodosBienes.Atributo.Datos_Atributo;
import MetodosBienes.Bienes.Datos_Bien;
import MetodosBienes.Clase.Datos_Clase;
import MetodosBienes.JBPMaprobar.CompleteAnalizarBienPorActivos;
import MetodosBienes.JBPMaprobar.CompleteAnalizarBienPorSeguros;
import MetodosBienes.JBPMaprobar.CompleteAnalizarViabilidadDelBien;
import MetodosBienes.JBPMaprobar.TareaAnalizarBienPorActivos;
import MetodosBienes.JBPMaprobar.TareaAnalizarBienPorSeguros;
import MetodosBienes.JBPMaprobar.TareaAnalizarViabilidadDelBien;
import MetodosBienes.Marca.Alta_Marca;
import MetodosBienes.Marca.AsociarMarca;
import MetodosBienes.Modelo.Datos_Modelo;
import MetodosBienes.SubClase.Datos_SubClase;
import utility.ExcelUtils;
import utility.Constant;

public class ParametrosBienes {


	public static String Defoult_URL = Constant.Defoult_URL;
	public static String token = Constant.token;
	public static String idBien;
	public static String idClase;
	public static String bodyClase;
	public static String idAtributo;
	public static String bodyAtributo;
	public static String bodySubClase;
	public static String idSubClase;
	public static String bodyMarca;
	public static String idMarca;
	public static String bodyAsocMarca;
	public static String idAsocMarca;
	public static String bodyModelo;
	public static String idModelo;
	public static String bodyBien;
	public static String valorTipo;
	public static String procesoID_Bien;
	public static String bienID;
	public static String procesoAnalizarXActivosID;
	public static String procesoAnalizarXSegurosID;
	public static String vidaUtil;
	public static String procesoAnalizarViabilidadID;
	public static int row;
	public static ArrayList<String> nombresCabeceras = new ArrayList<String>(Arrays.asList("Tipificación","Patentable", "Semaforo", "IVA"));
	public static ArrayList<String> bodyCabeceras = new ArrayList<String>();
	public static ArrayList<String> idCabecera = new ArrayList<String>();
	public static String statusModel;

	public String atributo(int row) throws Exception {
		Datos_Atributo datos_Atributos = new Datos_Atributo();
		String atributo_post = datos_Atributos.setdatos_atributo(row);
		ParametrosBienes.row = row;
		Response response = 
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.body(atributo_post)
				.when()
				.post(Defoult_URL+"products/good/attribute");
		bodyAtributo = response.getBody().asString(); 
		idAtributo = Integer.toString(response.getBody().jsonPath().get("id"));
		return idAtributo;
	}


	public void extraerAtributos() throws Exception {

		int i;
		for(i=0; i < 4;i++) {
			String getCabecera = (Defoult_URL + "products/good/attribute/search");
			Response responseCabecera=
					given()
					.contentType("application/json")
					.headers("x-auth-token",token)
					.queryParam("page", 1)
					.queryParam("size", 15)
					.queryParam("name", nombresCabeceras.get(i))
					.queryParam("status", "true")
					.when().get(getCabecera);
			String serchCabecera = responseCabecera.getBody().asString();
			bodyCabeceras.add((i+2), (((serchCabecera.split("content\":\\["))[1]).split("\\]\\}"))[0]);
			idCabecera.add((i+2), (((bodyCabeceras.get(i+2)).split("\\{\"id\":"))[1]).split(",\"name\"")[0]);
		}
	}


	public String clase() throws Exception {

		Datos_Clase datos_clase = new Datos_Clase();
		String clase_post = datos_clase.setdatos_clase(row);
		Response response=
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.body(clase_post)
				.when()
				.post(Defoult_URL+"products/good/class");
		bodyClase = response.getBody().asString();
		idClase = Integer.toString(response.getBody().jsonPath().get("id"));
		return idClase;
	}


	public String subClase(int i) throws Exception {

		Datos_SubClase datosSubClase = new Datos_SubClase();
		String subClase_post = datosSubClase.setdatos_clase(bodyClase, bodyAtributo, idAtributo, row, bodyCabeceras.get(2), idCabecera.get(2), 
				bodyCabeceras.get(3), idCabecera.get(3), bodyCabeceras.get(4), idCabecera.get(4), bodyCabeceras.get(5), idCabecera.get(5),
				bodyCabeceras.get(0), idCabecera.get(0), bodyCabeceras.get(1), idCabecera.get(1));
		Response response =
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.body(subClase_post)
				.when()
				.post(Defoult_URL+"products/good/subclass");
		bodySubClase = response.getBody().asString();
		idSubClase = Integer.toString(response.getBody().jsonPath().get("id"));
		return idSubClase;
	}


	public String marca() throws Exception {

		Alta_Marca altaMarca = new Alta_Marca();
		String altaMarca_post = altaMarca.setaltaMarca(row);
		Response response =
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.body(altaMarca_post)
				.when()
				.post(Defoult_URL+"products/good/brandMaster");
		bodyMarca = response.getBody().asString();
		idMarca = Integer.toString(response.getBody().jsonPath().get("id"));

		//		Asociar Marca generada

		AsociarMarca asocMarca = new AsociarMarca();
		String asocMarca_post = asocMarca.setAsocMarca(bodySubClase, bodyMarca, row);
		Response responseAsoc =
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.body(asocMarca_post)
				.when()
				.post(Defoult_URL+"products/good/brand");
		bodyAsocMarca = responseAsoc.getBody().asString();
		idAsocMarca = Integer.toString(responseAsoc.getBody().jsonPath().get("id"));
		return idAsocMarca;
	}


	public void modelo() throws Exception {

		Datos_Modelo altaModelo = new Datos_Modelo();
		String altaModelo_post = altaModelo.setAltaModelo(bodyAsocMarca, row);
		Response response =
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.body(altaModelo_post)
				.when()
				.post(Defoult_URL+"products/good/model");
		bodyModelo = response.getBody().asString();
	}

	public String extraerModel() {

		Response responseModel=
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.queryParam("page", 1)
				.queryParam("size", 15)
				.queryParam("modelName")
				.queryParam("classId", idClase)
				.queryParam("subclassId", idSubClase)
				.queryParam("brandId", idAsocMarca)
				.when().get(Defoult_URL + "products/good/model/search");
		idModelo = Integer.toString(responseModel.getBody().jsonPath().get("content[0].id"));
		return idModelo;
	}


	public String bien(int i) throws Exception {

		Datos_Bien altaBien = new Datos_Bien();
		String altaBien_post = altaBien.datosBien(idClase, idSubClase, idAsocMarca, idModelo, bodyCabeceras.get(2), idCabecera.get(2), 
				bodyCabeceras.get(3), idCabecera.get(3), bodyCabeceras.get(4), idCabecera.get(4), bodyCabeceras.get(5), idCabecera.get(5),
				bodyCabeceras.get(0), idCabecera.get(0), bodyCabeceras.get(1), idCabecera.get(1), bodyAtributo, idAtributo, row);
		Response response =
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.body(altaBien_post)
				.when()
				.post(Defoult_URL+"products/good");
		bodyBien = response.getBody().asString();
		idBien = Integer.toString(response.getBody().jsonPath().get("id"));
		return bodyBien;
	}
	
	public String idBien() {
		return idBien;
	}

	public void JBPMBienes() throws Exception {

		obtenerNumeroProceso_Bien();
		obtenerIDActivosYSeguros();
		TareaAnalisisBienPorActivos();
		CompleteAnalisisBienPorActivos();
		TareaAnalisisBienPorSeguros();
		CompleteAnalisisBienPorSeguros();
		obtenerIDViabiliadBien();
		TareaAnalizarViabilidad();
		CompleteAnalizarViabilidad();
	}


	//Para obtener el numero procesID

	public void obtenerNumeroProceso_Bien() throws Exception {

		Response response  = 
				given() 
				.contentType("application/json")
				.headers("x-auth-token",token)
				.when().post(Defoult_URL+"products/good/analize/" + idBien);
		
		procesoID_Bien = response.getBody().asString();

	}

	//Para obtener los ID de las tareas analizar bienes por activos y analizar bienes por seguros

	public void obtenerIDActivosYSeguros() throws Exception {


		Response responseAct =
				given() 
				.contentType("application/json")
				.headers("x-auth-token",token)
				.queryParam("page", 1)
				.queryParam("size", 10)
				.queryParam("name", "Analizar Bien por Activos")
				.when().get(Defoult_URL+"bpm/cuore.admin.QA");
		procesoAnalizarXActivosID = Integer.toString(responseAct.getBody().jsonPath().get("content[0].id"));

		Response responseSeg =
				given() 
				.contentType("application/json")
				.headers("x-auth-token",token)
				.queryParam("page", 1)
				.queryParam("size", 10)
				.queryParam("name", "Analizar Bien por Seguros")
				.when().get(Defoult_URL+"bpm/cuore.admin.QA");
		procesoAnalizarXSegurosID = Integer.toString(responseSeg.getBody().jsonPath().get("content[0].id"));
	}

	//Completa los campos de la tarea analizar bienes por activos

	public void TareaAnalisisBienPorActivos() throws Exception {

		valorTipo = ExcelUtils.getCellData(row,17);
		vidaUtil = ExcelUtils.getCellData(row,19);

		TareaAnalizarBienPorActivos analizarBienXActivos = new TareaAnalizarBienPorActivos();

		String post_AnalisisActivos= analizarBienXActivos.setTareaAnalizarBienPorActivos(bodyBien, valorTipo, vidaUtil);
		
		given() 
		.contentType("application/json")
		.headers("x-auth-token",token)
		.body(post_AnalisisActivos)
		.when().post(Defoult_URL+"products/good/analysis");								
	}	

	//Par completar la tarea jbpm de analizar bienes por activos 

	public void CompleteAnalisisBienPorActivos() throws Exception {

		CompleteAnalizarBienPorActivos objCompleteAnalizarBienXActivos = new CompleteAnalizarBienPorActivos();

		String post_CompleteActivos= objCompleteAnalizarBienXActivos.mtdCompleteanalizarBienXActivos(bienID, procesoID_Bien, 
				procesoAnalizarXActivosID);

		given() 
		.contentType("application/json")
		.headers("x-auth-token",token)
		.body(post_CompleteActivos)
		.when().post(Defoult_URL+"bpm/complete");								
	}	

	//Completa los campos de la tarea analizar bienes por seguros

	public void TareaAnalisisBienPorSeguros() throws Exception {

		TareaAnalizarBienPorSeguros varAnalizarBienXSeguros = new TareaAnalizarBienPorSeguros();

		String post_AnalisisSeguros= varAnalizarBienXSeguros.setTareaAnalizarBienPorSeguros(bodyBien, valorTipo, vidaUtil);

		given() 
		.contentType("application/json")
		.headers("x-auth-token",token)
		.body(post_AnalisisSeguros)
		.when().post(Defoult_URL+"products/good/analysis");								
	}	

	//Par completar la tarea jbpm de analizar bienes por SEGUROS 

	public void CompleteAnalisisBienPorSeguros() throws Exception {

		CompleteAnalizarBienPorSeguros objCompleteAnalizarBienXSeguros = new CompleteAnalizarBienPorSeguros();

		String post_CompleteSeguros= objCompleteAnalizarBienXSeguros.mtdCompleteanalizarBienXSeguros(bienID, procesoID_Bien,
				procesoAnalizarXSegurosID);

		given() 
		.contentType("application/json")
		.headers("x-auth-token",token)
		.body(post_CompleteSeguros)
		.when().post(Defoult_URL+"bpm/complete");								
	}

	public void obtenerIDViabiliadBien() throws Exception {


		Response response = given() 
				.contentType("application/json")
				.headers("x-auth-token",token)
				.queryParam("page", 1)
				.queryParam("size", 10)
				.queryParam("name", "Analizar Vialidad del Bien")
				.when().get(Defoult_URL+"bpm/cuore.admin.QA");
		procesoAnalizarViabilidadID = Integer.toString(response.getBody().jsonPath().get("content[0].id"));

	}


	public void TareaAnalizarViabilidad() throws Exception {

		TareaAnalizarViabilidadDelBien varAnalizarViabilidadDelBien = new TareaAnalizarViabilidadDelBien();

		String post_AnalisisViabilidad= varAnalizarViabilidadDelBien.setAnalizarViabilidad (bodyBien, valorTipo);

		given() 
		.contentType("application/json")
		.headers("x-auth-token",token)
		.body(post_AnalisisViabilidad)
		.when().post(Defoult_URL+"products/good/analysis");								
	}	

	//Par completar la tarea jbpm de analizar bienes por SEGUROS 

	public void CompleteAnalizarViabilidad() throws InterruptedException {
		Thread.sleep(5000);

		CompleteAnalizarViabilidadDelBien objCompeteViabilidad = new CompleteAnalizarViabilidadDelBien();

		String post_CompleteViabilidad= objCompeteViabilidad.mtdCompleteAnalizarViabilidad(bienID, procesoID_Bien, 
				procesoAnalizarViabilidadID);

		given() 
		.contentType("application/json")
		.headers("x-auth-token",token)
		.body(post_CompleteViabilidad)
		.when().post(Defoult_URL+"bpm/complete");								
	}

}
