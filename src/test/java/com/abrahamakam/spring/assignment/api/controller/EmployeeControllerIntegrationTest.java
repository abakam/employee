package com.abrahamakam.spring.assignment.api.controller;

import com.abrahamakam.spring.assignment.api.form.EmployeeForm;
import com.abrahamakam.spring.assignment.config.IntegrationTestWebMvcConfig;
import com.abrahamakam.spring.assignment.factory.EmployeeFactory;
import com.abrahamakam.spring.assignment.persistence.dao.EmployeeDAO;
import com.abrahamakam.spring.assignment.persistence.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {IntegrationTestWebMvcConfig.class, IntegrationTestWebMvcConfig.class})
public class EmployeeControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private EmployeeFactory factory;

    @Autowired
    private EmployeeDAO dao;

    private MockMvc mockMvc;

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    public void cleanup() {

    }

    @Test
    public void testGiven_saveEmployeesUri_thenVerifyEmployeeSaved() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Employee emp = factory.getObject();

        EmployeeForm form = new EmployeeForm();
        form.copy(emp, form);

        String formJson = mapper.writeValueAsString(form);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(CONTENT_TYPE).content(formJson))
                .andDo(print())
                .andExpect(status().isCreated());

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/employees/1")
                .contentType(CONTENT_TYPE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        /*
         * Id assigned after save
         */
        emp.setId(1L);
        String empJson = mapper.writeValueAsString(emp);

        assertEquals(result.getResponse().getContentAsString(), empJson);
    }


}








