package br.com.bancoxyz;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.net.HttpHeaders;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import br.com.bancoxyz.controller.ClientController;
import br.com.bancoxyz.dto.ClientDTO;
import br.com.bancoxyz.error.exception.ResourceNotFoundException;
import br.com.bancoxyz.model.Client;
import br.com.bancoxyz.service.ClientService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ClientController.class })
@AutoConfigureMockMvc
public class ClientTest {

    private final String BASE_URL = "/clients";

    private ObjectMapper objectMapper;
    @Autowired
    private ClientController clientController;
    @MockBean
    private ClientService clientService;

    private MockMvc mockMvc;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    public void insert_201() throws Exception {

        ClientDTO dto = new ClientDTO("Afonso Mateus", "afonso@gmail.com", "02654220273",
        LocalDate.parse("2000-04-23"));
        Client client = dto.parseToClient();

        when(clientService.insert(any(Client.class))).thenReturn(client);

        mockMvc.perform(post(BASE_URL).content(objectMapper.writeValueAsString(dto))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(dto.getName())))
            .andExpect(jsonPath("$.email", is(dto.getEmail())))
            .andExpect(jsonPath("$.cpf", is(dto.getCpf())))
            .andExpect(jsonPath("$.dateOfBirth", is(dto.getDateOfBirth().format(formatter))));

        verify(clientService, times(1)).insert(any(Client.class));
    }

    @Test(expected = NestedServletException.class)
    public void insert_409() throws Exception {
        ClientDTO dto = new ClientDTO("Afonso Mateus", "afonso@gmail.com", "02654220273",
        LocalDate.parse("2000-04-23"));
        when(clientService.insert(any(Client.class))).thenThrow(new DataIntegrityViolationException(""));

        mockMvc.perform(post(BASE_URL).content(objectMapper.writeValueAsString(dto))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict());

        verify(clientService, times(0)).insert(any(Client.class));
    }

    @Test()
    public void insert_400() throws Exception {

        ClientDTO dto = new ClientDTO("Afonso", "afonso@.com", "123.456.789.10",
        LocalDate.now().withDayOfMonth(LocalDate.now().getDayOfMonth() + 1));

        mockMvc.perform(post(BASE_URL).content(objectMapper.writeValueAsString(dto))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(clientService, times(0)).insert(any(Client.class));
    }

    @Test()
    public void findById_200() throws Exception {
        ClientDTO dto = new ClientDTO("Afonso Mateus", "afonso@gmail.com", "02654220273",
        LocalDate.parse("2000-04-23"));
        Client client = dto.parseToClient();

        when(clientService.findById(eq(1L))).thenReturn(client);
        mockMvc.perform(get(BASE_URL + "/1").content(objectMapper.writeValueAsString(dto))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(dto.getName())))
            .andExpect(jsonPath("$.email", is(dto.getEmail())))
            .andExpect(jsonPath("$.cpf", is(dto.getCpf())))
            .andExpect(jsonPath("$.dateOfBirth", is(dto.getDateOfBirth().format(formatter))));;

        verify(clientService, times(1)).findById(eq(1L));
    }

    @Test(expected = NestedServletException.class)
    public void findById_404() throws Exception {
        when(clientService.deleteById(eq(1L)))
            .thenThrow(new ResourceNotFoundException("", 1L));
        mockMvc.perform(delete(BASE_URL + "/1"))
            .andExpect(status().isNoContent());

        verify(clientService, times(1)).deleteById(eq(1L));
    }

    @Test
    public void update_201() throws Exception {

        ClientDTO dto = new ClientDTO("Afonso Mateus", "afonso@gmail.com", "02654220273",
        LocalDate.parse("2000-04-23"));
        Client client = dto.parseToClient();

        when(clientService.update(eq(1L), any(Client.class))).thenReturn(client);

        mockMvc.perform(put(BASE_URL + "/1").content(objectMapper.writeValueAsString(dto))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(dto.getName())))
            .andExpect(jsonPath("$.email", is(dto.getEmail())))
            .andExpect(jsonPath("$.cpf", is(dto.getCpf())))
            .andExpect(jsonPath("$.dateOfBirth", is(dto.getDateOfBirth().format(formatter))));

        verify(clientService, times(1)).update(eq(1L) ,any(Client.class));
    }

    @Test(expected = NestedServletException.class)
    public void update_409() throws Exception {
        ClientDTO dto = new ClientDTO("Afonso Mateus", "afonso@gmail.com", "02654220273",
        LocalDate.parse("2000-04-23"));
        when(clientService.update(eq(1L), any(Client.class))).thenThrow(new DataIntegrityViolationException(""));

        mockMvc.perform(put(BASE_URL + "/1").content(objectMapper.writeValueAsString(dto))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict());

        verify(clientService, times(0)).insert(any(Client.class));
    }

    @Test()
    public void update_400() throws Exception {

        ClientDTO dto = new ClientDTO("Afonso", "afonso@.com", "123.456.789.10",
        LocalDate.now().withDayOfMonth(LocalDate.now().getDayOfMonth() + 1));

        mockMvc.perform(put(BASE_URL + "/1").content(objectMapper.writeValueAsString(dto))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(clientService, times(0)).insert(any(Client.class));
    }

    @Test(expected = NestedServletException.class)
    public void update_404() throws Exception {

        ClientDTO dto = new ClientDTO("Afonso Mateus", "afonso@gmail.com", "02654220273",
        LocalDate.parse("2000-04-23"));

        when(clientService.update(eq(1L), any(Client.class)))
            .thenThrow(new ResourceNotFoundException("", 1L));
        mockMvc.perform(put(BASE_URL + "/1").content(objectMapper.writeValueAsString(dto))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(clientService, times(0)).update(eq(1L), any(Client.class));
    }

    @Test(expected = NestedServletException.class)
    public void delete_404() throws Exception {
        when(clientService.deleteById(eq(1L)))
            .thenThrow(new ResourceNotFoundException("", 1L));
        mockMvc.perform(delete(BASE_URL + "/1"))
            .andExpect(status().isNotFound());

        verify(clientService, times(0)).deleteById(eq(1L));
    }

    @Test()
    public void delete_204() throws Exception {
        when(clientService.deleteById(eq(1L)))
            .thenReturn(true);
        mockMvc.perform(delete(BASE_URL + "/1"))
            .andExpect(status().isNoContent());

        verify(clientService, times(1)).deleteById(eq(1L));
    }

}
