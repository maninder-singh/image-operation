package com.maninder.image.service

class UtilService {

    public boolean checkNull(def value){
        return !value ? true : false
    }

    public float divide(def firstNumber,def secondNumber){
        return (firstNumber / secondNumber)
    }
}