package com.example.olisaude.service;

import com.example.olisaude.model.Client;
import com.example.olisaude.util.ObjectResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HighRiskHealthyManagementImpl implements HighRiskHealthyManagement{

    @Autowired
    private ServiceClient serviceClient;

    @Override
    public List<Client> listTopTenRiskClients() {
        var list = serviceClient.listClients();

        List<ObjectResponse<Double, Long>> grades = new ArrayList<>();
        List<ObjectResponse<Double, Long>> finalGrades = grades;

        //LISTA DE SOMA DE GRADES E ID
        list.stream().map(c -> finalGrades.add(new ObjectResponse<>(
                c.getHealthProblemList().stream().map(h -> Double.parseDouble(h.getProblemDegree().toString())).reduce(0.0, (subtotal, element) -> subtotal + element)
                , c.getHealthProblemList().get(0).getClientId()))).collect(Collectors.toList());

        //LISTAGEM DO MAIOR PRO MENOR
        grades = finalGrades.stream().map(value ->{
            var sum = value.getObject();
            var score = (1 / (1 + Math.pow(Math.E, -(-2.8 + sum))));

            return new ObjectResponse<Double, Long>(score, value.getId());
        }).sorted(Collections.reverseOrder(Comparator.comparing(ObjectResponse::getObject))).collect(Collectors.toList());

        //PREPARANDO LISTA DE RETORNO
        List<Client> clients = new ArrayList<>();

        grades.stream().map(g ->{
            list.stream().filter(c -> g.getId() == c.getHealthProblemList().get(0).getClientId() && clients.size() < 10).map(clients::add).collect(Collectors.toList());
            return g;
        }).collect(Collectors.toList());

        return clients;
    }
}
