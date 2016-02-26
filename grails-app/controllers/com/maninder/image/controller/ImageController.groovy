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
        def imageAsDataUrl = params.dataurl as boolean
        def cropParameter = [:] as Map
        def image
        def result = [:] as Map

        try{
            cropParameter.put("x",xCoordinate)
            cropParameter.put("y",yCoordinate)
            cropParameter.put("width",width)
            cropParameter.put("height",height)
            image = imageService.crop(fileUrl,cropParameter,imageAsDataUrl)
            result.put('image',image)
            response.status = 200
            render result as JSON
        }catch(IOException io){
            response.status = 500
            result.put('message',io.getMessage())
            render result as JSON
        }catch(Exception ex){
            response.status = 500
            result.put('message','Error occur while image')
            render result as JSON
        }
    }

    def thumbnailImage(){
        def result = [:] as Map
        render result as JSON
    }
}
