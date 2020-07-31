package cdut.accounting.repository;

import cdut.accounting.model.entity.FinanceAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinanceAccountRepository extends CrudRepository<FinanceAccount, String> {
    /**
     * 查找用户账户财产
     *
     * @param type    财产类型
     * @param name    财产名称
     * @param ownerId 用户id
     */
    FinanceAccount findByTypeAndNameAndOwnerId(String type, String name, int ownerId);

    /**
     * 根据账户id和用户id删除账户
     */
    void deleteByUidAndOwnerId(int uid, int ownerId);

    List<FinanceAccount> findAllByOwnerId(int id);
}
