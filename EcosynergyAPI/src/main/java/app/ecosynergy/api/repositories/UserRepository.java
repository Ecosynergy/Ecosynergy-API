package app.ecosynergy.api.repositories;

import app.ecosynergy.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u FROM User u WHERE u.userName =:userName")
    User findByUsername(@Param("userName") String userName);

    @Query(value = "SELECT u FROM User u WHERE u.email =:email")
    User findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.userName LIKE %:userName%")
    List<User> findByUsernameContaining(@Param("userName") String userName);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);
}
