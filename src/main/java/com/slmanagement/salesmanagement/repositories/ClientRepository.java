package com.slmanagement.salesmanagement.repositories;

import com.slmanagement.salesmanagement.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByName(String name);
}
