package com.slmanagement.salesmanagement.services;

import com.slmanagement.salesmanagement.entities.Client;
import com.slmanagement.salesmanagement.exceptions.BusinessRuleException;
import com.slmanagement.salesmanagement.repositories.ClientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public Client save(Client client) {
        handleValidateDuplicateClient(client);
        return clientRepository.save(client);
    }

    public Client update(Long id, Client client) {
        Client handleGetClient = handleCheckClient(id);
        handleValidateDuplicateClient(client);
        BeanUtils.copyProperties(client, handleGetClient, "id");
        return clientRepository.save(handleGetClient);
    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    private Client handleCheckClient(Long id) {
        Optional<Client> client = findById(id);

        if (client.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }

        return client.get();
    }

    private void handleValidateDuplicateClient(Client client) {
        Client clientFound = clientRepository.findByName(client.getName());

        if (clientFound != null && !Objects.equals(clientFound.getId(), client.getId())) {
            throw new BusinessRuleException(
                    String.format("O cliente %s já existe com o código %s.", client.getName().toUpperCase(), clientFound.getId())
            );
        }
    }
}
