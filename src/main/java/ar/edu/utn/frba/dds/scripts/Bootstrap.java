package ar.edu.utn.frba.dds.scripts;

import ar.edu.utn.frba.dds.model.ColaboradorHumano;
import ar.edu.utn.frba.dds.model.Heladera;
import ar.edu.utn.frba.dds.model.MedioDeContacto;
import ar.edu.utn.frba.dds.model.TipoDocumento;
import ar.edu.utn.frba.dds.model.Ubicacion;
import ar.edu.utn.frba.dds.model.Usuario;
import ar.edu.utn.frba.dds.model.repositorios.MapaHeladeras;
import ar.edu.utn.frba.dds.model.repositorios.RepoColaboradores;
import ar.edu.utn.frba.dds.model.repositorios.RepoTecnicos;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDate;

public class Bootstrap implements WithSimplePersistenceUnit {
  public void init() {
    withTransaction(() -> {
      agregarColaborador("manu_dev", "contraseniaManu", "manu", "garcia");
      agregarColaborador("laura84", "lauraPass123", "laura", "rodriguez");
      agregarColaborador("carlos_dev", "carlosSecure!", "carlos", "martinez");
      agregarColaborador("ana2024", "ana#Pass456", "ana", "lopez");
      agregarColaborador("pablo_tech", "pabloSecret789", "pablo", "sanchez");
      agregarColaborador("sofia_qa", "sofiaTest2024", "sofia", "fernandez");
      agregarColaborador("diego_sys", "diegoAdmin!", "diego", "perez");
      agregarColaborador("maria_dev", "mariaSecure123", "maria", "gonzalez");
      agregarColaborador("juan_it", "juanPass2024", "juan", "diaz");
      agregarColaborador("lucia_admin", "luciaSecret!", "lucia", "torres");

      agregarHeladera("HeladeritaDeJuan", "0001", new Ubicacion(-34.611778, -58.375637));
      agregarHeladera("NeveraDelParque", "0002", new Ubicacion(-34.603722, -58.381592));
      agregarHeladera("HeladeriaCentral", "0003", new Ubicacion(-34.606817, -58.435752));
      agregarHeladera("FrigoBarrio", "0004", new Ubicacion(-34.617622, -58.368852));
      agregarHeladera("CoolFridge", "0005", new Ubicacion(-34.595862, -58.383796));
      agregarHeladera("NeveraComunitaria", "0006", new Ubicacion(-34.609032, -58.402435));
      agregarHeladera("FridgeShare", "0007", new Ubicacion(-34.620873, -58.371834));
      agregarHeladera("HeladeraSocial", "0008", new Ubicacion(-34.599856, -58.386487));
      agregarHeladera("ComuniFridge", "0009", new Ubicacion(-34.615283, -58.428936));
      agregarHeladera("NeveraVecinal", "0010", new Ubicacion(-34.592758, -58.391723));
    });
  }

  private static void agregarHeladera(String nombre, String numeroDeSerie, Ubicacion ubicacion) {
    MapaHeladeras.getInstance().agregarHeladera( new Heladera(nombre,
        40,
        ubicacion,
        numeroDeSerie,
        LocalDate.now(),
        15.0,
        0.0,
        null,
        null,
        null,
        null,
        null,
        RepoTecnicos.getInstance())
    );
  }

  private void agregarColaborador(String usuario, String contrasenia, String nombre, String apellido) {
    RepoColaboradores.getInstance().agregarColaborador(new ColaboradorHumano(nombre,
        apellido,
        LocalDate.of(1995, 10, 10),
        "Calle Falsa 124",
        new MedioDeContacto(null, "test@yahoo.com", "987654321"),
        new Usuario(contrasenia, usuario),
        null,
        TipoDocumento.DNI,
        180924103,
        null
    ));
  }
}
