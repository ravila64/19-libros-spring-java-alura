package com.aluracursos.libros.main;

import com.aluracursos.libros.model.Datos;
import com.aluracursos.libros.model.DatosLibros;
import com.aluracursos.libros.service.ConsumoAPI;
import com.aluracursos.libros.service.ConvierteDatos;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MainBooks {
   private final String URL_BASE = "https://gutendex.com/books/";
   private ConsumoAPI consunoAPI = new ConsumoAPI();
   private ConvierteDatos conversor = new ConvierteDatos();
   private Scanner teclado = new Scanner(System.in);

   public void viewMewu() {
      var json = consunoAPI.obtenerDatos(URL_BASE);
      //System.out.println(json);
      var datos = conversor.obtenerDatos(json, Datos.class);
      System.out.println(datos);
      // 10 libros + descargados, opcion 5-5 del curso
      System.out.println("Top 10 libros mas descargados");
      datos.listaLibros().stream()
            .sorted(Comparator.comparing(DatosLibros::numDescargas).reversed())
            .limit(10)
            .map(l -> l.titulo().toUpperCase())
            .forEach(System.out::println);

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

      // trabajando con estadisticas  DoubleSUmmatyStatistics
      // datos = estan todos los datos
      DoubleSummaryStatistics est = datos.listaLibros().stream()
            .filter(d-> d.numDescargas()>0)
            .collect(Collectors.summarizingDouble(DatosLibros::numDescargas));
      System.out.println("Media de descargas "+est.getAverage());;
      System.out.println("Cantidad max de descargas "+est.getMax());
      System.out.println("Cantidad min de descargas "+est.getMin());
      System.out.println("Cantidad de registros evaluados "+est.getCount());
   }
}
