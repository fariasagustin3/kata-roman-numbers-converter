# Kata Números romanos
## Desarrollo de la Fase 1 y la Fase 2

El desafío se divide en dos fases con la fase 2 opcional, es por eso que se tomó la decisión de desarrollar un entregable para
ambas fases respetando los requerimientos para cada uno, realizando tests unitarios y de integración para los métodos del
componente. Debajo de los requerimientos se podrá encontrar la documentación y las instrucciones para correr el
proyecto exitosamente junto con sus tests.

## Requerimientos

### Fase 1

Desarrollar un componente que permita convertir números enteros a romanos y viceversa según el siguiente esquema: 

* 1 ➔ I
* 2 ➔ II
* 3 ➔ III
* 4 ➔ IV
* 5 ➔ V
* 9 ➔ IX
* 21 ➔ XXI
* 50 ➔ L
* 100 ➔ C
* 500 ➔ D
* 1000 ➔ M


En ambos métodos de conversión, el componente debe validar si se ingresa un valor no permitido y responder con una excepción personalizada. 

**Plus Fase 1:** Aplicar TDD o al menos hacer Tests unitarios del componente probando al menos 2 border cases para cada método de conversión.


### Fase 2 

Exponer el método del componente que convierte valores numéricos arábigos a romanos en un endpoint (GET) 
Exponer el método del componente que convierte valores numéricos romanos a arábigos en un endpoint (GET)

**Plus Fase 2:** Aplicar TDD (Test de integración usando la suite de Spring). 


### Requerimientos/Restricciones

**Fase 1 y 2:** Usar Java 17 o superior. Maven o Gradle para la gestión de dependencias. 
Para los puntos plus de cada fase, en lo relacionado a la infraestructura de tests se pueden usar las siguientes herramientas JUnit5+Mockito o Spock y Spring Boot Testing. 
**Fase 2:** Usar Spring boot 3+.

Completar y modificar este readme e incluirlo como parte del repositorio agregando detalles sobre cómo construir el proyecto desde cero y ponerlo en ejecución. 

## Conversor de números romanos - Fases 1 y 2

### Descripción del proyecto

Este proyecto implementa un sistema de conversión bidireccional entre números arábigos (enteros) y
números romanos, desarrollado como parte del challenge de desarrollo. La solución está construida
utilizando Spring Boot 3.5.5 con Java 21, siguiendo principios de desarrollo guiado por pruebas (TDD)
y buenas prácticas de desarrollo.

**Fase 1** incluye el componente de conversión con validaciones y excepciones personalizadas.

**Fase 2** expone este componente a través de endpoints REST con manejo global de excepciones.

### Tecnologías utilizadas

* Java 21: lenguaje de programación con su versión LTS.
* Spring Boot 3.5.5: framework principal para inyección de dependencias y sencillo de escalar a API REST.
* Spring Web: para la creación de controladores REST.
* Maven: gestión de dependencias.
* JUnit: framework de testing unitario.
* Mockito: para mocking en tests unitarios.
* Spring Boot Test: infraestructura de testing para pruebas de integración.

### Justificación de tecnologías

La idea de utilizar Java 21 con Spring Boot 3.5.5 nace con la posibilidad de escalar fácilmente un componente 
a una API REST que brinde una solución eficiente para la conversión de números romanos a arábigos y viceversa. 
Luego de la creación de la fase 1, donde se creó el servicio con sus correspondientes tests unitarios y de
integración, se procedió con la extensión de la aplicación exponiendo endpoints REST por medio de controladores.

### Funcionalidades implementadas

El sistema permite realizar conversiones bidireccionales con soporte completo para el rango de números que
van del 1 al 3999, tanto a través del componente directo como mediante endpoints REST.

#### Arábigo - Romano

* 1 ➔ I
* 2 ➔ II
* 3 ➔ III
* 4 ➔ IV
* 5 ➔ V
* 9 ➔ IX
* 21 ➔ XXI
* 50 ➔ L
* 100 ➔ C
* 500 ➔ D
* 1000 ➔ M

#### Romano - Arábigo

En este caso se realizó una conversión inversa completa con validación de formato, con soporte para entradas
en mayúsculas y minúsculas. Además, cuenta con una validación estricta de sintaxis romana.

## Endpoints REST (Fase 2)

La aplicación expone los siguientes endpoints:

### `GET /api/converter/to-roman`

#### Parámetros

`number` (Integer): Número arábigo a convertir (rango: 1-3999)

#### Ejemplo de respuesta exitosa

```json
{
  "status": 200,
  "result": "MCMXCIV"
}
```

#### Ejemplo de respuesta con error

```json
{
  "status": 400,
  "message": "El número debe estar entre 1 y 3999, recibido: 0"
}
```

### `GET /api/converter/to-arabic`

Convierte un número romano a arábigo.

#### Parámetros

`roman` (String): Número romano a convertir

#### Ejemplo de respuesta exitosa:

```json
{
  "status": 200,
  "result": 1994
}
```

#### Ejemplo de respuesta con error:

```json
{
  "status": 400,
  "message": "Caracter invalido en numero romano: Z"
}
```

### Validaciones y manejo de errores

El sistema implementa manejo de errores por medio del uso de excepciones personalizadas:

* **InvalidArabicNumberException**: para números fuera del rango permitido.
* **InvalidRomanNumberException**: para formatos romanos inválidos, caracteres no permitidos o valores nulos o vacíos.

Estas excepciones son capturadas por el GlobalExceptionHandler que proporciona respuestas HTTP consistentes
con códigos de estado apropiados (400 Bad Request) y mensajes de error descriptivos.

### Estructura del proyecto

```textplain
src/
├── main/java/com/agustin/challenge_possumus/
│   ├── controller/
│   │   └── ConverterController.java
│   ├── service/
│   │   └── RomanNumberConverterService.java
│   └── exception/
│       ├── GlobalExceptionHandler.java
│       ├── InvalidArabicNumberException.java
│       └── InvalidRomanNumberException.java
└── test/java/com/agustin/challenge_possumus/
├── controller/
│   ├── ConverterControllerTest.java
│   └── ConverterControllerIntegrationTest.java
├── exception/
│   └── GlobalExceptionHandlerTest.java
└── service/
├── RomanNumberConverterServiceTest.java
└── RomanNumberConverterServiceIntegrationTest.java
```

### Componente principal

#### Fase 1

**RomanNumberConverterService**: servicio de Spring que encapsula toda la lógica de conversión utilizando un **Map**
ordenado para mapear valores arábigo-romano con substracciones incluidas. Cuenta con un algoritmo de conversión eficiente
que itera sobre el mapa para construir representaciones romanas. Además, garantiza que una conversión romana pueda ser
revertida al número original.

#### Fase 2

**ConverterController**: controlador REST que expone los endpoints `/to-roman` y `/to-arabic`, delegando la lógica
de conversión al servicio y proporcionando respuestas HTTP estructuradas.

**GlobalExceptionHandler**: manejador global de excepciones que intercepta las excepciones personalizadas del servicio
y las convierte en respuestas HTTP apropiadas con códigos de estado y mensajes consistentes.


## Instrucciones de construcción y ejecución

### Requisitos técnicos
* Maven
* GIT
* JDK 21

### Pasos para ejecutar el proyecto localmente

1. Clonar el repositorio
```bash
git clone https://github.com/fariasagustin3/kata-roman-numbers-converter.git
cd kata-roman-numbers-converter
```
2. Compilar el proyecto
```bash
mvn clean compile
```
3. Ejecutar tests
```bash
# Ejecutar todos los tests
mvn test

# Ejecutar solo tests unitarios del servicio
mvn test -Dtest=RomanNumberConverterServiceTest

# Ejecutar solo tests de integración del servicio
mvn test -Dtest=RomanNumberConverterServiceIntegrationTest

# Ejecutar tests del controlador
mvn test -Dtest=ConverterControllerTest

# Ejecutar tests de integración del controlador
mvn test -Dtest=ConverterControllerIntegrationTest

# Ejecutar tests del manejador de excepciones
mvn test -Dtest=GlobalExceptionHandlerTest
```
4. Construir artefacto
```bash
mvn clean package
```
5. Ejecutar aplicación
```bash
mvn spring-boot:run
```
