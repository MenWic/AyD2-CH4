# Challenge 3: EX4 GlobalShop

Este proyecto contiene la solucion del challenge de refactorizacion para el caso GlobalShop.

En la raiz del proyecto estan los archivos base del ejercicio:

* Enunciado.md contiene las instrucciones del challenge.
* initial_code.java contiene el codigo inicial del que se partio.

## Que HICE

El codigo inicial tenia muchas responsabilidades en la clase:

* calculo de precios
* descuentos
* guardado
* auditoria
* notificacion
* ejecucion manual.

Solucioné separando esas responsabilidades en distintos archivos buscando tener SRP, OCP, LSP, DIP mediante disntintos patrones de diseño.

La implementacion:


Organizacion:

```text
orders
-- application
-- command
-- domain
-- exception
-- pricing
-- promotion
-- support
```

## Decisiones principales

Usé Strategy para separar el calculo por tipo de orden (OrderType):

* STANDARD
* EXPRESS
* BULK

Las promociones las dejé separadas para evitar condicionales grandes.

Usé OrderProcessorFacade como punto central para procesar una orden.

Flujo principal que seguí:

```text
calcular subtotal
aplicar promocion
guardar orden
registrar auditoria
notificar
```

El guardado es en memoria con InMemoryOrderStore. La auditoria se registra en archivo y la notificacion la simulo por consola.

## Reglas consideradas

Para montos usé BigDecimal.

Las promociones se aplican despues del calculo base.

Para BULK tomé esta interpretacion:

```text
amount <= 2000              a: 5% descuento
amount > 2000 && <= 5000    a: 15% descuento
amount > 5000               a; 25% descuento
```

## Tests
```text
src/test/java/menwic/ayd2/ex4/orders
```

Para calculos principales, promociones, bordes importantes, validaciones y flujo general.

## Comandos

Ejecutar tests:

```bash
mvn test
```

Ejecutar la aplicacion:

```bash
mvn spring-boot:run
```

Con Maven Wrapper:

```bash
./mvnw test
./mvnw spring-boot:run
```

En Windows:

```bash
mvnw.cmd test
mvnw.cmd spring-boot:run
```


## Comandos
Ejecutar tests:
```bash
./mvnw test
```
