package cdut.accounting.repository;

import cdut.accounting.model.entity.Tally;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TallyRepository extends PagingAndSortingRepository<Tally, String> {
}
