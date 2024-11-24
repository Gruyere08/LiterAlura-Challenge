package com.challenge.LiteraAlura.repository;

import com.challenge.LiteraAlura.model.Autor;
import com.challenge.LiteraAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTitulo(String titulo);

    @Query("SELECT l FROM Libro l JOIN l.lenguajes leng WHERE leng = :lenguaje")
    List<Libro> findByLenguaje(@Param("lenguaje") String lenguaje);

    @Query("SELECT l FROM Libro l JOIN FETCH l.autores")
    List<Libro> findAllWithAutores();

    List<Libro> findTop5ByOrderByDescargasDesc();

    @Modifying
    @Transactional
    @Query("DELETE FROM Libro l")
    void deleteAllLibros();


}
