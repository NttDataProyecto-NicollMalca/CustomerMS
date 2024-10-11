package com.example.customerms.business;

import com.example.customerms.model.Cliente;
import com.example.customerms.model.ClienteRequest;
import com.example.customerms.model.ClienteResponse;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class ClienteMapper {

    private static ClienteMapper instance;

    private ClienteMapper() {
    }

    public static ClienteMapper getInstance() {
        if (instance == null) {
            instance = new ClienteMapper();
        }
        return instance;
    }

    private int generarIdAleatorio() {
        return IntStream.generate(() -> new Random().nextInt(900000) + 100000)
                .limit(1)
                .findFirst()
                .getAsInt();
    }

    public Cliente getClienteofClienteRequest(ClienteRequest request, Integer existingId) {
        Cliente cliente = new Cliente();

        if (existingId != null) {
            cliente.setId(existingId);
        } else {
            cliente.setId(generarIdAleatorio());
        }
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());

        if (!request.getDni().matches("^[0-9]{8}$")) {
            throw new IllegalArgumentException("El DNI debe tener exactamente 8 dígitos");
        }

        cliente.setDni(request.getDni());

        if (!request.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Debe proporcionar un correo electrónico válido");
        }
        cliente.setEmail(request.getEmail());

        return cliente;
    }

    public ClienteResponse getClienteResponseofCliente(Cliente cliente) {
        ClienteResponse response = new ClienteResponse();
        response.setId(cliente.getId());
        response.setNombre(cliente.getNombre());
        response.setApellido(cliente.getApellido());
        response.setDni(cliente.getDni());
        response.setEmail(cliente.getEmail());
        return response;
    }
}

