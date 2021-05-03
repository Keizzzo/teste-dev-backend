package com.example.olisaude.service;

import com.example.olisaude.model.Client;
import com.example.olisaude.model.HealthProblem;
import com.example.olisaude.repository.Dao;
import com.example.olisaude.repository.SubClassesDao;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ServiceClientImpl implements ServiceClient {

    @Autowired
    private Dao<Client> clientRepository;

    @Autowired
    private Dao<HealthProblem> healthProblemRepository;

    @Autowired
    private SubClassesDao<HealthProblem> subClassesHealthProblem;

    @Override
    public Long insertClient(Client client) {

        try {
            var id = clientRepository.insert(client);

            //REFINAR INSERT POR BATCH UPDATE
            client.getHealthProblemList().forEach(c -> {
                c.setClientId(id);
                healthProblemRepository.insert(c);
            });

            return id;

        } catch (Exception e) {
            throw new RuntimeException("Error in Client Register");
        }
    }

    @Override
    public Client updateClient(Client client, Long id) {
        return null;
    }

    @Override
    public Client findClient(Long id) {

        try {
            var client = clientRepository.find(id);
            var listHealthProblem = subClassesHealthProblem.listBySuperId(id);
            client.setHealthProblemList(listHealthProblem);

            return client;

        } catch (Exception e) {
            throw new RuntimeException("Error finding Client Register");
        }
    }

    @Override
    public List<Client> listClients() {

        var clients = clientRepository.list();

        clients.forEach(obj -> {
            obj.getObject().setHealthProblemList(subClassesHealthProblem.listBySuperId(obj.getId()));
        });

        var clientsList = new ArrayList<Client>();
        clients.stream().map(c -> clientsList.add(c.getObject())).collect(Collectors.toList());

        return clientsList;
    }
}
