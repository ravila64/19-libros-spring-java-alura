package com.aluracursos.libros.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// pom.xml adicionar la dependencia jackson databind

public class ConvierteDatos implements  IConvierteDatos{

   private ObjectMapper objectMapper = new ObjectMapper();
   @Override
   public <T> T obtenerDatos(String json, Class<T> clase) {
      try {
         return objectMapper.readValue(json, clase);
      } catch (JsonProcessingException e) {
         throw new RuntimeException(e);
      }
   }
}
