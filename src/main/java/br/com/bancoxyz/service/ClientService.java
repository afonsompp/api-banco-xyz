package br.com.bancoxyz.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.bancoxyz.error.exception.ResourceNotFoundException;
import br.com.bancoxyz.model.Client;
import br.com.bancoxyz.repository.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    private String notFoundMsg = "Não possível encontrar o registro com id: ";

    public Client insert(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        Optional<Client> obj = clientRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(notFoundMsg, id));
    }

    public boolean deleteById(Long id) {
        try {
            clientRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(notFoundMsg, id);
        }
    }

    public Client update(Long id, Client obj) {
        try {
            Client client = clientRepository.findById(id).get();
            updateData(client, obj);
            return clientRepository.save(client);
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException(notFoundMsg, id);
        }
    }

    private void updateData(Client client, Client obj) {
        client.setName(obj.getName());
        client.setEmail(obj.getEmail());
        client.setCpf(obj.getCpf());
        client.setDateOfBirth(obj.getDateOfBirth());
    }
}
