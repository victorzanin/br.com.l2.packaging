package br.com.l2.packaging.builder;

import br.com.l2.packaging.model.*;

import java.util.List;

public class DataBuilder {
    public final static String BASE_PATH = "http://127.0.0.1:8080/l2/packaging/v1";
    public final static String ORDERS = "/orders";

    public static OrderRequest buildOrderRequest() {
        var order = new OrderRequest();
        order.setPedidos(List.of(buildOrderDetailRequest()));
        return order;
    }

    private static OrderDetailsRequest buildOrderDetailRequest() {
        var detail = new OrderDetailsRequest();
        detail.setPedidoId(1);
        detail.setProdutos(List.of(buildProductRequest()));
        return detail;
    }

    private static ProductRequest buildProductRequest() {
        var product = new ProductRequest();
        product.setDimension(buildDimensionRequest());
        product.setProdutoId("PS5");
        return product;
    }

    private static DimensionRequest buildDimensionRequest() {
        var dimension = new DimensionRequest();
        dimension.setAltura(40);
        dimension.setLargura(10);
        dimension.setComprimento(25);
        return dimension;
    }

    public static OrderResponse buildOrderResponse() {
        var response = new OrderResponse();
        response.setPedidos(List.of(buildOrderDetailResponse()));
        return response;
    }

    private static OrderDetailsResponse buildOrderDetailResponse() {
        var detail = new OrderDetailsResponse();
        detail.setCaixas(List.of(buildProductBoxResponse()));
        detail.setPedidoId(1);
        return detail;
    }

    private static ProductBoxResponse buildProductBoxResponse() {
        var product = new ProductBoxResponse();
        product.setProdutos(List.of("PS5"));
        product.setCaixaId("Caixa 2");
        return product;
    }
}
