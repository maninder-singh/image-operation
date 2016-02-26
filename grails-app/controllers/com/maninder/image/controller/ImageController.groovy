package com.maninder.image.controller

import grails.converters.JSON

class ImageController {

    def imageService
    def utilService

    def cropImage(){

        def imageUrl = params.url as String
        def xCoordinate = params.x as Long
        def yCoordinate = params.y as Long
        def height = params.height as Long
        def width = params.width as Long
        def cropParameter = [:] as Map
        def image
        def result = [:] as Map

        try{
            if(!utilService.isUrlValid(imageUrl)){
                response.status = 500
                result.put("message","File url is not valid.Please check it.")
                return
            }
            cropParameter.put("x",xCoordinate)
            cropParameter.put("y",yCoordinate)
            cropParameter.put("width",width)
            cropParameter.put("height",height)
            image = imageService.crop(imageUrl,cropParameter)
            result.put('image',image)
            response.status = 200
        }catch(IOException io){
            response.status = 500
            result.put('message',io.getMessage())
        }catch(Exception ex){
            response.status = 500
            result.put('message','Error occur while cropping image')
        }finally{
            render result as JSON
        }
    }

    def thumbnailImage(){
        def result = [:] as Map
        def imageUrl = params.url as String
        def height = params.height as Long
        def width = params.width as Long
        def image
        try {
            if(!utilService.isUrlValid(imageUrl)){
                result.put("message","File url is not valid.Please check it.")
                return
            }
            image = imageService.thumbnail(imageUrl,height,width)
            result.put('image',image)
            response.status = 200
        }catch(Exception ex){
            result.put('message','Error occur while generating thumbnail')
            response.status = 500
        }finally{
            render result as JSON
        }
    }
}
