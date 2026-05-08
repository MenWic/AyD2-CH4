Revisa la implementación actual del challenge GlobalShop. La estructura ya está correcta y no debe cambiarse. No agregues API, controllers, DTOs, JPA, repositories, ports, adapters formales, endpoints ni nuevas capas.

El objetivo de esta pasada es hacer ajustes finos de claridad, naming y depuración de tests, manteniendo una suite más compacta, crítica y realista.

No cambies la lógica de negocio ni las reglas de cálculo.

1. Renombrar constantes que dicen DISCOUNT pero realmente representan factores multiplicadores.

Ejemplos:
- LOW_TIER_DISCOUNT -> LOW_TIER_FACTOR
- MID_TIER_DISCOUNT -> MID_TIER_FACTOR
- HIGH_TIER_DISCOUNT -> HIGH_TIER_FACTOR
- PREMIUM_DISCOUNT -> PREMIUM_FACTOR
- RETAIL_PREMIUM_DISCOUNT -> RETAIL_PREMIUM_FACTOR
- WHOLESALE_LOW_DISCOUNT -> WHOLESALE_LOW_FACTOR
- WHOLESALE_HIGH_DISCOUNT -> WHOLESALE_HIGH_FACTOR
- WHOLESALE_COMPATIBILITY_DISCOUNT -> WHOLESALE_COMPATIBILITY_FACTOR
- SAVE10_DISCOUNT -> SAVE10_FACTOR, si el valor representa 0.90
- SAVE20_DISCOUNT -> SAVE20_FACTOR, si el valor representa 0.80
- BLACKFRIDAY_DISCOUNT -> BLACKFRIDAY_FACTOR, si el valor representa 0.70
- WELCOME_DISCOUNT -> WELCOME_FACTOR, si el valor representa 0.85

No cambies valores. Solo mejora nombres cuando el valor sea un factor multiplicador.

2. En StandardPricingStrategy, hacer explícito el caso CustomerType.INTERNATIONAL.

Evitar que INTERNATIONAL sea tratado como caso implícito por descarte.

La lógica debe quedar clara:
- RETAIL
- WHOLESALE
- INTERNATIONAL
- caso no soportado: lanzar excepción clara

No modificar resultados esperados.

3. Agregar test para PricingStrategyResolver cuando falta una estrategia.

Caso:
- construir resolver sin BulkPricingStrategy
- llamar resolve(OrderType.BULK)
- debe lanzar IllegalArgumentException

Nombre sugerido:
- resolveThrowsWhenStrategyIsMissing

4. Agregar test para PromotionPolicyResolver cuando falta una policy.

Caso:
- construir resolver sin WelcomePromotionPolicy
- llamar resolve(PromotionCode.WELCOME)
- debe lanzar IllegalArgumentException

Nombre sugerido:
- resolveThrowsWhenPolicyIsMissing

5. Reducir la cantidad de tests y dejar solo los más críticos.

No queremos una suite enorme ni tests sobrepensados. Queremos una suite compacta que demuestre correctamente:
- reglas principales;
- bordes de negocio relevantes;
- composición realista de reglas;
- validaciones importantes;
- ejecución del flujo principal;
- fallos controlados en resolvers.

Eliminar tests que:
- repitan exactamente una regla ya cubierta por otro test;
- prueben microdetalles internos sin impacto funcional;
- sean demasiado artificiales para el reto;
- validen clases triviales cuando la facade ya cubre el comportamiento;
- aumenten cantidad sin aportar confianza real.

No eliminar tests que cubran reglas críticas o decisiones ambiguas del enunciado.

6. Suite mínima recomendada, compacta y defendible.

Mantener o ajustar tests para cubrir estos casos.

Pricing:

- STANDARD + RETAIL + premium aplica 10%.
  Motivo: valida regla premium en STANDARD.

- STANDARD + WHOLESALE con monto > 1000 aplica 15%.
  Motivo: valida regla wholesale más importante.

- STANDARD + INTERNATIONAL + weekend aplica recargos correctamente.
  Motivo: valida cliente internacional y recargo de fin de semana.

- EXPRESS + WHOLESALE + premium aplica recargo express, ajuste wholesale y descuento premium.
  Motivo: cubre combinación realista y orden de aplicación.

- BULK con monto <= 2000 aplica 5%.
  Motivo: valida tramo bajo.

- BULK con monto 2000.01 aplica 15%.
  Motivo: valida borde de cambio al tramo medio.

- BULK con monto 5000.01 aplica 25%.
  Motivo: valida borde de cambio al tramo alto.

No es necesario mantener todos los casos intermedios si estos ya cubren la lógica de tramos y bordes.

Promotions:

- SAVE10 aplica 10%.
- BLACKFRIDAY aplica 30%.
- null retorna NoPromotionPolicy o mantiene subtotal, según cómo esté implementado.
- PromotionPolicyResolver lanza excepción si falta una policy.

No es obligatorio mantener tests individuales para SAVE20 y WELCOME si la estructura de policies ya queda clara y BLACKFRIDAY/SAVE10 cubren la mecánica. Si WELCOME se usa para el test de resolver faltante, suficiente.

Facade:

- procesa una orden completa y retorna total correcto.
- STANDARD + INTERNATIONAL + weekend + BLACKFRIDAY valida que la promoción se aplica al final.
- guarda, audita y notifica la orden en el flujo principal.
- rechaza orden nula.
- rechaza monto negativo o cero.
- rechaza tipo de orden nulo o tipo de cliente nulo.

Agrupar validaciones relacionadas con tests parametrizados si ayuda a reducir cantidad sin perder claridad.

Resolvers:

- PricingStrategyResolver resuelve una strategy existente.
- PricingStrategyResolver lanza excepción si falta una strategy.
- PromotionPolicyResolver retorna NoPromotionPolicy con null.
- PromotionPolicyResolver lanza excepción si falta una policy.

Support:

- FileOrderAuditTest con @TempDir solo si aporta valor y no complica.
- InMemoryOrderStoreTest puede eliminarse si su comportamiento queda suficientemente cubierto por la facade.

Commands:

- No crear tests individuales para SaveOrderCommand, AuditOrderCommand o NotifyCustomerCommand si la facade ya verifica que guardar, auditar y notificar ocurren.
- Solo mantener tests de commands si ya existen y aportan algo distinto al flujo principal.

7. No hacer cambios de arquitectura.

No cambiar:
- estructura de paquetes;
- nombres principales de clases;
- OrderProcessorFacade;
- PricingStrategy;
- PromotionPolicy;
- OrderStore;
- OrderAudit;
- OrderNotifier;
- support;
- reglas de negocio;
- enfoque de refactor simple.

8. Estilo de código.

Mantener:
- nombres claros y autodescriptivos;
- sin variables tipo a, b, c, d;
- sin comentarios innecesarios;
- sin emojis;
- sin double para dinero;
- sin strings mágicos para tipos de orden, cliente o promoción;
- sin mezclar cálculo con guardado, auditoría o notificación.

9. Ejecutar tests.

Al finalizar ejecutar:

mvn test

Si el Maven Wrapper está incompleto, reportar que falta .mvn/wrapper y usar mvn test si está disponible.

10. Reporte final esperado.

Reportar brevemente:
- constantes renombradas;
- tests agregados;
- tests eliminados y razón breve;
- cantidad final aproximada de tests;
- resultado de mvn test;
- confirmar que no se cambió la arquitectura.