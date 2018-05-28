package Ejecutor;


import utility.Constant;
import utility.ExcelUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import Impuestos.Datos_confImpuestos;
import MetodosBienes.Atributo.AtributoInicial;
import MetodosBienes.Bienes.Datos_Bien;
import Precondiciones.ParametrosBienes;
import Precondiciones.ParametrosBusqueda;
import Precondiciones.ParametrosProductos;
import Precondiciones.ParametrosProveedores;
import Precondiciones.ParametrosServicios;
import Precondiciones.ParametrosTasas;
import Proveedor.Datos_Proveedor;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class Ejecutor_Api{

	public static ArrayList<String> proveedores = new ArrayList<String>();
	public static String [][] bienes;
	public static ArrayList<String> tipoTasa = new ArrayList<String>();
	public static ArrayList<String> idTasa = new ArrayList<String>();
	public static ArrayList<String> acuerdos = new ArrayList<String>();
	public static ArrayList<String> idAcuerdo = new ArrayList<String>();
	public static ArrayList<String> idServicio = new ArrayList<String>();
	public static ArrayList<String> idProducto = new ArrayList<String>();
	ParametrosBusqueda buscar = new ParametrosBusqueda();
	public static int registros;
	public static int row;


	@Given("^La planilla \"([^\"]*)\"$")
	public void la_planilla(String planilla) throws Throwable {
		System.out.println("\n*************************Inicializando Ejecución*************************\n");
		ExcelUtils.setExcelInicial(Constant.Path_TestData + planilla);
		System.out.println(">Iniciando validación de Atributos Cabeceras no parametrizados");
		AtributoInicial setAtributoInicial = new AtributoInicial();
		setAtributoInicial.atributosInicialesTipoSeg();
		setAtributoInicial.atributosInicialesClassSeg();
		System.out.println("--->Finalizó exitosamente la validación de Atributos Cabecera no parametrizados");
		System.out.println("\n*************************************************************************\n");
		assertNotNull(ParametrosBienes.idCabecera.get(0));
	}

	@When("^Se dan de alta los proveedores de la solapa \"([^\"]*)\"$")
	public void se_dan_de_alta_los_proveedores_de_la_solapa(String solapa) throws Throwable {
		System.out.println("********************Inicializando Alta  de Proveedores********************\n");
		ExcelUtils.setExcelFile(solapa);
		registros = (int) ExcelUtils.getCellDataint(3,9);
		int i;
		if(registros != 0) {
			for (i= 0; i < (registros); i++) {
				row = (i+3);
				ParametrosProveedores setParametros = new ParametrosProveedores();
				String proveedor = setParametros.setProveedores(row);
				proveedores.add(i, proveedor);
				System.out.println("> Se dió de alta el " + (row - 2) + "º Proveedor de " + registros);
			}System.out.println("\n*************************************************************************\n");
			assertEquals((row - 2), registros);
		}else {
			System.out.println("> No hay Proveedores para dar de alta");
			System.out.println("\n*************************************************************************\n");
			assertEquals(0, registros);
		}
	}

	@When("^Se dan de alta los bienes de la solapa \"([^\"]*)\"$")
	public void se_dan_de_alta_los_bienes_de_la_solapa(String solapa) throws Throwable {
		System.out.println("**********************Inicializando Alta  de Bienes**********************\n");
		ExcelUtils.setExcelFile(solapa);
		registros = (int) ExcelUtils.getCellDataint(3,15);
		int i;
		if(registros != 0) {

			//Genero matriz
			bienes = new String [registros][7];

			for (i= 0; i < (registros); i++) {
				row = (i+3);
				ParametrosBienes setParametros = new ParametrosBienes();
				bienes[i][0] = setParametros.atributo(row);//4
				setParametros.extraerAtributos();
				System.out.println("> Se generó el " + (row-2) + "º Atributo de " + registros );
				bienes[i][1] = setParametros.clase();//0
				System.out.println("> Se generó la " + (row-2) + "º Clase de " + registros );
				bienes[i][2] = setParametros.subClase(i);//1
				System.out.println("> Se generó la " + (row-2) + "º SubClase de " + registros );
				bienes[i][3] = setParametros.marca();//2
				System.out.println("> Se generó la " + (row-2) + "º Marca Asociada de " + registros );
				setParametros.modelo();//5
				System.out.println("> Se generó el " + (row-2) + "º Modelo de " + registros );
				bienes[i][4] = setParametros.extraerModel();
				bienes[i][5] = setParametros.bien(i);//3
				bienes[i][6] = setParametros.idBien();//6
				System.out.println("> Se generó el " + (row-2) + "º Bien de " + registros );
				setParametros.JBPMBienes();
				System.out.println("> Se aprobó el " + (row-2) + "º Bien de " + registros );
				System.out.println("\n*************************************************************************\n");
			}assertEquals((row - 2), registros);
		}else {
			System.out.println("\nNo hay Bienes para dar de alta");
			System.out.println("\n*************************************************************************\n");
			assertEquals(0, registros);
		}
	}

	@When("^Se dan de alta las tasas de la solapa \"([^\"]*)\"$")
	public void se_dan_de_alta_las_tasas_de_la_solapa(String solapa) throws Throwable {
		System.out.println("************************Inicializando Alta de Tasas************************\n");
		ExcelUtils.setExcelFile(solapa);
		registros = (int) ExcelUtils.getCellDataint(3,16);
		int i;

		if(registros != 0) {

			for (i= 0; i < (registros); i++) {
				int row = (i+3);
				ParametrosTasas setParametros = new ParametrosTasas();
				tipoTasa.add(i, setParametros.tasa(row));
				if(tipoTasa.get(i).equals("Variable")) {
					setParametros.tasaVariable(row);
					setParametros.tasaTTR(row);
					System.out.println("> Se dió de alta la " + (row-2) + "º Tasa de " + registros + ".\n--->Generando su SubTasa, Tasa Variable y sus Tasas TTR<---");
				}else {
					setParametros.tasaTTR(row);
					System.out.println("> Se dió de alta la " + (row-2) + "º Tasa de " + registros + ".\n--->Generando su SubTasa y sus Tasas TTR<---");
				}	
				System.out.println("\n*************************************************************************\n");
				idTasa .add(i, setParametros.idTasa());
			}
		}else {
			System.out.println("\nNo hay Tasas para dar de alta");
			System.out.println("\n*************************************************************************\n");
		}
	}

	@When("^Se configuran los impuestos de la solapa \"([^\"]*)\"$")
	public void se_configuran_los_impuestos_de_la_solapa(String solapa) throws Throwable {
		System.out.println("*********************Inicializando Alta de Impuestos*********************\n");
		ExcelUtils.setExcelFile(solapa);
		registros = (int) ExcelUtils.getCellDataint(3,6);
		int i;

		if(registros != 0) {

			for (i= 0; i < (registros); i++) {
				int row = (i+3);
				ParametrosTasas setImpuestos = new ParametrosTasas();
				setImpuestos.impuestos(row);
				System.out.println("> Se configuro el " + (row-2) + "º Impuesto de " + registros);	
			}System.out.println("\n*************************************************************************\n");
		}else {
			System.out.println("\nNo hay Impuestos para configurar");
			System.out.println("\n*************************************************************************\n");
		}
	}
	 
	@When("^Se dan de alta los acuerdos de la solapa \"([^\"]*)\"$")
	public void metodosAltaAcuerdo(String solapa) throws Throwable {
		System.out.println("***************Inicializando Alta de Acuerdos de Servicios***************\n");
		ExcelUtils.setExcelFile(solapa);
		registros = (int) ExcelUtils.getCellDataint(3,7);
		int i;
		if(registros != 0) {

			for (i= 0; i < (registros); i++) {
				int row = (i+3);
				int seleccionBien = (int) ExcelUtils.getCellDataint(row,12);
				int seleccionProveedor = (int) ExcelUtils.getCellDataint(row,17);
				ParametrosProveedores setParametros = new ParametrosProveedores();
				String valorAcuerdo = setParametros.acuerdoServicio(row, bienes[seleccionBien][5], proveedores.get(seleccionProveedor));
				acuerdos.add(i, valorAcuerdo);
				idAcuerdo.add(i, setParametros.idAcuerdo());
				System.out.println("> Se dió de alta el " + (row-2) + "º Acuerdo de Servicio de " + registros);
			}System.out.println("\n*************************************************************************\n");
		}else {
			System.out.println("\nNo hay Acuerdos para dar de alta");
			System.out.println("\n*************************************************************************\n");
		}
	}

	@When("^Se dan de alta los servicios de la solapa \"([^\"]*)\"$")
	public void se_dan_de_alta_los_servicios_de_la_solapa(String solapa) throws Exception {
		System.out.println("***********Inicializando Alta de Servicios***********\n");
		ExcelUtils.setExcelFile(solapa);
		registros = (int) ExcelUtils.getCellDataint(3,6);
		int i;

		if(registros != 0) {
			for (i= 0; i < (registros); i++) {
				int row = (i+3);
				int seleccionBien = (int) ExcelUtils.getCellDataint(row,13);
				int seleccionAcuerdo = (int) ExcelUtils.getCellDataint(row,16);
				ParametrosServicios setParametros = new ParametrosServicios();
				idServicio.add(i, setParametros.servicio(row, bienes[seleccionBien][5], acuerdos.get(seleccionAcuerdo)));
				setParametros.jbpmServicio_Crear();
				setParametros.jbpmServicio_Aprobar();
				System.out.println("> Se generó y aprobó el " + (row-2) + "º Servicio de " + registros);
			}System.out.println("\n*************************************************************************\n");
		}else {
			System.out.println("\nNo hay Servicios para dar de alta");
			System.out.println("\n*************************************************************************\n");
		}
	}

	@When("^Se dan de alta los productos de la solapa \"([^\"]*)\"$")
	public void se_dan_de_alta_los_productos_de_la_solapa(String solapa) throws Throwable {
		System.out.println("***********Inicializando Alta de Productos***********\n");
		ExcelUtils.setExcelFile(solapa);
		registros = (int) ExcelUtils.getCellDataint(3,10);
		int i;
		if(registros != 0) {

			for (i= 0; i < (registros); i++) {
				int row = (i+3);
				int seleccionBien = (int) ExcelUtils.getCellDataint(row,15);
				ParametrosProductos setParametros = new ParametrosProductos();
				idProducto.add(i, setParametros.AltaProducto(row, bienes[seleccionBien][5]));
				setParametros.createProducto();
				setParametros.CompleteAprobarProducto();
				setParametros.aprobarProducto();
				System.out.println("> Se dió de alta y aprobó el " + (row-2) + "º Producto de " + registros);
			}System.out.println("\n*************************************************************************\n");
		}else {
			System.out.println("\nNo hay Productos para dar de alta");
			System.out.println("\n*************************************************************************\n");
		}
	}

	@Then("^Validamos que al buscar los Proveedores en la app, los mismos se dieron de alta$")
	public void validamos_que_al_buscar_los_Proveedores_en_la_app_los_mismos_se_dieron_de_alta() throws Throwable {
		int i;
		if(registros != 0) {
			for (i= 0; i < (registros); i++) {
				int statusProv = ParametrosProveedores.statusProv.get(i);
				assertEquals(201, statusProv);
				int idEncontrado = buscar.buscarProveedores(Datos_Proveedor.nombreProv.get(i));
				int idProv = ParametrosProveedores.idProv.get(i);
				assertEquals(idEncontrado, idProv);
			}
		}else {
			System.out.println("\nNo se dió de alta ningún Proveedor");
		}
	}
	
	@Then("^Validamos que al buscar los Bienes en la app, los mismos se dieron de alta$")
	public void validamos_que_al_buscar_los_Bienes_en_la_app_los_mismos_se_dieron_de_alta() throws Throwable {
		int i;
		if(registros != 0) {
			for (i= 0; i < (registros); i++) {
								
				//Validación de Atributos
				String idAtributo = buscar.buscarAtributos(Datos_Bien.nameAtributo.get(i));
				assertEquals(bienes[i][0], idAtributo);
				
				//Validación de Clases
				String idClase = buscar.buscarClases(Datos_Bien.nameClase.get(i));
				assertEquals(bienes[i][1], idClase);

				//Validación de SubClases
				String idSubClase = buscar.buscarSubClases(Datos_Bien.nameSubClase.get(i));
				assertEquals(bienes[i][2], idSubClase);
				
				//Validación de Marcas
				String idMarca = buscar.buscarMarcas(Datos_Bien.nameMarca.get(i));
				assertEquals(bienes[i][3], idMarca);

				//Validación de Modelo
				String idModelo = buscar.buscarModelos(Datos_Bien.nameModelo.get(i));
				assertEquals(bienes[i][4], idModelo);
				
				//Validación de Bien
				String idBien = buscar.buscarBienes(Datos_Bien.valorVersion.get(i), i);
				assertEquals(bienes[i][6], idBien);
			}
		}else {
			System.out.println("\nNo se dió de alta ningún Bien");
		}
	}

	@Then("^Validamos que al buscar las Tasas en la app, las mismas se dieron de alta$")
	public void validamos_que_al_buscar_las_Tasas_en_la_app_las_mismas_se_dieron_de_alta() throws Throwable {
		int i;
		if(registros != 0) {
			for (i= 0; i < (registros); i++) {
				String idEncontrado = buscar.buscarTasas(i);
				String idSubTasa = idTasa.get(i);
				assertEquals(idEncontrado, idSubTasa);
			}
		}else {
			System.out.println("\nNo se dió de alta ninguna Tasa");
		}
	}
	
	@Then("^Validamos que al buscar los Impuestos en la app, los mismos se configuraron$")
	public void validamos_que_al_buscar_los_Impuestos_en_la_app_los_mismos_se_configuraron() throws Throwable {
		int i;
		if(registros != 0) {
			for (i= 0; i < (registros); i++) {
				String idEncontrado = buscar.buscarImpuestos(i);
				int idImpuesto = Datos_confImpuestos.idProv.get(i);
				assertEquals(idEncontrado, idImpuesto);
			}
		}else {
			System.out.println("\nNo se configuró ningún Impuesto");
		}
	}
	
	@Then("^Validamos que al buscar los Acuerdos en la app, los mismos se dieron de alta$")
	public void validamos_que_al_buscar_los_Acuerdos_en_la_app_los_mismos_se_dieron_de_alta() throws Throwable {
		int i;
		if(registros != 0) {
			for (i= 0; i < (registros); i++) {
				String idEncontrado = buscar.buscarAcuerdo(i);
				assertEquals(idEncontrado, idAcuerdo.get(i));
			}
		}else {
			System.out.println("\nNo se dió de alta ningún Acuerdo");
		}
	}
	
	@Then("^Validamos que al buscar los Servicios en la app, los mismos se dieron de alta$")
	public void validamos_que_al_buscar_los_Servicios_en_la_app_los_mismos_se_dieron_de_alta() throws Throwable {
		int i;
		if(registros != 0) {
			for (i= 0; i < (registros); i++) {
				String idEncontrado = buscar.buscarServicios(i);
				assertEquals(idEncontrado, idServicio.get(i));
			}
		}else {
			System.out.println("\nNo se dió de alta ningún Servicio");
		}
	}
	
	@Then("^Validamos que al buscar los Productos en la app, los mismos se dieron de alta$")
	public void validamos_que_al_buscar_los_Productos_en_la_app_los_mismos_se_dieron_de_alta() throws Throwable {
		int i;
		if(registros != 0) {
			for (i= 0; i < (registros); i++) {
				String idEncontrado = buscar.buscarProductos(i);
				assertEquals(idEncontrado, idProducto.get(i));
			}
		}else {
			System.out.println("\nNo se dió de alta ningún Producto");
		}
	}
}
