package br.com.bancoxyz.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.bancoxyz.model.Client;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ApiModel(value = "ClientResponse", description = "Resposta esperada de um cliente")
public class ClientDTOResponse {

    @ApiModelProperty(notes = "${client.model.id}",
    position = 1, example = "1", required = true)
    private Long id;
    @ApiModelProperty(notes = "${client.model.name}",
    position = 2, example = "Bob Brown", required = true)
    private String name;
    @ApiModelProperty(notes = "${client.model.email}",
    position = 3, example = "exemple@exemple.com", required = true)
    private String email;
    @ApiModelProperty(notes = "${client.model.email}",
    position = 4, example = "146.674.500-29", required = true)
    private String cpf;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    @ApiModelProperty(notes = "${client.model.date}",
    position = 5, example = "31/12/2000", required = true)
    private LocalDate dateOfBirth;

    public static ClientDTOResponse parseToDtoResponse(Client client) {
        return new ClientDTOResponse(client.getId(), client.getName(), client.getEmail(), client.getCpf(),
                client.getDateOfBirth());
    }

    public static List<ClientDTOResponse> parseListToDtoResponse(List<Client> clients) {
        List<ClientDTOResponse> list = new ArrayList<ClientDTOResponse>();
        for (Client c : clients) {
            list.add(parseToDtoResponse(c));
        }
        return list;
    }
}
