package ar.edu.utn.frba.dds.externo;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MissingJavadocType"})
public interface WSensor {
  Reading getWeight(String serialNumber);
}
