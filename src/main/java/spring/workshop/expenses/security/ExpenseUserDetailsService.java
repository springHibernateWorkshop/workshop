package spring.workshop.expenses.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.enums.Right;
import spring.workshop.expenses.enums.Role;
import spring.workshop.expenses.services.UserService;

// @Service
// public class ExpenseUserDetailsService implements UserDetailsService {

//     @Autowired
//     private UserService userService;

//     @Override
//     public UserDetails loadUserByUsername(String username) {
//         User user = userService.getUserByUsername(username);
//         if (user == null) {
//             throw new UsernameNotFoundException(username);
//         }
//         return new ExpenseUserPrincipal(user);
//     }
// }
public class ExpenseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            for (Right right : role.getRights()) {
                authorities.add(new SimpleGrantedAuthority(right.getName()));
            }
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }
}