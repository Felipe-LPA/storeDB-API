package com.letscode.store.controller;

import com.letscode.store.dto.ClientDTO;
import com.letscode.store.dto.ResponsePurchaseDTO;
import com.letscode.store.service.ClientService;
import com.letscode.store.service.PurchaseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PurchaseController.class)
public class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PurchaseService purchaseService;

    @Test
    public void savePurchaseTest() throws Exception {
        Mockito.when(purchaseService.savePurchase(Mockito.any())).thenReturn(ResponsePurchaseDTO.builder().build());

        mockMvc.perform(post("/purchase").with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n" +
                            "    \"cpf\": \"75528488044\",\n" +
                            "    \"purchasedProducts\":[\n" +
                            "        {\n" +
                            "            \"productCode\": \"300\",\n" +
                            "            \"quantity\": 20\n" +
                            "        }\n" +
                            "    ]\n" +
                            "}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void listPurchaseTest() throws Exception {
        Mockito.when(purchaseService.listPurchase(Mockito.any(), Mockito.any())).thenReturn(Page.empty());

        mockMvc.perform(get("/purchase"))
                .andExpect(status().isOk());
    }

}
