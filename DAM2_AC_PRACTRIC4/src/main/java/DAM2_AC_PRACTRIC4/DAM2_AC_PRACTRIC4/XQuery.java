package DAM2_AC_PRACTRIC4.DAM2_AC_PRACTRIC4;

import java.util.Objects;
import java.util.Scanner;

import javax.xml.transform.OutputKeys;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

public class XQuery {
	
	  private static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/Pruebas";
	    private static String USER = "admin";
	    private static String PASSWORD = "admin";

	    private static XQueryService xquery;
	public static void main(String[] args) {

		
		
	      final String driver = "org.exist.xmldb.DatabaseImpl";
	        int opcion;
			Scanner reader = new Scanner(System.in);


	        // initialize database driver
	        Class cl = null;
	        try {
	          cl = Class.forName(driver);
	          Database database = (Database) cl.newInstance();
	          database.setProperty("create-database", "true");
	          DatabaseManager.registerDatabase(database);

	          Collection col = null;
	          XMLResource res = null;

	          // get the collection
	          col = DatabaseManager.getCollection(URI, USER, PASSWORD);
	          col.setProperty(OutputKeys.INDENT, "no");
	          xquery = Objects.requireNonNull((XQueryService) col.getService(XQueryService.SERVICE_NAME, null));
	          
	          
	      System.out.println("Productos:" +"\n"+"1-Número de productos por zona" + "\n" + "2-Denominación de los productos entra las etiquetas <zona10></zona10>..." +"\n" +"3-Producto más caro por zona" + "\n"
	      +"4-Denominación de los productos entra las etiquetas <placa></placa>"+ "\n" + "Sucursales:"+"\n"+ "5-El código de sucursal tipo pensión" +"\n" + "6-Por cada sucursal: código,director,población,suma del total saldodebe y suma del total saldohaber de sus cuentas."  
	    		  + "\n" + "7-Nombre de los directores,código de sucursal y la población con más de 3 cuentas"+ "\n"+ "8- Devuelve por cada sucursal, el código de sucursal y los datos de las cuentas con más saldodebe" + "\n" 
	      + "9-Devuelve la cuenta del tipo PENSIONES que ha hecho más aportación"); 
	  	  opcion=reader.nextInt();
	  	  while(opcion!=0) {
	  	  switch(opcion) {
	  	  case 1:
	  		caso1();

	  		  
	  		  break;
	  	  case 2:
	  		  
	  		caso2();


	  		  break;
	  		  
	  	  case 3:

	  		caso3();

	  		  break;
	  	  case 4:

	  		caso4();

	  		  break;
	  	  case 5:

	  		caso5();
	  		  break;
	  	  case 6:

	  		caso6();
	  		  break;
	  	  case 7:
	  		  
	  		caso7();
	  		
	  		  break;
	  	  case 8:
	  		caso8();

	  		  break;
	  	 case 9:
	  		caso9();

		  		  break;
	  	 
	  		  
	  		  
	  	  default:
	  		 System.out.println("Opcion no valida");

	  	  
	  	  
	  	  }
	  	  
	  	  System.out.println("Productos:" +"\n"+"1-Número de productos por zona" + "\n" + "2-Denominación de los productos entra las etiquetas <zona10></zona10>..." +"\n" +"3-Producto más caro por zona" + "\n"
	  		      +"4-Denominación de los productos entra las etiquetas <placa></placa>"+ "\n" + "Sucursales:"+"\n"+ "5-El código de sucursal tipo pensión" +"\n" + "6-Por cada sucursal: código,director,población,suma del total saldodebe y suma del total saldohaber de sus cuentas."  
	  		    		  + "\n" + "7-Nombre de los directores,código de sucursal y la población con más de 3 cuentas"+ "\n"+ "8- Devuelve por cada sucursal, el código de sucursal y los datos de las cuentas con más saldodebe" + "\n" 
	  		      + "9-Devuelve la cuenta del tipo PENSIONES que ha hecho más aportación"); 
	  	  opcion=reader.nextInt();

	  	  }

	  	    
	  	    
	  	    
	  	    
	  	    

	          
	        } catch (ClassNotFoundException e) {
	          e.printStackTrace();
	        } catch (IllegalAccessException e) {
	          e.printStackTrace();
	        } catch (InstantiationException e) {
	          e.printStackTrace();
	        } catch (XMLDBException e) {
	          e.printStackTrace();
	        }	
		
		
		
		
		
		
	}
	
	
    private static void caso1() throws XMLDBException {
        ResourceIterator Iterator = xquery.query("for $v in distinct-values(/productos/produc/cod_zona) return ($v, count(/productos/produc[cod_zona = $v]))").getIterator();

        while (Iterator.hasMoreResources()) {
            System.out.println();
            XMLResource res = ((XMLResource) Iterator.nextResource());
            System.out.print("La zona " + res.getContent());
            if (Iterator.hasMoreResources()) {
                XMLResource res2 = ((XMLResource) Iterator.nextResource());
                System.out.println(" tiene " + res2.getContent() + " productos");
            }
        }
    }
    
    
    
	
    private static void caso2() throws XMLDBException {
    	  ResourceIterator Iterator = xquery.query("for $v in distinct-values(/productos/produc/cod_zona) return element{ 'zona' || $v }{ /productos/produc[cod_zona = $v]/denominacion }").getIterator();
          while (Iterator.hasMoreResources()) {
              System.out.println();
              XMLResource res = ((XMLResource) Iterator.nextResource());
              System.out.println(res.getContent());
          }
    }
    
    
    
	
    private static void caso3() throws XMLDBException {
    	   ResourceIterator Iterator = xquery.query("for $v in distinct-values(/productos/produc/cod_zona) return ($v, /productos/produc[precio = max(/productos/produc[cod_zona = $v]/precio)]/denominacion/text())").getIterator();
           while (Iterator.hasMoreResources()) {
               System.out.println();
               XMLResource res = ((XMLResource) Iterator.nextResource());
               System.out.print("En la zona " + res.getContent());
               if (Iterator.hasMoreResources()) {
                   XMLResource res2 = ((XMLResource) Iterator.nextResource());
                   System.out.println(", el producto más caro es " + res2.getContent());
               }
           }
    }
    
    
	
    private static void caso4() throws XMLDBException {
    	   ResourceIterator Iterator = xquery.query("(<placa>{/productos/produc/denominacion[contains(., 'Placa Base')]}</placa>,"
                   + "<micro>{/productos/produc/denominacion[contains(., 'Micro')]}</micro>,"
                   + "<memoria>{/productos/produc/denominacion[contains(., 'Memoria')]}</memoria>,"
                   + "<otros>{/productos/produc/denominacion[not(contains(., 'Memoria') or contains(., 'Micro') or contains(., 'Placa Base'))]}</otros>)").getIterator();
    	   
					while (Iterator.hasMoreResources()) {
					System.out.println();
					XMLResource res = ((XMLResource) Iterator.nextResource());
					System.out.println(res.getContent());
					}
    }
    
    
    
    private static void caso5() throws XMLDBException {
        ResourceIterator Iterator = xquery.query("for $suc in /sucursales/sucursal return (data($suc/@codigo), count($suc/cuenta[data(@tipo)='AHORRO']), count($suc/cuenta[data(@tipo)='PENSIONES']))").getIterator();
        while (Iterator.hasMoreResources()) {
            System.out.println();
            XMLResource res = ((XMLResource) Iterator.nextResource());
            System.out.print("La sucursal " + res.getContent());

            if (Iterator.hasMoreResources()) {
                XMLResource res2 = (XMLResource) Iterator.nextResource();
                System.out.print(" tiene " + res2.getContent() + " cuentas tipo AHORRO y ");
            }

            if (Iterator.hasMoreResources()) {
                XMLResource res3 = (XMLResource) Iterator.nextResource();
                System.out.println(res3.getContent() + " cuentas tipo PENSIONES");
            }
        }
    }
    
    
    
    
    private static void caso6() throws XMLDBException {
        ResourceIterator Iterator = xquery.query("for $suc in /sucursales/sucursal return (data($suc/@codigo), $suc/director/text(), $suc/poblacion/text(), sum($suc/cuenta/saldodebe), sum($suc/cuenta/saldohaber))").getIterator();

        while (Iterator.hasMoreResources()) {
            System.out.println();

            XMLResource res = (XMLResource) Iterator.nextResource();
            System.out.println("Código: " + res.getContent());

            if (Iterator.hasMoreResources()) {
                res = (XMLResource) Iterator.nextResource();
                System.out.println("Director: " + res.getContent());
            }

            if (Iterator.hasMoreResources()) {
                res = (XMLResource) Iterator.nextResource();
                System.out.println("Población: " + res.getContent());
            }

            if (Iterator.hasMoreResources()) {
                res = (XMLResource) Iterator.nextResource();
                System.out.println("Total saldodebe: " + res.getContent());
            }

            if (Iterator.hasMoreResources()) {
                res = (XMLResource) Iterator.nextResource();
                System.out.println("Total saldohaber: " + res.getContent());
            }
        }

    }
    
    
    
    private static void caso7() throws XMLDBException {
        ResourceIterator Iterator = xquery.query("for $suc in /sucursales/sucursal[count(cuenta) > 3] return (data($suc/@codigo), $suc/director/text(), $suc/poblacion/text())").getIterator();

        while (Iterator.hasMoreResources()) {
            System.out.println();

            XMLResource res = (XMLResource) Iterator.nextResource();
            System.out.println("Código: " + res.getContent());

            if (Iterator.hasMoreResources()) {
                res = (XMLResource) Iterator.nextResource();
                System.out.println("Director: " + res.getContent());
            }

            if (Iterator.hasMoreResources()) {
                res = (XMLResource) Iterator.nextResource();
                System.out.println("Población: " + res.getContent());
            }
        }
    }


    private static void caso8() throws XMLDBException {
        ResourceIterator Iterator = xquery.query("for $suc in /sucursales/sucursal return (data($suc/@codigo), $suc/cuenta[saldodebe = max($suc/cuenta/saldodebe)]/*/text())").getIterator();

        while (Iterator.hasMoreResources()) {
            System.out.println();

            XMLResource res = (XMLResource) Iterator.nextResource();
            System.out.println("Cuenta con más saldodebe de la sucursal " + res.getContent());

            if (Iterator.hasMoreResources()) {
                res = (XMLResource) Iterator.nextResource();
                System.out.println("Nombre: " + res.getContent());
            }

            if (Iterator.hasMoreResources()) {
                res = (XMLResource) Iterator.nextResource();
                System.out.println("Número: " + res.getContent());
            }

            if (Iterator.hasMoreResources()) {
                res = (XMLResource) Iterator.nextResource();
                System.out.println("Saldohaber: " + res.getContent());
            }

            if (Iterator.hasMoreResources()) {
                res = (XMLResource) Iterator.nextResource();
                System.out.println("Saldodebe: " + res.getContent());
            }
        }
    }

    
    private static void caso9() throws XMLDBException {
        ResourceIterator Iterator = xquery.query("/sucursales/sucursal/cuenta[data(@tipo) = 'PENSIONES' and aportacion = max(/sucursales/sucursal/cuenta/aportacion)]").getIterator();

        while (Iterator.hasMoreResources()) {
            System.out.println();

            XMLResource res = (XMLResource) Iterator.nextResource();
            System.out.println(res.getContent());
        }
    }
    
    
    
}
