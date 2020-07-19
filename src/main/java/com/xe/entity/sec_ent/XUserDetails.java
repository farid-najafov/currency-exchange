package com.xe.entity.sec_ent;

import com.xe.entity.api.Exchange;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class XUserDetails implements UserDetails {

    private final long id;

    private final String fullName;

    private final String password;

    private final String email;

    private final Collection<Exchange> exchanges;

    private final String[] roles;

    public XUserDetails(long id, String fullName, String password, String email, Collection<Exchange> exchanges, String[] roles) {
        this.id = id;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.exchanges = exchanges;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(roles)
                .map(s -> "ROLE_" + s)
                .map(s -> (GrantedAuthority) () -> s)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
