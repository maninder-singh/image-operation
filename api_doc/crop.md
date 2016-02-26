
**Crop Image**
----
Return a cropped version of original image.

* **URL**

  /images/crop

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   ```
   url=[String]
   Description - url of image
   ```
   
   **Optional:**
   
   ```
   x=[Int] 
   Default value - 0 
   Description - the x coordinate of the upper-left corner of the specified rectangular region
   ```
   ```
   y=[Int] 
   Default value - 0
   Description - the y coordinate of the upper-left corner of the specified rectangular region
   ```
   ```
   height=[Int] 
   Default value - height of original image
   Description - the height of the specified rectangular region
   ```
   ```
   width=[Int] 
   Default value - width of original image
   Description - the width of the specified rectangular region
   ```

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{ image : encoded data uri of image }`
 
* **Error Response:**

  * **Code:** 500 NOT FOUND <br />
    **Content:** 
    
    `{ message : "File url is not valid.Please check it." }` <br />
    OR <br />
    `{ message : "Image doesn't exists." }` <br />
    OR <br />
    `{ message : "Error occur while cropping image." }`
    

* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/images/crop?url=http://a10.gaanacdn.com/images/radio_rect_mirchi/2.jpg&x=20&y=20&width=300&height=300",
      dataType: "json",
      contentType: "application/json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```

