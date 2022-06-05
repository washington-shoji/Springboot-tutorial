package com.dailycodebuffer.Springboot.tutorial.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.dailycodebuffer.Springboot.tutorial.entity.Department;
import com.dailycodebuffer.Springboot.tutorial.service.DepartmentService;

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    void setUp() {
        department = Department.builder()
                .departmentName("IT")
                .departmentAddress("Sydney")
                .departmentCode("IT-07")
                .departmentId(1L)
                .build();
    }

    @Test
    void testSaveDepartment() throws Exception {
        Department inputDepartment = Department.builder()
                .departmentName("IT")
                .departmentAddress("Sydney")
                .departmentCode("IT-07")
                .build();

        Mockito.when(departmentService.saveDepartment(department)).thenReturn(department);

        mockMvc.perform(MockMvcRequestBuilders.post("/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"departmentName\":\"IT\",\n" +
                        "\t\"departmentAddress\":\"Sydney\",\n" +
                        "\t\"departmentCode\":\"IT-07\"\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testFetchDepartmentById() throws Exception {
        Mockito.when(departmentService.fetchDepartmentById(1L))
                .thenReturn(department);

        mockMvc.perform(MockMvcRequestBuilders.get("/departments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.departmentName").value(department.getDepartmentName()));
    }

}
