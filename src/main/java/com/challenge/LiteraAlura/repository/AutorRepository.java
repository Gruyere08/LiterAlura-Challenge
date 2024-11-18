package com.challenge.LiteraAlura.repository;

import com.challenge.LiteraAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre(String nombre);


    @Query("SELECT a FROM Autor a JOIN FETCH a.libros")
    List<Autor> findAllWithLibros();

    @Query("SELECT a FROM Autor a WHERE a.anio_nacimiento <= :anio " +
            "AND a.anio_nacimiento IS NOT NULL " +
            "AND (a.anio_fallecimiento IS NULL OR a.anio_fallecimiento >= :anio)")
    List<Autor> findAutoresVivosEnAnio(@Param("anio") int anio);


}
