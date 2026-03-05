package com.alura.literatura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    private String nombreAutor;
    private Integer nacimientoAutor;
    private Integer fallecimientoAutor;
    private String idioma;
    private Double numeroDeDescargas;

    // Vínculo real con la tabla autores
    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;

    public Libro() {}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idioma = !datosLibro.idiomas().isEmpty() ? datosLibro.idiomas().get(0) : "Desconocido";
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();

        if (!datosLibro.autor().isEmpty()) {
            // Creamos la relación con la entidad Autor
            DatosAutor datosAutor = datosLibro.autor().get(0);
            this.autor = new Autor(datosAutor);


            this.nombreAutor = datosAutor.nombre();
            this.nacimientoAutor = datosAutor.fechaDeNacimiento();
            this.fallecimientoAutor = datosAutor.fechaDeFallecimiento();
        }
    }

    // Getters
    public String getTitulo() { return titulo; }
    public String getNombreAutor() { return nombreAutor; }
    public Integer getNacimientoAutor() { return nacimientoAutor; }
    public Integer getFallecimientoAutor() { return fallecimientoAutor; }
    public String getIdioma() { return idioma; }

    @Override
    public String toString() {
        return String.format("""
                ----- LIBRO -----
                Título: %s
                Autor: %s (%s - %s)
                Idioma: %s
                Descargas: %.2f
                -----------------
                """, titulo, nombreAutor, nacimientoAutor, fallecimientoAutor, idioma, numeroDeDescargas);
    }
}