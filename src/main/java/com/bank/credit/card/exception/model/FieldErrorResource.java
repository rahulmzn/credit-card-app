package com.bank.credit.card.exception.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * FieldErrorResource is created to use for field validation as part of exception handling framework
 * @author rahul kumar
 */
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Getter
public class FieldErrorResource extends GlobalErrorResource {

    /**
     * @apiNote parameter will be used to define if invalid data passed for entity field
     */
    private final String parameter;

    @Builder
    public FieldErrorResource(String parameter, String code, String message) {
        super(code, message);
        this.parameter = parameter;
    }
}
