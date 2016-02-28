**Blur Image**
----
Returns a blur version of original image.

* **URL**

  /images/blur

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   ```
   url=[String]
   Description - url of image
   ```
   
* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{ image : encoded data uri of blur image }`
 
* **Error Response:**

  * **Code:** 500 NOT FOUND <br />
    **Content:** 
    
    `{ message : "File url is not valid.Please check it." }` <br />
    OR <br />
    `{ message : "Image doesn't exists." }` <br />
    OR <br />
    `{ message : "Error occur while generated blur image." }`
    

* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/images/blur?url=http://a10.gaanacdn.com/images/radio_rect_mirchi/2.jpg",
      dataType: "json",
      contentType: "application/json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```

