package com.maninder.image.service

import grails.transaction.Transactional
import sun.misc.BASE64Encoder
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.net.URL

class ImageService {

    def fileService

    def String crop(def imageUrl,def cropParameter,def imageAsDataUrl = false) throws IOException,Exception{
        def imageData = getImage(imageUrl)
        imageData = cropImage(imageData,cropParameter)
        imageAsDataUrl ? getImageDataUrl(imageData) : saveImageOnServer(imageData)
    }

    private String getImageDataUrl(def imageData){
        def baos = new ByteArrayOutputStream()
        try {
            saveImage(imageData,baos)
            getEncodedImage(baos.toByteArray())
        }catch (IOException io){
            throw new IOException(io)
        }catch (Exception ex){
            throw new Exception(ex)
        }finally{
            if(baos != null){
                baos.flush()
                baos.close()
            }
        }
    }

    private String getEncodedImage(def imageData){
        def encoder = new BASE64Encoder()
        encoder.encode(imageData)
    }

    private String saveImageOnServer(def imageData) throws IOException,Exception{
        def fileName

        if (!fileService.isDirectoryExists()) {
            fileService.createImageDirectory()
        }
        fileName = fileService.getNewFileName()
        saveImage(imageData,new File(fileName))
        fileName
    }

    private void saveImage(def imageData,def image){
        ImageIO.write(imageData,'png',image)
    }

    private BufferedImage readImage(def image) throws IOException,Exception{
        ImageIO.read(image)
    }
    private BufferedImage cropImage(def imageData,def cropParameter){
        def croppedImage
        def x = cropParameter.get('x').intValue() // the X coordinate of the upper-left corner of the specified rectangular region
        def y = cropParameter.get('y').intValue() // the Y coordinate of the upper-left corner of the specified rectangular region
        def width = cropParameter.get('width').intValue() // the width of the specified rectangular region
        def height = cropParameter.get('height').intValue() // the height of the specified rectangular region

        try {
            // check if x coordinate of upper left corner is null
            if (!x) {
                x = 0
            }
            // check if y coordinate of upper left corner is null
            if (!y) {
                y = 0
            }
            //check if width is null
            if (!width) {
                width = imageData.width
            }
            //check if height of crop image is greater than original image height
            if (!height) {
                height = imageData.height
            }
            // check if crop image height is greater than original image height
            if((y + height ) > imageData.height){
                height = imageData.height - y
            }
            // check if crop image width is greater than original image width
            if((x + width) > imageData.width){
                width = imageData.width - x
            }
            croppedImage = imageData.getSubimage(x, y, width, height)
            croppedImage
        }catch(IOException io){
            throw new IOException(io)
        }catch(Exception ex){
            throw new Exception(ex)
        }finally{
            if(croppedImage != null){
                croppedImage.flush()
            }
        }
    }

    private BufferedImage getImage(def imageUrl){
        def imageData = null

        try{
            imageData = readImage(new URL(imageUrl))
            imageData // return image data
        }catch(IOException io){
            throw new IOException("Image doesn't exists")
        }finally{
            if(imageData != null){
                imageData.flush()
            }
        }
    }
}
