package com.bank.credit.card.exception.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * GlobalErrorResource will be used to handel global error responses
 * @author rahul kumar
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
public class GlobalErrorResource {
    /**
     * Error unique transaction code
     */
    private String code;
    /**
     * Detailed message about exception
     */
    private String message;
}
