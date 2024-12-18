package org.iesvdm.tienda;

import com.sun.source.tree.AssertTree;
import org.iesvdm.tienda.modelo.Fabricante;
import org.iesvdm.tienda.modelo.Producto;
import org.iesvdm.tienda.repository.FabricanteRepository;
import org.iesvdm.tienda.repository.ProductoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;


@SpringBootTest
class TiendaApplicationTests {

	@Autowired
	FabricanteRepository fabRepo;
	
	@Autowired
	ProductoRepository prodRepo;
    @Autowired
    private ProductoRepository productoRepository;

	@Test
	void testAllFabricante() {
		var listFabs = fabRepo.findAll();
		
		listFabs.forEach(f -> {
			System.out.println(">>"+f+ ":");
			f.getProductos().forEach(System.out::println);
		});
	}
	
	@Test
	void testAllProducto() {
		var listProds = prodRepo.findAll();

		listProds.forEach( p -> {
			System.out.println(">>"+p+":"+"\nProductos mismo fabricante "+ p.getFabricante());
			p.getFabricante().getProductos().forEach(pF -> System.out.println(">>>>"+pF));
		});
				
	}

	
	/**
	 * 1. Lista los nombres y los precios de todos los productos de la tabla producto
	 */
	@Test
	void test1() {
		var listProds = prodRepo.findAll();
		listProds.forEach( p -> {
			System.out.println("Fabricante: "+ p.getFabricante() + " Precio Producto: " + p.getPrecio());
		});
	}
	
	
	/**
	 * 2. Devuelve una lista de Producto completa con el precio de euros convertido a dólares .
	 */
	@Test
	void test2() {
		var listProds = prodRepo.findAll();
		listProds.forEach( p -> {
			System.out.println("Producto: "+ p.getNombre() + " Precio: "+ p.getPrecio()*1.08 + "$"); //1.08 es a como esta el dólar a día de hoy.
		});
	}
	
	/**
	 * 3. Lista los nombres y los precios de todos los productos, convirtiendo los nombres a mayúscula.
	 */
	@Test
	void test3() {
		var listProds = prodRepo.findAll();
		listProds.forEach( p -> {
			System.out.println("Producto: "+ p.getNombre().toUpperCase() + " Precio: "+ p.getPrecio());
		});
	}
	
	/**
	 * 4. Lista el nombre de todos los fabricantes y a continuación en mayúsculas los dos primeros caracteres del nombre del fabricante.
	 */
	@Test
	void test4() {
		var listFabs = fabRepo.findAll();
		var result = listFabs.stream();
		result.forEach(fabricante -> {
			String nombre = fabricante.getNombre();
			System.out.println(nombre + " - " + nombre.substring(0,2).toUpperCase());
		});

		}
	/**
	 * 5. Lista el código de los fabricantes que tienen productos.
	 */
	@Test
	void test5() {
		var listFabs = fabRepo.findAll();
		var result = listFabs.stream()
				.filter(f -> !f.getProductos().isEmpty())
				.toList();

		result.forEach(resul -> System.out.println("Código: " + resul.getCodigo()));
	}
	
	/**
	 * 6. Lista los nombres de los fabricantes ordenados de forma descendente.
	 */
	@Test
	void test6() {
		var listFabs = fabRepo.findAll();
		var result = listFabs.stream()
				.sorted(comparing(Fabricante::getNombre).reversed())
				.toList();
		result.forEach(resul -> System.out.println("Fabricante: " + resul.getNombre()));
	}
	
	/**
	 * 7. Lista los nombres de los productos ordenados en primer lugar por el nombre de forma ascendente y en segundo lugar por el precio de forma descendente.
	 */
	@Test
	void test7() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.sorted(comparing(Producto::getNombre)
				.thenComparing(Producto::getPrecio, reverseOrder()))
				.toList();
		result.forEach(resul -> System.out.println("Producto: " + resul.getNombre()));
	}
	
	/**
	 * 8. Devuelve una lista con los 5 primeros fabricantes.
	 */
	@Test
	void test8() {
		var listFabs = fabRepo.findAll();
		var result = listFabs.stream().toList();
		for (int i = 0; i < 5; i++) {
			System.out.println("Fabricante: " + result.get(i).getNombre());
		}

	}
	
	/**
	 * 9.Devuelve una lista con 2 fabricantes a partir del cuarto fabricante. El cuarto fabricante también se debe incluir en la respuesta.
	 */
	@Test
	void test9() {
		var listFabs = fabRepo.findAll();
		var l = listFabs.stream()
				.skip(3)
				.limit(2)
				.toList();
		System.out.println(l);

		Assertions.assertEquals(l.get(0).getNombre() , "Samsung");

	}
	
	/**
	 * 10. Lista el nombre y el precio del producto más barato
	 */
	@Test
	void test10() {
		var listProds = prodRepo.findAll();
		var productoBarato = listProds.stream()
				.sorted(comparing(Producto::getPrecio))
				.map(producto -> "Nombre = " + producto.getNombre() + " precio = " + producto.getPrecio())
				.limit(1)
				.findAny();
		System.out.println(productoBarato);
		Assertions.assertTrue(productoBarato.orElse("").contains("59.99"));
	}
	
	/**
	 * 11. Lista el nombre y el precio del producto más caro
	 */
	@Test
	void test11() {
		var listProds = prodRepo.findAll();
		listProds.stream()
				.max(comparing(Producto::getPrecio))
				.ifPresentOrElse(producto -> System.out.println(producto.getNombre() + producto.getPrecio()),
				() -> System.out.println("Colección Vacia"));
	}
	
	/**
	 * 12. Lista el nombre de todos los productos del fabricante cuyo código de fabricante es igual a 2.
	 * 
	 */
	@Test
	void test12() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.filter(p -> p.getFabricante().getCodigo() == 2)
				.map(Producto::getNombre)
				.toList();
		System.out.println(result);


	}
	
	/**
	 * 13. Lista el nombre de los productos que tienen un precio menor o igual a 120€.
	 */
	@Test
	void test13() {
		var listProds = prodRepo.findAll();
		var filtro = listProds.stream()
				.filter(producto -> producto.getPrecio() <= 120)
				.toList();

		filtro.forEach(producto -> System.out.println(producto.getNombre()));

		Assertions.assertEquals(3, filtro.size());
	}
	
	/**
	 * 14. Lista los productos que tienen un precio mayor o igual a 400€.
	 */
	@Test
	void test14() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.filter(producto -> producto.getPrecio() >= 400)
				.toList();

		result.forEach(producto -> System.out.println(producto.getNombre()));
	}
	
	/**
	 * 15. Lista todos los productos que tengan un precio entre 80€ y 300€. 
	 */
	@Test
	void test15() {
		var listProds = prodRepo.findAll();
		var filtro = listProds.stream()
				.filter(producto -> producto.getPrecio() <= 300 && producto.getPrecio() >= 80)
				.toList();

		filtro.forEach(producto -> System.out.println(producto.getNombre()));

	}
	
	/**
	 * 16. Lista todos los productos que tengan un precio mayor que 200€ y que el código de fabricante sea igual a 6.
	 */
	@Test
	void test16() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.filter(producto -> producto.getPrecio() > 200 && producto.getCodigo() == 6)
				.toList();

		result.forEach(producto -> System.out.println(producto.getNombre()));

		}

	
	/**
	 * 17. Lista todos los productos donde el código de fabricante sea 1, 3 o 5 utilizando un Set de codigos de fabricantes para filtrar.
	 */
	@Test
	void test17() {
		var listProds = prodRepo.findAll();
		Set<Integer> codigo = Set.of(1,3,5);

		var result = listProds.stream()
				.filter(producto -> codigo.contains(producto.getCodigo()))
				.toList();

		result.forEach(producto -> System.out.println(producto.getNombre()));

	}
	
	/**
	 * 18. Lista el nombre y el precio de los productos en céntimos.
	 */
	@Test
	void test18() {
		var listProds = prodRepo.findAll();
		var result = listProds.parallelStream()
				.map(producto -> "Nombre = " + producto.getNombre() + " precio = " + producto.getPrecio()*100)
				.toList();

		result.forEach(s -> System.out.println(s));
		Assertions.assertEquals(11, result.size());
	}
	
	
	/**
	 * 19. Lista los nombres de los fabricantes cuyo nombre empiece por la letra S
	 */
	@Test
	void test19() {
		var listFabs = fabRepo.findAll();
		var result = listFabs.stream()
				.filter(fabricante -> fabricante.getNombre().substring(0,1).equalsIgnoreCase("s"))
				.toList();
		result.forEach(producto -> System.out.println(producto.getNombre()));

		Assertions.assertEquals(2, result.size());
		Assertions.assertTrue(result.stream().anyMatch(fabricante -> fabricante.getNombre().equalsIgnoreCase("Samsung")));
		Assertions.assertTrue(result.stream().anyMatch(fabricante -> fabricante.getNombre().equalsIgnoreCase("Seagate")));
	}
	
	/**
	 * 20. Devuelve una lista con los productos que contienen la cadena Portátil en el nombre.
	 */
	@Test
	void test20() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.filter(producto -> producto.getNombre().contains("Portátil"))
				.toList();
		result.forEach(producto -> System.out.println(producto.getNombre()));

		Assertions.assertEquals(2, result.size());
	}
	
	/**
	 * 21. Devuelve una lista con el nombre de todos los productos que contienen la cadena Monitor en el nombre y tienen un precio inferior a 215 €.
	 */
	@Test
	void test21() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.filter(producto -> producto.getPrecio() <= 215 && producto.getNombre().contains("Monitor"))
				.toList();
		result.forEach(producto -> System.out.println(producto.getNombre()));

		Assertions.assertEquals(1, result.size());
	}
	
	/**
	 * 22. Lista el nombre y el precio de todos los productos que tengan un precio mayor o igual a 180€. 
	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre (en orden ascendente).
	 */

	@Test
	void test22() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.filter(producto -> producto.getPrecio() >= 180)
				.sorted(comparing(Producto::getPrecio))

				.toList();

		result.forEach(producto -> System.out.println(producto.getNombre()));
		result.forEach(producto -> System.out.println(producto.getPrecio()));
	}
	
	/**
	 * 23. Devuelve una lista con el nombre del producto, precio y nombre de fabricante de todos los productos de la base de datos. 
	 * Ordene el resultado por el nombre del fabricante, por orden alfabético.
	 */
	@Test
	void test23() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.sorted(comparing(producto -> producto.getFabricante().getNombre()))
				.map(producto -> producto.getNombre() + " " + producto.getPrecio() + " " + producto.getFabricante().getNombre())
				.toList();
		result.forEach(System.out::println);

	Assertions.assertEquals(11, result.size());
	}
	
	/**
	 * 24. Devuelve el nombre del producto, su precio y el nombre de su fabricante, del producto más caro.
	 */
	@Test
	void test24() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.max(comparing(Producto::getPrecio))
				.stream().toList();
		result.forEach(producto -> System.out.println(producto.getNombre() + " " + producto.getPrecio() + " " + producto.getFabricante().getNombre()));

		Assertions.assertEquals(1, result.size());

	}
	
	/**
	 * 25. Devuelve una lista de todos los productos del fabricante Crucial que tengan un precio mayor que 200€.
	 */
	@Test
	void test25() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.filter(producto -> producto.getFabricante().getNombre().equals("Crucial") && producto.getPrecio() > 200)
				.toList();

		System.out.println(result);
		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals(755.0, result.getFirst().getPrecio());
	}
	
	/**
	 * 26. Devuelve un listado con todos los productos de los fabricantes Asus, Hewlett-Packard y Seagate
	 */
	@Test
	void test26() {
		var listProds = prodRepo.findAll();
		Set<String> setFabricantes = new HashSet<>();
		setFabricantes.add("Asus");
		setFabricantes.add("Hewlett-Packard");
		setFabricantes.add("Seagate");

		var result = listProds.stream()
				.filter(fabricante -> setFabricantes.contains(fabricante.getFabricante().getNombre()))
				.toList();

		result.forEach(producto -> System.out.println(producto.getNombre()));

		Assertions.assertEquals(5, result.size());
		Assertions.assertTrue(result.stream().anyMatch(producto -> producto.getFabricante().getNombre().equalsIgnoreCase("Hewlett-Packard")));
		Assertions.assertTrue(result.stream().anyMatch(producto -> producto.getFabricante().getNombre().equalsIgnoreCase("Seagate")));
		Assertions.assertTrue(result.stream().anyMatch(producto -> producto.getFabricante().getNombre().equalsIgnoreCase("Asus")));
	}
	
	/**
	 * 27. Devuelve un listado con el nombre de producto, precio y nombre de fabricante, de todos los productos que tengan un precio mayor o igual a 180€. 
	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre.
	 * El listado debe mostrarse en formato tabla. Para ello, procesa las longitudes máximas de los diferentes campos a presentar y compensa mediante la inclusión de espacios en blanco.
	 * La salida debe quedar tabulada como sigue:

Producto                Precio             Fabricante
-----------------------------------------------------
GeForce GTX 1080 Xtreme|611.5500000000001 |Crucial
Portátil Yoga 520      |452.79            |Lenovo
Portátil Ideapd 320    |359.64000000000004|Lenovo
Monitor 27 LED Full HD |199.25190000000003|Asus

	 */		
	@Test
	void test27() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.filter(p -> p.getPrecio() >= 180)
				.sorted(comparing(Producto::getPrecio, reverseOrder()).thenComparing(producto -> producto.getFabricante().getNombre()))
				.map(producto -> producto.getNombre() + "|" + producto.getPrecio() + "|" + producto.getFabricante().getNombre())
				.toList();
		System.out.println("Producto                    Precio                    Fabricante\n");
		result.forEach(System.out::println);

		Assertions.assertTrue(result.size() < listProds.size());

	}
	
	/**
	 * 28. Devuelve un listado de los nombres fabricantes que existen en la base de datos, junto con los nombres productos que tiene cada uno de ellos. 
	 * El listado deberá mostrar también aquellos fabricantes que no tienen productos asociados. 
	 * SÓLO SE PUEDEN UTILIZAR STREAM, NO PUEDE HABER BUCLES
	 * La salida debe queda como sigue:
Fabricante: Asus

            	Productos:
            	Monitor 27 LED Full HD
            	Monitor 24 LED Full HD

Fabricante: Lenovo

            	Productos:
            	Portátil Ideapd 320
            	Portátil Yoga 520

Fabricante: Hewlett-Packard

            	Productos:
            	Impresora HP Deskjet 3720
            	Impresora HP Laserjet Pro M26nw

Fabricante: Samsung

            	Productos:
            	Disco SSD 1 TB

Fabricante: Seagate

            	Productos:
            	Disco duro SATA3 1TB

Fabricante: Crucial

            	Productos:
            	GeForce GTX 1080 Xtreme
            	Memoria RAM DDR4 8GB

Fabricante: Gigabyte

            	Productos:
            	GeForce GTX 1050Ti

Fabricante: Huawei

            	Productos:


Fabricante: Xiaomi

            	Productos:

	 */
	@Test
	void test28() {
		var listFabs = fabRepo.findAll();
		var result = listFabs.stream()
				.map(fabricante -> "Fabricante: " + fabricante.getNombre() + "\n\n"
									+ "Productos: " + "\n"
						+ fabricante.getProductos()
						.stream().map(producto -> producto.getNombre()+"\n")
						.collect(Collectors.joining()))
				.toList();
		result.forEach(System.out::println);

	}
	
	/**
	 * 29. Devuelve un listado donde sólo aparezcan aquellos fabricantes que no tienen ningún producto asociado.
	 */
	@Test
	void test29() {
		var listFabs = fabRepo.findAll();
		var result = listFabs.stream()
				.filter(fabricante -> fabricante.getProductos().isEmpty())
				.toList();

		result.forEach(fabricante -> System.out.println(fabricante.getNombre()));

		Assertions.assertEquals(2, result.size());
	}
	
	/**
	 * 30. Calcula el número total de productos que hay en la tabla productos. Utiliza la api de stream.
	 */
	@Test
	void test30() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.count();

		System.out.println(result);

		Assertions.assertEquals(11, result);

	}

	
	/**
	 * 31. Calcula el número de fabricantes con productos, utilizando un stream de Productos.
	 */
	@Test
	void test31() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.map(Producto::getFabricante)
				.distinct()
				.count();

		System.out.println(result);

		Assertions.assertEquals(7, result);
	}
	
	/**
	 * 32. Calcula la media del precio de todos los productos
	 */
	@Test
	void test32() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.mapToDouble(Producto::getPrecio)
				.average();

		System.out.println(result);

	}
	
	/**
	 * 33. Calcula el precio más barato de todos los productos. No se puede utilizar ordenación de stream.
	 */
	@Test
	void test33() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.mapToDouble(value -> value.getPrecio())
				.min();

		System.out.println(result);
		Assertions.assertFalse(result.isEmpty());
	}
	
	/**
	 * 34. Calcula la suma de los precios de todos los productos.
	 */
	@Test
	void test34() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.mapToDouble(Producto::getPrecio)
				.sum();

		System.out.println(result);


	}
	
	/**
	 * 35. Calcula el número de productos que tiene el fabricante Asus.
	 */
	@Test
	void test35() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.map(Producto -> Objects.equals(Producto.getFabricante().getNombre(), "Asus"))
				.distinct()
				.count();

		System.out.println(result);
		Assertions.assertEquals(2, result);
	}
	
	/**
	 * 36. Calcula la media del precio de todos los productos del fabricante Asus.
	 */
	@Test
	void test36() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.filter(producto -> producto.getFabricante().getNombre().equals("Asus"))
				.mapToDouble(Producto::getPrecio)
				.average()
				.orElse(0);

		System.out.println(result);
	}
	
	
	/**
	 * 37. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos que tiene el fabricante Crucial. 
	 *  Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 */
	@Test
	void test37() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.filter(producto -> producto.getFabricante().getNombre().equals("Crucial"))
				.map(producto -> new Double[]{
						producto.getPrecio(), producto.getPrecio(), producto.getPrecio(), 1.0})
				.reduce((doubles, doubles2) -> new Double[]{
						Math.min(doubles[0], doubles2[0]),
						Math.max(doubles[1], doubles2[1]),
						doubles[2] + doubles2[2],
						doubles[3]+doubles2[3]
				})
				.orElse(new Double[]{});

		Double media = result[3]>0 ? result[2]/result[3]: 0.0;

		System.out.println("El valor minimo: " + result[0]);
		System.out.println("El valor maximo: " + result[1]);
		System.out.println("El valor medio: " + result[2]);
		System.out.println("Productos que tiene Crucial: " + result[3]);

	}
	
	/**
	 * 38. Muestra el número total de productos que tiene cada uno de los fabricantes. 
	 * El listado también debe incluir los fabricantes que no tienen ningún producto. 
	 * El resultado mostrará dos columnas, una con el nombre del fabricante y otra con el número de productos que tiene. 
	 * Ordene el resultado descendentemente por el número de productos. Utiliza String.format para la alineación de los nombres y las cantidades.
	 * La salida debe queda como sigue:
	 
     Fabricante     #Productos
-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
           Asus              2
         Lenovo              2
Hewlett-Packard              2
        Samsung              1
        Seagate              1
        Crucial              2
       Gigabyte              1
         Huawei              0
         Xiaomi              0

	 */
	@Test
	void test38() {
		var listFabs = fabRepo.findAll();
		// Imprimimos el encabezado
		System.out.println("Fabricante       #Productos");
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");

		var result = listFabs.stream()
				.map(fabricante -> Map.entry(fabricante.getNombre(),
						fabricante.getProductos() != null ? fabricante.getProductos().size() : 0))
				.sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()))
				.toList();

		for (var entry : result) {
			System.out.println(String.format("%-15s %10d", entry.getKey(), entry.getValue()));
		}

	}
	
	/**
	 * 39. Muestra el precio máximo, precio mínimo y precio medio de los productos de cada uno de los fabricantes. 
	 * El resultado mostrará el nombre del fabricante junto con los datos que se solicitan. Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 * Deben aparecer los fabricantes que no tienen productos.
	 */
	@Test
	void test39() {
		var listFabs = fabRepo.findAll();

		System.out.println("Fabricante         Precio Mínimo     Precio Máximo     Precio Medio");
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");

		listFabs.stream().forEach(fabricante -> {
			Double[] stats = fabricante.getProductos().stream()
					.map(producto -> producto.getPrecio())
					.reduce(new Double[]{Double.MAX_VALUE, Double.MIN_VALUE, 0.0, 0.0},
							(acc, precio) -> {
								acc[0] = Math.min(acc[0], precio);
								acc[1] = Math.max(acc[1], precio);
								acc[2] += precio;
								acc[3] += 1;
								return acc;
							},
							(acc1, acc2) -> acc1
					);

			double precioMedio = stats[3] > 0 ? stats[2] / stats[3] : 0.0;
			double precioMin = stats[3] > 0 ? stats[0] : 0.0;
			double precioMax = stats[3] > 0 ? stats[1] : 0.0;


			System.out.println(String.format("%-15s %15.2f %15.2f %15.2f",
					fabricante.getNombre(), precioMin, precioMax, precioMedio));
		});

	}
	
	/**
	 * 40. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos de los fabricantes que tienen un precio medio superior a 200€. 
	 * No es necesario mostrar el nombre del fabricante, con el código del fabricante es suficiente.
	 */
	@Test
	void test40() {
		var listFabs = fabRepo.findAll();
		System.out.println("Fabricante         Precio Mínimo     Precio Máximo     Precio Medio");
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");

		listFabs.stream().forEach(fabricante -> {
			Double[] stats = fabricante.getProductos().stream()
					.filter(p -> p.getPrecio() > 200)
					.map(producto -> producto.getPrecio())
					.reduce(new Double[]{Double.MAX_VALUE, Double.MIN_VALUE, 0.0, 0.0},
							(acc, precio) -> {
								acc[0] = Math.min(acc[0], precio);
								acc[1] = Math.max(acc[1], precio);
								acc[2] += precio;
								acc[3] += 1;
								return acc;
							},
							(acc1, acc2) -> acc1
					);

			double precioMedio = stats[3] > 0 ? stats[2] / stats[3] : 0.0;
			double precioMin = stats[3] > 0 ? stats[0] : 0.0;
			double precioMax = stats[3] > 0 ? stats[1] : 0.0;


			System.out.println(String.format("%-15s %15.2f %15.2f %15.2f",
					fabricante.getCodigo(), precioMin, precioMax, precioMedio));
		});


	}
	
	/**
	 * 41. Devuelve un listado con los nombres de los fabricantes que tienen 2 o más productos.
	 */
	@Test
	void test41() {
		var listFabs = fabRepo.findAll();
		var result = listFabs.stream()
				.filter(fabricante -> fabricante.getProductos().size() >= 2)
				.toList();

		result.forEach(System.out::println);
	}
	
	/**
	 * 42. Devuelve un listado con los nombres de los fabricantes y el número de productos que tiene cada uno con un precio superior o igual a 220 €. 
	 * Ordenado de mayor a menor número de productos.
	 */
	@Test
	void test42() {
		var listFabs = fabRepo.findAll();
		var result =  listFabs.stream()
				.map(fabricante -> Map.entry(
						fabricante.getNombre(),
						(int) fabricante.getProductos().stream()
								.filter(producto -> producto.getPrecio() >= 220)
								.count()
				))
				.filter(stringIntegerEntry -> stringIntegerEntry.getValue() > 0)
						.sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()))
				.toList();

		result.forEach(System.out::println);


	}
	
	/**
	 * 43.Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 */
	@Test
	void test43() {
		var listFabs = fabRepo.findAll();
		var result = listFabs.stream()
				.filter(fabricante -> fabricante.getProductos().stream()
								.mapToDouble(producto -> producto.getPrecio())
								.sum() > 1000
				)
				.map(fabricante -> fabricante.getNombre())
				.collect(Collectors.toList());

		result.forEach(System.out::println);

		Assertions.assertEquals("Lenovo",result.get(0));


	}
	
	/**
	 * 44. Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 * Ordenado de menor a mayor por cuantía de precio de los productos.
	 */
	@Test
	void test44() {
		var listFabs = fabRepo.findAll();
		var result = listFabs.stream()
				.filter(fabricante -> fabricante.getProductos().stream()
						.mapToDouble(producto -> producto.getPrecio())
						.sum() > 1000
				)
				.sorted(Comparator.comparingDouble(fabricante ->
						fabricante.getProductos().stream()
								.mapToDouble(producto -> producto.getPrecio())
								.sum()
				))
				.map(fabricante -> fabricante.getNombre())
				.collect(Collectors.toList());

		result.forEach(System.out::println);

		Assertions.assertEquals("Lenovo",result.get(0));
	}
	
	/**
	 * 45. Devuelve un listado con el nombre del producto más caro que tiene cada fabricante. 
	 * El resultado debe tener tres columnas: nombre del producto, precio y nombre del fabricante. 
	 * El resultado tiene que estar ordenado alfabéticamente de menor a mayor por el nombre del fabricante.
	 */
	@Test
	void test45() {
		var listFabs = fabRepo.findAll();
		var result = listFabs.stream()
				.flatMap(fabricante -> fabricante.getProductos().stream()
						.map(producto -> Map.of(
								"nombreProducto", producto.getNombre(),
								"precio", producto.getPrecio(),
								"nombreFabricante", fabricante.getNombre()
						))
				)
				.collect(Collectors.groupingBy(
						productoMap -> productoMap.get("nombreFabricante"),
						Collectors.reducing((p1, p2) ->
								(Double) p1.get("precio") >= (Double) p2.get("precio") ? p1 : p2
						)
				))
				.values().stream()
				.filter(Optional::isPresent)
				.map(Optional::get)
				.sorted(Comparator.comparing(o -> (String) o.get("nombreFabricante")))
				.collect(Collectors.toList());

		result.forEach(item ->
				System.out.println("Producto: " + item.get("nombreProducto") +
						", Precio: " + item.get("precio") +
						", Fabricante: " + item.get("nombreFabricante"))
		);
	}
	
	/**
	 * 46. Devuelve un listado de todos los productos que tienen un precio mayor o igual a la media de todos los productos de su mismo fabricante.
	 * Se ordenará por fabricante en orden alfabético ascendente y los productos de cada fabricante tendrán que estar ordenados por precio descendente.
	 */
	@Test
	void test46() {
		var listFabs = fabRepo.findAll();
		var promedioPorFabricante = listFabs.stream()
				.collect(Collectors.toMap(
						fabricante -> fabricante.getNombre(),
						fabricante -> fabricante.getProductos().stream()
								.mapToDouble(producto -> producto.getPrecio())
								.average().orElse(0) // Media de precios por fabricante
				));

		// Paso 2: Filtrar productos que tienen un precio mayor o igual a la media
		var result = listFabs.stream()
				.flatMap(fabricante -> fabricante.getProductos().stream()
						.filter(producto -> producto.getPrecio() >= promedioPorFabricante.get(fabricante.getNombre())) // Filtramos por precio
						.map(producto -> Map.of(
								"nombreProducto", producto.getNombre(),
								"precio", producto.getPrecio(),
								"nombreFabricante", fabricante.getNombre()
						))
				)
				.sorted((o1, o2) -> o2.size() - o1.size())

				.collect(Collectors.toList());

		// Mostrar resultados
		result.forEach(item ->
				System.out.println("Producto: " + item.get("nombreProducto") +
						", Precio: " + item.get("precio") +
						", Fabricante: " + item.get("nombreFabricante"))
		);
	}

}
