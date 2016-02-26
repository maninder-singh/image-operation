package com.maninder.image.service

import grails.transaction.Transactional

class FileService {

    def grailsApplication
    private static final String FILENAME = 'Image_'

    private String getImageDirectory(){
        homeDirectory() + File.separator + grailsApplication.config.media.image.directory
    }

    def boolean isDirectoryExists(){
        def imageDirectory = new File(getImageDirectory())
        !imageDirectory.exists() ? false : true
    }

    def boolean createImageDirectory(){
        def imageDir =  getImageDirectory()
        def absoluteDir = new File(imageDir)
        absoluteDir.mkdirs()
    }

    private String hostServerName(){
        grailsApplication.config.image.server.name
    }
    private String homeDirectory(){
        System.getProperty("user.home")
    }

    def String getNewFileName(){
        hostServerName() + FILENAME + getCurrentTime()
    }

    private Long getCurrentTime(){
        System.currentTimeMillis()
    }

}
