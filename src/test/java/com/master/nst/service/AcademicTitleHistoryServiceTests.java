package com.master.nst.service;

import com.master.nst.converter.impl.AcademicTitleHistoryConverter;
import com.master.nst.converter.impl.MemberConverter;
import com.master.nst.domain.*;
import com.master.nst.dto.*;
import com.master.nst.exception.EntityNotFoundException;
import com.master.nst.repository.AcademicTitleHistoryRepository;
import com.master.nst.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class AcademicTitleHistoryServiceTests {

    @Autowired
    AcademicTitleHistoryService academicTitleHistoryService;

    @MockBean
    AcademicTitleHistoryRepository academicTitleHistoryRepository;
    @MockBean
    AcademicTitleHistoryConverter academicTitleHistoryConverter;
    @MockBean
    MemberRepository memberRepository;
    @MockBean
    MemberConverter memberConverter;

    @Test
    public void getAllTest(){
        AcademicTitle academicTitle = new AcademicTitle(1L, "Prof");
        AcademicTitle academicTitle2 = new AcademicTitle(2l, "Assistant");

        AcademicTitleDto academicTitleDto = new AcademicTitleDto(1l, "Prof");
        AcademicTitleDto academicTitle2Dto = new AcademicTitleDto(2l, "Assistant");

        ScientificField scientificField = new ScientificField(1l, "AI");
        ScientificFieldDto scientificFieldDto = new ScientificFieldDto(1l, "AI");

        Member member = new Member(1l, "Aleksa", "Aleksic"
                , null, academicTitle, null, scientificField);
        MemberDto memberDto = new MemberDto(1l, "Aleksa", "Aleksic"
                , null, academicTitleDto, null, scientificFieldDto);

        AcademicTitleHistory ath1 = new AcademicTitleHistory(
                1l, member, LocalDate.of(2020,4,10),
                null, academicTitle, scientificField);
        AcademicTitleHistory ath2 = new AcademicTitleHistory(
                2l, member, LocalDate.of(2020,8,10),
                null, academicTitle2, scientificField);

        AcademicTitleHistoryDto athDto1 = new AcademicTitleHistoryDto(
                1l, memberDto, LocalDate.of(2020,4,10),
                null, academicTitleDto, scientificFieldDto);
        AcademicTitleHistoryDto athDto2 = new AcademicTitleHistoryDto(
                2l, memberDto, LocalDate.of(2020,8,10),
                null, academicTitle2Dto, scientificFieldDto);

        List<AcademicTitleHistory> histories = Arrays.asList(ath1, ath2);

        Mockito.when(academicTitleHistoryRepository.findAll()).thenReturn(histories);
        Mockito.when(academicTitleHistoryConverter.toDto(ath1)).thenReturn(athDto1);
        Mockito.when(academicTitleHistoryConverter.toDto(ath2)).thenReturn(athDto2);

        List<AcademicTitleHistoryDto> result = academicTitleHistoryService.getAll();

        for (int i = 0; i < histories.size(); i++) {
            AcademicTitleHistory expected = histories.get(i);
            AcademicTitleHistoryDto actual = result.get(i);
            Assertions.assertEquals(actual, academicTitleHistoryConverter.toDto(expected));
        }
    }

    @Test
    public void deleteSuccessTest(){
        AcademicTitle academicTitle = new AcademicTitle(1l, "Prof");

        ScientificField scientificField = new ScientificField(1l, "AI");

        Member member = new Member(1l, "Aleksa", "Aleksic"
                , null, academicTitle, null, scientificField);

        AcademicTitleHistory ath1 = new AcademicTitleHistory(
                1l, member, LocalDate.of(2020,4,10),
                null, academicTitle, scientificField);

        when(academicTitleHistoryRepository.findById(ath1.getId())).thenReturn(Optional.of(ath1));
        academicTitleHistoryService.delete(ath1.getId());
        verify(academicTitleHistoryRepository, times(1)).delete(ath1);
    }

    @Test
    public void deleteFailureTest(){
        AcademicTitle academicTitle = new AcademicTitle(1l, "Prof");

        ScientificField scientificField = new ScientificField(1l, "AI");

        Member member = new Member(1l, "Aleksa", "Aleksic"
                , null, academicTitle, null, scientificField);

        AcademicTitleHistory ath1 = new AcademicTitleHistory(
                1l, member, LocalDate.of(2020,4,10),
                null, academicTitle, scientificField);

        when(academicTitleHistoryRepository.findById(ath1.getId())).thenReturn(Optional.empty());
        academicTitleHistoryRepository.delete(ath1);
        Assertions.assertThrows(EntityNotFoundException.class, ()-> {
            academicTitleHistoryService.delete(ath1.getId());
        });
    }

    @Test
    public void findByIdSuccessTest(){
        AcademicTitle academicTitle = new AcademicTitle(1l, "Prof");
        AcademicTitleDto academicTitleDto = new AcademicTitleDto(1l, "Prof");

        ScientificField scientificField = new ScientificField(1l, "AI");
        ScientificFieldDto scientificFieldDto = new ScientificFieldDto(1l, "AI");

        Member member = new Member(1l, "Aleksa", "Aleksic"
                , null, academicTitle, null, scientificField);
        MemberDto memberDto = new MemberDto(1l, "Aleksa", "Aleksic"
                , null, academicTitleDto, null, scientificFieldDto);

        AcademicTitleHistory ath1 = new AcademicTitleHistory(
                1l, member, LocalDate.of(2020,4,10),
                null, academicTitle, scientificField);
        AcademicTitleHistoryDto athDto1 = new AcademicTitleHistoryDto(
                1l, memberDto, LocalDate.of(2020,4,10),
                null, academicTitleDto, scientificFieldDto);

        when(academicTitleHistoryRepository.findById(ath1.getId())).thenReturn(Optional.of(ath1));
        when(academicTitleHistoryConverter.toDto(ath1)).thenReturn(athDto1);
        AcademicTitleHistoryDto actual = academicTitleHistoryService.findById(ath1.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(athDto1, actual);
    }

    @Test
    public void findByIdFailure(){
        AcademicTitle academicTitle = new AcademicTitle(1l, "Prof");

        ScientificField scientificField = new ScientificField(1l, "AI");

        Member member = new Member(1l, "Aleksa", "Aleksic"
                , null, academicTitle, null, scientificField);

        AcademicTitleHistory ath1 = new AcademicTitleHistory(
                1l, member, LocalDate.of(2020,4,10),
                null, academicTitle, scientificField);
        when(academicTitleHistoryRepository.findById(ath1.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            academicTitleHistoryService.findById(ath1.getId());
        });
    }

    @Test
    public void findByMemberIdSuccess(){
        AcademicTitle academicTitle = new AcademicTitle(1l, "Prof");
        AcademicTitleDto academicTitleDto = new AcademicTitleDto(1l, "Prof");

        AcademicTitle academicTitle2 = new AcademicTitle(2l, "Assistant");
        AcademicTitleDto academicTitleDto2 = new AcademicTitleDto(2l, "Assistant");

        ScientificField scientificField = new ScientificField(1l, "AI");
        ScientificFieldDto scientificFieldDto = new ScientificFieldDto(1l, "AI");

        Department department = new Department(1l, "Department1", "dept1");
        EducationTitle educationTitle = new EducationTitle(1l, "Master");
        DepartmentDto departmentDto = new DepartmentDto(1l, "Department1", "dept1");
        EducationTitleDto educationTitleDto = new EducationTitleDto(1l, "Master");

        Member member = new Member(1l, "Aleksa", "Aleksic"
                , department, academicTitle, educationTitle, scientificField);
        MemberDto memberDto = new MemberDto(1l, "Aleksa", "Aleksic"
                , departmentDto, academicTitleDto, educationTitleDto, scientificFieldDto);

        AcademicTitleHistory ath1 = new AcademicTitleHistory(
                1l, member, LocalDate.of(2020,4,10),
                null, academicTitle, scientificField);
        AcademicTitleHistoryDto athDto1 = new AcademicTitleHistoryDto(
                1l, memberDto, LocalDate.of(2020,4,10),
                null, academicTitleDto, scientificFieldDto);

        AcademicTitleHistory ath2 = new AcademicTitleHistory(
                2l, member, LocalDate.of(2020,4,10),
                null, academicTitle2, scientificField);
        AcademicTitleHistoryDto athDto2 = new AcademicTitleHistoryDto(
                2l, memberDto, LocalDate.of(2020,4,10),
                null, academicTitleDto2, scientificFieldDto);

        List<AcademicTitleHistory> histories = Arrays.asList(ath1, ath2);

        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(academicTitleHistoryConverter.toDto(ath1)).thenReturn(athDto1);
        when(academicTitleHistoryConverter.toDto(ath2)).thenReturn(athDto2);
        when(academicTitleHistoryRepository.findByMember(member)).thenReturn(Optional.of(histories));

        List<AcademicTitleHistoryDto> result = academicTitleHistoryService.findByMemberId(member.getId());
        Assertions.assertEquals(histories.size(), result.size());

        for (int i = 0; i < histories.size(); i++) {
            AcademicTitleHistory expected = histories.get(i);
            AcademicTitleHistoryDto actual = result.get(i);
            Assertions.assertEquals(actual, academicTitleHistoryConverter.toDto(expected));
        }
    }

    @Test
    public void findByMemberIdMemberNotExist(){
        Member member = new Member(1l, "Aleksa", "Aleksic"
                , null, null, null, null);
        AcademicTitleHistory ath1 = new AcademicTitleHistory(
                1l, member, LocalDate.of(2020,4,10),
                null, null, null);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            academicTitleHistoryService.findByMemberId(member.getId());
        });
    }

    @Test
    public void findByMemberIdATHNotFound(){
        Member member = new Member(1l, "Aleksa", "Aleksic"
                , null, null, null, null);
        Member member2 = new Member(2l, "Pera", "Peric"
                , null, null, null, null);
        AcademicTitleHistory ath1 = new AcademicTitleHistory(
                1l, member, LocalDate.of(2020,4,10),
                null, null, null);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(memberRepository.findById(member2.getId())).thenReturn(Optional.of(member2));
        when(academicTitleHistoryRepository.findByMember(member2)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            academicTitleHistoryService.findByMemberId(member2.getId());
        });
    }

}
