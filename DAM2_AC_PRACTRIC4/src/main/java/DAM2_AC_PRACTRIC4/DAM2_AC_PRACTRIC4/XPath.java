package DAM2_AC_PRACTRIC4.DAM2_AC_PRACTRIC4;

import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmldb.api.*;

import java.util.Objects;
import java.util.Scanner;

import javax.xml.transform.OutputKeys;

/**
 * Hello world!
 *
 */
public class XPath 
{
    private static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/Pruebas";
    private static String USER = "admin";
    private static String PASSWORD = "admin";

    private static XPathQueryService xpath;
    
    public static void main( String[] args ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException
    {
        final String driver = "org.exist.xmldb.DatabaseImpl";
        int opcion=0;
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
  	    xpath = Objects.requireNonNull((XPathQueryService) col.getService(XPathQueryService.SERVICE_NAME, null));

  	  System.out.println("Productos:" +"\n"+"1-Nodos denominación y precio de todos los productos" + "\n" + "2-Nodos de los productos que sean placas base" +"\n" +"3-Nodos de los productos con precio mayor de 60€ y de la zona 20" + "\n"
  		      +"4-número de productos que son memorias y de la zona 10."+"\n"+ "5-La media de precio de los micros" +"\n" + "6-Los datos de los productos cuyo stock mínimo sea mayor que su stock actual"  
  		    		  + "\n" + "7-El nombre del producto y el precio de aquellos cuyo stock mínimo sea mayor que su stock actual y sean de la zona 40"+ "\n"+ "8-El producto más caro" + "\n" 
  		      + "9-El producto más barato de la zona 20" + "\n" +"10-El producto más caro de la zona 10"); 

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
  	  case 10:

  		  
  		caso10();

  		  break;
  		  
  		  
  		  
  	  default:
	  		 System.out.println("Opcion no valida");
  	}

  	  
  	 System.out.println("Productos:" +"\n"+"1-Nodos denominación y precio de todos los productos" + "\n" + "2-Nodos de los productos que sean placas base" +"\n" +"3-Nodos de los productos con precio mayor de 60€ y de la zona 20" + "\n"
 		      +"4-número de productos que son memorias y de la zona 10."+"\n"+ "5-La media de precio de los micros" +"\n" + "6-Los datos de los productos cuyo stock mínimo sea mayor que su stock actual"  
 		    		  + "\n" + "7-El nombre del producto y el precio de aquellos cuyo stock mínimo sea mayor que su stock actual y sean de la zona 40"+ "\n"+ "8-El producto más caro" + "\n" 
 		      + "9-El producto más barato de la zona 20" + "\n" +"10-El producto más caro de la zona 10"); 

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
    
    
    
   public static void caso1() throws XMLDBException {
	   
        ResourceIterator Iterator = xpath.query("/productos/produc/*[self::denominacion or self::precio]").getIterator();
        while (Iterator.hasMoreResources()) {

            System.out.println();

            XMLResource res  = ((XMLResource) Iterator.nextResource());
            System.out.println(res .getContent());

            if (Iterator.hasMoreResources()) {
                XMLResource precio = ((XMLResource) Iterator.nextResource());
                System.err.println(precio.getContent());
            }
        }         	   
   }
   
   
   
   
   
  public static void caso2() throws XMLDBException {
	   
	  ResourceIterator Iterator = xpath.query("/productos/produc[denominacion[contains(., 'Placa Base')]]").getIterator();

      while (Iterator.hasMoreResources()) {
          System.out.println();
          XMLResource res  = ((XMLResource) Iterator.nextResource());
          System.out.println(res .getContent());
      }        	   
  }
   
   
  public static void caso3() throws XMLDBException {
	   
	  ResourceIterator Iterator = xpath.query("/productos/produc[precio[text() > 60] and cod_zona[text() = 20]]").getIterator();

      while (Iterator.hasMoreResources()) {
          System.out.println();
          XMLResource res  = ((XMLResource) Iterator.nextResource());
          System.out.println(res .getContent());
      }        	   
  }
  
  
  
  public static void caso4() throws XMLDBException {
	   
	  ResourceIterator Iterator = xpath.query("count(/productos/produc[denominacion[contains(., 'Memoria')] and cod_zona[text() = 10]])").getIterator();

 
          System.out.println();
          XMLResource res  = ((XMLResource) Iterator.nextResource());
          System.out.println("Hay " + res .getContent() + " productos «Memoria» de la zona 10.");
          
              	   
  }
   
  
  public static void caso5() throws XMLDBException {
	   
	  ResourceIterator Iterator = xpath.query("sum(/productos/produc[denominacion[contains(., 'Micro')]]/precio/text()) div count(/productos/produc[denominacion[contains(., 'Micro')]])").getIterator();
	    
      while (Iterator.hasMoreResources()) {
          System.out.println();
          XMLResource res  = ((XMLResource) Iterator.nextResource());
          System.out.println("La media de los precios de los microprocesadores es " + res .getContent());
      }        	   
  }
   
  
  public static void caso6() throws XMLDBException {
	   
	  ResourceIterator Iterator = xpath.query("/productos/produc[number(stock_minimo) > number(stock_actual)]").getIterator();
      
      while (Iterator.hasMoreResources()) {
          System.out.println();
          XMLResource res  = ((XMLResource) Iterator.nextResource());

          NodeList nodosProduc = res .getContentAsDOM().getChildNodes();

          for (int i = 0; i < nodosProduc.getLength(); ++i) {
              Node n = nodosProduc.item(i);
              if (n.getLocalName() != null) {
                  System.out.println(n.getLocalName() + " = " + n.getTextContent());
              }
          }
      }       	   
  }
   
  
  public static void caso7() throws XMLDBException {
	   
	  ResourceIterator Iterator = xpath.query("/productos/produc/*[(self::denominacion or self::precio) and number(../stock_minimo/text()) > number(../stock_actual/text()) and ../cod_zona/text() = 40]/text()").getIterator();

      while (Iterator.hasMoreResources()) {
          System.out.println();
          XMLResource res  = ((XMLResource) Iterator.nextResource());
          System.out.println("nombre = " + res .getContent());

          if (Iterator.hasMoreResources()) {
              XMLResource res2 = ((XMLResource) Iterator.nextResource());
              System.out.println("precio = " + res2.getContent());
          }
      }      	   
  }
   
  public static void caso8() throws XMLDBException {
	   
	  ResourceIterator Iterator = xpath.query("/productos/produc[precio = max(/productos/produc/precio)]").getIterator();
      while (Iterator.hasMoreResources()) {
          System.out.println();
          XMLResource res  = ((XMLResource) Iterator.nextResource());
          System.out.println(res .getContent());
      }       	   
  }
   
  
  
  
  public static void caso9() throws XMLDBException {
	   
	  ResourceIterator Iterator = xpath.query("/productos/produc[precio = min(/productos/produc[cod_zona = 20]/precio)]").getIterator();
      while (Iterator.hasMoreResources()) {
          System.out.println();
          XMLResource res   = ((XMLResource) Iterator.nextResource());
          System.out.println(res .getContent());
      }        	   
  }
   
  
  public static void caso10() throws XMLDBException {
	   
	   ResourceIterator Iterator = xpath.query("/productos/produc[precio = max(/productos/produc[cod_zona = 10]/precio)]").getIterator();
       while (Iterator.hasMoreResources()) {
           System.out.println();
           XMLResource res  = ((XMLResource) Iterator.nextResource());
           System.out.println(res .getContent());
       }       	   
  }
   
   
}
