package com.packet.indoor.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder(builderClassName = "Builder")
@Getter
@AllArgsConstructor
public class UserDetail implements UserDetails {

    private String username;
    private String groupname;
    private List<String> roles;
    private Boolean isActive;
    private Boolean isDeleted;

    public static UserDetail create(String username, String groupname, List<String> roles, Boolean isActive, Boolean isDeleted){
        return UserDetail.builder()
                .username(username)
                .groupname(groupname)
                .roles(roles)
                .isActive(isActive)
                .isDeleted(isDeleted)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> roles = this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return roles;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
