package cdut.accounting.repository;

import cdut.accounting.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String name);

    User findByEmail(String email);

    User findByUid(int id);
}
