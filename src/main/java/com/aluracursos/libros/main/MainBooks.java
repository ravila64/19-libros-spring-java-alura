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
