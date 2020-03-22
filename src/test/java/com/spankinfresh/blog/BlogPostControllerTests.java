package com.spankinfresh.blog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BlogPostControllerTests {
    String RESOURCE_URI = "/api/articles";
    @Test
    @DisplayName("T01 - Post returns status of CREATED")
    public void test01(@Autowired MockMvc mockMvc)
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE_URI))
                .andExpect(status().isCreated());
    }
    @Test
    @DisplayName("T02 - Post returns Location header for new item")
    public void test02(@Autowired MockMvc mockMvc) throws Exception {
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE_URI)).andReturn();
        MockHttpServletResponse mockResponse = result.getResponse();
        assertEquals("http://localhost/api/articles/1",
                mockResponse.getHeader("Location"));
    }
}
