package es.cic.curso008_ejerc007.controller;

import es.cic.curso008_ejerc007.model.Biblioteca;
import es.cic.curso008_ejerc007.repository.BibliotecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bibliotecas")
public class BibliotecaController {

    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    @GetMapping
    public List<Biblioteca> getAllBibliotecas() {
        return bibliotecaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Biblioteca> getBibliotecaById(@PathVariable Long id) {
        Optional<Biblioteca> biblioteca = bibliotecaRepository.findById(id);
        return biblioteca.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Biblioteca createBiblioteca(@RequestBody Biblioteca biblioteca) {
        return bibliotecaRepository.save(biblioteca);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Biblioteca> updateBiblioteca(@PathVariable Long id, @RequestBody Biblioteca bibliotecaDetails) {
        Optional<Biblioteca> biblioteca = bibliotecaRepository.findById(id);
        if (biblioteca.isPresent()) {
            Biblioteca updatedBiblioteca = biblioteca.get();
            updatedBiblioteca.setNombre(bibliotecaDetails.getNombre());
            updatedBiblioteca.setDireccion(bibliotecaDetails.getDireccion());
            updatedBiblioteca.setTelefono(bibliotecaDetails.getTelefono());
            return ResponseEntity.ok(bibliotecaRepository.save(updatedBiblioteca));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBiblioteca(@PathVariable Long id) {
        Optional<Biblioteca> biblioteca = bibliotecaRepository.findById(id);
        if (biblioteca.isPresent()) {
            bibliotecaRepository.delete(biblioteca.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}