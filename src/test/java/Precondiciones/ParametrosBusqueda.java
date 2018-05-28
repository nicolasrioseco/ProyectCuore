package Precondiciones;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;

import Ejecutor.Ejecutor_Api;
import Impuestos.Datos_confImpuestos;
import MetodosTasas.Tasa.Datos_Tasa;
import Producto.Datos_Producto;
import Proveedor.Datos_AcuerdoServicio;
import Servicio.Datos_Servicio;
import io.restassured.response.Response;

public class ParametrosBusqueda {
	public static String Defoult_URL = "http://34.234.32.246:8082/api/";
	public static String token = "eyJraWQiOiJCTWltU1VNNWtxNTJsSXhDQk1LWiswQlBnSjNleE1sb3VCQlRDMlhIRk5BPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiI2YjBiNjY3YS00ZGM4LTQ5OTktYTM5ZS02YjliYWZmMzhkZWQiLCJjb2duaXRvOmdyb3VwcyI6WyJDVU9SRS1BZG1pbiJdLCJldmVudF9pZCI6IjNlNzdlY2E3LTM4ZGItMTFlOC1hMGZmLTE1YmVkYmIxZmYyZiIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4iLCJhdXRoX3RpbWUiOjE1MjI5Mzc0OTMsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX0VmWXZQajdCRyIsImV4cCI6MTUyMjk0MTA5MywiaWF0IjoxNTIyOTM3NDkzLCJqdGkiOiI5NmNjMjJmMS0wYWEyLTRkZjctYWQxMi1jNzI4YjQ3M2M4Y2IiLCJjbGllbnRfaWQiOiIxbTZqamZpbDVuaXVjcml2ZDgyZnVuamlmcyIsInVzZXJuYW1lIjoiY3VvcmUuYWRtaW4uUUEifQ.RJiOseAcOCAs9i_ktU1RC9ohrSkP-lxfW_vx3JLoT2Xzuat1XvlfjCdX-7-_J96Sw4oQ38ZRY1i7q-jlVs9CMWBNBaSjWGSWYLXiOhhk4lE4nGuMhvBA0_lpPkw0fLgANq1Ua7KPH7BZ5xyfIU4G2WL9TlOmR4GfHNzYgqwgMGUBR0XrdYE_wCN_DzXOY5AC3_80r71DPOBA-2qQmkwc03z1PBeaInMnV2MwSBCzKU3eeK8Jtxwq5c83S43HzaZowk_7L8aTVzEMWwxZYSondAg3zuGEnP9StlZ1dGbjalLuAvmIEmbNhXb28ITtYvL0R_ZxljOOCHNUoJIraXF3cw";

	public int buscarProveedores(String nombre) {
		String getRequest = (Defoult_URL + "products/supplier/search");
		Response responseProveedor=
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.queryParam("page", 1)
				.queryParam("size", 15)
				.queryParam("name", nombre)
				.queryParam("identificationNumber", 1234577)
				.queryParam("identificationType", 96)
				.when().get(getRequest);
		int serchProveedor = responseProveedor.path("content[0].id");
		return serchProveedor;
	}
	
	public String buscarAtributos(String nombre) {
		String getRequest = (Defoult_URL + "products/good/attribute/search");
		Response responseAtributo=
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.queryParam("page", 1)
				.queryParam("size", 15)
				.queryParam("name", nombre)
				.queryParam("valueType", 1)
				.queryParam("format", 2)
				.queryParam("status", "true")
				.when().get(getRequest);
		String serchAtributo = Integer.toString(responseAtributo.path("content[0].id"));
		return serchAtributo;
	}
	
	public String buscarClases(String nombre) {
		String getRequest = (Defoult_URL + "products/good/class/search");
		Response responseClase=
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.queryParam("page", 1)
				.queryParam("size", 15)
				.queryParam("className", nombre)
				.when().get(getRequest);
		String serchClase = Integer.toString(responseClase.path("content[0].id"));
		return serchClase;
	}
	
	public String buscarSubClases(String nombre) {
		String getRequest = (Defoult_URL + "products/good/subclass/search");
		Response responseSubClase=
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.queryParam("page", 1)
				.queryParam("size", 15)
				.queryParam("classId")
				.queryParam("subclassName", nombre)
				.when().get(getRequest);
		String serchSubClase = Integer.toString(responseSubClase.path("content[0].id"));
		return serchSubClase;
	}

	public String buscarMarcas(String nombre) {
		String getRequest = (Defoult_URL + "products/good/brand/search");
		Response responseMarcas=
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.queryParam("page", 1)
				.queryParam("size", 15)
				.queryParam("classId")
				.queryParam("subclassId")
				.queryParam("brandName", nombre)
				.when().get(getRequest);
		String serchMarcas = Integer.toString(responseMarcas.path("content[0].id"));
		return serchMarcas;
	}
	
	public String buscarModelos(String nombre) {
		String getRequest = (Defoult_URL + "products/good/model/search");
		Response responseModelo=
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.queryParam("page", 1)
				.queryParam("size", 15)
				.queryParam("modelName", nombre)
				.queryParam("classId")
				.queryParam("subclassId")
				.queryParam("brandId")
				.when().get(getRequest);
		String serchModelo = Integer.toString(responseModelo.path("content[0].id"));
		return serchModelo;
	}
	
	public String buscarBienes(String nombre, int i) {
		String getRequest = (Defoult_URL + "products/good/good/search");
		Response responseBien=
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.queryParam("page", 1)
				.queryParam("size", 15)
				.queryParam("version", nombre)
				.queryParam("classId", Ejecutor_Api.bienes[i][1])
				.queryParam("subclassId", Ejecutor_Api.bienes[i][2])
				.queryParam("brandId", Ejecutor_Api.bienes[i][3])
				.queryParam("modelId", Ejecutor_Api.bienes[i][4])
				.when().get(getRequest);
		String serchBien = Integer.toString(responseBien.path("content[0].id"));
		return serchBien;
	}
	
	public String buscarTasas(int i) {
		String getRequest = (Defoult_URL + "finances/rate/search/filter");
		Response responseTasa=
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.queryParam("page", 1)
				.queryParam("size", 15)
				.queryParam("typeRate", Datos_Tasa.idTipo.get(i))
				.queryParam("typeMoney", Datos_Tasa.moneda.get(i))
				.queryParam("subTypeRate", Datos_Tasa.nameSubT.get(i))
				.queryParam("state", "Alta")
				.when().get(getRequest);
		String serchTasa = Integer.toString(responseTasa.path("content[0].rateID"));
		return serchTasa;
	}
	
	public String buscarImpuestos(int i) {
		String getRequest = (Defoult_URL + "finances/sealTax/search");
		Response responseImpuesto=
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.queryParam("page", 1)
				.queryParam("size", 15)
				.queryParam("province", Datos_confImpuestos.idProv.get(i))
				.queryParam("excendent")
				.queryParam("declaration")
				.queryParam("reduction")
				.queryParam("state", "Alta")
				.when().get(getRequest);
		String serchImpuesto = Integer.toString(responseImpuesto.path("content[0].provinceId.id"));
		return serchImpuesto;
	}
	
	public String buscarAcuerdo(int i) {
		String getRequest = (Defoult_URL + "products/supplier/agreement/search");
		Response responseAcuerdo=
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.queryParam("name", Datos_AcuerdoServicio.nombreAcuerdo.get(i))
				.queryParam("validFrom")
				.queryParam("validUntil")
				.queryParam("agreementState")
				.queryParam("supplierId", ParametrosProveedores.idProv.get(i))
				.when().get(getRequest);
		ArrayList<String> listAcuerdo = new ArrayList<String>(responseAcuerdo.getBody().jsonPath().get("id"));
		String idAcuerdo = (Arrays.toString(listAcuerdo.toArray())).replaceAll("[^\\dA-Za-z]", "");
		return idAcuerdo;
	}
	
	public String buscarServicios(int i) {
		String getRequest = (Defoult_URL + "products/service/");
		Response responseServicio=
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.queryParam("page", 1)
				.queryParam("size", 15)
				.queryParam("name", Datos_Servicio.nombreServicio.get(i))
				.queryParam("dateFrom")
				.queryParam("dateUntil")
				.queryParam("donor")
				.queryParam("invoicedBy")
				.when().get(getRequest);
		String serchServicio = Integer.toString(responseServicio.path("content[0].id"));
		return serchServicio;
	}
	
	public String buscarProductos(int i) {
		String getRequest = (Defoult_URL + "products/product/search/product");
		Response responseProducto=
				given()
				.contentType("application/json")
				.headers("x-auth-token",token)
				.queryParam("page", 1)
				.queryParam("size", 15)
				.queryParam("name", Datos_Producto.nombreProducto.get(i))
				.queryParam("validFrom")
				.queryParam("validUntil")
				.queryParam("statusId")
				.queryParam("clientTypeId")
				.queryParam("bankingId")
				.queryParam("comafiClient")
				.queryParam("lessorId")
				.queryParam("contractTypeId")
				.queryParam("amortizationId")
				.when().get(getRequest);
		String serchProducto = Integer.toString(responseProducto.path("content[0].id"));
		return serchProducto;
	}
}