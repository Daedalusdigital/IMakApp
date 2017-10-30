package com.daedalusdigital.imakapp.validations;

/**
 * Created by abhishek
 * on 09/05/17.
 */

public interface Validation {
    boolean validate(String str);
    String getErrorMessage();
}
