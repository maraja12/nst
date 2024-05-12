package com.master.nst.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.master.nst.dto.AcademicTitleHistoryDto;
import com.master.nst.dto.DepartmentDto;
import com.master.nst.dto.MemberDto;
import com.master.nst.exception.EntityNotFoundException;
import com.master.nst.service.AcademicTitleHistoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("test")
@WebMvcTest(AcademicTitleHistoryController.class)
@AutoConfigureTestDatabase
public class AcademicTitleHistoryControllerTests {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    AcademicTitleHistoryService academicTitleHistoryService;

    @Test
    public void getAllTest() throws Exception{
        MemberDto memberDto = new MemberDto(1l, "Aleksa", "Aleksic"
                , null, null, null, null);
        MemberDto memberDto2 = new MemberDto(1l, "Aleksa", "Aleksic"
                , null, null, null, null);
        AcademicTitleHistoryDto athDto1 = new AcademicTitleHistoryDto(
                1l, memberDto, LocalDate.of(2020,4,10),
                null, null, null);
        AcademicTitleHistoryDto athDto2 = new AcademicTitleHistoryDto(
                2l, memberDto2, LocalDate.of(2020,8,10),
                null, null, null);
        List<AcademicTitleHistoryDto> histories = Arrays.asList(athDto1, athDto2);
        when(academicTitleHistoryService.getAll()).thenReturn(histories);
        MvcResult result = mockMvc.perform(get("/academic-title-history"))
                .andExpect(status().isFound()).andReturn();
        List<AcademicTitleHistoryDto> returnedHistories =objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<AcademicTitleHistoryDto>>() {
                });
        Assertions.assertNotNull(returnedHistories);
        Assertions.assertEquals(histories, returnedHistories);
        Assertions.assertTrue(returnedHistories.contains(athDto1));
        Assertions.assertTrue(returnedHistories.contains(athDto2));
    }

    @Test
    public void deleteSuccessTest() throws Exception{
        AcademicTitleHistoryDto athDto1 = new AcademicTitleHistoryDto(
                1l, null, LocalDate.of(2020,4,10),
                null, null, null);
        doNothing().when(academicTitleHistoryService).delete(athDto1.getId());
        //1 -> parametar
        mockMvc.perform(delete("/academic-title-history/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Academic title history with id = 1 is removed!"));
        verify(academicTitleHistoryService, times(1)).delete(athDto1.getId());
    }

    @Test
    public void deleteFailureTest() throws Exception{
        AcademicTitleHistoryDto athDto1 = new AcademicTitleHistoryDto(
                1l, null, LocalDate.of(2020,4,10),
                null, null, null);
        doThrow(EntityNotFoundException.class).when(academicTitleHistoryService).delete(athDto1.getId());
        mockMvc.perform(delete("/academic-title-history/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByIdSuccessTest() throws Exception{
        AcademicTitleHistoryDto athDto1 = new AcademicTitleHistoryDto(
                1l, null, LocalDate.of(2020,4,10),
                null, null, null);
        when(academicTitleHistoryService.findById(athDto1.getId())).thenReturn(athDto1);
        ResultActions response = mockMvc.perform(get
                ("/academic-title-history/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(athDto1)));
        response.andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo(athDto1.getId().intValue())))
                .andExpect(jsonPath("$.startDate", equalTo(athDto1.getStartDate().toString())))
                .andExpect(jsonPath("$.endDate", equalTo(athDto1.getEndDate())));
        verify(academicTitleHistoryService, times(1)).findById(athDto1.getId());
    }

    @Test
    public void findByIdFailureTest() throws Exception{
        AcademicTitleHistoryDto athDto1 = new AcademicTitleHistoryDto(
                1l, null, LocalDate.of(2020,4,10),
                null, null, null);
        when(academicTitleHistoryService.findById(athDto1.getId()))
                .thenThrow(EntityNotFoundException.class);
        mockMvc.perform(get("/academic-title-history/{id}", 1)
                        .content(objectMapper.writeValueAsString(athDto1)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByMemberIdSuccessTest() throws Exception{
        MemberDto memberDto = new MemberDto(1l, "Aleksa", "Aleksic"
                , null, null, null, null);
        AcademicTitleHistoryDto athDto1 = new AcademicTitleHistoryDto(
                1l, memberDto, LocalDate.of(2020,4,10),
                null, null, null);
        List<AcademicTitleHistoryDto> histories = List.of(athDto1);
        when(academicTitleHistoryService.findByMemberId(memberDto.getId())).thenReturn(histories);
        MvcResult result = mockMvc.perform(get(
                "/academic-title-history/member/{member-id}", 1))
                .andExpect(status().isFound()).andReturn();
        List<AcademicTitleHistoryDto> returnedHistories =objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<AcademicTitleHistoryDto>>() {
                });
        Assertions.assertNotNull(returnedHistories);
        Assertions.assertEquals(histories, returnedHistories);
        Assertions.assertTrue(returnedHistories.contains(athDto1));
    }

    @Test
    public void findByMemberIdFailureTest() throws Exception{
        MemberDto memberDto = new MemberDto(1l, "Aleksa", "Aleksic"
                , null, null, null, null);
        AcademicTitleHistoryDto athDto1 = new AcademicTitleHistoryDto(
                1l, memberDto, LocalDate.of(2020,4,10),
                null, null, null);
        when(academicTitleHistoryService.findByMemberId(memberDto.getId()))
                .thenThrow(EntityNotFoundException.class);
        mockMvc.perform(get("/academic-title-history/member/{member-id}", 1)
                        .content(objectMapper.writeValueAsString(athDto1)))
                .andExpect(status().isNotFound());
    }
}
