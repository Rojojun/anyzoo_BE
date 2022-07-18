package com.finalproject.breeding.user.security;


import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Transactional
public class UserDetailsImpl implements UserDetails {

    private final User user;
    private Map<String, Object> attributes;

    //일반로그인
    public UserDetailsImpl(User user) {
        this.user = user;
    }

    //OAuth 로그인
    public UserDetailsImpl(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    public User getUser() {
        return user;
    }

    /*public List<Post> getPosts() {
        return member.getPosts();
    }*/

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRole role = user.getUserRole();
        String authority = user.getUserRole().name();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

}
