package cdut.accounting.repository;

import cdut.accounting.model.entity.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TeamRepository extends CrudRepository<Team, String> {
    Team findByUid(int uid);
}
