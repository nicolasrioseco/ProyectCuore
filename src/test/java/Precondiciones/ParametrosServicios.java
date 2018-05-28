package Precondiciones;

import static io.restassured.RestAssured.given;

import Servicio.AprobarServicio;
import Servicio.Datos_Servicio;
import io.restassured.response.Response;
import utility.Constant;

public class ParametrosServicios {


	public static String Defoult_URL = Constant.Defoult_URL;
	public static String token = Constant.token;
	public static String bodyServicio;
	public static String idServicio;
	public static String idProcess;
	public static int idTarea;

	public String servicio(int row, String bodyBien, String bodyAcuerdo) throws Exception {


		Datos_Servicio datos_Servicio = new Datos_Servicio();
		String servicio_post = datos_Servicio.setdatosServicio(row,bodyBien,bodyAcuerdo);
		Response response = 
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.body(servicio_post)
				.when()
				.post(Defoult_URL+"products/service");
		bodyServicio = response.getBody().asString();
		idServicio = Integer.toString(response.getBody().jsonPath().get("id"));
		
		return idServicio;
	}

	public void jbpmServicio_Crear() throws InterruptedException {

		AprobarServicio crearTarea = new AprobarServicio();
		String postGenerar = crearTarea.setGenerarTarea(idServicio);
		Response response = 
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.body(postGenerar)
				.when().post(Defoult_URL+"bpm/create");
		idProcess = response.getBody().asString();
		idTarea = Integer.parseInt(idProcess) + 10;		
		Thread.sleep(1500);
	}

	public void jbpmServicio_Aprobar() throws InterruptedException {

		AprobarServicio aprobarTarea = new AprobarServicio();
		String postAprobar = aprobarTarea.setAprobarTarea(idServicio, idProcess, idTarea);
		given()
		.contentType("application/json")
		.headers("x-auth-token",token)
		.body(postAprobar)
		.when().post(Defoult_URL+"bpm/complete")
		.then()
		.assertThat().statusCode(201);

		String postAprobarServicio = aprobarTarea.setAprobarServicio(bodyServicio);
		given()
		.contentType("application/json")
		.headers("x-auth-token",token)
		.body(postAprobarServicio)
		.when().post(Defoult_URL+"products/service")
		.then()
		.assertThat().statusCode(201);

	}

}

//		final Gson gson = new Gson();
//		final String representacionJSON = gson.toJson(servicio_post);
//		assertEquals("{\"id\":46,\"nombre\":\"Miguel\",\"empresa\":\"Autentia\"}", representacionJSON);	