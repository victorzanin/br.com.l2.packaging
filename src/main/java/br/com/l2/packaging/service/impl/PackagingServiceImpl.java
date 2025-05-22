package br.com.l2.packaging.service.impl;

import br.com.l2.packaging.model.*;
import br.com.l2.packaging.service.PackagingService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@NoArgsConstructor
public class PackagingServiceImpl implements PackagingService {

    private static final String OBS_NOT_FIT_IN_THE_BOX = "Produto(s) não cabe(m) em nenhuma caixa disponível.";
    private static final List<Box> BOXES = List.of(
            new Box("Caixa 1", 30, 40, 80),
            new Box("Caixa 2", 80, 50, 40),
            new Box("Caixa 3", 50, 80, 60)
    );

    @Override
    public OrderResponse orderPackage(OrderRequest orderRequest) {
        OrderResponse response = new OrderResponse();
        orderRequest.getPedidos().forEach(order -> {
            List<ProductBoxResponse> boxes = runPackage(order.getProdutos());
            var detail = new OrderDetailsResponse();
            detail.setCaixas(boxes);
            detail.setPedidoId(order.getPedidoId());
            response.addPedidosItem(detail);
        });
        return response;
    }

    private List<ProductBoxResponse> runPackage(List<ProductRequest> products) {
        List<ProductBoxResponse> response = new ArrayList<>();
        List<ProductRequest> notAllowed = new ArrayList<>(products);

        for (Box box : BOXES) {
            var productInBox = notAllowed.stream()
                    .filter(product -> checkProductMeasurement(product, box))
                    .map(ProductRequest::getProdutoId)
                    .toList();

            notAllowed.removeIf(product -> checkProductMeasurement(product, box));

            if (!productInBox.isEmpty()) {
                response.add(buildProductBoxResponse(box.nome(), productInBox, null));
            }
        }

        if (!notAllowed.isEmpty()) {
            ProductBoxResponse productBox = buildProductBoxResponse(
                    null,
                    notAllowed.stream().map(ProductRequest::getProdutoId).toList(),
                    OBS_NOT_FIT_IN_THE_BOX
            );
            response.add(productBox);
        }

        return response;
    }

    private ProductBoxResponse buildProductBoxResponse(String boxId, List<String> products, String obs) {
        ProductBoxResponse productBox = new ProductBoxResponse();

        if (isNotBlank(boxId))
            productBox.setCaixaId(boxId);

        if (isNotBlank(obs))
            productBox.setObservacao(obs);

        if (nonNull(products))
            productBox.setProdutos(products);

        return productBox;
    }

    private boolean checkProductMeasurement(ProductRequest product, Box box) {
        var dimension = product.getDimension();
        return dimension.getAltura() <= box.altura() &&
                dimension.getLargura() <= box.altura() &&
                dimension.getComprimento() <= box.comprimento();
    }
}
