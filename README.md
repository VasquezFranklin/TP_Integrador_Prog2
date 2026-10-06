# Trabajo Practico Integrador - Programación 2
# Sistema Integral de Gestión de Socios, Servicios, Equipamiento y Facturación

## Descripción del Proyecto
1. Este proyecto es un sistema de software en consola o interfaz gráfica (GUI) desarrollado para un Centro Deportivo de Alto Rendimiento.
2. Permite administrar de manera automatizada y centralizada la nómina de socios, el control de accesos, el alquiler de equipamiento, el sistema de sanciones y la liquidación de cuotas.
3. Fue creado como Trabajo Práctico Integrador 2026 para la materia Programación II de la Facultad de Ingeniería de la Universidad Nacional de Jujuy (UNJu).
4. La cátedra está a cargo del Ing. Fabian Castillo.

## Características Principales
1. Gestión Multicategoría de Socios: Permite registrar y administrar perfiles bajo las membresías de Socio Estándar, Socio VIP y Socio Vitalicio, con indexación de búsqueda por DNI.
2. Liquidación Automatizada de Cuotas: Calcula los valores aplicando cargos adicionales por zonas exclusivas o descuentos por antigüedad utilizando despacho dinámico polimórfico.
3. Módulo de Alquiler y Sanciones: Gestiona el préstamo de elementos deportivos (lockers, raquetas, pelotas) y aplica recargos automáticos en la factura ante devoluciones fuera de término.
4. Control de Acceso (Molinete): Valida que el socio tenga el pago al día, estado activo y ausencia de sanciones antes de permitir el ingreso.
5. Gestión de Reservas: Administra las inscripciones a las actividades impidiendo la existencia de reservas duplicadas.
6. Facturación e Historial: Emite comprobantes y permite la consulta del historial de facturas organizadas por período fiscal.
7. Módulo de Reportes: Genera listados dinámicos con búsquedas rápidas y ordenamientos.

## Arquitectura y Tecnologías
1. Arquitectura de Software: El sistema respeta un diseño estricto en Tres Capas (3-Tier) separando el código en los paquetes Presentacion, LogicaNegocio y Datos.
2. Inyección de Dependencias: El ensamblado del sistema se realiza en una Raíz de Composición (Inicio.java) que conecta la capa de Datos, Negocio y Presentación para mantener un desacoplamiento nulo.
3. Persistencia Multi-Archivo: La información sobrevive a los reinicios del sistema mediante la serialización de objetos en cuatro archivos independientes: Socios.txt, Facturas.txt, Reservas.txt y Equipamiento.txt.
4. Java Collections Framework: Se implementan colecciones anidadas complejas, como Map<String, Socio>, Map<String, List<Factura>>, Map<String, Set<Reserva>> (con equals y hashCode sobreescritos) y List<Alquiler>.
5. Algoritmos Estándar: Utiliza Collections.sort() con clases Comparator personalizadas y Collections.binarySearch() para los reportes.
6. Manejo de Errores Encapsulado: Las excepciones técnicas de E/S son capturadas en la capa de datos y relanzadas como excepciones de dominio (por ejemplo, PersistenciaException) para aislar la interfaz.

## Metodología y Organización del Equipo
1. El proyecto fue desarrollado colaborativamente utilizando el marco de trabajo ágil eduScrum.
2. El desarrollo fue realizado por un equipo multidisciplinar de 4 alumnos.
3. El trabajo se dividió en 3 Sprints de desarrollo con una duración de 2 semanas cada uno.
4. El avance, la visibilidad de las tareas y los Criterios de Aceptación se gestionaron mediante un tablero visual digital "Flap" (como Trello).