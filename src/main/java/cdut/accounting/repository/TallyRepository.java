package cdut.accounting.repository;

import cdut.accounting.model.entity.Tally;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TallyRepository extends CrudRepository<Tally, String> {
}
