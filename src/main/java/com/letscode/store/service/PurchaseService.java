package com.letscode.store.service;

import com.letscode.store.dto.ProductAndQuantityDTO;
import com.letscode.store.dto.ProductDTO;
import com.letscode.store.dto.RequestPurchaseDTO;
import com.letscode.store.dto.ResponsePurchaseDTO;
import com.letscode.store.exception.NotEnoughException;
import com.letscode.store.model.Client;
import com.letscode.store.model.Product;
import com.letscode.store.model.Purchase;
import com.letscode.store.repository.PurchaseRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ClientService clientService;
    private final ProductService productService;
    private final PurchaseProductService purchaseProductService;

    public ResponsePurchaseDTO savePurchase(RequestPurchaseDTO requestPurchaseDTO) {
        Client client = clientService.getClient(requestPurchaseDTO.getCpf());

        List<ProductAndQuantityDTO> productAndQuantityDTOS = getProductAndQuantityDTOS(requestPurchaseDTO);

        Double totalPurchased = getTotalPurchased(productAndQuantityDTOS);

        Purchase purchase = purchaseRepository.save(
                Purchase.builder()
                        .purchaseDate(LocalDateTime.now())
                        .totalPurchased(totalPurchased)
                        .client(client)
                        .build()
        );

        productAndQuantityDTOS.forEach(productAndQuantityDTO -> productService.updateProduct(ProductDTO.convert(productAndQuantityDTO.getProduct())));

        purchaseProductService.savePurchaseProduct(purchase, productAndQuantityDTOS);

        return ResponsePurchaseDTO.convert(purchase, productAndQuantityDTOS);
    }

    private List<ProductAndQuantityDTO> getProductAndQuantityDTOS(RequestPurchaseDTO requestPurchaseDTO) {
        return requestPurchaseDTO
                .getPurchasedProducts()
                .stream()
                .map(requestPurchaseProductDTO -> {
                    Product product = productService
                            .getProduct(requestPurchaseProductDTO.getProductCode());
                    if (product.getQuantity() < requestPurchaseProductDTO.getQuantityPurchased()){
                        throw new NotEnoughException("Not enough of product with code " + product.getProductCode());
                    }
                    product.setQuantity(product.getQuantity() - requestPurchaseProductDTO.getQuantityPurchased());
                    return ProductAndQuantityDTO.builder()
                            .product(product)
                            .quantityPurchased(requestPurchaseProductDTO.getQuantityPurchased())
                            .build();
                })
                .collect(Collectors.toList());
    }

    private Double getTotalPurchased(List<ProductAndQuantityDTO> productAndQuantityDTOS) {
        AtomicReference<Double> returnTotal = new AtomicReference<>(0D);
        productAndQuantityDTOS.forEach(productAndQuantityDTO -> returnTotal.updateAndGet(v ->
                v + productAndQuantityDTO.getProduct().getPrice()
                * productAndQuantityDTO.getQuantityPurchased()));
        return returnTotal.get();
    }

    public Page<ResponsePurchaseDTO> listPurchase(Predicate predicate, Pageable pageable) {

                Page<Purchase> purchasePage = purchaseRepository.findAll(predicate, pageable);
        return purchasePage.map(ResponsePurchaseDTO::convert);
    }
}
