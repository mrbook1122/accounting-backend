package cdut.accounting.repository;

import cdut.accounting.model.entity.JoinApply;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JoinApplyRepository extends CrudRepository<JoinApply, String> {
    List<JoinApply> findAllByOwnerId(int id);
}
