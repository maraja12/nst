package com.master.nst.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class DepartmentDto implements Serializable {
    private Long id;
    @NotNull
    private String name;
    private String short_name;

    public DepartmentDto() {
    }

    public DepartmentDto(Long id, String name, String short_name) {
        this.id = id;
        this.name = name;
        this.short_name = short_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }
}
