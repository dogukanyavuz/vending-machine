package com.exercise.vendingmachine.service.impl;

import com.exercise.vendingmachine.dto.UserDetailsDto;
import com.exercise.vendingmachine.model.User;
import com.exercise.vendingmachine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.exercise.vendingmachine.exception.UsernameNotFoundException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
// @Qualifier("VendingMachineUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findTopByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
        return new UserDetailsDto(user);
    }

}
