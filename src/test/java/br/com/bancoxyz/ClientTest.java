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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import br.com.bancoxyz.controller.ClientController;
import br.com.bancoxyz.dto.ClientDTO;
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
}