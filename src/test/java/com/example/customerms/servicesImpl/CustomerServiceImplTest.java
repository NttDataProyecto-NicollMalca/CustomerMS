package com.example.customerms.servicesImpl;

import com.example.customerms.api.ClientesApiController;
import com.example.customerms.business.ClienteServiceImp;
import com.example.customerms.business.ClienteValidatorImp;
import com.example.customerms.config.GlobalExceptionHandler;
import com.example.customerms.model.Cliente;
import com.example.customerms.model.ClienteRequest;
import com.example.customerms.model.ClienteResponse;
import com.example.customerms.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImp clienteService;

    private MockMvc mockMvc;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ClientesApiController clientesApiController;

    private ClienteValidatorImp clienteValidator;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientesApiController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        clienteValidator = new ClienteValidatorImp();


    }


    @Test
    @DisplayName("Cuando el cliente tiene cuentas activas, debe lanzar una excepción")
    void cuandoClienteTieneCuentasActivas() {
        Long clienteId = 1L;

        Cliente cliente = new Cliente();
        cliente.setId(clienteId.intValue());
        cliente.setNombre("Luisa");
        cliente.setApellido("Chavez");
        cliente.setDni("74859696");
        cliente.setEmail("luisa_chavez@gmFail.com");

        Mockito.when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        Mockito.when(restTemplate.getForObject(anyString(), Mockito.eq(List.class)))
                .thenReturn(Collections.singletonList(Map.of("saldo", 100.0)));

        assertThrows(IllegalArgumentException.class, () -> {
            clienteService.eliminarCliente(clienteId);
        });

        Mockito.verify(clienteRepository, Mockito.never()).delete(cliente);
    }

    @Test
    @DisplayName("Debe fallar si el DNI no tiene 8 dígitos")
    void testDniInvalido() {
        ClienteRequest clienteRequest = new ClienteRequest();
        clienteRequest.setNombre("Carlos");
        clienteRequest.setApellido("Pérez");
        clienteRequest.setDni("1234");
        clienteRequest.setEmail("carlos_perez@example.com");


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteValidator.validar(clienteRequest);
        });

        System.out.println("Mensaje de la excepción: " + exception.getMessage());
    }

    @Test
    @DisplayName("Debe fallar si el email no es válido")
    void testEmailInvalido() {
        ClienteRequest clienteRequest = new ClienteRequest();
        clienteRequest.setNombre("Carlos");
        clienteRequest.setApellido("Pérez");
        clienteRequest.setDni("12345678");
        clienteRequest.setEmail("email_invalido");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteValidator.validar(clienteRequest);
        });

        System.out.println("Mensaje de la excepción: " + exception.getMessage());

    }


}
