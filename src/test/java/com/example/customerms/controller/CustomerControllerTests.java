package com.example.customerms.controller;

import com.example.customerms.ClienteDelegateImp;
import com.example.customerms.api.ClientesApiController;
import com.example.customerms.business.ClienteMapper;
import com.example.customerms.business.ClienteValidator;
import com.example.customerms.business.ClienteValidatorImp;
import com.example.customerms.config.GlobalExceptionHandler;
import com.example.customerms.model.ApiError;
import com.example.customerms.model.Cliente;
import com.example.customerms.model.ClienteRequest;
import com.example.customerms.model.ClienteResponse;
import com.example.customerms.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTests {

    private MockMvc mockMvc;

    @Mock
    private ClienteDelegateImp clienteDelegate;

    @InjectMocks
    private ClientesApiController clientesApiController;

    @Mock
    private ClienteValidator clienteValidator;



    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientesApiController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Cuando agrego un cliente correctamente, debe retornar un estado 200 OK")
    void agregarClienteReturnOk() throws Exception {
        ClienteRequest clienteRequest = new ClienteRequest();
        clienteRequest.setNombre("Luisa");
        clienteRequest.setApellido("Chavez");
        clienteRequest.setDni("74859696");
        clienteRequest.setEmail("luisa_chavez@gmail.com");

        ClienteMapper clienteMapper = ClienteMapper.getInstance();
        Cliente cliente = clienteMapper.getClienteofClienteRequest(clienteRequest, null);
        ClienteResponse clienteResponse = clienteMapper.getClienteResponseofCliente(cliente);

        Mockito.when(clienteDelegate.agregarCliente(Mockito.any(ClienteRequest.class)))
                .thenReturn(ResponseEntity.ok(clienteResponse));

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(clienteRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.nombre").value("Luisa"))
                .andExpect(jsonPath("$.apellido").value("Chavez"))
                .andExpect(jsonPath("$.dni").value("74859696"))
                .andExpect(jsonPath("$.email").value("luisa_chavez@gmail.com"));
    }


    @Test
    @DisplayName("Cuando actualizo un cliente correctamente, debe retornar un estado 200 OK")
    void actualizarClienteReturnOk() throws Exception {
        int clienteId = 123456;

        ClienteRequest clienteRequest = new ClienteRequest();
        clienteRequest.setNombre("Luisa");
        clienteRequest.setApellido("Chavez");
        clienteRequest.setDni("74859696");
        clienteRequest.setEmail("luisa_chavez1224@gmail.com");

        ClienteMapper clienteMapper =  ClienteMapper.getInstance();
        Cliente cliente = clienteMapper.getClienteofClienteRequest(clienteRequest, clienteId);
        ClienteResponse clienteResponse = clienteMapper.getClienteResponseofCliente(cliente);

        Mockito.when(clienteDelegate.actualizarCliente(Mockito.eq((long) clienteId), Mockito.any(ClienteRequest.class)))
                .thenReturn(ResponseEntity.ok(clienteResponse));


        mockMvc.perform(MockMvcRequestBuilders.put("/clientes/{id}", clienteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(clienteRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clienteId))
                .andExpect(jsonPath("$.nombre").value("Luisa"))
                .andExpect(jsonPath("$.apellido").value("Chavez"))
                .andExpect(jsonPath("$.dni").value("74859696"))
                .andExpect(jsonPath("$.email").value("luisa_chavez1224@gmail.com"));
    }

    @Test
    @DisplayName("Cuando obtengo un cliente por ID correctamente, debe retornar un estado 200 OK")
    void getClientePorIdReturnOk() throws Exception {
        Long clienteId = 741452L;
        Cliente cliente = new Cliente();
        cliente.setId(clienteId.intValue());
        cliente.setNombre("Piero");
        cliente.setApellido("Arevalo");
        cliente.setDni("89894574");
        cliente.setEmail("piero_arevalo@gmail.com");

        ClienteResponse clienteResponse = ClienteMapper.getInstance().getClienteResponseofCliente(cliente);

        Mockito.when(clienteDelegate.getClientePorId(clienteId)).thenReturn(ResponseEntity.ok(clienteResponse));


        System.out.println("Mock de GetClienteById configurado: " + clienteDelegate.getClientePorId(Mockito.eq((long) clienteId)));

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/{id}", clienteId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clienteId.intValue()))
                .andExpect(jsonPath("$.nombre").value("Piero"))
                .andExpect(jsonPath("$.apellido").value("Arevalo"))
                .andExpect(jsonPath("$.dni").value("89894574"))
                .andExpect(jsonPath("$.email").value("piero_arevalo@gmail.com"));
    }

    @Test
    @DisplayName("Cuando listamos todos los clientes, debe retornar un estado 200 OK")
    void listarClientesReturnOk() throws Exception {
        Long clienteId1 = 146810L;
        Cliente cliente1 = new Cliente();
        cliente1.setId(clienteId1.intValue());
        cliente1.setNombre("Luisa");
        cliente1.setApellido("Chavez");
        cliente1.setDni("74859696");
        cliente1.setEmail("luisa_chavez@gmail.com");

        Long clienteId2 = 146811L;
        Cliente cliente2 = new Cliente();
        cliente2.setId(clienteId2.intValue());
        cliente2.setNombre("Carlos");
        cliente2.setApellido("Pérez");
        cliente2.setDni("12345678");
        cliente2.setEmail("carlos_perez@gmail.com");

        ClienteResponse clienteResponse1 = ClienteMapper.getInstance().getClienteResponseofCliente(cliente1);
        ClienteResponse clienteResponse2 = ClienteMapper.getInstance().getClienteResponseofCliente(cliente2);


        List<ClienteResponse> listaClientes = Arrays.asList(clienteResponse1, clienteResponse2);
        Mockito.when(clienteDelegate.listarClientes()).thenReturn(ResponseEntity.ok(listaClientes));

        System.out.println("Mock de ListarClientes configurado: " + clienteDelegate.listarClientes());

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(clienteId1.intValue()))
                .andExpect(jsonPath("$[0].nombre").value("Luisa"))
                .andExpect(jsonPath("$[0].apellido").value("Chavez"))
                .andExpect(jsonPath("$[0].dni").value("74859696"))
                .andExpect(jsonPath("$[0].email").value("luisa_chavez@gmail.com"))
                .andExpect(jsonPath("$[1].id").value(clienteId2.intValue()))
                .andExpect(jsonPath("$[1].nombre").value("Carlos"))
                .andExpect(jsonPath("$[1].apellido").value("Pérez"))
                .andExpect(jsonPath("$[1].dni").value("12345678"))
                .andExpect(jsonPath("$[1].email").value("carlos_perez@gmail.com"));

    }

    @Test
    @DisplayName("Cuando elimino un cliente correctamente, debe retornar un estado 204 NO CONTENT")
    void eliminarClienteReturnNoContent() throws Exception {
        Long id = Long.valueOf(122456);

        Mockito.when(clienteDelegate.eliminarCliente(Mockito.eq(id))).thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(MockMvcRequestBuilders.delete("/clientes/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testApiErrorCreation() {
        ApiError apiError = new ApiError();
        OffsetDateTime now = OffsetDateTime.now();

        apiError.setTimestamp(now);
        apiError.setStatus(500);
        apiError.setError("Internal Server Error");
        apiError.setMessage("El DNI ya está registrado en el sistema");
        apiError.setPath("/clientes");

        assertEquals(now, apiError.getTimestamp());
        assertEquals(500, apiError.getStatus());
        assertEquals("Internal Server Error", apiError.getError());
        assertEquals("El DNI ya está registrado en el sistema", apiError.getMessage());
        assertEquals("/clientes", apiError.getPath());
    }


    @Test
    @DisplayName("Cuando el DNI ya está registrado, debe devolver un InlineResponse500 con el mensaje adecuado")
    void testDniYaRegistrado() throws Exception {
        Mockito.when(clienteDelegate.agregarCliente(Mockito.any(ClienteRequest.class)))
                .thenThrow(new IllegalArgumentException("El DNI ya está registrado en el sistema"));


        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"nombre\": \"Juan\", \"apellido\": \"Perez\", \"dni\": \"12345678\", \"email\": \"juan.perez@example.com\" }"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("El DNI ya está registrado en el sistema"))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.path").value("/clientes"));
    }

    @Test
    @DisplayName("Error si DNI tiene menos a 8 digitos")
    void testDniInvalido() throws Exception {
        ClienteRequest clienteRequest = new ClienteRequest();
        clienteRequest.setNombre("Carlos");
        clienteRequest.setApellido("Pérez");
        clienteRequest.setDni("1234");
        clienteRequest.setEmail("carlos_perez@example.com");

        Mockito.doThrow(new IllegalArgumentException("El DNI debe tener exactamente 8 dígitos"))
                .when(clienteDelegate).agregarCliente(Mockito.any(ClienteRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"nombre\": \"Carlos\", \"apellido\": \"Pérez\", \"dni\": \"1234\", \"email\": \"carlos_perez@example.com\" }"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("El DNI debe tener exactamente 8 dígitos"))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.path").value("/clientes"));
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}