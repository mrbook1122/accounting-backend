package cdut.accounting.repository;

import cdut.accounting.model.entity.Tally;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TallyRepository extends PagingAndSortingRepository<Tally, String> {
    List<Tally> findByDateBetweenAndUserId(Date start, Date end, int userId);
}
