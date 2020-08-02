package cdut.accounting.repository;

import cdut.accounting.model.entity.Tally;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TallyRepository extends PagingAndSortingRepository<Tally, String> {
    List<Tally> findByDateBetweenAndUserIdOrderByDateDesc(Date start, Date end, int userId);

    List<Tally> findByDateBetweenAndUserIdAndReismAndReismStatus(Date start, Date end, int userId, boolean reism,
                                                                 boolean reismStatus);

    List<Tally> findAllByUid(List<Integer> ids);
}
