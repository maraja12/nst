package com.master.nst.service;

import com.master.nst.converter.impl.DepartmentConverter;
import com.master.nst.domain.Department;
import com.master.nst.domain.DepartmentHistory;
import com.master.nst.domain.Member;
import com.master.nst.domain.MemberRole;
import com.master.nst.dto.DepartmentDto;
import com.master.nst.exception.EntityAlreadyExistsException;
import com.master.nst.exception.EntityNotFoundException;
import com.master.nst.exception.MemberNotBelongToDepartmentException;
import com.master.nst.repository.DepartmentHistoryRepository;
import com.master.nst.repository.DepartmentRepository;
import com.master.nst.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentRepository;

    @MockBean
    private DepartmentConverter departmentConverter;
    @MockBean
    private MemberRepository memberRepository;
    @MockBean
    private DepartmentHistoryRepository departmentHistoryRepository;

    @Test
    public void saveSuccessTest(){
        DepartmentDto departmentDto = new DepartmentDto(1l, "Department1", "dept1");
        Department department = new Department(1l, "Department1", "dept1");
        when(departmentConverter.toEntity(departmentDto)).thenReturn(department);
        when(departmentRepository.save(department)).thenReturn(department);
        when(departmentRepository.findByName(department.getName())).thenReturn(Optional.empty());
        when(departmentConverter.toDto(department)).thenReturn(departmentDto);
        DepartmentDto savedDto = departmentService.save(departmentDto);
        Assertions.assertNotNull(savedDto);
        Assertions.assertEquals(departmentDto, savedDto);
    }

    @Test
    public void saveFailureTest(){
        DepartmentDto departmentDto = new DepartmentDto(2l, "Department2", "dept2");
        Department department = new Department(2l, "Department2", "dept2");
        when(departmentConverter.toEntity(departmentDto)).thenReturn(department);
        when(departmentRepository.findByName(department.getName())).thenReturn(Optional.of(department));
        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> {
            departmentService.save(departmentDto);
        });
    }

    @Test
    public void getAllTest(){
        Department department1 = new Department(4l, "Department4", "dept4");
        Department department2 = new Department(5l, "Department5", "dept5");
        List<Department> departments = Arrays.asList(department1, department2);

        //it is learned what to return
        when(departmentRepository.findAll()).thenReturn(departments);

        DepartmentDto departmentDto1 = new DepartmentDto(4l, "Department4", "dept4");
        DepartmentDto departmentDto2 = new DepartmentDto(5l, "Department5", "dept5");

        when(departmentConverter.toDto(department1)).thenReturn(departmentDto1);
        when(departmentConverter.toDto(department2)).thenReturn(departmentDto2);

        List<DepartmentDto> result = departmentService.getAll();

        for (int i = 0; i < departments.size(); i++) {
            Department expected = departments.get(i);
            DepartmentDto actual = result.get(i);
            Assertions.assertEquals(actual, departmentConverter.toDto(expected));
        }
    }

    @Test
    public void deleteSuccessTest(){
        Department department = new Department(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        departmentService.delete(department.getId());
        verify(departmentRepository, times(1)).delete(department);
    }

    @Test
    public void deleteFailureTest(){
        Department department = new Department(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            departmentService.delete(department.getId());
        });
    }

    @Test
    public void updateSuccessTest(){
        Department department = new Department(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        department.setName("Department7");
        department.setShortName("dept7");
        DepartmentDto departmentDto = new DepartmentDto(6l, "Department7", "dept7");
        when(departmentRepository.save(department)).thenReturn(department);
        when(departmentConverter.toDto(department)).thenReturn(departmentDto);
        DepartmentDto actual = departmentService.update(departmentDto);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(departmentDto, actual);
    }

    @Test
    public void updateFailureTest(){
        Department department = new Department(6l, "Department6", "dept6");
        DepartmentDto departmentDto = new DepartmentDto(6l, "Department6", "dept6");
        when(departmentConverter.toDto(department)).thenReturn(departmentDto);
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            departmentService.update(departmentDto);
        });
    }

    @Test
    public void findByIdSuccessTest(){
        Department department = new Department(6l, "Department6", "dept6");
        DepartmentDto departmentDto = new DepartmentDto(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        when(departmentConverter.toDto(department)).thenReturn(departmentDto);
        DepartmentDto actual = departmentService.findById(department.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(departmentDto, actual);
    }

    @Test
    public void findByIdFailureTest(){
        Department department = new Department(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            departmentService.findById(department.getId());
        });

    }

    @Test
    public void setManagerDeptHistoryExistWithoutMemberSuccessTest(){
        //this member was never par of management history
        Department department = new Department(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        Member member = new Member(1l, "Marija", "Rajic", department,
                null, null, null);
        Member member2 = new Member(2l, "Janko", "Jankovic", department,
                null, null, null);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(memberRepository.findById(member2.getId())).thenReturn(Optional.of(member2));
        department.setManager(member);
        DepartmentHistory departmentHistory = new DepartmentHistory(
                1l, LocalDate.now(), null, MemberRole.MANAGER, department, member);
        List<DepartmentHistory> histories = new ArrayList<>();
        histories.add(departmentHistory);
        when(departmentHistoryRepository.findByDepartment(department)).thenReturn(Optional.of(histories));
        departmentService.setManager(department.getId(), member2.getId());
        Assertions.assertNotEquals(histories.get(0).getEndDate(), null);
        Assertions.assertEquals(member2.getId(), department.getManager().getId());
    }

    @Test
    public void setManagerDeptHistoryExistWithOldMemberHistorySuccessTest(){
        //this member was part of management earlier, but is not anymore
        //i want him to become manager again
        Department department = new Department(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        Member member = new Member(1l, "Marija", "Rajic", department,
                null, null, null);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        department.setManager(member);
        DepartmentHistory departmentHistory = new DepartmentHistory(
                1l, LocalDate.of(2022,2,2), LocalDate.of(2023,2,2),
                MemberRole.MANAGER, department, member);
        List<DepartmentHistory> histories = new ArrayList<>();
        histories.add(departmentHistory);
        when(departmentHistoryRepository.findByDepartment(department)).thenReturn(Optional.of(histories));
        departmentService.setManager(department.getId(), member.getId());
        Assertions.assertNotEquals(histories.get(0).getEndDate(), null);
        Assertions.assertEquals(member.getId(), department.getManager().getId());
    }

    @Test
    public void setManagerDeptHistoryExistWithCurrentMangerSuccessTest(){
        //Marija is current manager and i want to replace her with Pera
        //endDate of Marija is not null anymore
        Department department = new Department(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        Member member = new Member(1l, "Marija", "Rajic", department,
                null, null, null);
        Member member2 = new Member(2l, "Pera", "Peric", department,
                null, null, null);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(memberRepository.findById(member2.getId())).thenReturn(Optional.of(member2));
        department.setManager(member);
        DepartmentHistory departmentHistory = new DepartmentHistory(
                1l, LocalDate.of(2022,2,2), null,
                MemberRole.MANAGER, department, member);
        List<DepartmentHistory> histories = new ArrayList<>();
        histories.add(departmentHistory);
        when(departmentHistoryRepository.findByDepartment(department)).thenReturn(Optional.of(histories));
        Assertions.assertNull(histories.get(0).getEndDate());
        Assertions.assertEquals(histories.get(0).getRole(), MemberRole.MANAGER);
        departmentService.setManager(department.getId(), member2.getId());
        Assertions.assertNotNull(histories.get(0).getEndDate());
        Assertions.assertEquals(member2.getId(), department.getManager().getId());
    }

    @Test
    public void setManagerDeptHistoryExistWithCurrentSecretarySuccessTest(){
        //Marija is current secretary
        //Pera wants to become manager
        Department department = new Department(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        Member member = new Member(1l, "Marija", "Rajic", department,
                null, null, null);
        Member member2 = new Member(2l, "Pera", "Peric", department,
                null, null, null);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(memberRepository.findById(member2.getId())).thenReturn(Optional.of(member2));
        department.setSecretary(member);
        DepartmentHistory departmentHistory = new DepartmentHistory(
                1l, LocalDate.of(2022,2,2), null,
                MemberRole.SECRETARY, department, member);
        List<DepartmentHistory> histories = new ArrayList<>();
        histories.add(departmentHistory);
        when(departmentHistoryRepository.findByDepartment(department)).thenReturn(Optional.of(histories));
        Assertions.assertEquals(histories.get(0).getEndDate(), null);
        Assertions.assertEquals(histories.get(0).getRole(), MemberRole.SECRETARY);
        departmentService.setManager(department.getId(), member2.getId());
        Assertions.assertEquals(histories.get(0).getEndDate(), null);
        Assertions.assertEquals(member2.getId(), department.getManager().getId());
        Assertions.assertEquals(member.getId(), department.getSecretary().getId());
    }
    @Test
    public void setManagerHistoryDoesNotExistSuccessTest(){
        //department history is empty
        Department department = new Department(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        Member member = new Member(1l, "Marija", "Rajic", department,
                null, null, null);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(departmentHistoryRepository.findByDepartment(department)).thenReturn(Optional.empty());
        departmentService.setManager(department.getId(), member.getId());
        Assertions.assertEquals(member.getId(), department.getManager().getId());
    }
    @Test
    public void setManagerMemberNotExist(){
        Department department = new Department(6l, "Department6", "dept6");
        Member member = new Member(1l, "Marija", "Rajic", department, null, null, null);
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        when(memberRepository.findById(member.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            departmentService.setManager(department.getId(), member.getId());
        });
    }

    @Test
    public void setManagerDepartmentNotExist(){
        Department department = new Department(6l, "Department6", "dept6");
        Member member = new Member(1l, "Marija", "Rajic", department, null, null, null);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(memberRepository.findById(department.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            departmentService.setManager(department.getId(), member.getId());
        });
    }

    @Test
    public void setManagerDoNotBelongToDepartment(){
        Department department = new Department(6l, "Department6", "dept6");
        Department department2 = new Department(5l, "Department5", "dept5");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        Member member = new Member(1l, "Marija", "Rajic", department2, null, null, null);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        Assertions.assertThrows(MemberNotBelongToDepartmentException.class, () -> {
            departmentService.setManager(department.getId(), member.getId());
        });
    }

    @Test
    public void setManagerAlreadyPartOfManagement() {
        Department department = new Department(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));

        Member member = new Member(1l, "Marija", "Rajic", department, null, null, null);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        department.setManager(member);

        DepartmentHistory departmentHistory = new DepartmentHistory(
                1l, (LocalDate.of(2024, 4, 11)), null, MemberRole.MANAGER, department, member);
        List<DepartmentHistory> histories = new ArrayList<>();
        histories.add(departmentHistory);
        when(departmentHistoryRepository.findByDepartment(department)).thenReturn(Optional.of(histories));

        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> {
            departmentService.setManager(department.getId(), member.getId());
        });
    }
    @Test
    public void setManagerDepartmentAlreadyHasManager(){
        Department department = new Department(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        Member member = new Member(1l, "Marija", "Rajic", department,
                null, null, null);
        Member member2 = new Member(2l, "Janko", "Jankovic", department,
                null, null, null);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(memberRepository.findById(member2.getId())).thenReturn(Optional.of(member2));
        department.setManager(member);
        DepartmentHistory departmentHistory = new DepartmentHistory(
                1l, LocalDate.now(), null, MemberRole.MANAGER, department, member);
        List<DepartmentHistory> histories = new ArrayList<>();
        histories.add(departmentHistory);
        when(departmentHistoryRepository.findByDepartment(department)).thenReturn(Optional.of(histories));
        departmentService.setManager(department.getId(), member2.getId());
        //previous manager got end date, not null anymore
        Assertions.assertNotEquals(histories.get(0).getEndDate(), null);
        Assertions.assertEquals(histories.get(0).getRole(), MemberRole.MANAGER);
        Assertions.assertEquals(department.getManager(), member2);
    }

    @Test
    public void setSecretaryHistoryExistSuccessTest(){
        Department department = new Department(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        Member member = new Member(1l, "Marija", "Rajic", department,
                null, null, null);
        Member member2 = new Member(2l, "Janko", "Jankovic", department,
                null, null, null);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(memberRepository.findById(member2.getId())).thenReturn(Optional.of(member2));
        department.setSecretary(member);
        DepartmentHistory departmentHistory = new DepartmentHistory(
                1l, LocalDate.now(), null, MemberRole.SECRETARY, department, member);
        List<DepartmentHistory> histories = new ArrayList<>();
        histories.add(departmentHistory);
        when(departmentHistoryRepository.findByDepartment(department)).thenReturn(Optional.of(histories));
        departmentService.setSecretary(department.getId(), member2.getId());
        Assertions.assertNotEquals(histories.get(0).getEndDate(), null);
        Assertions.assertEquals(member2.getId(), department.getSecretary().getId());
    }
    @Test
    public void setSecretaryDeptHistoryExistWithCurrentManagerSuccessTest(){
        //Marija is current manager
        //Pera wants to become secretary
        Department department = new Department(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        Member member = new Member(1l, "Marija", "Rajic", department,
                null, null, null);
        Member member2 = new Member(2l, "Pera", "Peric", department,
                null, null, null);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(memberRepository.findById(member2.getId())).thenReturn(Optional.of(member2));
        department.setManager(member);
        DepartmentHistory departmentHistory = new DepartmentHistory(
                1l, LocalDate.of(2022,2,2), null,
                MemberRole.MANAGER, department, member);
        List<DepartmentHistory> histories = new ArrayList<>();
        histories.add(departmentHistory);
        when(departmentHistoryRepository.findByDepartment(department)).thenReturn(Optional.of(histories));
        Assertions.assertNull(histories.get(0).getEndDate());
        Assertions.assertEquals(histories.get(0).getRole(), MemberRole.MANAGER);
        departmentService.setSecretary(department.getId(), member2.getId());
        Assertions.assertNull(histories.get(0).getEndDate());
        Assertions.assertEquals(member2.getId(), department.getSecretary().getId());
        Assertions.assertEquals(member.getId(), department.getManager().getId());
    }
    @Test
    public void setSecretaryDeptHistoryExistWithOldMemberSuccessTest(){
        Department department = new Department(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        Member member = new Member(1l, "Marija", "Rajic", department,
                null, null, null);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        department.setSecretary(member);
        DepartmentHistory departmentHistory = new DepartmentHistory(
                1l, LocalDate.of(2022,2,2),
                LocalDate.of(2023,2,2), MemberRole.SECRETARY, department, member);
        List<DepartmentHistory> histories = new ArrayList<>();
        histories.add(departmentHistory);
        when(departmentHistoryRepository.findByDepartment(department)).thenReturn(Optional.of(histories));
        departmentService.setSecretary(department.getId(), member.getId());
        Assertions.assertNotEquals(histories.get(0).getEndDate(), null);
        Assertions.assertEquals(member.getId(), department.getSecretary().getId());
    }
    @Test
    public void setSecretaryHistoryDoesNotExistSuccessTest(){
        Department department = new Department(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        Member member = new Member(1l, "Marija", "Rajic", department,
                null, null, null);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(departmentHistoryRepository.findByDepartment(department)).thenReturn(Optional.empty());
        departmentService.setSecretary(department.getId(), member.getId());
        Assertions.assertEquals(member.getId(), department.getSecretary().getId());
    }

    @Test
    public void setSecretaryDepartmentNotExist(){
        Department department = new Department(6l, "Department6", "dept6");
        Member member = new Member(1l, "Marija", "Rajic", department, null, null, null);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            departmentService.setSecretary(department.getId(), member.getId());
        });
    }

    @Test
    public void setSecretaryMemberNotExist(){
        Department department = new Department(6l, "Department6", "dept6");
        Member member = new Member(1l, "Marija", "Rajic", department, null, null, null);
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        when(memberRepository.findById(member.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            departmentService.setSecretary(department.getId(), member.getId());
        });
    }

    @Test
    public void setSecretaryDoNotBelongToDepartment(){
        Department department = new Department(6l, "Department6", "dept6");
        Department department2 = new Department(5l, "Department5", "dept5");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        Member member = new Member(1l, "Marija", "Rajic", department2, null, null, null);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        Assertions.assertThrows(MemberNotBelongToDepartmentException.class, () -> {
            departmentService.setSecretary(department.getId(), member.getId());
        });
    }

    @Test
    public void setSecretaryAlreadyPartOfManagement() {
        Department department = new Department(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));

        Member member = new Member(1l, "Marija", "Rajic", department, null, null, null);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        department.setSecretary(member);

        DepartmentHistory departmentHistory = new DepartmentHistory(
                1l, (LocalDate.of(2024, 4, 11)), null, MemberRole.SECRETARY,
                department, member);
        List<DepartmentHistory> histories = new ArrayList<>();
        histories.add(departmentHistory);
        when(departmentHistoryRepository.findByDepartment(department)).thenReturn(Optional.of(histories));

        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> {
            departmentService.setSecretary(department.getId(), member.getId());
        });
    }
    @Test
    public void setSecretaryDepartmentAlreadyHasSecretary(){
        Department department = new Department(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        Member member = new Member(1l, "Marija", "Rajic", department,
                null, null, null);
        Member member2 = new Member(2l, "Janko", "Jankovic", department,
                null, null, null);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(memberRepository.findById(member2.getId())).thenReturn(Optional.of(member2));
        department.setSecretary(member);
        DepartmentHistory departmentHistory = new DepartmentHistory(
                1l, LocalDate.now(), null, MemberRole.SECRETARY, department, member);
        List<DepartmentHistory> histories = new ArrayList<>();
        histories.add(departmentHistory);
        when(departmentHistoryRepository.findByDepartment(department)).thenReturn(Optional.of(histories));
        departmentService.setSecretary(department.getId(), member2.getId());
        //previous manager got end date, not null anymore
        Assertions.assertNotEquals(histories.get(0).getEndDate(), null);
        Assertions.assertEquals(department.getSecretary(), member2);
    }
    }



