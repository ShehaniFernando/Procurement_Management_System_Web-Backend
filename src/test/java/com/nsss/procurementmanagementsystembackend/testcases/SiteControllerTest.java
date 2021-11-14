package com.nsss.procurementmanagementsystembackend.testcases;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsss.procurementmanagementsystembackend.controller.SiteController;
import com.nsss.procurementmanagementsystembackend.model.Site;
import com.nsss.procurementmanagementsystembackend.repository.SiteRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SiteController.class)
public class SiteControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    SiteRepository siteRepository;

    Site SITE_1;
    Site SITE_2;
    Site SITE_3;

    @BeforeEach
    public void init() {
        SITE_1 = new Site("Site A", "143/2 Flower Road, Kandy", "Kandy", "0774444444", "available");
        SITE_2 = new Site("Site B", "144/3 Temple Road, Kandy", "Matara", "0775555555", "available");
        SITE_3 = new Site("Site C", "145/4 Methsiri Road, Kandy", "Malabe", "0776666666", "available");

        SITE_1.setId("1");
        SITE_2.setId("2");
        SITE_3.setId("3");
    }

    @Test
    public void getAllSitesTestCase() throws Exception {
        List<Site> sites = new ArrayList<>(Arrays.asList(SITE_1, SITE_2, SITE_3));

        Mockito.when(siteRepository.findAll()).thenReturn(sites);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/access/sites")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addSiteTestCase() throws Exception {
        Mockito.when(siteRepository.save(SITE_1)).thenReturn(SITE_1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/access/sites")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(SITE_1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Site created successfully"));
    }
}