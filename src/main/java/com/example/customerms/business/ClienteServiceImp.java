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

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    ClienteMapper clienteMapper;

    @Autowired
    private RestTemplate restTemplate; // Para llamadas a otros microservicios

    private static final String Account_WS = "http://localhost:8081/cuentas";


    @Override
    public ClienteResponse actualizarCliente(Long id, ClienteRequest clienteRequest) {

        // Obtener el cliente existente de la base de datos
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con id: " + id));

        // Pasar el ID existente al mapper para que no lo reemplace
        Cliente clienteActualizado = clienteMapper.getClienteofClienteRequest(clienteRequest, clienteExistente.getId());

        // Guardar los cambios
        return clienteMapper.getClienteResponseofCliente(clienteRepository.save(clienteActualizado));

    }

    @Override
    public ClienteResponse agregarCliente(ClienteRequest clienteRequest) {
        if (clienteRepository.existsByDni(clienteRequest.getDni())) {
            throw new IllegalArgumentException("El DNI ya está registrado en el sistema");
        }
        return clienteMapper.getClienteResponseofCliente(
                clienteRepository.save(clienteMapper.getClienteofClienteRequest(clienteRequest,null)));
    }

    @Override
    public ResponseEntity<Void>  eliminarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con id: " + id));

        // Verificar si el cliente tiene cuentas activas
        verificarCuentasActivas(id);


        clienteRepository.delete(cliente);

        return ResponseEntity.noContent().build();

    }

    @Override
    public ClienteResponse getClientePorId(Long id) {
        // Verifica si el cliente existe, si no, lanza IllegalArgumentException
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
        // Hacer la solicitud al microservicio para obtener las cuentas del cliente
        try {
            List<Map<String, Object>> cuentas = restTemplate.getForObject(Account_WS, List.class);


            // Verificar si hay cuentas activas
            boolean tieneCuentasActivas = cuentas.stream()
                    .anyMatch(cuenta -> (Double) cuenta.get("saldo") > 0); // Suponiendo que el saldo es un Double

            if (tieneCuentasActivas) {
                throw new IllegalArgumentException("No se puede eliminar el cliente porque tiene cuentas activas con saldo mayor a 0.");
            }
        } catch (RestClientException e) {
            // Manejar error de cliente
            throw new RuntimeException("Error al obtener cuentas del microservicio", e);
        }

    }
}
