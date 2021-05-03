package com.example.olisaude.controller;

import com.example.olisaude.model.Client;
import com.example.olisaude.service.HighRiskHealthyManagement;
import com.example.olisaude.service.ServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private ServiceClient serviceClient;

    @Autowired
    private HighRiskHealthyManagement highRiskHealthyManagement;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @RequestMapping(value = "/client", method = RequestMethod.POST)
    public Long insertClient(@RequestBody Client client){

        return serviceClient.insertClient(client);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
    public Client findClient(@PathVariable Long id){

        return serviceClient.findClient(id);

    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @RequestMapping(value = "/client/list", method = RequestMethod.GET)
    public List<Client> listClients(){

        return serviceClient.listClients();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @RequestMapping(value = "/client/list/top-ten-risk-clients", method = RequestMethod.GET)
    public List<Client> listTopTenRiskClients(){

        return  highRiskHealthyManagement.listTopTenRiskClients();
    }

}
