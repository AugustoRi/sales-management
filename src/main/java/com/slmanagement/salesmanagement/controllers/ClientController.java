package com.slmanagement.salesmanagement.controllers;

import com.slmanagement.salesmanagement.dtos.requests.ClientRequestDTO;
import com.slmanagement.salesmanagement.dtos.responses.ClientResponseDTO;
import com.slmanagement.salesmanagement.entities.Client;
import com.slmanagement.salesmanagement.services.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(tags = "Clients")
@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @ApiOperation(value = "List all clients", nickname = "findAllClients")
    @GetMapping
    public List<ClientResponseDTO> findAll() {
        return clientService
                .findAll()
                .stream()
                .map(ClientResponseDTO::handleConvertToClientDTO)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "List categories by id", nickname = "findCategoryById")
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> findById(@PathVariable Long id) {
        Optional<Client> client = clientService.findById(id);

        return client
                .map(cat -> ResponseEntity.ok(ClientResponseDTO.handleConvertToClientDTO(cat)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Save client", nickname = "saveClient")
    @PostMapping
    public ResponseEntity<ClientResponseDTO> save(@Valid @RequestBody ClientRequestDTO clientDTO) {
        Client handleSaveClient = clientService.save(clientDTO.handleConvertToEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(ClientResponseDTO.handleConvertToClientDTO(handleSaveClient));
    }

    @ApiOperation(value = "Update client", nickname = "updateClient")
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ClientRequestDTO clientDTO) {
        Client handleUpdatedClient = clientService.update(id, clientDTO.handleConvertToEntity(id));
        return ResponseEntity.ok(ClientResponseDTO.handleConvertToClientDTO(handleUpdatedClient));
    }

    @ApiOperation(value = "Delete Client", nickname = "deleteClient")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }
}
