package com.bbek.BbekServiceA.serviceImp;

import com.bbek.BbekServiceA.entities.UserModel;
import com.bbek.BbekServiceA.model.UserPrincipal;
import com.bbek.BbekServiceA.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.bbek.BbekServiceA.util.Constant.USER_NOT_FOUND;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = repo.findByUsername(username);
        if(user == null){
            System.out.println(USER_NOT_FOUND);
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }
        return new UserPrincipal(user);
    }
}
