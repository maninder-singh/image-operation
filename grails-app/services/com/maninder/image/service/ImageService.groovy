package com.maninder.image.service

import sun.misc.BASE64Encoder
import javax.imageio.ImageIO
import java.awt.AlphaComposite
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.awt.image.ConvolveOp
import java.awt.image.Kernel

class ImageService {

    def utilService
    private static final String BASE64_ENCODED = 'data:image/png;base64,'
    private static final int DEFAULT_THUMBNAIL_HEIGHT_FACTOR = 10
    private static final int DEFAULT_THUMBNAIL_WIDTH_FACTOR = 10


    def String crop(def imageUrl,def cropParameter) throws IOException,Exception{
        def imageData = getImageByUrl(imageUrl)
        imageData = cropImage(imageData,cropParameter)
        getImageDataUrl(imageData)
    }

    def thumbnail(def imageUrl,def height,def width) throws IllegalArgumentException,IOException,Exception{
        def imageData = getImageByUrl(imageUrl)
        imageData = thumbnailImage(imageData,height,width)
        getImageDataUrl(imageData)
    }

    private String getImageDataUrl(def imageData){
        def baos = new ByteArrayOutputStream()
        try {
            saveImage(imageData,baos)
            BASE64_ENCODED + getEncodedImage(baos.toByteArray())
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

    private BufferedImage getImageByUrl(def imageUrl){
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

    private BufferedImage thumbnailImage(def imageData,def height,def width){
        def thumbnailHeight
        def thumbnailWidth
        def hScale
        def wScale
        def scale
        try{
            thumbnailHeight = utilService.checkNull(height) ? utilService.divide(imageData.height,DEFAULT_THUMBNAIL_HEIGHT_FACTOR) : height
            thumbnailWidth = utilService.checkNull(width) ? utilService.divide(imageData.width,DEFAULT_THUMBNAIL_WIDTH_FACTOR) : width
            hScale = utilService.divide(thumbnailHeight,imageData.height)
            wScale = utilService.divide(thumbnailWidth,imageData.width)
            scale = Math.min(hScale,wScale)
            if(scale < 1){
                // scale down image
                if(scale < wScale ){
                    thumbnailWidth = Math.round(scale * imageData.width)
                }else if(scale < hScale){
                    thumbnailHeight = Math.round(scale * imageData.height)
                }
                rescaleImage(imageData,thumbnailWidth as int, thumbnailHeight as int)
            }
        }catch(IllegalArgumentException ia){
            throw new IllegalArgumentException(ia)
        }catch(IOException io){
            throw new IOException(io)
        }catch(Exception ex){
            throw new Exception(ex)
        }finally{
            if(imageData != null){
                imageData.flush()
            }
        }
    }

    /**
     * Rescale an image to the desired size
     *
     * @param image: thumbnail image bytes
     * @param imageWidth: rescale image width
     * @param imageHeight: rescale image height
     * @return rescale image bytes
     */
    def BufferedImage rescaleImage(BufferedImage image, int rescaleWidth, int rescaleHeight) {
        BufferedImage originalImage = new BufferedImage(image.width, image.height, image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType());
        Kernel kernel = getKernelObject()
        setConvolveOp(kernel,image,originalImage)
        // Make a scaled image
        BufferedImage rescaleImage = new BufferedImage(rescaleWidth, rescaleHeight, image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType());
        setImageGraphics(rescaleImage.createGraphics(),originalImage,rescaleWidth,rescaleHeight)
        rescaleImage
    }

    /**
     * Performs convolution operation on each component of original image and set it into scaled image
     * For reference: Convolution is a spatial operation that computes the output pixel from an input
     * pixel by multiplying the kernel with the surround of the input pixel
     *
     * @param kernel
     * @param originalImage
     * @param scaledImage
     */
    private void setConvolveOp(Kernel kernel,def originalImage,def scaledImage){
        ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,null);
        convolve.filter(originalImage, scaledImage);
    }

    /**
     * Set 2-d graphics property of thumbnail image
     *
     * @param graphics2D: 2-d graphics object
     * @param outputData: output buffered image
     * @param width
     * @param height
     * @return void
     */
    private void setImageGraphics(Graphics2D graphics2D,def outputData,def width,height){
        graphics2D.setComposite(AlphaComposite.Src);
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
        graphics2D.drawImage(outputData, 0, 0, width, height, null);
        graphics2D.dispose();
    }

    /**
     * Return matrix that describes how a specified pixel and its
     * surrounding pixels affect the value computed for the pixel's position in the output image
     *
     * @return kernel object
     */
    private Kernel getKernelObject(){
        return new Kernel(3, 3, getKernelData())
    }

    /**
     * Return the kernel data in row major order
     * For Reference: Static data for sharpening of the pixel in image
     *
     * @return kernel data
     */
    private Float[] getKernelData(){
        def data = new Float[9]
        data[0] = 0.0625f
        data[1] = 0.125f
        data[2] = 0.0625f
        data[3] = 0.125f
        data[4] = 0.25f
        data[5] = 0.125f
        data[6] = 0.0625f
        data[7] = 0.125f
        data[8] = 0.0625f
        data
    }

}
