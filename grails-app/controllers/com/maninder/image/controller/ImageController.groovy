package com.maninder.image.controller

import grails.converters.JSON

class ImageController {

    def imageService

    def cropImage(){

        def fileUrl = params.url as String
        def xCoordinate = params.x as Long
        def yCoordinate = params.y as Long
        def height = params.height as Long
        def width = params.width as Long
        def cropParameter = [:] as Map
        def image
        def result = [:] as Map

        try{
            cropParameter.put("x",xCoordinate)
            cropParameter.put("y",yCoordinate)
            cropParameter.put("width",width)
            cropParameter.put("height",height)
            image = imageService.crop(fileUrl,cropParameter)
            result.put('image',image)
            response.status = 200
        }catch(IOException io){
            response.status = 500
            result.put('message',io.getMessage())
        }catch(Exception ex){
            response.status = 500
            result.put('message','Error occur while image')
        }finally{
            render result as JSON
        }
    }

    def thumbnailImage(){
        def result = [:] as Map
        def fileUrl = params.url as String
        def height = params.height as Long
        def width = params.width as Long
        def image
        try {
            image = imageService.thumbnail(fileUrl,height,width)
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
