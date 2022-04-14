package com.letscode.store.service;

import com.letscode.store.dto.ProductAndQuantityDTO;
import com.letscode.store.model.*;
import com.letscode.store.repository.PurchaseProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@ExtendWith(SpringExtension.class)
public class PurchaseProductServiceTest {
    @InjectMocks
    private PurchaseProductService purchaseProductService;

    @Mock
    private PurchaseProductRepository purchaseProductRepository;

    @Test
    public void testIfPurchaseProductIsSaved(){

//        Purchase purchase = Purchase.builder()
//                .purchaseDate(LocalDateTime.now())
//                .client(
//                        Client.builder()
//                                .name("Felipe")
//                                .cpf("123")
//                                .build()
//                )
//                .totalPurchased(1000D)
//                .build();
//
//        ProductAndQuantityDTO productAndQuantityDTO = ProductAndQuantityDTO.builder()
//                .product(Product.builder()
//                        .productCode("1")
//                        .quantity(100)
//                        .price(100D)
//                        .build())
//                .quantityPurchased(12)
//                .build();
//
//        List<ProductAndQuantityDTO> productAndQuantityDTOS = new ArrayList<>();
//        productAndQuantityDTOS.add(productAndQuantityDTO);
//
//        PurchaseProduct purchaseProduct = PurchaseProduct.builder()
//                .purchasedProductKey(PurchasedProductKey.builder()
//                        .idProduct(productAndQuantityDTO.getProduct().getId())
//                        .idPurchase(purchase.getId())
//                        .build())
//                .product(productAndQuantityDTO.getProduct())
//                .purchase(purchase)
//                .quantityPurchased(productAndQuantityDTO.getQuantityPurchased())
//                .build();
        PurchaseProduct purchaseProduct = PurchaseProduct.builder().build();
        PurchaseProduct purchaseProduct1 = PurchaseProduct.builder().build();

        List<PurchaseProduct> purchaseProducts = new ArrayList<>();
        purchaseProducts.add(purchaseProduct);
        purchaseProducts.add(purchaseProduct1);

        Purchase purchase = Purchase.builder().build();
        List<ProductAndQuantityDTO> productAndQuantityDTOS = new ArrayList<>();

        Mockito.when(purchaseProductRepository.saveAll(Mockito.any())).thenReturn(purchaseProducts);

        List<PurchaseProduct> purchaseProductsReturned = purchaseProductService.savePurchaseProduct(purchase, productAndQuantityDTOS);

        Assertions.assertNotNull(purchaseProductsReturned);
        Assertions.assertEquals(2, purchaseProductsReturned.size());
    }

}
