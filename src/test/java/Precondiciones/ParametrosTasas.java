package Precondiciones;

import static io.restassured.RestAssured.given;

import Impuestos.Datos_confImpuestos;
import MetodosTasas.Tasa.Datos_Tasa;
import MetodosTasas.TasaTTR.Datos_TTR;
import MetodosTasas.TasaVariable.Datos_TasaVariable;
import io.restassured.response.Response;
import utility.Constant;

public class ParametrosTasas {


	public static String Defoult_URL = Constant.Defoult_URL;
	public static String token = Constant.token;
	public String bodyTasa;
	public String idTasa;
	public String bodyTV;
	public String bodyTTR;


	public String tasa(int row) throws Exception {

		Datos_Tasa datosTasa = new Datos_Tasa();
		String tasa_post = datosTasa.setTasa(row);
		Response response = 
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.body(tasa_post)
				.when()
				.post(Defoult_URL+"finances/rate");
		bodyTasa = response.getBody().asString();
		idTasa = Integer.toString(response.getBody().jsonPath().get("rateID"));
		String tipoTasa = datosTasa.getTipo();
		return tipoTasa;
	}
	
	public String idTasa() {
		return idTasa;
	}

	public void tasaVariable(int row) throws Exception {

		Datos_TasaVariable datosTV = new Datos_TasaVariable();
		String tv_post = datosTV.setTasaVariable(bodyTasa, row);
		Response response = 
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.body(tv_post)
				.when()
				.post(Defoult_URL+"finances/rate");
		bodyTV = response.getBody().asString();
	}

	public void tasaTTR(int row) throws Exception {

		int cantVidaMedias = 3;
		int i;

		for (i= 0; i < cantVidaMedias; i++) {
			Datos_TTR datos_TasaTTR = new Datos_TTR();
			String TasaTTR_post = datos_TasaTTR.setTasaTTR(i, row);	
			Response response = 
					given()
					.contentType("application/json")
					.headers("x-auth-token",token)
					.body(TasaTTR_post)
					.when().post(Defoult_URL+"finances/transferenceRate");
			bodyTTR = response.getBody().asString();
		}
	}

	public void impuestos(int row) throws Exception {

		Datos_confImpuestos datosImpuestos = new Datos_confImpuestos();
		String impuesto_post = datosImpuestos.setdatosImpuestos(row);
		given()
		.contentType("application/json")
		.headers("x-auth-token",token)
		.body(impuesto_post)
		.when().post(Defoult_URL+"finances/sealTax")
		.then().statusCode(201)
		.assertThat().statusCode(201);
	}
}
