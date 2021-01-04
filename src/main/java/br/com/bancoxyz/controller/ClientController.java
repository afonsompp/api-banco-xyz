package br.com.bancoxyz.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.bancoxyz.dto.ClientDTO;
import br.com.bancoxyz.dto.ClientDTOResponse;
import br.com.bancoxyz.model.Client;
import br.com.bancoxyz.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping(value = "/clients")
@Api(tags = "Cliente", description = "Operações referentes a rota de cliente")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "${client.post.value}")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Retorna json com as informações cadastradas."),
        @ApiResponse(code = 400, message = "Dados inválidos!"),
        @ApiResponse(code = 409, message = "Dados duplicados!")
    })
    public ResponseEntity<ClientDTOResponse> insert(
            @ApiParam(name = "cliente", value = "${client.body}", required = true)
            @RequestBody @Valid ClientDTO dto){

        Client client = clientService.insert(dto.parseToClient());
        return new ResponseEntity<>(ClientDTOResponse.parseToDtoResponse(client), HttpStatus.CREATED);
    }

    @ApiOperation(value = "${client.get.value}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Todos os clientes retornados com sucesso."),
    })
    @GetMapping
    public ResponseEntity<List<ClientDTOResponse>> findAll() {
        List<ClientDTOResponse> clients = ClientDTOResponse
            .parseListToDtoResponse(clientService.findAll());
            return ResponseEntity.ok().body(clients);
    }

    @ApiOperation(value = "${client.getid.value}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Retorna json com o cliente encontrado."),
        @ApiResponse(code = 404, message = "Nenhum cliente encontrado"),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTOResponse> findById(
            @ApiParam(value = "${client.getid.param}", required = true)
            @PathVariable Long id) {
        Client client = clientService.findById(id);
        return ResponseEntity.ok().body(ClientDTOResponse.parseToDtoResponse(client));
    }

}
