class UrlMappings {

	static mappings = {

        "/images/crop"(controller: "image",parseRequest: true){
            action = [
                    GET: "cropImage"
            ]
        }
        "/images/thumbnail"(controller: "image" , parseRequest: true){
            action = [
                    GET : "thumbnailImage"
            ]
        }

        "/"(view:"/index")
        "500"(view:'/error')
	}
}
