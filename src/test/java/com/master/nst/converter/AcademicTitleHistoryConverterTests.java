package com.master.nst.converter;

import com.master.nst.converter.impl.AcademicTitleHistoryConverter;
import com.master.nst.domain.*;
import com.master.nst.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class AcademicTitleHistoryConverterTests {

    @Autowired
    private AcademicTitleHistoryConverter academicTitleHistoryConverter;

    @Test
    public void toDtoTest(){
        AcademicTitle academicTitle = new AcademicTitle(1l, "Prof");
        ScientificField scientificField = new ScientificField(1l, "AI");
        //also need department and education title due to toDto() method in member
        Department department = new Department(1l, "Department1", "dept1");
        EducationTitle educationTitle = new EducationTitle(1l, "Master");
        Member member = new Member(1l, "Pera", "Peric"
                , department, academicTitle, educationTitle, scientificField);
        AcademicTitleHistory ath = new AcademicTitleHistory(
                1l, member, LocalDate.of(2024,4,10),
                null, academicTitle, scientificField);
        AcademicTitleHistoryDto actual = academicTitleHistoryConverter.toDto(ath);

        Assertions.assertEquals(ath.getId(), actual.getId());
        Assertions.assertEquals(ath.getStartDate(), actual.getStartDate());
        Assertions.assertEquals(ath.getEndDate(), actual.getEndDate());
        Assertions.assertEquals(ath.getMember().getId(), actual.getMemberDto().getId());
        Assertions.assertEquals(ath.getAcademicTitle().getId(), actual.getAcademicTitleDto().getId());
        Assertions.assertEquals(ath.getScientificField().getId(), actual.getScientificFieldDto().getId());
    }

    @Test
    public void toEntityTest(){
        AcademicTitleDto academicTitle = new AcademicTitleDto(1l, "Prof");
        ScientificFieldDto scientificField = new ScientificFieldDto(1l, "AI");
        //also need department and education title due to toDto() method in member
        DepartmentDto department = new DepartmentDto(1l, "Department1", "dept1");
        EducationTitleDto educationTitle = new EducationTitleDto(1l, "Master");
        MemberDto member = new MemberDto(1l, "Pera", "Peric"
                , department, academicTitle, educationTitle, scientificField);
        AcademicTitleHistoryDto ath = new AcademicTitleHistoryDto(
                1l, member, LocalDate.of(2024,4,10),
                null, academicTitle, scientificField);
        AcademicTitleHistory actual = academicTitleHistoryConverter.toEntity(ath);

        Assertions.assertEquals(ath.getId(), actual.getId());
        Assertions.assertEquals(ath.getStartDate(), actual.getStartDate());
        Assertions.assertEquals(ath.getEndDate(), actual.getEndDate());
        Assertions.assertEquals(ath.getMemberDto().getId(), actual.getMember().getId());
        Assertions.assertEquals(ath.getAcademicTitleDto().getId(), actual.getAcademicTitle().getId());
        Assertions.assertEquals(ath.getScientificFieldDto().getId(), actual.getScientificField().getId());
    }
}
