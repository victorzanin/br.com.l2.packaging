package br.com.l2.packaging.service.impl;

import br.com.l2.packaging.model.*;
import br.com.l2.packaging.service.PackagingService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
@NoArgsConstructor
public class PackagingServiceImpl implements PackagingService {

    private static final List<int[]> CAIXAS = Arrays.asList(
            new int[]{30, 40, 80},
            new int[]{80, 50, 40},
            new int[]{50, 80, 60}
    );

    @Override
    public OrderResponse orderPackage(OrderRequest orderRequest) {
        OrderResponse response = new OrderResponse();
        orderRequest.getPedidos().forEach(
                order -> {
                    var boxes = runPackage(order.getProdutos());
                    OrderDetailsResponse orderDetail = new OrderDetailsResponse();
                    orderDetail.setPedidoId(order.getPedidoId());
                    orderDetail.setCaixas(boxes);
                    response.addPedidosItem(orderDetail);
                });
        return response;
    }

    public List<ProductBoxResponse> runPackage(List<ProductRequest> products) {
        List<ProductBoxResponse> response = new ArrayList<>();
        List<ProductRequest> notAllowed = new ArrayList<>(products); // Copia original da lista para evitar problemas na remoção

        for (int[] caixa : CAIXAS) {
            String boxName = "Caixa " + (CAIXAS.indexOf(caixa) + 1);
            List<String> produtosNaCaixa = new ArrayList<>();

            Iterator<ProductRequest> iterator = notAllowed.iterator();
            while (iterator.hasNext()) {
                ProductRequest product = iterator.next();
                var dimension = product.getDimensoes();

                if (dimension.getAltura() <= caixa[0] &&
                        dimension.getLargura() <= caixa[1] &&
                        dimension.getComprimento() <= caixa[2]) {

                    produtosNaCaixa.add(product.getProdutoId());
                    iterator.remove();
                }
            }

            if (!produtosNaCaixa.isEmpty()) {
                ProductBoxResponse boxResponse = new ProductBoxResponse();
                boxResponse.setProdutos(produtosNaCaixa);
                boxResponse.setCaixaId(boxName);
                response.add(boxResponse);
            }
        }

        if (!notAllowed.isEmpty()) {
            ProductBoxResponse notAllowedBox = new ProductBoxResponse();
            notAllowedBox.setCaixaId(null);
            notAllowedBox.setProdutos(
                    notAllowed.stream().map(ProductRequest::getProdutoId).toList()
            );
            notAllowedBox.setObservacao("Produto(s) não cabe(m) em nenhuma caixa disponível.");
            response.add(notAllowedBox);
        }

        return response;
    }
}
