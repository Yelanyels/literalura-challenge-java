package com.alura.literatura.repository;

import com.alura.literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Consulta derivada para encontrar autores vivos en un año específico
    // Busca: nacimiento <= año Y (fallecimiento >= año O fallecimiento es nulo)
    List<Autor> findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqual(Integer anioNacimiento, Integer anioFallecimiento);
}