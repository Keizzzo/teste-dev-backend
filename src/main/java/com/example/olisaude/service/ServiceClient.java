package com.example.olisaude.service;

import com.example.olisaude.model.Client;

import java.util.List;

public interface ServiceClient {

    Long insertClient(Client client);
    Client updateClient(Client client, Long id);
    Client findClient(Long id);
    List<Client> listClients();


}
