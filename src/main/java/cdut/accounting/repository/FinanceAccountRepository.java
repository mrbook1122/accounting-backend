package cdut.accounting.repository;

import cdut.accounting.model.entity.FinanceAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

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
}
