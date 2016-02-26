package com.maninder.image.service

import org.apache.commons.validator.routines.UrlValidator

class UtilService {

    public boolean checkNull(def value){
        return !value ? true : false
    }

    public float divide(def firstNumber,def secondNumber){
        return (firstNumber / secondNumber)
    }

    public boolean isUrlValid(def url){
        def urlValidator = new UrlValidator()
        urlValidator.isValid(url)
    }
}