package com.example.customerms.business;

import com.example.customerms.model.ClienteRequest;
import com.example.customerms.model.ClienteResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ClienteService {

    public ClienteResponse actualizarCliente(Long id, ClienteRequest clienteRequest);

    public ClienteResponse agregarCliente(ClienteRequest clienteRequest);

    public void eliminarCliente(Long id);

    public ClienteResponse getClientePorId(Long id);

    public List<ClienteResponse> listarClientes();

}
