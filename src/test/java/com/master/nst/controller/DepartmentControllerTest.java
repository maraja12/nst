package com.master.nst.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.master.nst.dto.DepartmentDto;
import com.master.nst.dto.MemberDto;
import com.master.nst.exception.EntityAlreadyExistsException;
import com.master.nst.exception.EntityNotFoundException;
import com.master.nst.service.DepartmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DepartmentService departmentService;

    @Test
    public void saveDepartmentSuccessTest() throws Exception{

        DepartmentDto departmentDto = new DepartmentDto(1l, "Department1", "dept1");
        when(departmentService.save(departmentDto)).thenReturn(departmentDto);
        ResultActions response = mockMvc.perform(post("/department").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(departmentDto)));
        response.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                //kastujem zbog stringa u long
                .andExpect(jsonPath("$.id", equalTo(departmentDto.getId().intValue())))
                .andExpect(jsonPath("$.name", equalTo(departmentDto.getName())))
                .andExpect(jsonPath("$.shortName", equalTo(departmentDto.getShortName())));
        verify(departmentService, times(1)).save(departmentDto);
    }

    @Test
    public void saveDepartmentFailureTest() throws Exception{
        DepartmentDto departmentDto = new DepartmentDto(1l, "Department1", "dept1");
        when(departmentService.save(departmentDto)).thenThrow(EntityAlreadyExistsException.class);
        mockMvc.perform(post("/department").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(departmentDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllTest() throws Exception{
        DepartmentDto departmentDto1 = new DepartmentDto(1l, "Department1", "dept1");
        DepartmentDto departmentDto2 = new DepartmentDto(2l, "Department2", "dept2");
        List<DepartmentDto> departments = Arrays.asList(departmentDto1, departmentDto2);
        when(departmentService.getAll()).thenReturn(departments);
        MvcResult result = mockMvc.perform(get("/department"))
                .andExpect(status().isOk()).andReturn();
        List<DepartmentDto> returnedDepartments =objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<DepartmentDto>>() {
                });
        Assertions.assertNotNull(returnedDepartments);
        Assertions.assertEquals(departments, returnedDepartments);
        Assertions.assertTrue(returnedDepartments.contains(departmentDto1));
        Assertions.assertTrue(returnedDepartments.contains(departmentDto2));
    }

    @Test
    public void deleteSuccessTest() throws Exception{
        DepartmentDto departmentDto = new DepartmentDto(1l, "Department1", "dept1");
        doNothing().when(departmentService).delete(departmentDto.getId());
        //1 -> parametar
        mockMvc.perform(delete("/department/{1}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Department is removed!"));
        verify(departmentService, times(1)).delete(departmentDto.getId());
    }

    @Test
    public void deleteFailureTest() throws Exception{
        DepartmentDto departmentDto = new DepartmentDto(1l, "Department1", "dept1");
        doThrow(EntityNotFoundException.class).when(departmentService).delete(departmentDto.getId());
        mockMvc.perform(delete("/department/{1}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateSuccessTest() throws Exception{
        DepartmentDto departmentDto = new DepartmentDto(1l, "Department1", "dept1");
        departmentDto.setName("Department2");
        departmentDto.setShortName("dept2");
        when(departmentService.update(departmentDto)).thenReturn(departmentDto);
        mockMvc.perform(put("/department").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(departmentDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(departmentDto.getId().intValue())))
                .andExpect(jsonPath("$.name", equalTo(departmentDto.getName())))
                .andExpect(jsonPath("$.shortName", equalTo(departmentDto.getShortName())));
        verify(departmentService, times(1)).update(departmentDto);
    }

    @Test
    public void updateFailureTest() throws Exception{
        DepartmentDto departmentDto = new DepartmentDto(1l, "Department1", "dept1");
        when(departmentService.update(departmentDto)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(put("/department").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(departmentDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByIdSuccessTest() throws Exception{
        DepartmentDto departmentDto = new DepartmentDto(1l, "Department1", "dept1");
        when(departmentService.findById(departmentDto.getId())).thenReturn(departmentDto);
        ResultActions response = mockMvc.perform(get("/department/{1}", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(departmentDto)));
        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo(departmentDto.getId().intValue())))
                .andExpect(jsonPath("$.name", equalTo(departmentDto.getName())))
                .andExpect(jsonPath("$.shortName", equalTo(departmentDto.getShortName())));
        verify(departmentService, times(1)).findById(departmentDto.getId());
    }

    @Test
    public void findByIdFailureTest() throws Exception{
        DepartmentDto departmentDto = new DepartmentDto(1l, "Department1", "dept1");
        when(departmentService.findById(departmentDto.getId())).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(get("/department/{1}", 1)
                        .content(objectMapper.writeValueAsString(departmentDto)))
                .andExpect(status().isNotFound());
    }
    @Test
    public void setManagerSuccessTest() throws Exception{

        DepartmentDto departmentDto = new DepartmentDto(1l, "Department1", "dept1");
        MemberDto memberDto = new MemberDto(1l, "Janko", "Jankovic", departmentDto, null, null, null);
        Mockito.doNothing().when(departmentService).setManager(departmentDto.getId(), memberDto.getId());
        ResultActions response = mockMvc.perform
                (post("/department/{dept-id}/manager/{member-id}",1 ,1))
                    .andExpect(status().isOk())
                .andExpect(content().string(
                        "Manager of department with id = " +1+" is saved!"));
        verify(departmentService, times(1))
                .setManager(departmentDto.getId(), memberDto.getId());
    }

    @Test
    public void setManagerFailureTest() throws Exception{
        DepartmentDto departmentDto = new DepartmentDto(1l, "Department1", "dept1");
        MemberDto memberDto = new MemberDto(1l, "Janko", "Jankovic", null, null, null, null);
        doThrow(EntityNotFoundException.class).when(departmentService).setManager
                (departmentDto.getId(), memberDto.getId());
        mockMvc.perform(post("/department/{dept-id}/manager/{member-id}", 1, 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void setSecretarySuccessTest() throws Exception{

        DepartmentDto departmentDto = new DepartmentDto(1l, "Department1", "dept1");
        MemberDto memberDto = new MemberDto(1l, "Janko", "Jankovic", departmentDto, null, null, null);
        Mockito.doNothing().when(departmentService).setSecretary(departmentDto.getId(), memberDto.getId());
        mockMvc.perform(post(
                "/department/{dept-id}/secretary/{member-id}",1 ,1))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Secretary of department with id = " +1+" is saved!"));
        verify(departmentService, times(1))
                .setSecretary(departmentDto.getId(), memberDto.getId());
    }

    @Test
    public void setSecretaryFailureTest() throws Exception{
        DepartmentDto departmentDto = new DepartmentDto(2l, "Department1", "dept1");
        MemberDto memberDto = new MemberDto(2l, "Janko", "Jankovic", null, null, null, null);
        doThrow(EntityNotFoundException.class).when(departmentService).setSecretary
                (departmentDto.getId(), memberDto.getId());
        mockMvc.perform(post("/department/{dept-id}/secretary/{member-id}", 2, 2))
                .andExpect(status().isNotFound());
    }
}
