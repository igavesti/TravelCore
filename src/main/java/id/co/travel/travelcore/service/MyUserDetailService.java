package id.co.travel.travelcore.service;

import id.co.travel.travelcore.model.MyUserDetail;
import id.co.travel.travelcore.repository.dao.UserRepository;
import id.co.travel.travelcore.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException("Username Not Found");
        return new MyUserDetail(user);
    }
}
