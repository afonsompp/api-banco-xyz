package br.com.bancoxyz.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bancoxyz.dto.ClientDTO;
import br.com.bancoxyz.dto.ClientDTOResponse;
import br.com.bancoxyz.model.Client;
import br.com.bancoxyz.service.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @PostMapping
    public ResponseEntity<ClientDTOResponse> insert(@RequestBody @Valid ClientDTO dto){
        Client client = clientService.insert(dto.parseToClient());
        return new ResponseEntity<>(ClientDTOResponse.parseToDtoResponse(client), HttpStatus.CREATED);
    }
}
