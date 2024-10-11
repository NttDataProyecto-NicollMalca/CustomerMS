package com.example.customerms.business;

import com.example.customerms.model.Cliente;
import com.example.customerms.model.ClienteRequest;
import com.example.customerms.model.ClienteResponse;
import com.example.customerms.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ClienteServiceImp implements ClienteService{

    private final  ClienteRepository clienteRepository;
    private final  ClienteMapper clienteMapper;
    private final  ClienteValidator  clienteValidator;
    private final  RestTemplate restTemplate;

<<<<<<< HEAD
=======
    @Autowired
    private RestTemplate restTemplate; 
>>>>>>> 7838560ab9e5f5b68064f7480a8be014f5db2cff

    private static final String Account_WS = "http://localhost:8081/cuentas";

    @Autowired
    public ClienteServiceImp(ClienteRepository clienteRepository, ClienteMapper clienteMapper,
                             ClienteValidator clienteValidator, RestTemplate restTemplate) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.clienteValidator = clienteValidator;
        this.restTemplate = restTemplate;
    }


    @Override
    public ClienteResponse actualizarCliente(Long id, ClienteRequest clienteRequest) {

        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con id: " + id));

<<<<<<< HEAD
        clienteValidator.validar(clienteRequest);

=======
>>>>>>> 7838560ab9e5f5b68064f7480a8be014f5db2cff
        Cliente clienteActualizado = clienteMapper.getClienteofClienteRequest(clienteRequest, clienteExistente.getId());

        return clienteMapper.getClienteResponseofCliente(clienteRepository.save(clienteActualizado));
    }

    @Override
    public ClienteResponse agregarCliente(ClienteRequest clienteRequest) {
        clienteValidator.validar(clienteRequest);

        if (clienteRepository.existsByDni(clienteRequest.getDni())) {
            throw new IllegalArgumentException("El DNI ya está registrado en el sistema");
        }
        return clienteMapper.getClienteResponseofCliente(
                clienteRepository.save(clienteMapper.getClienteofClienteRequest(clienteRequest,null)));
    }

    @Override
    public void  eliminarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con id: " + id));

        verificarCuentasActivas(id);
<<<<<<< HEAD
=======

>>>>>>> 7838560ab9e5f5b68064f7480a8be014f5db2cff
        clienteRepository.delete(cliente);
    }

    @Override
    public ClienteResponse getClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con id: " + id));

        return clienteMapper.getClienteResponseofCliente(cliente);
    }

    @Override
    public List<ClienteResponse> listarClientes() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::getClienteResponseofCliente)
                .collect(Collectors.toList());
    }

    private void verificarCuentasActivas(Long clienteId) {
        try {
            List<Map<String, Object>> cuentas = restTemplate.getForObject(Account_WS, List.class);
<<<<<<< HEAD
            boolean tieneCuentasActivas = cuentas.stream()
                    .anyMatch(cuenta -> (Double) cuenta.get("saldo") > 0);
=======

            boolean tieneCuentasActivas = cuentas.stream()
                    .anyMatch(cuenta -> (Double) cuenta.get("saldo") > 0); 
>>>>>>> 7838560ab9e5f5b68064f7480a8be014f5db2cff

            if (tieneCuentasActivas) {
                throw new IllegalArgumentException("No se puede eliminar el cliente porque tiene cuentas activas con saldo mayor a 0.");
            }
        } catch (RestClientException e) {
            throw new RuntimeException("Error al obtener cuentas del microservicio", e);
        }

    }
}
