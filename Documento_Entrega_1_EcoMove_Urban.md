# UNIVERSIDAD DE LAS AMÉRICAS / FACULTAD DE INGENIERÍA Y CIENCIAS APLICADAS
## INGENIERÍA DE SOFTWARE
### ASIGNATURA: PROGRAMACIÓN II (ISWZ1103)
---

# PROYECTO INTEGRADOR: ENTREGABLE N° 1 (PUNTOS 1 AL 5)
### TEMA: "EcoMove Urban" - Sistema de Gestión, Rebalanceo Dinámico y Mantenimiento Predictivo para Sistemas de Bicicletas Públicas Compartidas en Entornos Metropolitanos

---

## 1. CUADRO DE ASIGNACIÓN DE ROLES

El equipo de trabajo está conformado por seis (6) estudiantes. Cada integrante asume un rol funcional principal para coordinar las etapas del ciclo de vida del software, sin perjuicio de su participación colaborativa e indivisible en el desarrollo de los módulos de la aplicación.

| Rol / Equipo | Integrantes | Responsabilidades Principales |
| :--- | :--- | :--- |
| **Líder de Proyecto** | **FIGUEROA VASQUEZ, MIGUEL** | Coordinación general del equipo de desarrollo, gestión de cronograma, moderación de sesiones de trabajo, supervisión de entregables y seguimiento de hitos de planificación. |
| **Análisis y Diseño** | **HERRERA CUJANO, DARIKSON**<br>**LEMA TAPIA, VICTORIA** | Levantamiento exhaustivo de requerimientos del dominio, modelado conceptual del problema, definición de reglas de negocio y diseño estructural del diagrama de clases UML (capa de negocio y relaciones). |
| **Desarrollo de Software** | **LLERENA VIRACOCHA, ALEX**<br>**ROSARIO RODRIGUEZ, MARIA** | Implementación de la lógica orientada a objetos en lenguaje Java (paquetes `modelo` y `negocio`), control de versiones en GitHub y desarrollo de la interfaz gráfica de usuario (`interfaz`). |
| **Aseguramiento de Calidad y Pruebas** | **YE CAO, FELIX** | Diseño y ejecución del plan de pruebas funcionales, validación de lógica de negocio, verificación de consistencia de datos de entrada y elaboración de la matriz de casos de prueba con evidencias. |

*Nota aclaratoria:* La asignación de roles metodológicos no limita la autoría técnica de cada integrante: cada uno de los seis miembros es responsable directo e individual de un módulo funcional del sistema, implementando sus respectivas clases, atributos, métodos y validaciones.

---

## 2. IDENTIFICACIÓN DEL PROBLEMA

### Contexto del problema
El crecimiento urbano acelerado y la alta dependencia del transporte motorizado privado y colectivo representan dos de los mayores desafíos estructurales en las principales metrópolis de América Latina. Según reportes de la Comisión Económica para América Latina y el Caribe (CEPAL, 2022), los niveles de congestión vehicular en las urbes andinas generan pérdidas equivalentes al 2.5% del Producto Interno Bruto (PIB) anual debido a las horas-hombre perdidas en el tráfico, sumado a una degradación crítica de la calidad del aire provocada por las emisiones de gases de efecto invernadero (GEI) y material particulado (PM2.5). En este escenario, ONU-Hábitat (2023) subraya que los Sistemas de Bicicletas Públicas Compartidas (SBPC) constituyen el pilar más eficiente y sostenible para resolver la problemática del transporte de "primera y última milla", al articularse como un alimentador ecológico de los corredores troncales y sistemas de metro.

En la ciudad de Quito, Ecuador, la geografía alargada y las pendientes pronunciadas acentúan la complejidad del transporte urbano. De acuerdo con el Instituto Nacional de Estadística y Censos (INEC, 2023) y la Secretaría de Movilidad del Municipio del Distrito Metropolitano de Quito (MDMQ, 2023), los ciudadanos enfrentan tiempos de traslado promedio superiores a los 65 minutos en horas pico en el transporte público tradicional (Trolebús, Ecovía y buses convencionales). Aunque la inauguración del Metro de Quito ha marcado un hito en la movilidad masiva subterránea, su efectividad global depende directamente de la conectividad en superficie. Históricamente, el sistema municipal de bicicletas públicas "BiciQuito" ha enfrentado profundas crisis de operatividad: según informes de veeduría ciudadana y auditorías de movilidad del MDMQ (2023), más del 40% de las estaciones han permanecido inoperativas o desabastecidas durante franjas horarias críticas, y más de 300 unidades quedaron fuera de servicio simultáneamente debido a la carencia de repuestos oportunos y fallas mecánicas no anticipadas.

De forma complementaria, el Institute for Transportation and Development Policy (ITDP, 2021), en su Guía Global de Planificación de Bicicletas Compartidas, advierte que la tasa de supervivencia de un sistema de micromovilidad pública depende de dos factores operacionales críticos: la **tasa de disponibilidad de bicicletas en origen** (que el usuario encuentre una bicicleta en buen estado) y la **tasa de disponibilidad de anclajes vacíos en destino** (que el ciclista pueda devolver la bicicleta sin toparse con una estación saturada). Cuando estos dos factores caen por debajo del 85%, el sistema entra en un círculo vicioso de abandono por parte de los usuarios, quienes vuelven al transporte a combustión.

### Descripción del problema
Actualmente, los sistemas de micromovilidad pública compartida en entornos urbanos sufren una marcada ineficiencia operativa que se manifiesta en tres fallas estructurales:

1. **¿Qué está pasando actualmente?**
   - **Desbalance espacial severo (Mareas Pendulares):** En las mañanas (06:30 a 09:00), los usuarios retiran masivamente bicicletas de estaciones residenciales (e.g., sectores sur y norte periférico) y se dirigen hacia los polos financieros, educativos y corporativos (e.g., sector La Carolina, El Labrador, Universidades). Esto provoca que las estaciones residenciales queden rápidamente vacías (0% de disponibilidad de bicis) y las estaciones de destino queden saturadas al 100% (anclajes bloqueados, impidiendo que los usuarios entreguen su unidad). En la tarde (17:00 a 19:30), el flujo se invierte con la misma rigidez.
   - **Degradación no monitoreada de la flota y bicis averiadas en circulación:** Las bicicletas sufren desgastes severos (pastillas de freno cristalizadas, cadenas destensadas, llantas desgastadas, cambios rotos). En las unidades de pedaleo asistido (eléctricas), las baterías frecuentemente se descargan en plena ruta, dejando a los usuarios varados en pendientes empinadas.
   - **Falta de incentivos colaborativos:** Los usuarios se comportan como consumidores pasivos de rutas unidireccionales, ya que el sistema no ofrece incentivos económicos ni simbólicos para que los propios ciudadanos ayuden a reequilibrar la red.

2. **¿Quiénes se ven afectados?**
   - **Los ciudadanos y ciclistas urbanos:** Sufren frustración, pérdida de tiempo y riesgos viales al encontrar estaciones desiertas para salir o estaciones llenas que les impiden cerrar su viaje a tiempo, exponiéndose a sanciones indebidas.
   - **El personal operativo y cuadrillas de calle:** Conductores de furgonetas de rebalanceo y mecánicos que trabajan a ciegas, ejecutando rutas arbitrarias de recolección sin priorización técnica ni datos predictivos de demanda.
   - **El Municipio y la administración del transporte metropolitano:** Desperdicio de recursos públicos, depreciación acelerada de la flota adquirida con fondos ciudadanos y pérdida de confianza ciudadana en la movilidad no motorizada.

3. **¿Por qué el proceso actual no es eficiente?**
   - La toma de decisiones de redistribución de flota es eminentemente reactiva. Las cuadrillas de furgonetas solo se movilizan cuando los usuarios ya han saturado los canales de quejas o cuando un supervisor nota visualmente la acumulación.
   - No existe un cálculo estandarizado de desgaste mecánico preventivo: una bicicleta se revisa únicamente cuando ya ha sufrido una rotura catastrófica en vía pública reportada por un usuario furioso.
   - La flota de bicicletas eléctricas carece de umbrales automáticos de reserva energética, permitiendo préstamos de unidades con menos del 15% de batería que terminan varadas en medio de la calzada.

### Procesos actuales
En la actualidad, las operaciones de un sistema público de bicicletas típico se efectúan de manera desconectada, empírica y manual:
- **Asignación y recepción en estación:** El usuario acude a la estación y pasa su tarjeta física o firma una planilla manual. La estación valida mecánicamente el seguro, pero no verifica el estado de salud de la batería ni si el usuario excede tiempos de uso previos de manera sistemática.
- **Detección de estaciones vacías o saturadas:** Los supervisores de zona realizan llamadas telefónicas o envían mensajes por radio/WhatsApp a las cuadrillas de camionetas informando que "hacen falta bicis en la estación X". Las camionetas transitan en medio del tráfico pesado cargando y descargando unidades sin una orden de rebalanceo estructurada ni cálculo de capacidad óptima.
- **Gestión de fallas mecánicas:** Si un ciclista nota que la bicicleta tiene el freno roto, usualmente la ancla nuevamente y gira el sillín hacia atrás (código informal de la calle) para avisar al siguiente usuario. Si otro usuario no se percata, vuelve a retirar la bicicleta averiada, poniéndose en riesgo de accidente. El taller de mantenimiento registra las reparaciones en cuadernos de novedades físicos o en hojas de cálculo aisladas sin correlación con el kilometraje recorrido por la unidad.

### Datos involucrados
En el entorno operativo de este problema intervienen los siguientes elementos de información:
- **Personas:** Ciclistas registrados (usuarios activos, historial de cumplimiento, saldo de puntos de incentivo), operadores de furgonetas de rebalanceo, técnicos mecánicos de taller y supervisores de estación.
- **Objetos:** Bicicletas mecánicas tradicionales, bicicletas de pedaleo asistido con batería de litio (e-bikes), estaciones fijas de anclaje, tótems de estación, anclajes individuales electromecánicos (docks), furgonetas de transporte y kits de repuestos de taller (frenos, cadenas, neumáticos).
- **Registros:** Registros de inicios y fines de viaje, odómetros de kilometraje acumulado por unidad, estados de carga porcentual de baterías (SoC), bitácoras de fallas reportadas por usuarios y registros de órdenes de rebalanceo.
- **Eventos:** Apertura de ventana de hora pico matutina/vespertina, alertas de estación en umbral crítico (<15% de bicis disponibles), alertas de saturación (>85% de ocupación), eventos de bloqueo por batería baja (<20%), incidentes viales y desanclajes forzados.
- **Documentos:** Hojas de ruta de rebalanceo de furgonetas, manifiestos de ingreso de bicicletas a taller de mantenimiento correctivo, contratos de prestación de servicio y reportes de emisiones de CO2 mitigadas.

### Importancia del problema
Resolver la inestabilidad operativa y el desbalance de los sistemas de bicicletas públicas es de máxima prioridad metropolitana. Según el ITDP (2021), un sistema balanceado y con mantenimiento confiable incrementa la demanda de viajes en más de un 60%, descongestionando las arterias vehiculares en un 12% y reduciendo hasta 180 gramos de CO2 equivalente por cada kilómetro recorrido por un ciclista en reemplazo de un vehículo a combustión. Adicionalmente, garantizar bicicletas en estado óptimo disminuye a cero los siniestros por fallas de frenos y dignifica el transporte público, articulando de forma verdaderamente intermodal el flujo de pasajeros con sistemas masivos como el Metro de Quito.

---

## 3. CUADRO DE IDENTIFICACIÓN DEL PROBLEMA

| Elemento | Descripción |
| :--- | :--- |
| **Cliente** | Secretaría de Movilidad del Municipio del Distrito Metropolitano de Quito (MDMQ) y la Empresa Pública Metropolitana de Transporte de Pasajeros de Quito (EPMTPQ). |
| **Usuario** | - **Ciclistas urbanos / Ciudadanos:** Personas que realizan trayectos diarios de primera y última milla.<br>- **Operadores de Logística y Rebalanceo:** Conductores de furgonetas encargados del traslado de unidades.<br>- **Mecánicos de Mantenimiento:** Personal técnico del taller central.<br>- **Supervisores de Operaciones:** Administradores del servicio de micromovilidad metropolitana. |
| **Requerimientos funcionales (Visión Alto Nivel)** | - Supervisar y calcular el índice de saturación y disponibilidad de estaciones y anclajes físicos en tiempo real.<br>- Monitorear la flota de bicicletas (mecánicas y eléctricas), calculando la autonomía dinámica y el estado de la batería.<br>- Gestionar los préstamos de viajes con validación estricta de tiempos máximos permitidos y penalizaciones por mora.<br>- Calcular el índice de desgaste predictivo de componentes para ordenar el retiro automático de unidades inseguras.<br>- Generar órdenes de rebalanceo optimizadas para trasladar bicicletas entre estaciones saturadas y deficitarias.<br>- Otorgar incentivos de "Puntos Verdes" por rebalanceo ciudadano y calcular la huella de carbono (CO2) mitigada por viaje. |
| **Mundo del problema** | Dominio de la movilidad urbana sostenible, gestión de flotas de micromovilidad compartida, logística de balanceo territorial en redes de transporte y mantenimiento preventivo de vehículos ligeros de cero emisiones. |

---

## 4. REQUERIMIENTOS FUNCIONALES POR MÓDULOS

A continuación se presentan los requerimientos funcionales del sistema, organizados estrictamente en seis (6) módulos funcionales independientes. Cada módulo ha sido asignado a un estudiante del equipo de trabajo y contiene al menos dos requerimientos funcionales documentados bajo la estructura formal exigida, incluyendo reglas de negocio, cálculos y transformaciones de estado complejas.

```
========================================================================================
MÓDULO 1: GESTIÓN DE ESTACIONES Y ANCLAJES INTELIGENTES
Responsable: FIGUEROA VASQUEZ, MIGUEL
========================================================================================
```

### Requerimiento RF-1.1
| Elemento | Descripción |
| :--- | :--- |
| **Nombre** | **RF-1.1: Registro y Configuración de Estaciones y Anclajes Físicos** |
| **Resumen** | Permite dar de alta estaciones de micromovilidad en la red urbana, definiendo su ubicación georreferenciada, zona tarifaria y la cantidad total de anclajes electromecánicos (docks) asignados, configurando el estado inicial de cada anclaje (Operativo, Averiado, Ocupado o En Mantenimiento). |
| **Entradas** | Código único de estación (e.g., "EST-012"), nombre descriptivo (e.g., "Estación La Carolina Norte"), coordenadas geográficas (latitud, longitud), sector metropolitano, capacidad total de anclajes (número entero positivo > 0) y tipo de estación (estándar o intermodal con Metro). |
| **Resultado** | Estación registrada en el catálogo maestro del sistema con todos sus anclajes numerados creados en estado "Operativo Libre", lista para vincular bicicletas y disponible para la visualización en el mapa de operaciones. |

### Requerimiento RF-1.2 *(Lógica de Negocio Compleja)*
| Elemento | Descripción |
| :--- | :--- |
| **Nombre** | **RF-1.2: Cálculo del Índice de Saturación y Detección de Anomalías de Capacidad** |
| **Resumen** | Evalúa en tiempo real el porcentaje de ocupación de la estación (`Ocupación = (Bicicletas Ancladas / Anclajes Operativos Totales) * 100`). Aplica reglas de negocio para clasificar automáticamente el estado operativo de la estación en: *Crítica Deficitaria* (<15%), *Operación Regular* (15% a 85%), o *Saturada con Bloqueo de Arribos* (>85%). Además, analiza la tasa de variación en una ventana móvil de 20 minutos; si la tasa de desanclajes supera el umbral de 0.8 bicis/minuto en hora pico, genera una alerta preventiva de desabastecimiento inminente. |
| **Entradas** | Código de estación, cantidad de bicicletas actualmente ancladas, cantidad de anclajes operativos, registro de desanclajes/arribos en los últimos 20 minutos y horario del sistema (identificación de franja horaria pico vs valle). |
| **Resultado** | Valor numérico del Índice de Saturación (%), Categoría de Estado de la Estación (Deficitaria, Equilibrada, Saturada), Bandera booleana de Alerta Temprana de Desabastecimiento/Sobrecupo y emisión del evento de notificación a la central logística. |

---

```
========================================================================================
MÓDULO 2: FLOTA DE BICICLETAS Y TELEMETRÍA DE BATERÍAS
Responsable: HERRERA CUJANO, DARIKSON
========================================================================================
```

### Requerimiento RF-2.1
| Elemento | Descripción |
| :--- | :--- |
| **Nombre** | **RF-2.1: Registro y Clasificación Técnica de Unidades de Flota** |
| **Resumen** | Permite incorporar nuevas unidades al parque de bicicletas del sistema, tipificándolas según su tecnología en: *Bicicleta Mecánica Convencional* o *Bicicleta Eléctrica Asistida (E-Bike)*, registrando número de serie del cuadro, peso, modelo, rodado de rueda y fecha de puesta en servicio. |
| **Entradas** | Número de serie del chasis (alfanumérico único), tipo de propulsión (Mecánica / Eléctrica), marca, modelo, peso en kilogramos, rodado (e.g., 26", 28") y fecha de incorporación. Para modelos eléctricos: capacidad nominal de la batería en Watts-hora (Wh) y voltaje nominal (V). |
| **Resultado** | Unidad de bicicleta registrada en el sistema con identificador único de flota, odómetro inicializado en 0.0 km, estado operativo establecido en "Disponible" y asignación de ficha técnica correspondiente. |

### Requerimiento RF-2.2 *(Lógica de Negocio Compleja)*
| Elemento | Descripción |
| :--- | :--- |
| **Nombre** | **RF-2.2: Algoritmo de Estimación de Autonomía Dinámica y Bloqueo por Batería Crítica** |
| **Resumen** | Para las bicicletas eléctricas asistidas, calcula en tiempo real la autonomía proyectada en kilómetros en función del porcentaje de carga restante (State of Charge - SoC), el número de ciclos de recarga acumulados (factor de degradación química de celdas de litio: degradación = ciclos * 0.05%) y el factor topográfico medio del sector de circulación (castigo de 20% de autonomía en zonas con pendientes pronunciadas de Quito). Si el SoC cae por debajo del umbral mínimo de seguridad (20%), la unidad cambia automáticamente su estado a "Inhabilitada por Batería Baja", bloqueando el mecanismo de desanclaje para nuevos usuarios y enviando orden de recarga. |
| **Entradas** | Identificador de bicicleta eléctrica, porcentaje actual de batería (SoC entre 0% y 100%), ciclos de carga completados (entero), voltaje instantáneo de celdas y sector de estación actual. |
| **Resultado** | Autonomía restante estimada en kilómetros (número decimal), Factor de Degradación de Batería (%), Estado de Disponibilidad actualizado (Disponible / Bloqueada por Carga Crítica) y emisión de alerta de recarga técnica si SoC ≤ 20%. |

---

```
========================================================================================
MÓDULO 3: PRÉSTAMO DE VIAJES Y REGLAS DE CIRCULACIÓN
Responsable: LEMA TAPIA, VICTORIA
========================================================================================
```

### Requerimiento RF-3.1
| Elemento | Descripción |
| :--- | :--- |
| **Nombre** | **RF-3.1: Inicio y Cierre Formal de Viaje Intermodal** |
| **Resumen** | Gestiona el ciclo de vida del préstamo de una bicicleta. Valida la identidad del usuario, verifica que el anclaje libere la unidad mecánicamente al iniciar el viaje, y registra la hora, estación de origen e identificador de bicicleta. Al llegar a la estación de destino, detecta la inserción en el dock receptor, comprueba el anclaje físico exitoso y cierra el registro de viaje. |
| **Entradas** | Cédula/ID de usuario ciclista, código de anclaje de origen, código de estación de origen; al finalizar: código de estación de destino, código de anclaje receptor y hora de recepción confirmada. |
| **Resultado** | Generación del registro de viaje con identificador único de sesión, actualización de estados (Bicicleta: "En Uso" -> "Anclada en Destino"; Anclaje origen: "Libre"; Anclaje destino: "Ocupado") y emisión de comprobante digital de viaje cerrado al usuario. |

### Requerimiento RF-3.2 *(Lógica de Negocio Compleja)*
| Elemento | Descripción |
| :--- | :--- |
| **Nombre** | **RF-3.2: Motor de Validación de Reglas de Viaje, Exceso de Tiempo y Sanciones** |
| **Resumen** | Aplica las políticas del reglamento de micromovilidad metropolitana. Calcula la duración del trayecto en minutos. Si la duración es menor o igual a 45 minutos (tiempo estándar gratuito de viaje), el costo es cero. Si el viaje supera los 45 minutos, calcula los bloques de exceso (fracciones de 15 minutos). Si el tiempo excede los 90 minutos de forma continua sin justificación, el sistema clasifica el viaje como "Infracción Grave por Retención Indebida", bloquea preventivamente la cuenta del ciclista impidiendo futuros préstamos durante 72 horas y genera una notificación de cobro administrativo proporcional. Además, impide el préstamo si el usuario tiene una sanción vigente o si la bicicleta seleccionada tiene un reporte de falla mecánica pendiente. |
| **Entradas** | Identificador de viaje activo, marca de tiempo de inicio (timestamp), marca de tiempo de recepción, estado de habilitación de la cuenta del usuario y matriz de penalizaciones tarifarias. |
| **Resultado** | Duración total calculada (minutos), Minutos de exceso tarifable, Monto de recargo calculado ($), Estado de cuenta del usuario (Habilitado / Suspendido Temporalmente / En Mora) y código de sanción administrativa si aplica. |

---

```
========================================================================================
MÓDULO 4: MANTENIMIENTO Y DESGASTE PREDICTIVO
Responsable: LLERENA VIRACOCHA, ALEX
========================================================================================
```

### Requerimiento RF-4.1
| Elemento | Descripción |
| :--- | :--- |
| **Nombre** | **RF-4.1: Registro de Novedades Técnicas y Bitácora de Taller** |
| **Resumen** | Permite registrar reportes de fallas ingresados por los usuarios al anclar la unidad (mediante botón de avería en el dock o pantalla de cierre) o ingresados por el equipo de supervisores, catalogando el componente averiado (frenos, cadena, pedales, transmisión, ruedas, luces o sistema eléctrico) y nivel de severidad. Permite además al mecánico registrar las intervenciones de reparación y sustitución de piezas. |
| **Entradas** | Código de bicicleta, componente afectado (selección de lista normalizada), descripción de la falla, nivel de severidad inicial reportado (Leve, Moderado, Crítico) y código del informante (usuario o inspector). |
| **Resultado** | Orden de inspección técnica vinculada al chasis de la bicicleta, registro cronológico en la bitácora de mantenimiento y marcado de la unidad con indicador de avería. |

### Requerimiento RF-4.2 *(Lógica de Negocio Compleja)*
| Elemento | Descripción |
| :--- | :--- |
| **Nombre** | **RF-4.2: Algoritmo de Cálculo de Score de Salud Mecánica (SSM) y Retiro Preventivo** |
| **Resumen** | Ejecuta un modelo determinístico de desgaste predictivo para cada unidad. El sistema calcula el *Score de Salud Mecánica (SSM)* en una escala de 0 a 100 puntos mediante la fórmula ponderada: `SSM = 100 - (Km_desde_ultimo_service * 0.04) - (Dias_desde_revision * 0.25) - Penalidad_Reportes_Usuarios`. Si la unidad tiene reportes leves acumulados, resta 10 puntos por cada uno; si tiene un reporte de falla en frenos o dirección (componentes críticos de seguridad), el SSM se reduce automáticamente a 0. Regla de negocio: Si `SSM < 70`, la bicicleta cambia inmediatamente su estado a "Inoperativa - Requiere Taller", el anclaje electromecánico se traba permanentemente con candado digital impidiendo cualquier desanclaje por parte de ciclistas y se genera una orden de traslado prioritario a taller central. |
| **Entradas** | Kilómetros recorridos acumulados desde el último mantenimiento preventivo, fecha del último servicio técnico, tipo y severidad de incidencias reportadas en las últimas 72 horas y coeficiente de desgaste por tipo de vía. |
| **Resultado** | Índice numérico SSM (0.0 a 100.0), Estado operativo de la bicicleta (Operativa / Requiere Mantenimiento Preventivo / Bloqueada en Taller), Bloqueo automático de liberación en el dock y generación automática del ticket de ingreso a taller. |

---

```
========================================================================================
MÓDULO 5: REBALANCEO OPERATIVO Y ENRUTAMIENTO DE CAMIONETAS
Responsable: ROSARIO RODRIGUEZ, MARIA
========================================================================================
```

### Requerimiento RF-5.1
| Elemento | Descripción |
| :--- | :--- |
| **Nombre** | **RF-5.1: Registro de Cuadrillas y Furgonetas de Redistribución** |
| **Resumen** | Permite registrar y administrar los vehículos de logística (camionetas/furgonetas adaptadas) que operan en la ciudad, especificando su capacidad máxima de transporte de bicicletas (e.g., capacidad de 15 a 25 unidades), conductor a cargo, zona de patrullaje asignada y estado actual del vehículo (Disponible, En Ruta de Carga, En Ruta de Descarga, Fuera de Servicio). |
| **Entradas** | Placa vehicular de la furgoneta, conductor asignado, capacidad máxima de carga (número entero positivo), zona urbana asignada (Norte, Centro, Sur, Valles) y horario de turno operativo. |
| **Resultado** | Unidad de rebalanceo dada de alta en el sistema logístico, inventario interno de carga inicializado en 0 bicicletas y disponibilidad para recibir misiones de redistribución en tiempo real. |

### Requerimiento RF-5.2 *(Lógica de Negocio Compleja)*
| Elemento | Descripción |
| :--- | :--- |
| **Nombre** | **RF-5.2: Algoritmo de Generación y Priorización de Órdenes de Rebalanceo** |
| **Resumen** | Calcula el diferencial de desbalance para cada estación: `Delta = Bicicletas_Actuales - (Capacidad_Total * 0.50)`. Si el Delta es fuertemente positivo (estación saturada) y en la misma zona existe una estación con Delta fuertemente negativo (estación vacía), el algoritmo genera una *Misión de Rebalanceo Óptima*. Asigna una prioridad numérica (1 a 5, siendo 5 máxima urgencia) evaluando: la magnitud del déficit, si la estación conecta con una parada del Metro de Quito (peso adicional +2 en prioridad) y la proximidad temporal de la hora pico. Calcula exactamente cuántas unidades retirar de la estación donante y cuántas inyectar en la estación receptora sin sobrepasar la capacidad de la furgoneta, actualizando el estado de la misión (Creada -> En Proceso -> Completada). |
| **Entradas** | Mapa de deltas de saturación de todas las estaciones de la zona, matriz de distancias relativas entre estaciones, condición de conectividad intermodal (Metro/Trolebús), capacidad libre de la furgoneta asignada e inventario actual a bordo. |
| **Resultado** | Orden de Misión de Rebalanceo generada con: Identificador de orden, Estación de recogida, Estación de entrega, Cantidad exacta de bicicletas a transferir, Nivel de Prioridad de Urgencia (1-5) y actualización del inventario en tránsito. |

---

```
========================================================================================
MÓDULO 6: INCENTIVOS URBANOS ("PUNTOS VERDES") Y HUELLA DE CARBONO
Responsable: YE CAO, FELIX
========================================================================================
```

### Requerimiento RF-6.1
| Elemento | Descripción |
| :--- | :--- |
| **Nombre** | **RF-6.1: Registro de Perfil Ciclista y Consulta de Saldo Ecológico** |
| **Resumen** | Permite a los usuarios registrados consultar su historial completo de viajes realizados, tiempo promedio de pedaleo, kilómetros acumulados de movilidad activa y saldo total acumulado en su billetera virtual de "Puntos Verdes", visualizando el desglose de beneficios canjeables disponibles (descuentos en pasajes del Metro de Quito o comercios adheridos). |
| **Entradas** | Identificador o cédula del ciclista autenticado, rango de fechas de consulta y filtro de visualización de historial. |
| **Resultado** | Pantalla/Reporte consolidado del usuario con: Total de kilómetros recorridos, Número de viajes completados, Balance de Puntos Verdes disponibles y catálogo de recompensas habilitadas según su nivel. |

### Requerimiento RF-6.2 *(Lógica de Negocio Compleja)*
| Elemento | Descripción |
| :--- | :--- |
| **Nombre** | **RF-6.2: Algoritmo de Rebalanceo Colaborativo (Crowdsourced) y Cálculo de CO2 Mitigado** |
| **Resumen** | Implementa un motor de incentivos para fomentar el auto-balanceo del sistema por parte de los propios ciudadanos. Al cerrar un viaje, el sistema evalúa si el trayecto ayudó a mitigar el desbalance de la red aplicando la condición: Si la estación de origen tenía un índice de saturación > 80% (estación llena) y la estación de destino tenía un índice < 20% (estación vacía), el viaje califica como *Viaje de Rebalanceo Colaborativo*. El algoritmo calcula una bonificación especial de Puntos Verdes: `Puntos_Otorgados = Puntos_Base + (Km_recorridos * 15) + Bono_Rebalanceo_Urgente (50 pts)`. Además, calcula de forma precisa los gramos de Dióxido de Carbono Equivalente (CO2e) evitados frente al automóvil a combustión mediante el factor metropolitano oficial: `CO2_Ahorrado = Km_recorridos * 178.5 gramos`. Si el viaje fue realizado en una estación deficitaria hacia otra deficitaria, no aplica bono de rebalanceo pero sí acredita la reducción de CO2. |
| **Entradas** | Código del viaje completado, distancia recorrida en kilómetros (obtenida por odómetro o cálculo de ruta origen-destino), índice de saturación de la estación de origen al momento del desanclaje e índice de la estación de destino al momento del anclaje. |
| **Resultado** | Acreditación matemática de Puntos Verdes en la cuenta del usuario, Total de gramos/kilogramos de CO2e mitigados acumulados en el perfil, Actualización del indicador global de sostenibilidad del sistema y emisión del distintivo de viaje sustentable. |

---

## 5. PLANIFICACIÓN DE ACTIVIDADES DEL PROYECTO

La planificación abarca el desarrollo iterativo del proyecto a lo largo del ciclo académico, cubriendo formalmente las seis etapas mínimas obligatorias y asignando líderes de actividad específicos acordes a los roles y responsabilidades definidos en el Punto 1.

| Rol o Equipo | Objetivo del Rol | Actividad | Responsable Directo | Fecha de Inicio | Fecha de Fin |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Líder & Análisis** | Analizar el problema real, recopilar fuentes estadísticas oficiales y delimitar el alcance del sistema de micromovilidad en Quito. | **1. Análisis del problema y contextualización urbana:** Búsqueda bibliográfica (CEPAL, ITDP, MDMQ), caracterización de la problemática de marea pendular y redacción del diagnóstico situacional del sistema. | FIGUEROA VASQUEZ, MIGUEL<br>HERRERA CUJANO, DARIKSON | 08/10/2024 | 14/10/2024 |
| **Análisis y Diseño** | Especificar formalmente los requerimientos funcionales y reglas de negocio complejas para cada uno de los 6 módulos. | **2. Definición formal de Requerimientos Funcionales:** Elaboración de tablas de RF (entradas, procesos, salidas, validaciones y lógica compleja) por módulo individual para los 6 integrantes. | LEMA TAPIA, VICTORIA<br>ROSARIO RODRIGUEZ, MARIA | 15/10/2024 | 22/10/2024 |
| **Diseño & Arquitectura** | Modelar la estructura del software mediante el Diagrama de Clases UML y definir la arquitectura modular en tres paquetes. | **3. Diseño de la solución (Modelo UML y Arquitectura):** Diseño del diagrama de clases UML de la capa de negocio (asociaciones, herencia, agregación), definición de atributos/métodos y estructura de paquetes (`modelo`, `negocio`, `interfaz`). | HERRERA CUJANO, DARIKSON<br>LLERENA VIRACOCHA, ALEX | 23/10/2024 | 03/11/2024 |
| **Desarrollo de Software** | Implementar la lógica del negocio orientada a objetos en Java y construir las ventanas de la interfaz gráfica. | **4. Desarrollo del sistema informático:** Codificación en Java de clases del modelo, gestores de negocio (algoritmos de saturación, autonomía, SSM, rebalanceo, puntos) y diseño de la interfaz gráfica de usuario (GUI). | LLERENA VIRACOCHA, ALEX<br>ROSARIO RODRIGUEZ, MARIA<br>FIGUEROA VASQUEZ, MIGUEL | 04/11/2024 | 24/11/2024 |
| **Calidad & Pruebas** | Verificar la solidez del sistema, validaciones de datos y ausencia de fallos en la lógica de negocio. | **5. Ejecución del Plan de Pruebas Funcionales:** Diseño de la matriz de casos de prueba (CP), ejecución de pruebas de caja negra, verificación de validaciones de entrada, captura de evidencias visuales y reporte de errores. | YE CAO, FELIX<br>LEMA TAPIA, VICTORIA | 25/11/2024 | 04/12/2024 |
| **Equipo Completo** | Corregir observaciones, refactorizar código y consolidar el informe final junto con el repositorio de GitHub. | **6. Correcciones, optimización y preparación de entrega:** Depuración de errores detectados en pruebas, optimización de la GUI, redacción de conclusiones/recomendaciones, compilación del ejecutable `.jar` y documentación final. | FIGUEROA VASQUEZ, MIGUEL<br>(Todo el equipo) | 05/12/2024 | 12/12/2024 |

---

## DECLARATORIA DE USO ÉTICO DE INTELIGENCIA ARTIFICIAL

En estricto cumplimiento de las políticas de integridad y honestidad académica de la Facultad de Ingeniería y Ciencias Aplicadas:

- **Herramienta empleada:** Antigravity AI Assistant / Google DeepMind.
- **Finalidad y alcance:** La herramienta fue utilizada exclusivamente como asistente de co-diseño metodológico, apoyo en la estructuración de tablas comparativas, refinamiento de redacción técnica y búsqueda asistida de lineamientos normativos y estándares de micromovilidad del ITDP.
- **Prompts utilizados:** 
  1. *"Estructurar una propuesta de proyecto de Programación Orientada a Objetos para un caso de estudio real sobre micromovilidad y rebalanceo de bicicletas compartidas en Quito, evitando temas genéricos como reservas o facturación."*
  2. *"Definir requerimientos funcionales formales con lógica de negocio compleja (algoritmos de saturación, autonomía de batería, desgaste predictivo SSM y cálculo de CO2) divididos en 6 módulos para 6 desarrolladores."*
- **Compromiso ético y autoría:** Los estudiantes del equipo declaran haber analizado, comprendido, verificado y validado cada uno de los conceptos, fórmulas, reglas de negocio y estructuras presentadas en este documento. Ningún contenido ha sido copiado ciegamente y el equipo se encuentra plenamente capacitado para sustentar oralmente y defender en código fuente cada módulo desarrollado ante el cuerpo docente.

---

## BIBLIOGRAFÍA Y REFERENCIAS (NORMA APA 7ma EDICIÓN)

1. **Comisión Económica para América Latina y el Caribe [CEPAL]. (2022).** *La congestión del tránsito urbano: Causas, consecuencias y políticas públicas para la sostenibilidad en América Latina*. Naciones Unidas. https://www.cepal.org/es/publicaciones
2. **Deitel, P. J., & Deitel, H. M. (2016).** *Java: Cómo programar* (10.ª ed.). Pearson Educación.
3. **Institute for Transportation and Development Policy [ITDP]. (2021).** *The bike-share planning guide: Best practices in system design, fleet balance, and station placement* (3.ª ed.). ITDP Global Publications. https://www.itdp.org/publication/the-bike-share-planning-guide/
4. **Instituto Nacional de Estadística y Censos [INEC]. (2023).** *Encuesta nacional de movilidad y transporte urbano en el Distrito Metropolitano de Quito: Resultados y series estadísticas*. INEC Ecuador.
5. **Municipio del Distrito Metropolitano de Quito [MDMQ]. (2023).** *Plan maestro de movilidad sostenible para el Distrito Metropolitano de Quito 2020-2040*. Secretaría de Movilidad de Quito.
6. **ONU-Hábitat. (2023).** *World cities report 2023: Envisaging the future of cities and low-carbon micromobility*. United Nations Human Settlements Programme. https://unhabitat.org/wcr/
7. **Oracle Corporation. (2023).** *Java Platform, Standard Edition Documentation (Java SE 17)*. Oracle. https://docs.oracle.com/en/java/javase/17/
8. **Pressman, R. S., & Maxim, B. R. (2021).** *Ingeniería del software: Un enfoque práctico* (9.ª ed.). McGraw-Hill Interamericana.
