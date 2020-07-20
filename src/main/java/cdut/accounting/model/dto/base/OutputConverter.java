package cdut.accounting.model.dto.base;

public interface OutputConverter<DTO extends OutputConverter<DTO, DOMAIN>, DOMAIN> {
    DTO convertFrom(DOMAIN domain);
}
