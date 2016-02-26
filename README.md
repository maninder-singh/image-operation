# image-operation

## api endpoint

### 1. Cropping an image
```
http://{{server name}}/image-operation/images/crop?url={{file-url}}&x={{x-cooridnate}}&y={{y-cooridnate}}&width={{width}}&height={{height}}&dataurl={{data-url}}

{{file-url}} - web url of image.
{{x-cooridnate}} - x coordinate of the image .
{{y-cooridnate}} - y coordinate of the image.
{{width}} - width of the image
{{height}} - height of the image ()
{{data-url}} - cropped version of image as encoded dataurl instead of physical file url.
```
