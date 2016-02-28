package com.maninder.image.service

import org.apache.commons.validator.routines.UrlValidator

class UtilService {

    /**
     * Check if value is null or not
     *
     * @param value: value
     * @return boolean
     */
    public boolean checkNull(def value){
        return !value ? true : false
    }

    /**
     * Divide a two number
     *
     * @param firstNumber: firstNumber
     * @param secondNumber: secondNumber
     * @return divide number
     */
    public float divide(def firstNumber,def secondNumber){
        return (firstNumber / secondNumber)
    }

    /**
     * Check if url is valid or not
     *
     * @param url: url
     * @return boolean
     */
    public boolean isUrlValid(def url){
        def urlValidator = new UrlValidator()
        urlValidator.isValid(url)
    }
}