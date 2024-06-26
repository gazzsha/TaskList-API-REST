package gazzsha.com.tasklist.repository.jpa;

import gazzsha.com.tasklist.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findAllByUsername(String username);

    @Query(value = """
                  SELECT EXISTS(SELECT 1
                          FROM users_tasks
                          WHERE user_id =:userId
                            AND task_id =:taskId)
            """, nativeQuery = true)
    boolean isTaskOwner(@Param("userId") Long userId, @Param("taskId") Long taskId);
}
