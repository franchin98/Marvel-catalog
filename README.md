# Marvel Catalog

Marvel catalog es una aplicación desarrollada para un trabajo práctico de la Universidad Nacional de La Matanza

## ¿Cómo funciona?

Utilizamos la API de Marvel para extraer la información. [Marvel Developer](https://developer.marvel.com/)

## Funcionalidades
La aplicación en una primera instalación, realiza una llamada para extraer los personajes al Endpoint /characters.
Estos personajes, se almacenan en una base de datos local de SqlDelight, filtrando aquellos personajes que no tienen imagen disponible.

Los personajes almacenados se muestran en la primer pantalla.

Se puede realizar una búsqueda de personajes por su nombre en la segunda pantalla. **__Se necesita conexión a Internet.__**


### Actualización de personajes
Al scrollear desde arriba hacia abajo, se visualizará un PullRefreshIndicator. 
La extracción de datos se hará aleatoriamente con una letra del abecedario, que se envía por parametro(nameStartsWith) en la URL.


