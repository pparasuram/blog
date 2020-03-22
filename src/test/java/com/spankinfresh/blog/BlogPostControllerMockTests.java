package com.spankinfresh.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spankinfresh.blog.data.BlogPostRepository;
import com.spankinfresh.blog.domain.BlogPost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
// import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class BlogPostControllerMockTests {
    @MockBean
    private BlogPostRepository mockRepository;
    private static final String RESOURCE_URI = "/api/articles";
    private final ObjectMapper mapper = new ObjectMapper();
    private static final BlogPost testPosting =
            new BlogPost(0L, "category", null, "title", "content");
/*
    @Test
    @DisplayName("T01 - POST accepts and returns blog post representation")
    public void postCreatesNewBlogEntry_Test(@Autowired MockMvc mockMvc)
            throws Exception {
        when(mockRepository.save(any(BlogPost.class))).thenReturn(testPosting);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testPosting)))
                .andExpect(status().isCreated())
                .andExpect( jsonPath("$.id").value(1L))
                .andExpect( jsonPath("$.title").value(testPosting.getTitle()))
                .andExpect( jsonPath("$.category").value(testPosting.getCategory()))
                .andExpect( jsonPath("$.content").value(testPosting.getContent()))
                .andReturn();
        MockHttpServletResponse mockResponse = result.getResponse();
        assertEquals("http://localhost/api/articles/1",
                mockResponse.getHeader("Location"));

        // Send the second instance:
        result = mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testPosting)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(
                        jsonPath("$.title").value(testPosting.getTitle()))
                .andExpect(
                        jsonPath("$.category").value(testPosting.getCategory()))
                .andExpect(
                        jsonPath("$.content").value(testPosting.getContent()))
                .andReturn();
        mockResponse = result.getResponse();
        assertEquals("http://localhost/api/articles/2",
                mockResponse.getHeader("Location"));
    }
*/
@Test
@DisplayName("T01 - POST accepts and returns blog post representation")
public void test01(@Autowired MockMvc mockMvc)
        throws Exception {
    when(mockRepository.save(any(BlogPost.class)))
            .thenReturn(testPosting);
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(testPosting)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(testPosting.getId()))
            .andExpect(jsonPath("$.title").value(testPosting.getTitle()))
            .andExpect(
                    jsonPath("$.category").value(testPosting.getCategory()))
            .andExpect(
                    jsonPath("$.content").value(testPosting.getContent()))
            .andReturn();
    MockHttpServletResponse mockResponse = result.getResponse();
    assertEquals(String.format(
            "http://localhost/api/articles/%d",testPosting.getId()),
            mockResponse.getHeader("Location"));
    verify(mockRepository, times(1)).save(any(BlogPost.class));
}
}
