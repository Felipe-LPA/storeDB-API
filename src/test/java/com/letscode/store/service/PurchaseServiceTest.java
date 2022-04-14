package com.letscode.store.service;

import com.letscode.store.dto.*;
import com.letscode.store.model.Client;
import com.letscode.store.model.Product;
import com.letscode.store.model.Purchase;
import com.letscode.store.model.PurchaseProduct;
import com.letscode.store.repository.PurchaseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class PurchaseServiceTest {

    @InjectMocks
    private PurchaseService purchaseService;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private ClientService clientService;

    @Mock
    private ProductService productService;

    @Mock
    private PurchaseProductService purchaseProductService;

    @Test
    public void TestSaveValidPurchase(){

        Client client = Client.builder()
                .cpf("123")
                .name("Felipe")
                .build();

        Purchase purchase = Purchase.builder()
                .purchaseDate(LocalDateTime.now())
                .totalPurchased(1000D)
                .client(client)
                .build();

        Product product = Product.builder()
                        .productCode("100")
                        .price(123D)
                        .quantity(1000)
                        .build();

        List<PurchaseProduct> purchaseProducts =  new ArrayList<>();
        PurchaseProduct.builder().build();

        Mockito.when(purchaseRepository.save(Mockito.any())).thenReturn(purchase);

        Mockito.when(clientService.getClient(Mockito.any())).thenReturn(client);

        Mockito.when(productService.updateProduct(Mockito.any())).thenReturn(ProductDTO.builder().build());

        Mockito.when(productService.getProduct(Mockito.any())).thenReturn(product);

        Mockito.when(purchaseProductService.savePurchaseProduct(Mockito.any(), Mockito.any())).thenReturn(purchaseProducts);

        RequestPurchaseProductDTO requestPurchaseProductDTO = RequestPurchaseProductDTO.builder()
                .productCode("100")
                .quantityPurchased(10)
                .build();

        RequestPurchaseProductDTO requestPurchaseProductDTO1 = RequestPurchaseProductDTO.builder()
                .productCode("200")
                .quantityPurchased(10)
                .build();

        List<RequestPurchaseProductDTO> requestPurchaseProductDTOS = new ArrayList<>();
        requestPurchaseProductDTOS.add(requestPurchaseProductDTO);
        requestPurchaseProductDTOS.add(requestPurchaseProductDTO1);

        RequestPurchaseDTO requestPurchaseDTO = RequestPurchaseDTO.builder()
                                                .cpf("123")
                                                .purchasedProducts(requestPurchaseProductDTOS)
                                                .build();

        ResponsePurchaseDTO returnedResponsePurchaseDTO = purchaseService.savePurchase(requestPurchaseDTO);

        Assertions.assertNotNull(returnedResponsePurchaseDTO);
        Assertions.assertEquals(
                ClientDTO.convert(client).getName(),
                returnedResponsePurchaseDTO.getClientDTO().getName()
        );
    }

    public void TestListPurchase(){

    }

}
