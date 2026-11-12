# TRABAJO PRÁCTICO INTEGRADOR 2026
## Sistema Integral de Gestión de Socios, Servicios, Equipamiento y Facturación
### Centro Deportivo de Alto Rendimiento

* **Materia:** Programación II
* **Docente Titular:** Ing. Fabián Castillo
* **Modalidad:** Equipo de 4 alumnos (Marco eduScrum)
* **Fecha de Entrega:** 20 de Noviembre de 2026

---

## 1. Descripción General del Sistema
El presente sistema es una solución integral desarrollada en **Java (Swing + FlatLaf)** bajo el paradigma de **Programación Orientada a Objetos pura**, diseñada para la administración centralizada y automatizada de un Centro Deportivo de Alto Rendimiento.

El software gestiona el padrón multicategoría de socios, el control de acceso biométrico/molinete, el préstamo y devolución de equipamiento con cálculo automático de mora y sanciones, la reserva de actividades con cupos limitados y prevención de duplicados, la facturación auditable con períodos fiscales y un módulo analítico de reportes y métricas deportivas y contables.

---

## 2. Arquitectura de Software en Tres Capas (3-Tier)

La aplicación sigue estrictamente una arquitectura desacoplada en tres paquetes independientes, garantizando la separación de responsabilidades:

```
┌────────────────────────────────────────────────────────┐
│               CAPA DE PRESENTACIÓN                     │
│  (GUI Swing + FlatLaf: Principal, Paneles de Vista)    │
└───────────────────────────┬────────────────────────────┘
                            │ (Invoca servicios del negocio)
                            ▼
┌────────────────────────────────────────────────────────┐
│             CAPA DE LÓGICA DE NEGOCIO                  │
│  (Logica.java, Reportes.java, Modelos y Excepciones)   │
└───────────────────────────┬────────────────────────────┘
                            │ (Persistencia transparente)
                            ▼
┌────────────────────────────────────────────────────────┐
│                   CAPA DE DATOS                        │
│   (AccesoDatos.java - Serialización Multi-Archivo)     │
└────────────────────────────────────────────────────────┘
```

### Reglas Arquitectónicas Cumplidas
* **Aislamiento Total:** La capa de presentación no accede jamás a archivos ni conoce la persistencia. La capa de datos no contiene ninguna regla ni validación de negocio.
* **Inyección de Dependencias (Raíz de Composición):** El sistema se inicia desde `Presentacion/Inicio.java` (*Composition Root*). Allí se crea la instancia técnica de `AccesoDatos`, se inyecta en el constructor de `Logica`, se vincula a `Reportes`, y esta se provee a la ventana principal `Principal`.

---

## 3. Diseño Orientado a Objetos y Polimorfismo Puro

Para dar cumplimiento a la rúbrica académica (calificación máxima), el sistema **erradica por completo el uso de condicionales con `instanceof`** para diferenciar comportamientos de negocio.

```
                    ┌─────────────────────────┐
                    │      <<abstract>>       │
                    │          Socio          │
                    ├─────────────────────────┤
                    │ + calcularCuotaMensual()│
                    │ + getTipo(): String     │
                    └────────────┬────────────┘
         ┌───────────────────────┼───────────────────────┐
         │                       │                       │
┌────────┴────────┐     ┌────────┴────────┐     ┌────────┴────────┐
│  SocioEstandar  │     │    SocioVip     │     │ SocioVitalicio  │
├─────────────────┤     ├─────────────────┤     ├─────────────────┤
│ Cuota base pura │     │ Cuota base +    │     │ Cuota base con  │
│                 │     │ adicional zonas │     │ descuento %     │
└─────────────────┘     └─────────────────┘     └─────────────────┘
```

* **Despacho Dinámico Polimórfico:** La liquidación mensual invoca `socio.calcularCuotaMensual()`. En tiempo de ejecución, la Máquina Virtual de Java (JVM) resuelve el cálculo exacto según el tipo concreto de membresía.
* **Principio Open/Closed (SOLID):** Incorporar una nueva membresía (ej. `SocioBecado` o `SocioPlatino`) solo requiere extender de `Socio` sin modificar ninguna otra clase del sistema.

---

## 4. Colecciones Anidadas y Algorítmica Avanzada

El sistema aprovecha al máximo el *Java Collections Framework* seleccionando las estructuras óptimas:

1. **`Map<String, Socio>` (Indexación en Memoria):** Permite búsquedas directas de socios por DNI en tiempo constante $O(1)$ y control instantáneo de duplicados (`SocioDuplicadoException`).
2. **`Map<String, Set<Reserva>>` (Reservas sin Duplicados):** Agrupadas por código de actividad. La clase `Reserva` sobreescribe de manera obligatoria `equals()` y `hashCode()`, haciendo matemáticamente imposible que un socio registre dos reservas para la misma clase y horario dentro del `Set`.
3. **`Map<String, List<Factura>>` (Historial Fiscal Anidado):** Cada socio administra sus facturas organizadas por clave de período fiscal `"yyyy-MM"`.
4. **`List<Alquiler>`:** Historial auditable de préstamos y devoluciones de equipamiento.
5. **Algoritmos de `Collections`:**
   * `Collections.sort()` con comparadores personalizados (`POR_APELLIDO_NOMBRE`, `POR_CUOTA_DESC`, orden de ocupación).
   * `Collections.binarySearch()` para la búsqueda rápida y precisa de socios por apellido sobre listas previamente ordenadas.

---

## 5. Persistencia Multi-Archivo Serializada y Excepciones

La clase `Datos.AccesoDatos` implementa persistencia en archivos binarios mediante `ObjectOutputStream` y `ObjectInputStream` con `try-with-resources`:

* **`Socios.txt`:** Padrón completo de socios, estados de habilitación, recargos pendientes y advertencias.
* **`Facturas.txt`:** Historial maestro de comprobantes de facturación (cuotas, reservas y alquileres).
* **`Reservas.txt`:** Mapa de conjuntos de reservas por actividad deportiva.
* **`Equipamiento.txt`:** Catálogo de elementos deportivos y estados de disponibilidad.
* **`Alquileres.txt`:** Registro de contratos de alquiler.
* **`Actividades.txt`:** Catálogo de disciplinas con horarios y cupos máximos.

### Tratamiento Encapsulado de Excepciones
* Las excepciones técnicas de E/S (`IOException`, `ClassNotFoundException`) son capturadas en `AccesoDatos` y relanzadas como `PersistenciaException`.
* La lógica de negocio maneja su propia jerarquía: `NegocioException`, `ReglaNegocioException`, `SocioDuplicadoException` y `AccesoDenegadoException`.
* **Cero `printStackTrace()`:** La interfaz de usuario no expone trazas técnicas de depuración, sino mensajes claros mediante diálogos interactivos.

---

## 6. Módulos y Funcionalidades del Sistema

* **Socios:** Alta de membresías con atributos diferenciados, búsqueda inmediata por DNI y visualización del perfil integral (doble clic) con historial de advertencias, recargos y acciones de gestión de sanciones y bajas.
* **Control de Acceso / Molinete:** Verificación de estado activo, ausencia de sanciones y pago al día. En caso de mora o penalización, deniega el paso indicando la causa exacta.
* **Alquiler de Equipamiento:** Préstamo de raquetas, casilleros y pelotas solo a socios habilitados. Control de fecha límite. En la devolución calcula los días de mora, aplica sanción automática, registra advertencia y computa el recargo para la próxima cuota.
* **Inscripción a Actividades:** Control de cupos limitados (Natación, Spin, Tenis) y rechazo estricto de inscripciones repetidas en el mismo horario.
* **Facturación e Historial:** Liquidación automática de cuotas con cobro de recargos acumulados. Emisión de comprobantes auditables individuales y consulta por período año/mes.
* **Reportes y Métricas:** Listados dinámicos ordenados por apellido o monto de cuota, reporte de morosos/sancionados, indicadores de recaudación mensual y porcentaje de ocupación promedio.

---

## 7. Requisitos y Ejecución

* **Java Development Kit (JDK):** Versión 17 o superior.
* **Librerías externas:** `FlatLaf 3.7.2` (Look and Feel moderno).
* **Ejecución rápida:**
  Hacer doble clic sobre el archivo:
  ```bash
  ejecutar.bat
  ```
  O compilar y ejecutar manualmente desde consola:
  ```bash
  javac -encoding UTF-8 -cp "target\classes;%USERPROFILE%\.m2\repository\com\formdev\flatlaf\3.7.2\flatlaf-3.7.2.jar" -d target\classes src\main\java\**\*.java
  java -cp "target\classes;%USERPROFILE%\.m2\repository\com\formdev\flatlaf\3.7.2\flatlaf-3.7.2.jar" Presentacion.Inicio
  ```

---

## 8. Trazabilidad con el Product Backlog (eduScrum)

| ID Historia | Descripción Técnica | Paquete |
| :--- | :--- | :--- |
| **HU-01** | Jerarquía de Socios y Cuotas Polimórficas sin `instanceof` | `LogicaNegocio.Modelos` |
| **HU-02** | Persistencia Multi-Archivo Serializada (`AccesoDatos`) | `Datos` |
| **HU-03** | Búsqueda Directa e Indexación por DNI en $O(1)$ | `LogicaNegocio` |
| **HU-04** | Alquiler de Equipamiento y Control de Mora | `LogicaNegocio` |
| **HU-05** | Reservas con Colección Anidada (`Set` con `equals`/`hashCode`) | `LogicaNegocio.Modelos` |
| **HU-06** | Control de Acceso / Molinete (`AccesoDenegadoException`) | `LogicaNegocio` |
| **HU-07** | Facturación e Historial por Período Fiscal (`"yyyy-MM"`) | `LogicaNegocio` |
| **HU-08** | Módulo de Reportes con `Collections.sort` y `binarySearch` | `LogicaNegocio` |
| **HU-09** | Manejo Encapsulado de Excepciones | `Datos` / `LogicaNegocio` |
| **HU-10** | Interfaz Gráfica de Usuario Interactiva | `Presentacion` |
| **HU-11** | Inyección de Dependencias y Arranque (`Inicio.java`) | `Presentacion` / Raíz |

