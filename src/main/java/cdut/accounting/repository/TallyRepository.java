package cdut.accounting.repository;

import cdut.accounting.model.entity.Tally;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TallyRepository extends PagingAndSortingRepository<Tally, String> {
    List<Tally> findByDateBetweenAndUsername(Date start, Date end, String username);
}
