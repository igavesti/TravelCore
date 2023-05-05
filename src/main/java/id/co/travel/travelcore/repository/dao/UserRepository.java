package id.co.travel.travelcore.repository.dao;

import id.co.travel.travelcore.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
