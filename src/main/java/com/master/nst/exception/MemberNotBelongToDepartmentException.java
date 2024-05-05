package com.master.nst.exception;

public class MemberNotBelongToDepartmentException extends RuntimeException{

    public MemberNotBelongToDepartmentException(String message) {
        super(message);
    }
}
