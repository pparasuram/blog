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

import java.util.Collection;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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
    private Collection<BlogPost>
    createMockBlogEntryList(BlogPost... itemArgs) {
        HashMap<Long, BlogPost> blogEntries = new HashMap<>();
        for (BlogPost blogPost : itemArgs) {
            blogEntries.put(blogPost.getId(), blogPost);
        }
        return blogEntries.values();
    }
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
                "http://localhost/api/articles/%d", testPosting.getId()),
                mockResponse.getHeader("Location"));
        verify(mockRepository, times(1)).save(any(BlogPost.class));
    }

    @Test
    @DisplayName("T03 - Post with missing values returns bad request")
    public void test03(@Autowired MockMvc mockMvc)
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new BlogPost())))
                .andExpect(status().isBadRequest());
        verify(mockRepository, never()).save(any(BlogPost.class));
    }
    @Test
    @DisplayName("T04 - Field errors present for each invalid property")
    public void test04(@Autowired MockMvc mockMvc) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new BlogPost())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.category")
                        .value("must not be null"))
                .andExpect(jsonPath("$.fieldErrors.title")
                        .value("must not be null"))
                .andExpect(jsonPath("$.fieldErrors.content")
                        .value("must not be null"));
        mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new BlogPost(0L, "",
                        null, "", ""))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.category").value(
                        "Please enter a category name of up to 200 characters"))
                .andExpect(jsonPath("$.fieldErrors.title").value(
                        "Please enter a title up to 200 characters in length"))
                .andExpect(
                        jsonPath("$.fieldErrors.content")
                                .value("Content is required"));
        verify(mockRepository, never()).save(any(BlogPost.class));
    }
    @Test
    @DisplayName("T05 - GET All works for empty list")
    public void test05(@Autowired MockMvc mockMvc) throws Exception {
        when(mockRepository.findAll()).
                thenReturn(createMockBlogEntryList());
        mockMvc.perform(MockMvcRequestBuilders.get(RESOURCE_URI))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));
        verify(mockRepository, times(1)).findAll();
    }
}
