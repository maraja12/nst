package com.master.nst.repository;

import com.master.nst.domain.AcademicTitle;
import com.master.nst.domain.AcademicTitleHistory;
import com.master.nst.domain.Member;
import com.master.nst.domain.ScientificField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase
public class AcademicTitleHistoryRepositoryTests {

    @Autowired
    private AcademicTitleHistoryRepository academicTitleHistoryRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AcademicTitleRepository academicTitleRepository;
    @Autowired
    private ScientificFieldRepository scientificFieldRepository;

    @Test
    public void findByMemberAndAcademicTitleTest(){
        AcademicTitle academicTitle = new AcademicTitle(1l, "Prof");
        academicTitleRepository.save(academicTitle);

        ScientificField scientificField = new ScientificField(1l, "Mathematics");
        scientificFieldRepository.save(scientificField);

        Member member = new Member(1l, "Marija", "Rajic"
                , null, academicTitle, null, scientificField);
        memberRepository.save(member);

        AcademicTitleHistory ath = new AcademicTitleHistory(
                1l, member, LocalDate.of(2024,4,10),
                null, academicTitle, scientificField);
        AcademicTitleHistory saved = academicTitleHistoryRepository.save(ath);

        Assertions.assertNotNull(saved);
        Optional<AcademicTitleHistory> found = academicTitleHistoryRepository.findByMemberAndAcademicTitle(
                member, academicTitle);
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(ath, found.get());
    }
}
