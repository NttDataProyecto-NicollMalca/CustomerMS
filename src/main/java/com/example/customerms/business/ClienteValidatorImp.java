package com.example.customerms.business;

import com.example.customerms.model.ClienteRequest;
import org.springframework.stereotype.Service;

@Service
public class ClienteValidatorImp implements ClienteValidator {

    @Override
    public void validar(ClienteRequest clienteRequest) {
        if (!clienteRequest.getDni().matches("^[0-9]{8}$")) {
            throw new IllegalArgumentException("El DNI debe tener exactamente 8 dígitos");
        }
        if (!clienteRequest.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Debe proporcionar un correo electrónico válido");
        }
    }
}
