package com.mindhub.homebanking.Services.implementations;

import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public ClientDTO getClient(Long id) {
//        return clientRepository.findById(id).map(client -> new ClientDTO(client)).orElse(null);
        ClientDTO clientDTO = new ClientDTO(clientRepository.findById(id).orElse(null));
        return clientDTO;
    }

    @Override
    public ClientDTO findByEmail(String email) {
        return new ClientDTO(clientRepository.findByEmail(email));
    }

    @Override
    public Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }


}
