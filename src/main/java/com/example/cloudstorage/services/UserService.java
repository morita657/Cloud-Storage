package com.example.cloudstorage.services;

import com.example.cloudstorage.model.User;
import com.example.cloudstorage.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private UsersMapper usersMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UsersMapper usersMapper, PasswordEncoder passwordEncoder){
        this.usersMapper = usersMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User getByUserId(Long userId) throws UsernameNotFoundException{
        User user = usersMapper.findById(userId);

        if (user == null){
            throw new UsernameNotFoundException(String.format("The user with ID '%s' was not found.", userId));
        }
        return user;
    }

    public User register(User user) throws Exception {
        String hashedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(hashedPassword);

        try {
            Integer i = usersMapper.create(user);

            System.out.println(i);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersMapper.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("The user '%s' was not found.", username));
        }

        return user;
    }
}
