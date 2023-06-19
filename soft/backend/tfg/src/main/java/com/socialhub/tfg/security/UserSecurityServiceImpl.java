package com.socialhub.tfg.security;

import com.socialhub.tfg.domain.User;
import com.socialhub.tfg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usuarioRepository
                .findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("No existe ningun usuario con username:" + username));
        return new UserSecurity(user);
    }
}
