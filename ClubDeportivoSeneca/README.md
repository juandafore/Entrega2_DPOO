# Club Deportivo Séneca (CDS)

Sistema de analítica deportiva del Club Deportivo Séneca: gestión de socios,
equipos, partidos, competencias, disciplinas individuales, entrenamientos,
tienda y cafetería. Implementado en Java.

## Requisitos

- Java 11 o superior (`java -version` para verificar).
- Eclipse (opcional) o cualquier terminal con `javac`/`java`.

## Cómo abrir en Eclipse

1. `File -> Import -> General -> Existing Projects into Workspace`.
2. Selecciona la carpeta `ClubDeportivoSeneca`.
3. Deja que Eclipse compile el proyecto.

## Cómo compilar y ejecutar desde la terminal

Desde la carpeta `ClubDeportivoSeneca`:

```bash
# Compilar
mkdir -p bin
find src -name "*.java" > sources.txt
javac -d bin @sources.txt

# Ejecutar la consola
java -cp bin main.Consola
```

## Uso de la aplicación (consola)

Al iniciar, la aplicación carga los datos desde la carpeta `datos/`.
Si no existen datos, crea un conjunto de datos de prueba automáticamente.

### Inicio de sesión

El menú principal permite **iniciar sesión** o **salir** (al salir se guardan los datos).

Usuarios de prueba:

| Rol            | Login     | Password  |
|----------------|-----------|-----------|
| Administrador  | admin     | admin     |
| Entrenador     | carlos    | carlos    |
| Entrenador     | diego     | diego     |
| Fisioterapeuta | fabiola   | fabiola   |
| Socio          | juan      | juan      |
| Socio          | maria     | maria     |
| Socio          | pedro     | pedro     |

Según el rol, aparece un menú distinto. Las opciones se eligen escribiendo el
número y presionando Enter.

### Menú Socio

1. Inscribirme a un equipo (deporte de conjunto).
2. Inscribirme a una disciplina individual (tenis, pádel, tenis de mesa).
3. Reservar una instalación.
4. Programar un partido de práctica contra otro socio.
5. Consultar mis estadísticas y mi ranking ELO.
6. Consultar resultados y tabla de posiciones.
7. Comprar en la tienda.
8. Comprar en la cafetería.
9. Redimir puntos de fidelidad.
10. Consultar mi historial.

### Menú Entrenador

1. Planear una sesión de entrenamiento.
2. Registrar asistencia y evaluación de una sesión.
3. Registrar el resultado y las estadísticas de un partido.
4. Gestionar la nómina de una competencia.
5. Sugerir un nuevo artículo o snack.
6. Solicitar un cambio de turno.
7. Comprar en la tienda con mi descuento de empleado.
8. Compartir mi código de descuento con un socio.

### Menú Fisioterapeuta

1. Sugerir un nuevo artículo o snack.
2. Solicitar un cambio de turno.
3. Comprar en la tienda con mi descuento de empleado.
4. Compartir mi código de descuento con un socio.

### Menú Administrador

1. Crear un equipo.
2. Dar de baja un equipo.
3. Registrar un socio.
4. Dar de baja un socio.
5. Consultar el inventario.
6. Mover un artículo entre ubicaciones.
7. Reabastecer la tienda.
8. Aprobar o rechazar sugerencias de artículos/snacks.
9. Administrar turnos (resolver solicitudes de cambio).
10. Inscribir un equipo en una competencia.
11. Consultar el historial completo.
12. Generar reporte de ventas.
13. Generar reporte deportivo.

## Persistencia

- Los datos se guardan en la carpeta `datos/` (archivo `club.dat`).
- Esa carpeta es distinta de la del código fuente (`src/`).
- Se guarda automáticamente al salir del menú principal.
- Para empezar desde cero, borra la carpeta `datos/`: al próximo inicio se
  vuelven a crear los datos de prueba.

## Programas de demostración

Además de la consola, hay 9 programas que demuestran funcionalidades concretas
sin interacción (útiles para probar la lógica):

```bash
java -cp bin main.Programa1   # Persistencia y autenticación (recibe carpeta como argumento opcional)
java -cp bin main.Programa2   # Equipos e inscripciones
java -cp bin main.Programa3   # Partidos internos, estadísticas y suspensiones
java -cp bin main.Programa4 TorneoCerros   # Competencias (o: Programa4 ASCUN)
java -cp bin main.Programa5   # Academias individuales, reservas y ranking ELO
java -cp bin main.Programa6   # Sesiones de entrenamiento y asistencia
java -cp bin main.Programa7   # Tienda y puntos de fidelidad
java -cp bin main.Programa8   # Cafetería y restricciones de seguridad
java -cp bin main.Programa9   # Administrador (turnos, inventario y reportes)
```

## Estructura del proyecto

```
ClubDeportivoSeneca/
  src/
    modelo/
      comun/               enumeraciones e interfaces
      usuarios/            Usuario, Administrador, Empleado, Entrenador, Fisioterapeuta, Socio
      deportesconjunto/    equipos, partidos, estadísticas, suspensiones
      competencia/         competencias (ASCUN, Torneo de los Cerros), tabla de posiciones
      deportesindividuales/ instalaciones, reservas, disciplinas, ranking ELO
      entrenamiento/       sesiones y asistencia
      tienda/              compras, artículos, inventario, mensualidades
      cafeteria/           ventas, productos, bebidas, snacks
      admin/               turnos, solicitudes, sugerencias, reportes
      Club.java            agregado raíz del dominio
    persistencia/          guardado y cargado en archivos
    main/                  Consola y programas de demostración
  datos/                   carpeta de datos (se crea al ejecutar)
```
