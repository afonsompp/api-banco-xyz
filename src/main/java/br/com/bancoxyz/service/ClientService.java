package br.com.bancoxyz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bancoxyz.model.Client;
import br.com.bancoxyz.repository.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client insert(Client client) {
        return clientRepository.save(client);
    }
}
