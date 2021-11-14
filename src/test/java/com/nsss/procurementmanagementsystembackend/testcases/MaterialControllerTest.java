package com.nsss.procurementmanagementsystembackend.testcases;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsss.procurementmanagementsystembackend.controller.MaterialController;
import com.nsss.procurementmanagementsystembackend.model.Material;
import com.nsss.procurementmanagementsystembackend.repository.MaterialRepository;
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

@WebMvcTest(MaterialController.class)
public class MaterialControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MaterialRepository materialRepository;

    Material MATERIAL_1;
    Material MATERIAL_2;
    Material MATERIAL_3;

    @BeforeEach
    public void init() {
        MATERIAL_1 = new Material("Cement", 1050, "available");
        MATERIAL_2 = new Material("Sand", 800, "available");
        MATERIAL_3 = new Material("Cable", 250.00, "unavailable");

        MATERIAL_1.setId("1");
        MATERIAL_2.setId("2");
        MATERIAL_3.setId("3");
    }

    @Test
    public void getAllMaterialsTestCase() throws Exception {
        List<Material> materials = new ArrayList<>(Arrays.asList(MATERIAL_1, MATERIAL_2, MATERIAL_3));

        Mockito.when(materialRepository.findAll()).thenReturn(materials);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/access/materials")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addMaterialTestCase() throws Exception {
        Mockito.when(materialRepository.save(MATERIAL_1)).thenReturn(MATERIAL_1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/access/materials")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(MATERIAL_1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Material created successfully"));
    }
}