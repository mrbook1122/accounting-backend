package cdut.accounting.repository;

import cdut.accounting.model.entity.TeamBill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamBillRepository extends CrudRepository<TeamBill, String> {
}
