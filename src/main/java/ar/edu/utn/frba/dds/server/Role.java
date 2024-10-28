package ar.edu.utn.frba.dds.server;

import io.javalin.security.RouteRole;

enum Role implements RouteRole {
  ANYONE,
  COLABORADOR,
  ADMIN
}
