package com.alura.literatura.repository;

import com.alura.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Derived Query para contar libros por idioma
    Long countByIdioma(String idioma);


    List<Libro> findByIdiomaIgnoreCase(String idioma);
}