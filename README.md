# LiterAlura-Challenge

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



## Opciones del menú de configuraciones

A continuación se detallarán todas las opciones que se encuentran dentro del menú de configuraciones:

### Opción 1 y 2: Ocultar Libros sin autores y Ocultar autores sin libros

![image](https://github.com/user-attachments/assets/de4340bc-fddd-44cc-ab8c-9b00fc2c733a)

Estas dos opciones sirven para ocultar de las búsquedas a elementos de la base de datos que no tengan otros elementos relacionados.

- **Opción 1:** Excluirá los **Libros sin autores** cuando se utilice la función "Listar libros registrados".
- **Opción 2:** Excluirá los **Autores sin libros** cuando se use la función "Listar Autores registrados" del menú principal.

Seleccionar cualquiera de estas dos opciones hará que el valor alterne entre **SI** y **NO**.


### Opción 3: Cambiar número de elementos por página en búsquedas

![image](https://github.com/user-attachments/assets/4254d512-5c94-479c-95d4-46beb95a47aa)

![image](https://github.com/user-attachments/assets/c7fed64f-3ce0-4046-90b5-c030b0f14ef7)
![image](https://github.com/user-attachments/assets/88053196-ed56-4439-990f-cfdd2773d1d9)




Esta opción sirve para alterar la cantidad de elementos que se mostrarán por página durante la mayoría de funciones de la aplicación. Esto hará posible mostrar más elementos si se desea o mostrar menos si se quiere que todos los elementos quepan en la pantalla.

El valor predeterminado de elementos por pantalla es **5**. Si desea cambiar el valor, debe seleccionar la opción e ingresar el nuevo valor. Este valor se guardará en un archivo de configuraciones y será recordado en la próxima sesión.
