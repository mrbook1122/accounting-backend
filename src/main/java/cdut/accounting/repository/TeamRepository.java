package cdut.accounting.repository;

import cdut.accounting.model.entity.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TeamRepository extends CrudRepository<Team, String> {
    Team findByUid(int uid);

    /**
     * 删除团队
     * @param id 团队id
     */
    void deleteByUid(int id);

    List<Team> findAllByUidBetween(int from, int end);
}
