package br.com.l2.packaging.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static br.com.l2.packaging.builder.DataBuilder.buildOrderRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class PackagingServiceImplTest {

    @InjectMocks
    private PackagingServiceImpl packagingService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testOrderPackage() {
        var orderRequest = buildOrderRequest();
        var response = packagingService.orderPackage(orderRequest);
        final var CAIXA_2 = "Caixa 2";
        final var PS5 = "PS5";

        assertFalse(response.getPedidos().isEmpty());
        assertEquals(1, (int) response.getPedidos().getFirst().getPedidoId());
        assertFalse(response.getPedidos().getFirst().getCaixas().isEmpty());
        assertEquals(CAIXA_2, response.getPedidos().getFirst().getCaixas().getFirst().getCaixaId());
        assertTrue(response.getPedidos().getFirst().getCaixas().getFirst().getProdutos().getFirst().contains(PS5));
    }
}
