package br.com.l2.packaging.controller;

import br.com.l2.packaging.service.PackagingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import static br.com.l2.packaging.builder.DataBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = OrdersApiController.class)
class OrdersApiControllerTest extends BaseControllerTest {

    @Mock
    private PackagingService packagingService;

    @Test
    void shouldPerformOrderPackagingWithSuccessfully() throws Exception {
        var url = BASE_PATH.concat(ORDERS);

        var request = buildOrderRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(post(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidos").isNotEmpty());
    }

}
