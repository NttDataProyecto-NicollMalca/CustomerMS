package com.example.customerms;

import com.example.customerms.api.ClientesApiDelegate;
import com.example.customerms.business.ClienteService;
import com.example.customerms.model.ClienteRequest;
import com.example.customerms.model.ClienteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteDelegateImp implements ClientesApiDelegate {

    @Autowired
    ClienteService clienteService;

    @Override
    public ResponseEntity<ClienteResponse> actualizarCliente(Long id, ClienteRequest clienteRequest) {
        return ResponseEntity.ok(clienteService.actualizarCliente(id,clienteRequest));
    }

    @Override
    public ResponseEntity<ClienteResponse> agregarCliente(ClienteRequest clienteRequest) {
        return ResponseEntity.ok(clienteService.agregarCliente(clienteRequest));
    }

    @Override
    public ResponseEntity<Void> eliminarCliente(Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ClienteResponse> getClientePorId(Long id) {
        return ResponseEntity.ok(clienteService.getClientePorId(id));
    }

    @Override
    public ResponseEntity<List<ClienteResponse>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }
}
