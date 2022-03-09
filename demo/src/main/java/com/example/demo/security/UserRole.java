package com.example.demo.security;

import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.example.demo.security.UserPermission.*;

public enum UserRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_READ,COURSE_WRITE,STUDENT_READ,STUDENT_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(COURSE_READ,STUDENT_READ));

    private final Set<UserPermission> permission;

    private UserRole(Set<UserPermission> permission) {
        this.permission = permission;
    }

    public Set<UserPermission> getPermissions(){
        return permission;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
            .map(permission->new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toSet());
        
            permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));

            return permissions;
    }
}
