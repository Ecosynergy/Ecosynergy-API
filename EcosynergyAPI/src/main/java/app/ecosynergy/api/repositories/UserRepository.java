package app.ecosynergy.api.repositories;

import app.ecosynergy.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u FROM User u WHERE u.userName =:userName")
    User findByUsername(@Param("userName") String userName);

    @Query(value = "SELECT u FROM User u WHERE  u.verificationToken =:verificationToken")
    User findByVerificationToken(String verificationToken);
}
