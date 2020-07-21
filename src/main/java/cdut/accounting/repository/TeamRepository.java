package cdut.accounting.repository;

import cdut.accounting.model.entity.Member;
import cdut.accounting.model.entity.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends CrudRepository<Team, String> {
    Team findByUid(int uid);
}
