package br.com.l2.packaging.controller;

import br.com.l2.packaging.api.OrdersApi;
import br.com.l2.packaging.model.OrderRequest;
import br.com.l2.packaging.model.OrderResponse;
import br.com.l2.packaging.service.PackagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersApiController implements BaseController, OrdersApi {
    @Autowired
    private PackagingService packagingService;

    @Override
    public ResponseEntity<OrderResponse> checkPackage(OrderRequest orderRequest) {
        var response = packagingService.orderPackage(orderRequest);
        return ResponseEntity.ok(response);
    }
}
