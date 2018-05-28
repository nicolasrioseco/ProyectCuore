package Precondiciones;

import static io.restassured.RestAssured.given;

import Producto.AprobarProducto;
import Producto.CompleteAprobarProductos;
import Producto.CreateProducto;
import Producto.Datos_Producto;
import io.restassured.response.Response;
import utility.Constant;


public class ParametrosProductos {

	public static String Defoult_URL = Constant.Defoult_URL;
	public static String token = Constant.token;
	public static String bodyProducto;
	public static String productoID;
	public static String procesoID_Producto;
	public static String aprobarProductoID;

	//Da de alta un Producto
	public String AltaProducto(int row, String bodyBien) throws Exception {
		
		Datos_Producto objDatos_Producto = new Datos_Producto();
		String altaProducto_post = objDatos_Producto.setDatos_Productos(row, bodyBien);
		Response response =
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.body(altaProducto_post)
				.when()
				.post(Defoult_URL+"products/product");
		bodyProducto = response.getBody().asString();
		productoID = Integer.toString(response.getBody().jsonPath().get("id"));
		return productoID;
	}

	//CREA LA TAREA APROBAR PRODUCTO
	public void createProducto() throws Exception {

		CreateProducto objDatosCreateProductos = new CreateProducto();
		String CreateProducto_post = objDatosCreateProductos.setCreateProducto(productoID);

		Response response1 =
				given() 
				.contentType("application/json")
				.headers("x-auth-token",token)
				.body(CreateProducto_post)
				.when()
				.post(Defoult_URL+"bpm/create");
		procesoID_Producto = response1.getBody().asString();

		//OBTENER EL ID DE LA TAREA APROBAR PRODUCTO
		Response response2 =
				given() 
				.contentType("application/json")
				.headers("x-auth-token",token)
				.queryParam("page", 1)
				.queryParam("size", 10)
				.queryParam("name", "Aprobar Producto")
				.when().get(Defoult_URL + "bpm/cuore.admin.QA");
		aprobarProductoID = response2.getBody().asString();
	}

	//COMPLETA LA TAREA Y LA ELIMINA DE LA BANDEJA
	public void CompleteAprobarProducto() throws Exception {

		CompleteAprobarProductos objCompleteAprobarProductos = new CompleteAprobarProductos();

		String post_CompleteAprobarProducto= objCompleteAprobarProductos.setCompletAprobarProducto(productoID, 
				procesoID_Producto, aprobarProductoID);

		given() 
		.contentType("application/json")
		.headers("x-auth-token",token)
		.body(post_CompleteAprobarProducto)
		.when().post(Defoult_URL+"bpm/complete");								
	}

	//SE APRUEBA EL PRODUCTO
	public void aprobarProducto() throws Exception {

		AprobarProducto objAprobarProducto = new AprobarProducto();

		String post_AprobarProducto= objAprobarProducto.setAprobarProducto(bodyProducto);

		given() 
		.contentType("application/json")
		.headers("x-auth-token",token)
		.body(post_AprobarProducto)
		.when().post(Defoult_URL+"products/product");								
	}

}
