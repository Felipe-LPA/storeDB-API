package com.letscode.store.controller;

import com.letscode.store.dto.ClientDTO;
import com.letscode.store.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClienteController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Test
    public void saveNewClientTest() throws Exception {

        Mockito.when(clientService.saveClient(Mockito.any())).thenReturn(ClientDTO.builder().build());

        mockMvc.perform(post("/client").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"name\": \"Felipe\",\n" +
                                "    \"cpf\": \"33670908080\"\n" +
                                "}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void getListClientsTest() throws Exception {
        Mockito.when(clientService.listClient(Mockito.any(), Mockito.any())).thenReturn(Page.empty());

        mockMvc.perform(get("/client"))
                .andExpect(status().isOk());

    }
    @Test
    public void updateClientTest() throws Exception {
        Mockito.when(clientService.updateClient(Mockito.any(), Mockito.any())).thenReturn(ClientDTO.builder().build());

        mockMvc.perform(put("/client/33670908080")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"name\": \"Felipe\",\n" +
                                "    \"cpf\": \"33670908080\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteClientTest() throws Exception {
        mockMvc.perform(delete("/client/33670908080"))
                .andExpect(status().isOk());
    }
}
