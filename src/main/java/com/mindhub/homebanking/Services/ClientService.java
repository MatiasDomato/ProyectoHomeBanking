package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClientService {
    public List<ClientDTO> getClients();
    public Client saveClient (Client client);
    public ClientDTO getClient(@PathVariable Long id);
    public ClientDTO findByEmail(String email);
    public Client findClientByEmail(String email);
}
