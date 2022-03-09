package com.example.demo.auth;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import static com.example.demo.security.UserRole.*;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao{

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username){
        return getApplicationUsers()
                .stream()
                .filter(ApplicationUser -> username.equals(ApplicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers(){
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
            new ApplicationUser(
                STUDENT.getGrantedAuthorities(),
                passwordEncoder.encode("password"),
                "rainer",
                true,
                true,
                true,
                true
            ),
            new ApplicationUser(
                ADMINTRAINEE.getGrantedAuthorities(),
                passwordEncoder.encode("password"),
                "evan",
                true,
                true,
                true,
                true
            ),
            new ApplicationUser(
                ADMIN.getGrantedAuthorities(),
                passwordEncoder.encode("password"),
                "maman",
                true,
                true,
                true,
                true
            )
        );

        return applicationUsers;
    }
}
