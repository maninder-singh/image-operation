
**Thumbnail of an image**
----
Returns a thumbnail version of original image.

* **URL**

  /images/thumbnail

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
   height=[Int] 
   Default value - one-tenth height of original image
   Description - height of thumbnai image
   ```
   ```
   width=[Int] 
   Default value - one-tenth width of original image
   Description - width of thumbnail image
   ```

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{ image : encoded data uri of thumbnail image }`
 
* **Error Response:**

  * **Code:** 500 NOT FOUND <br />
    **Content:** 
    
    `{ message : "File url is not valid.Please check it." }` <br />
    OR <br />
    `{ message : "Image doesn't exists." }` <br />
    OR <br />
    `{ message : "Error occur while generating thumbnail." }`
    

* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/images/thumbnail?url=http://a10.gaanacdn.com/images/radio_rect_mirchi/2.jpg&width=300&height=300",
      dataType: "json",
      contentType: "application/json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```

