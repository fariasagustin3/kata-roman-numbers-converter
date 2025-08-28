# Kata Números romanos
## Desarrollo de la Fase 1

El desafío se divide en dos fases, es por eso que se tomó la decisión de desarrollar un entregable para cada fase
respetando los requerimientos para cada uno, realizando tests unitarios y de integración para los métodos del
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

## Conversor de números romanos - Fase 1

### Descripción del proyecto

Este proyecto implementa un sistema de conversión bidireccional entre números arábigos (enteros) y 
números romanos, desarrollado como parte de challenge de desarrollo. La solución está construida 
utilizando Spring Boot 3.5.5 con Java 21, siguiendo principios de desarrollo guiado por pruebas (TDD) 
y buenas prácticas de desarrollo.

### Tecnologías utilizadas

* Java 21: lenguaje de programación con su versión LTS.
* Spring Boot 3.5.5: framework principal para inyección de dependencias y sencillo de escalar a API REST.
* Maven: gestión de dependencias.
* JUnit: framework de testing unitario.
* Spring Boot Test: infraestructura de testing para pruebas de integración.

### Justificación de tecnologías

La idea de utilizar Java 21 con Spring Boot 3.5.5 nace con la posibilidad de escalar fácilmente un componente 
a una API REST que brinde una solución eficiente para la conversión de números romanos a arábigos y viceversa. 
Está pensada además  para continuar con la Fase 2 del challenge técnico propuesto más arriba, donde incluye la
posibilidad de exponer este componente a un cliente REST mediante un controlador que recibe peticiones HTTP.

### Funcionalidades implementadas

El sistema permite realizar conversiones bidireccionales con soporte completo para el rango de números que
van del 1 al 3999.

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

### Validaciones y manejo de errores

El sistema implementa manejo de errores por medio del uso de excepciones personalizadas:

* **InvalidArabicNumberException**: para números fuera del rango permitido.
* **InvalidRomanNumberException**: para formatos romanos inválidos, caracteres no permitidos o valores nulos o vacíos.

### Estructura del proyecto

```declarative
src/
├── main/java/com/agustin/challenge_possumus/
│   ├── service/
│   │   └── RomanNumberConverterService.java
│   └── exception/
│       ├── InvalidArabicNumberException.java
│       └── InvalidRomanNumberException.java
└── test/java/com/agustin/challenge_possumus/service/
    ├── RomanNumberConverterServiceTest.java
    └── RomanNumberConverterServiceIntegrationTest.java
```

### Componente principal

**RomanNumberConverterService**: servicio de Spring que encapsula toda la lógica de conversión utilizando un **Map**
ordenado para mapear valores arábigo-romano con substracciones incluidas. Cuenta con un algoritmo de conversión eficiente
que itera sobre el mapa para construir representaciones romanas. Además, garantiza que una conversión romana pueda ser
revertida al número original.

## Instrucciones de construcción y ejecución

### Requisitos técnicos
* Maven
* GIT
* JDK 21

### Pasos para ejecutar el proyecto localmente

1. Clonar el repositorio
```declarative
git clone https://github.com/fariasagustin3/kata-roman-numbers-converter.git
cd kata-roman-numbers-converter
```
2. Compilar el proyecto
```declarative
mvn clean compile
```
3. Ejecutar tests
```declarative
# Ejecutar todos los tests
mvn test

# Ejecutar solo tests unitarios
mvn test -Dtest=RomanNumberConverterServiceTest

# Ejecutar solo tests de integración
mvn test -Dtest=RomanNumberConverterServiceIntegrationTest
```
4. Construir artefacto
```declarative
mvn clean package
```
5. Ejecutar aplicación
```declarative
mvn spring-boot:run
```
