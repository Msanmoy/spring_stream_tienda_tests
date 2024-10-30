use tienda;

/* Test 17 */
SELECT producto.nombre from producto where codigo_fabricante = 1 or codigo_fabricante = 3 or codigo_fabricante = 5;

/* Test 18 */
SELECT p.nombre, p.precio*100 from producto as p;


/* Test 22 */
SELECT p.nombre, p.precio FROM producto AS p WHERE p.precio >= 180 ORDER BY precio DESC, nombre ASC;