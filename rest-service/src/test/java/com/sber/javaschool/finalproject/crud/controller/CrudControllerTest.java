package com.sber.javaschool.finalproject.crud.controller;

import com.sber.javaschool.finalproject.crud.persistance.model.CrudEntity;
import com.sber.javaschool.finalproject.crud.persistance.repository.CrudRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CrudControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrudRepository mockRepository;

    @BeforeEach
    public void init() {
        CrudEntity entity = new CrudEntity(1, "this is title", "this is body");
        when(mockRepository.findById(1L)).thenReturn(Optional.of(entity));
    }

    @Test
    void count() throws Exception {
        MvcResult result = mockMvc.perform(get("/crud/count"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals("0", result.getResponse().getContentAsString());
        verify(mockRepository, times(1)).count();
    }

    @Test
    void readAll() throws Exception {
        List<CrudEntity> entities = List.of(
                new CrudEntity(1, "this is title", "this is body"),
                new CrudEntity(2, "this is title2", "this is body2")
        );
        when(mockRepository.findAll()).thenReturn(entities);

        mockMvc.perform(get("/crud/readall"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("this is title")))
                .andExpect(jsonPath("$[0].body", is("this is body")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("this is title2")))
                .andExpect(jsonPath("$[1].body", is("this is body2")));

        verify(mockRepository, times(1)).findAll();
    }
}