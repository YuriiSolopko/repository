package com.js.taxi.service;

import com.js.taxi.dao.ClientDao;
import com.js.taxi.domain.Client;
import com.js.taxi.exception.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Jura on 03.04.2015.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDao clientDao;

    @Override
    public boolean createClient(String name, String surname, String phone, String address) throws ClientException {
        if (name.length() > 1 && surname.length() > 1 && phone.length() > 5 && address.length() > 1) {
            Client client = new Client(name, surname, phone, address);
            clientDao.create(client);
            return true;
        } else {
            throw new ClientException("Incorrect data");
        }

    }

    @Override
    public List<Client> showClientsByPortion(int portionSize) {
        return clientDao.findPortion(0, portionSize);
    }

    @Override
    public List<Client> showClientsByPortions(int firstIndex, int portionSize) {
        return clientDao.findPortion(firstIndex, portionSize);
    }

    @Override
    public Long clientRowCount() {
        return clientDao.rowCount();
    }

    @Override
    public List<Client> showClientsGtSum(int sum) {
        return clientDao.showGtSum(sum);
    }

    @Override
    public List<Client> showClientsLastMonth() {
        return clientDao.lastMonth();
    }

    @Override
    public Client readClientByPhoneNo(String phoneNumber) {
        return clientDao.readByPhoneNo(phoneNumber);
    }

}
