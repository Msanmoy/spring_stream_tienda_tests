use tienda;

/* Test 1 */

/* Test 2 */

/* Test 3 */

/* Test 4 */

/* Test 5 */

/* Test 6 */

/* Test 7 */

/* Test 8 */
SELECT * from fabricante limit 5;

/* Test 9 */
SELECT * from fabricante limit 2 offset 3;

/* Test 10 */
SELECT p.nombre, p.precio FROM producto as p WHERE p.precio = (SELECT MIN(precio) FROM producto);

/* Test 11 */
SELECT p.nombre, p.precio FROM producto as p WHERE p.precio = (SELECT MAX(precio) FROM producto);

/* Test 12 */
SELECT producto.nombre from producto where codigo_fabricante = 2 ;

/* Test 13 */
SELECT producto.nombre from producto where precio <= 120;

/* Test 14 */
SELECT * from producto where precio >= 400;

/* Test 15 */
SELECT * from producto where precio > 80 AND precio < 300;

/* Test 16 */
SELECT * from producto where precio > 200 AND codigo_fabricante = 6;

/* Test 17 */
SELECT producto.nombre from producto where codigo_fabricante = 1 or codigo_fabricante = 3 or codigo_fabricante = 5;

/* Test 18 */
SELECT p.nombre, p.precio*100 from producto as p;

/* Test 19 */
SELECT f.nombre from fabricante as f where nombre LIKE 's%' OR nombre LIKE 'S%' ;

/* Test 20 */

/* Test 21 */

/* Test 22 */
SELECT p.nombre, p.precio FROM producto AS p WHERE p.precio >= 180 ORDER BY precio DESC, nombre ASC;

/* Test 23 */
SELECT p.nombre, p.precio, f.nombre from producto as p inner join fabricante as f on p.codigo_fabricante = f.codigo order by f.nombre;

/* Test 24 */
SELECT p.nombre, p.precio, f.nombre from producto as p inner join fabricante as f on p.codigo_fabricante = f.codigo order by precio DESC limit 1;

/* Test 25 */
SELECT p.nombre from producto as p join tienda.fabricante as f on p.codigo_fabricante = f.codigo where precio >= 200 AND f.nombre LIKE 'Crucial';

/* Test 26 */
SELECT * FROM producto as p join tienda.fabricante f on f.codigo = p.codigo_fabricante Where f.nombre in ('Asus','Hewlett-Packard','Seagate');

/* Test 27 */
SELECT p.nombre, p.precio FROM producto AS p JOIN tienda.fabricante f on f.codigo = p.codigo_fabricante WHERE p.precio >= 180 ORDER BY p.precio DESC, p.nombre ASC;

/* Test 28 */
    SELECT fabricante.nombre, producto.nombre FROM fabricante join producto on fabricante.codigo = producto.codigo_fabricante;

/* Test 29 */

/* Test 30 */