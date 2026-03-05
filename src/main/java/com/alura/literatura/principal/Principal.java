package com.alura.literatura.principal;

import com.alura.literatura.model.Autor;
import com.alura.literatura.model.DatosLibro;
import com.alura.literatura.model.DatosRespuesta;
import com.alura.literatura.model.Libro;
import com.alura.literatura.repository.AutorRepository;
import com.alura.literatura.repository.LibroRepository;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConvierteDatos;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner lectura = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/";
    private LibroRepository repository;
    private AutorRepository autorRepository; // Inyectamos el repositorio de autores

    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.repository = repository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ***************************************************
                    Elija la opción a través de su número:
                    1 - Buscar libro por título 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    
                    0 - Salir
                    ***************************************************
                    """;
            System.out.println(menu);

            try {
                var entrada = lectura.nextLine();
                opcion = Integer.parseInt(entrada);

                switch (opcion) {
                    case 1 -> buscarLibroPorTitulo();
                    case 2 -> mostrarLibrosBuscados();
                    case 3 -> listarAutoresRegistrados();
                    case 4 -> listarAutoresVivosEnAnio();
                    case 5 -> buscarLibrosPorIdioma();
                    case 0 -> System.out.println("Cerrando la aplicación...");
                    default -> System.out.println("Opción inválida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        var nombreLibro = lectura.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "%20"));
        var datos = conversor.obtenerDatos(json, DatosRespuesta.class);

        if (datos != null && !datos.resultadoLibros().isEmpty()) {
            DatosLibro datosLibro = datos.resultadoLibros().get(0);

            boolean yaExiste = repository.findAll().stream()
                    .anyMatch(l -> l.getTitulo().equalsIgnoreCase(datosLibro.titulo()));

            if (yaExiste) {
                System.out.println("\n[!] El libro '" + datosLibro.titulo() + "' ya está en tu catálogo.");
            } else {
                Libro libro = new Libro(datosLibro);
                repository.save(libro);
                System.out.println("\n----- LIBRO GUARDADO -----");
                System.out.println(libro);
            }
        } else {
            System.out.println("Libro no encontrado.");
        }
    }

    private void mostrarLibrosBuscados() {
        List<Libro> libros = repository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        System.out.println("\n--- AUTORES REGISTRADOS ---");
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosEnAnio() {
        System.out.println("Ingrese el año que desea consultar:");
        try {
            var entrada = lectura.nextLine();
            var anio = Integer.parseInt(entrada);

            // REQUISITO CARTILLA 11: Uso de Derived Query en el repositorio
            List<Autor> autoresVivos = autorRepository
                    .findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqual(anio, anio);

            if (autoresVivos.isEmpty()) {
                System.out.println("No se encontraron autores que estuvieran vivos en el año " + anio + ".");
            } else {
                System.out.println("\n--- AUTORES VIVOS EN EL AÑO " + anio + " ---");
                autoresVivos.forEach(a -> System.out.println(
                        "Autor: " + a.getNombre() + " (" + a.getNacimiento() + " - " +
                                (a.getFallecimiento() != null ? a.getFallecimiento() : "Presente") + ")"
                ));
            }
        } catch (NumberFormatException e) {
            // Manejo de valores inválidos (letras o caracteres)
            System.out.println("Error: Ingrese un año válido en formato numérico.");
        }
    }

    private void buscarLibrosPorIdioma() {
        System.out.println("""
                Ingrese el idioma para buscar los libros:
                es - español
                en - inglés
                fr - francés
                pt - portugués
                """);
        var idioma = lectura.nextLine();

        Long cantidad = repository.countByIdioma(idioma);
        List<Libro> libros = repository.findByIdiomaIgnoreCase(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma '" + idioma + "' registrados.");
        } else {
            System.out.println("\n---------- ESTADÍSTICAS POR IDIOMA ----------");
            System.out.println("Cantidad de libros en '" + idioma + "': " + cantidad);
            System.out.println("----------------------------------------------");
            libros.forEach(System.out::println);
        }
    }
}