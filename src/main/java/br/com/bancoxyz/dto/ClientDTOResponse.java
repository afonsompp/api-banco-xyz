package br.com.bancoxyz.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.bancoxyz.model.Client;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ClientDTOResponse {

    private Long id;
    private String name;
    private String email;
    private String cpf;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private LocalDate dateOfBirth;

    public static ClientDTOResponse parseToDtoResponse(Client client) {
        return new ClientDTOResponse(client.getId(), client.getName(), client.getEmail(), client.getCpf(),
                client.getDateOfBirth());
    }
}
