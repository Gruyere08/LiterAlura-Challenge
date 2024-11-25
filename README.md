# Challenge LiterAlura 📚

**Challenge LiterAlura** es una aplicación de consola desarrollada como parte de un desafío propuesto por **Alura Latam** en colaboración con el programa **Oracle Next Education (ONE)**. Este proyecto tiene como objetivo principal demostrar habilidades en desarrollo backend utilizando el **Spring Framework** y el lenguaje **Java**.

La aplicación fue diseñada para gestionar y explorar una base de datos de libros y autores, ofreciendo una experiencia rica en funcionalidades como búsquedas avanzadas, filtrado, paginación, y personalización de configuraciones. Además, incluye la capacidad de trabajar con datos locales, permitiendo agregar, eliminar y consultar libros y autores de forma dinámica.

---

## 🎯 Propósito del Proyecto

El propósito de este desafío es poner a prueba los conocimientos adquiridos en el desarrollo backend, específicamente:
- Implementación de **Spring Framework** para la creación de aplicaciones robustas y modulares.
- Gestión eficiente de bases de datos locales.
- Diseño de menús de navegación intuitivos y funcionales en una aplicación de consola.
- Incorporación de principios de desarrollo como modularidad, reusabilidad de código y buenas prácticas.

---

## ✨ Características Principales

- **Búsqueda de libros y autores**: Realiza consultas avanzadas basadas en palabras clave, año o idioma.
- **Listado de elementos registrados**: Explora libros y autores con datos enriquecidos, incluyendo popularidad y relación entre ellos.
- **Configuraciones personalizables**: Adapta la experiencia de la aplicación ajustando el número de elementos por página, filtrando datos, y más.
- **Gestión de datos locales**: Agrega, elimina y actualiza información almacenada en la base de datos.
- **Análisis y estadísticas**: Obtén insights como el TOP 5 de libros más descargados.

¡Explora las funciones completas del proyecto más abajo!

## 📖 Manual de Usuario:
## 🗂️ Opciones del Menú Principal:


![image](https://github.com/user-attachments/assets/0e3aa3e3-bad8-4393-aa98-db6eaa673e75)


## 🚀 Función 1: Búsqueda de Libros
![Captura de pantalla 2024-11-24 211918](https://github.com/user-attachments/assets/f1cb4624-43e1-4418-a3a9-5b37ad90ca92)
![Captura de pantalla 2024-11-24 210605](https://github.com/user-attachments/assets/1b8b172f-b0ea-4dea-8bb3-657cf73994e2)

Esta función permite a los usuarios realizar búsquedas de libros en una base de datos remota. Al ingresar una o varias palabras clave en el campo de búsqueda, la aplicación consulta la API y devuelve una lista de libros relevantes.

Los resultados de la búsqueda se presentan en una lista paginada, mostrando el título del libro y el autor. Luego de elegir el libro que desea, este será transformado y guardado en la base de datos local.

### 📱 Opciones de navegación:
- **Seleccion de un elemento:** Ingresa el número correspondiente a uno de los elementos mostrados para elegir dicho libro y guardarlo en la base de datos.
- **Página Anterior/Siguiente:** Navegar entre las diferentes páginas de resultados.
- **Seleccionar todos los elementos de esta página / Seleccionar TODOS los elementos:** Selecciona todos los libros de la página actual o de todas las páginas, respectivamente.
- **Salir:** Volver a la pantalla principal o cancelar la búsqueda.

---

## 📚 Funciones 2 y 3: Listado de Autores y Libros Registrados

![image](https://github.com/user-attachments/assets/4ca06dc7-4969-4f29-a9b9-50d24dd34e76)

![image](https://github.com/user-attachments/assets/959e2f32-2830-458e-a2da-41c26482abea)

![image](https://github.com/user-attachments/assets/1d99eafc-471f-4197-9422-ad318f64519b)

Las funciones 2 y 3 de la aplicación sirven para listar todos los autores y libros registrados en la base de datos local, respectivamente. Al seleccionar estas opciones, automáticamente se mostrará una lista de todos los autores o libros que hayan sido previamente registrados.

- **Libros** se muestran con su nombre, lista de autores y número de descargas.
- **Autores** se muestran con su nombre, período de vida y sus tres libros más populares.

### 📱 Opciones de navegación:
- **Página Anterior/Siguiente:** Permite navegar entre las distintas páginas de la lista.
- **Salir:** Regresar al menú principal.

---

## 🔍 Función 4: Búsqueda de Autores por Año

![Captura de pantalla 2024-11-24 214531](https://github.com/user-attachments/assets/307a0753-9159-497b-8767-01ef01080bf9)
![image](https://github.com/user-attachments/assets/cb77ea6d-8076-4392-b90e-03052f7716c3)

Esta función permite al usuario ingresar un año específico y, tras una búsqueda en la base de datos local, listar a todos los autores registrados que estuvieron vivos en dicho año.

### 📱 Opciones de navegación:
- **Página Anterior/Siguiente:** Permite navegar entre las distintas páginas de la lista.
- **Salir:** Regresar al menú principal.

---

## 🌍 Función 5: Búsqueda de Libros por Idioma

![image](https://github.com/user-attachments/assets/8bcbe7d2-736c-41e3-8e20-1915e2bef6bd)

![image](https://github.com/user-attachments/assets/71a418ef-cc24-4fcb-88df-2559b1dc3514)
![image](https://github.com/user-attachments/assets/300210e6-59d2-45bd-8b82-17f86bcbaa1c)

Esta función le permitirá al usuario buscar libros que estén disponibles en un idioma específico registrado en la base de datos local. Al seleccionar esta opción, se desplegará un menú con todas las opciones de idioma disponibles.

Una vez seleccionado el idioma, se mostrarán los libros disponibles en dicho idioma, mostrando:
- El nombre del libro.
- Los autores.
- La lista de idiomas en los que está disponible dicho libro.

### 📱 Opciones de navegación:
- **Seleccion de un elemento:** Ingresa el número correspondiente a uno de los elementos mostrados para elegir dicho libro.
- **Página Anterior/Siguiente:** Permite navegar entre las distintas páginas de la lista.
- **Salir:** Regresar al menú principal.

---

## 🏆 Función 6: Mostrar TOP 5 Libros Más Descargados

![Captura de pantalla 2024-11-24 220455](https://github.com/user-attachments/assets/36022056-b9b6-4d11-8934-97c92af5a16c)

Esta función permite mostrar el **TOP 5** de los libros con más descargas registrados en la base de datos. Al seleccionar esta opción, se listarán los 5 libros más descargados de la base de datos. Si no hay ningún libro registrado, no se mostrará nada.

### 📱 Opciones de navegación:
- **Ninguna.**

---

## ⚙️ Función 7: Configuraciones

Esta opción abre el menú de configuraciones.


![image](https://github.com/user-attachments/assets/f441f795-05c9-4f9f-873a-c96e7e7f97c2)


## ⚙️ Opciones del Menú de Configuraciones

A continuación, se detallan todas las opciones disponibles dentro del menú de configuraciones:

---

### 🖋️ Opción 1 y 2: Ocultar Libros sin Autores y Ocultar Autores sin Libros

![image](https://github.com/user-attachments/assets/de4340bc-fddd-44cc-ab8c-9b00fc2c733a)

Estas opciones permiten ocultar elementos de la base de datos que no tengan relación con otros elementos. Esto ayuda a mantener las listas más limpias y enfocadas en elementos útiles.

- **Opción 1:** Excluirá los **Libros sin autores** cuando se use la función "Listar libros registrados".
- **Opción 2:** Excluirá los **Autores sin libros** al utilizar la función "Listar autores registrados".

Seleccionar cualquiera de estas opciones alternará su estado entre **SI** y **NO**, según la preferencia del usuario.

---

### 🔢 Opción 3: Cambiar Número de Elementos por Página en Búsquedas

![image](https://github.com/user-attachments/assets/4254d512-5c94-479c-95d4-46beb95a47aa)
![image](https://github.com/user-attachments/assets/c7fed64f-3ce0-4046-90b5-c030b0f14ef7)
*Menu de idiomas con 3 elementos por pagina*
![image](https://github.com/user-attachments/assets/88053196-ed56-4439-990f-cfdd2773d1d9)
*Menu de idiomas con 10 elementos por pagina*

Esta opción permite personalizar cuántos elementos se mostrarán por página durante las búsquedas y listados de la aplicación. Es útil para ajustar la visualización según las preferencias del usuario.

- El valor predeterminado es **5** elementos por página.
- Al seleccionar esta opción, se podrá ingresar un nuevo valor. Este cambio se guardará automáticamente en un archivo de configuraciones y se recordará en la próxima sesión.

---

### 🗑️ Opción 4 y 5: Eliminar Libros y Autores

![image](https://github.com/user-attachments/assets/4bb86ce0-9b95-4e9c-ab29-244b482a0ea4)
![image](https://github.com/user-attachments/assets/001e8f66-4d11-4c0f-80b2-cafe34fa1bbf)

Estas opciones permiten eliminar elementos de la base de datos local de forma manual. 

- **Opción 4:** Eliminar libros.
- **Opción 5:** Eliminar autores.

Al seleccionar cualquiera de estas opciones, se desplegará un menú con los elementos disponibles. El usuario podrá navegar por este menú y seleccionar los elementos que desea eliminar.

#### 📱 Opciones de navegación para eliminar elementos:
- **Selección de un elemento:** Ingrese el número correspondiente al elemento que desea eliminar.
- **Página Anterior/Siguiente:** Navegar entre las páginas de resultados.
- **Seleccionar todos los elementos de esta página / Seleccionar TODOS los elementos:** Selecciona todos los elementos visibles en la página actual o en todas las páginas.
- **Salir:** Regresar al menú principal sin realizar cambios.

---

### 🧹 Opción 6: Limpiar Base de Datos

Esta opción permite **eliminar todos los elementos** de todas las tablas de la base de datos. Está diseñada para fines de prueba o para realizar una limpieza completa.

⚠️ **Advertencia:** Al seleccionar esta opción, todos los datos serán eliminados de forma permanente.

---

Cada una de estas opciones del menú de configuraciones ofrece flexibilidad al usuario para personalizar la experiencia y administrar la base de datos de manera efectiva.

