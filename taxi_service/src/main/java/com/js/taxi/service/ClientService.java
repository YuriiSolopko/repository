package com.js.taxi.service;

import com.js.taxi.domain.Client;
import com.js.taxi.exception.ClientException;

import java.util.List;

/**
 * Created by Jura on 03.04.2015.
 */
public interface ClientService {
    public boolean createClient(String name, String surname, String phone, String address) throws ClientException;
    public List<Client> showClientsByPortion(int portionSize);
    public List<Client> showClientsByPortions(int firstIndex, int portionSize);
    public List<Client> showClientsGtSum(int sum);
    public List<Client> showClientsLastMonth();
    public Long clientRowCount();
    public Client readClientByPhoneNo(String phoneNumber);
}
