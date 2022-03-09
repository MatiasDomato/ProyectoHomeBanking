package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {
    public List<ClientDTO> getClients();
    public Client saveClient (Client client);
}
