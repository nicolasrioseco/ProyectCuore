Feature: Casos a validar
	Description: Se van a validar todos los Servicios de altas y búsquedas

	Scenario: Validación de atributos cabeceras y Apertura de planilla de datos
		Given La planilla "TestData.xlsx"
		
  Scenario: Enviar Request de alta de Proveedores
    When Se dan de alta los proveedores de la solapa "Proveedor"
    Then Validamos que al buscar los Proveedores en la app, los mismos se dieron de alta

  Scenario: Enviar Request de alta de Bienes
    When Se dan de alta los bienes de la solapa "Bienes"
    Then Validamos que al buscar los Bienes en la app, los mismos se dieron de alta
    
  Scenario: Enviar Request de alta de Tasas
    When Se dan de alta las tasas de la solapa "Tasas"
    Then Validamos que al buscar las Tasas en la app, las mismas se dieron de alta
    
  Scenario: Enviar Request de Configuración de Impuestos
    When Se configuran los impuestos de la solapa "Impuestos"
    Then Validamos que al buscar los Impuestos en la app, los mismos se configuraron
    
  Scenario: Enviar Request de alta de Acuerdos de Servicios
    When Se dan de alta los acuerdos de la solapa "AcuerdoServicio"
    Then Validamos que al buscar los Acuerdos en la app, los mismos se dieron de alta
    
  Scenario: Enviar Request de alta de Servicios
    When Se dan de alta los servicios de la solapa "Servicios"
    Then Validamos que al buscar los Servicios en la app, los mismos se dieron de alta
    
  Scenario: Enviar Request de alta de Productos
    When Se dan de alta los productos de la solapa "Productos"
    Then Validamos que al buscar los Productos en la app, los mismos se dieron de alta