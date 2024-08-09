package es.cic.curso008_ejerc007.controller;

import es.cic.curso008_ejerc007.model.Biblioteca;
import es.cic.curso008_ejerc007.repository.BibliotecaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BibliotecaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    @BeforeEach
    public void setUp() {
        bibliotecaRepository.deleteAll();
    }

    @Test
    public void testUnauthorizedAccessToGetAllBibliotecas() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bibliotecas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUnauthorizedAccessToGetBibliotecaById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bibliotecas/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUnauthorizedAccessToCreateBiblioteca() throws Exception {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setNombre("Biblioteca Central");
        biblioteca.setDireccion("Calle Falsa 123");
        biblioteca.setTelefono("123456789");

        mockMvc.perform(MockMvcRequestBuilders.post("/bibliotecas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Biblioteca Central\",\"direccion\":\"Calle Falsa 123\",\"telefono\":\"123456789\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUnauthorizedAccessToUpdateBiblioteca() throws Exception {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setNombre("Biblioteca Central");
        biblioteca.setDireccion("Calle Falsa 123");
        biblioteca.setTelefono("123456789");
        biblioteca = bibliotecaRepository.save(biblioteca);

        mockMvc.perform(MockMvcRequestBuilders.put("/bibliotecas/" + biblioteca.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Biblioteca Actualizada\",\"direccion\":\"Calle Verdadera 456\",\"telefono\":\"987654321\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUnauthorizedAccessToDeleteBiblioteca() throws Exception {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setNombre("Biblioteca Central");
        biblioteca.setDireccion("Calle Falsa 123");
        biblioteca.setTelefono("123456789");
        biblioteca = bibliotecaRepository.save(biblioteca);

        mockMvc.perform(MockMvcRequestBuilders.delete("/bibliotecas/" + biblioteca.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}