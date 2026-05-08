```md
## Ejercicio 4:
#Estado inicial
La empresa Cod 'n bugs tiene como cliente una empresa de comercio electrónico llamada "GlobalShop", la cual está experimentando un rápido crecimiento. Actualmente procesan miles de órdenes diarias de clientes de todo el mundo, con diferentes tipos de envío, categorías de cliente y promociones especiales.

El equipo de desarrollo implementó una solución rápida para el procesamiento de órdenes, pero ahora el código se ha vuelto poco mantenible. Cada vez que quieren agregar un nuevo tipo de descuento, una nueva categoría de cliente o un nuevo método de envío, el equipo introduce errores en otras partes del sistema. Los líderes técnicos han identificado que el sistema necesita una refactorización completa antes de que sea demasiado tarde.

##Requerimientos del Sistema
El sistema actual debe ser capaz de:

###Calcular el precio final de una orden considerando:

- Tipo de orden: STANDARD (estándar), EXPRESS (exprés) o BULK (volumen)
- Tipo de cliente: RETAIL (minorista), WHOLESALE (mayorista) o INTERNATIONAL (internacional)
- Monto base de la compra
- Cliente premium (descuento adicional del 5-10% dependiendo del contexto)
- Fin de semana (recargo del 5% solo para clientes internacionales)
- Códigos promocionales: SAVE10 (10% off), SAVE20 (20% off), BLACKFRIDAY (30% off), WELCOME (15% off)

###Reglas de negocio específicas:

- Los envíos EXPRESS tienen un recargo del 30% sobre el monto base
- Los envíos BULK tienen descuentos escalonados: 5% (>2000), 15% (>2000 y ≤5000), 25% (>5000)
- Los clientes WHOLESALE con envío STANDARD tienen 5% off automático, o 15% si compran más de $1000
- Los clientes premium tienen prioridad en descuentos adicionales
- Los descuentos promocionales se aplican después de todos los demás cálculos

###Persistencia: Guardar cada orden procesada en una base de datos MySQL

###Auditoría: Registrar cada transacción en un archivo de log (orders.log)

###Notificación: Enviar un correo electrónico al cliente confirmando el pedido

#Requisitos de la Solución Refactorizada
- Unit tests
- Identificar y documentar todos los code smells y bad practices presentes en el código original
- Aplicar los patrones de diseño, incluyendo obligatoriamente: Strategy, Builder, Adapter, Facade, Command , justificando el uso de cada uno.
- Mejorar la legibilidad y mantenibilidad
- Hacer el código extensible para futuros cambios



```