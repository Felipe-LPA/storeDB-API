package com.letscode.store.service;

import com.letscode.store.dto.*;
import com.letscode.store.model.*;
import com.letscode.store.repository.PurchaseRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.PredicateTemplate;
import com.querydsl.core.types.Visitor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class PurchaseServiceTest {

    @InjectMocks
    private PurchaseService purchaseService;

//    @Mock
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

    @Test
    public void TestListPurchase(){
//        List<Product> products = new ArrayList<>();
        Product product1 = Product.builder()
                .productCode("1")
                .price(100D)
                .quantity(100)
                .id(1L)
                .build();

        Product product2 = Product.builder()
                .productCode("2")
                .price(200D)
                .quantity(100)
                .id(2L)
                .build();
//        products.add(productDTO1);
//        products.add(productDTO2);
//


        Client client = Client.builder()
                .id(1L)
                .name("Felipe")
                .cpf("123")
                .build();

        List<PurchaseProduct> purchaseProducts = new ArrayList<>();
        PurchaseProduct purchaseProduct = PurchaseProduct.builder()
                .product(product1)
                .purchasedProductKey(PurchasedProductKey.builder()
                        .idPurchase(1L)
                        .idProduct(1L)
                        .build())
                .purchase(Purchase.builder().build())
                .quantityPurchased(10)
                .build();

        PurchaseProduct purchaseProduct1 = PurchaseProduct.builder()
                .product(product2)
                .purchasedProductKey(PurchasedProductKey.builder()
                        .idPurchase(1L)
                        .idProduct(2L)
                        .build())
                .purchase(Purchase.builder().build())
                .quantityPurchased(10)
                .build();

        purchaseProducts.add(purchaseProduct);
        purchaseProducts.add(purchaseProduct1);

        List<Purchase> purchaseDTOS = new ArrayList<>();
//        purchaseProducts.add(PurchaseProduct.builder().build());
        Purchase purchase = Purchase.builder()
                .Id(1L)
                .purchaseDate(LocalDateTime.now())
                .totalPurchased(10000D)
                .client(client)
                .purchaseProducts(purchaseProducts)
                .build();
        purchaseDTOS.add(purchase);
        Page<Purchase> pagePurchase = new PageImpl<>(purchaseDTOS);


        Pageable page = PageRequest.of(0,20);
        Predicate predicate = QPurchase.purchase.client.cpf.contains("89247332060");
        Mockito.when(purchaseRepository.findAll(predicate, page)).thenReturn(pagePurchase);
        Page<ResponsePurchaseDTO> returnedResponsePurchasedDTO = purchaseService.listPurchase(predicate, page);
        Assertions.assertEquals(1,returnedResponsePurchasedDTO.getSize());
    }

}
