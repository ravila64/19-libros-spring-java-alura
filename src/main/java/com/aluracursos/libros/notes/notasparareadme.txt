a)	Parametrizar start.spring.io
b)	Crear package service
c)	Clase consumoAPI
d)	Crear interface IConvierteDatos
e)	Clase ConvierteDatos
f)	Adicionar en el pom.xml , la nueva dependencia Jackson, databind
g)	Modelando las clases de la app
h)	Generar el main package y colocar la clase MainLibros
i)	Instanciar
private ConsumoAPI consunoAPI = new ConsumoAPI();
private ConvierteDatos conversor= new ConvierteDatos();
j)	Luego en la principal implementar CommandLinesRunner y le genera unos métodos
k)	@SpringBootApplication
'''
public class LibrosApplication implements CommandLineRunner {
    public static void main(String[] args) {
       SpringApplication.run(LibrosApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {

    }
}
'''
l)	Usar records para llevar los datos del json recuperado a nuestra app, package model
Record datos
package com.aluracursos.libros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// listaLibros es resultados de las variables del curso
@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(
      @JsonAlias("results") List<DatosLibros> listaLibros
) {
}
record datos libros
package com.aluracursos.libros.model;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
      @JsonAlias("title") String titulo,
      @JsonAlias("authors") List<DatosAutor> autor,
      @JsonAlias("languages") List<String> idiomas,
      @JsonAlias("download_count") Double numDescargas
) {
}
**
Record datosAutor
package com.aluracursos.libros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
   @JsonAlias("name") String nombre,
   @JsonAlias("birth_year") String fechaNacimiento
) {
}
**
Arreglos en mainBooks, convertir el json con los records, al llamar conversor, imprimir datos
package com.aluracursos.libros.main;

import com.aluracursos.libros.model.Datos;
import com.aluracursos.libros.service.ConsumoAPI;
import com.aluracursos.libros.service.ConvierteDatos;

public class MainBooks {
   private final String URL_BASE = "https://gutendex.com/books/";
   private ConsumoAPI consunoAPI = new ConsumoAPI();
   private ConvierteDatos conversor= new ConvierteDatos();


   public void viewMewu(){
      var json = consunoAPI.obtenerDatos(URL_BASE);
      //System.out.println(json);
      var datos = conversor.obtenerDatos(json, Datos.class);
      System.out.println(datos);
   }
}
**
Se hizo un llamado a listaLibros (resultados), con datos, a esto se le hace un stream(),  para listar, los primeros 10 libros mas descargados, para eso se utilizo el sgte código.
// 10 libros + descargados, opcion 5-5 del curso
      System.out.println("Top 10 libros mas descargados");
      datos.listaLibros().stream()
            .sorted(Comparator.comparing(DatosLibros::numDescargas).reversed())
            .limit(10)
            .map(l->l.titulo().toUpperCase())
            .forEach(System.out::println);
**
Top 10 libros mas descargados
FRANKENSTEIN; OR, THE MODERN PROMETHEUS
MOBY DICK; OR, THE WHALE
PRIDE AND PREJUDICE
ROMEO AND JULIET
ALICE'S ADVENTURES IN WONDERLAND
THE GREAT GATSBY
A DOLL'S HOUSE : A PLAY
THE IMPORTANCE OF BEING EARNEST: A TRIVIAL COMEDY FOR SERIOUS PEOPLE
THE COMPLETE WORKS OF WILLIAM SHAKESPEARE
MIDDLEMARCH
**
// buscando libros por nombre – opción 5-6
// busqueda de libros por nombre
System.out.println("Digite libro a buscar ");
var tituloLibro = teclado.nextLine();
json = consunoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
Optional<DatosLibros> libroBuscado = datosBusqueda.listaLibros().stream()
      .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
      .findFirst();
if (libroBuscado.isPresent()) {
   System.out.println("Libro encontrado ");
   System.out.println(libroBuscado.get());  // trae todo los datos
   System.out.println("Autor "+libroBuscado.get().autor()+" del libro "+libroBuscado.get().titulo());
} else {
   System.out.println("Libro no encontrado");
}
**
07 gererando estadísticas
**
// trabajando con estadisticas  DoubleSUmmatyStatistics
// datos = estan todos los datos
DoubleSummaryStatistics est = datos.listaLibros().stream()
      .filter(d-> d.numDescargas()>0)
      .collect(Collectors.summarizingDouble(DatosLibros::numDescargas));
System.out.println("Media de descargas "+est.getAverage());;
System.out.println("Cantidad max de descargas "+est.getMax());
System.out.println("Cantidad min de descargas "+est.getMin());
System.out.println("Cantidad de registros evaluados "+est.getCount());

// pendiente buscar en la API, de libros en un rango de fechas, dependiendpo año publicación
