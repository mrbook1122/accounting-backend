package cdut.accounting.exception;

/**
 * 申请加入团队的异常情况
 */
public class JoinTeamException extends RuntimeException {
    public JoinTeamException(String message) {
        super(message);
    }
}
