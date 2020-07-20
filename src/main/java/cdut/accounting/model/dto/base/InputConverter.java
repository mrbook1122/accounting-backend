package cdut.accounting.model.dto.base;

public interface InputConverter<DOMAIN> {
    DOMAIN convertTo();
}
