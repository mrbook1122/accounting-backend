package cdut.accounting.repository;

import cdut.accounting.model.entity.JoinApply;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinApplyRepository extends CrudRepository<JoinApply, String> {
}
