package com.ecommerce.ecom.exceptions;

import lombok.NoArgsConstructor;

/* This exception is thrown when a particular resource that the client
 * wants to access is not present in the database of the application
 */
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String field;
    String fieldValue;
    Long fieldId;

    public ResourceNotFoundException(String resourceName, String field, String fieldValue) {
        super(String.format("%s not found with %s: %s", resourceName, field, fieldValue));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldValue = fieldValue;
    }


    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("%s not found with %s: %d", resourceName, field, fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }
}
