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

        "/images/blur"(controller: "image",parseRequest: true){
            action = [
                    GET: "blurImage"
            ]
        }

        "/"(view:"/index")
        "500"(view:'/error')
	}
}
