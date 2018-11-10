package com.itis.service.entity.enums;

public enum UserRole {

    SUPERADMIN("ROLE_SUPERADMIN"), ADMIN("ROLE_ADMIN"), TEACHER("ROLE_TEACHER"), STUDENT("ROLE_STUDENT");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }

}
