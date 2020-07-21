package cdut.accounting.repository;

import cdut.accounting.model.entity.TeamBill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TeamBillRepository extends CrudRepository<TeamBill, String> {
    List<TeamBill> findByTeamIdAndDateBetween(int teamId, Date start, Date end);
}
