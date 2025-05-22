package br.com.l2.packaging.service;

import br.com.l2.packaging.model.OrderRequest;
import br.com.l2.packaging.model.OrderResponse;

public interface PackagingService {

    OrderResponse orderPackage(OrderRequest orderRequest);
}
