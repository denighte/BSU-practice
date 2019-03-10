( function() {
    function arraysEqual(a, b) {
        if (a === b) return true;
        if (a == null || b == null) return false;
        if (a.length != b.length) return false;

        a = a.sort();
        b = b.sort();

        for (var i = 0; i < a.length; ++i) {
            if (a[i] !== b[i]) return false;
        }
        return true;
    }

    class PhotoPosts {

        constructor(cap) {
            this.postsArray = cap;
        }

        getPhotoPosts(filterConfig, top = 10, skip = 0) {
            let result = [];
            let len = top + skip > this.postsArray.length ? this.postsArray.length : top + skip;
            let keys = filterConfig == null ? [] : Object.keys(filterConfig);
            for (let i = skip; i < len; i++) {
                let toAdd = true;
                for (let j = 0; j < keys.length; j++) {
                    if (!(this.postsArray[i][keys[j]] === filterConfig[keys[j]])) {
                        toAdd = false;
                        break;
                    }
                }
                if (toAdd) {
                    result.push(this.postsArray[i]);
                }
            }
            return result;
        }

        getPhotoPost(id) {
            return this.postsArray.find(object => object.id == id);
        }

        addPhotoPost(object) {
            if (!PhotoPosts.validatePhotoPost(object)) {
                return false;
            }
            this.postsArray.push(object);
            return true;
        }

        removePhotoPost(id) {
            let length = this.postsArray.length;
            this.postsArray = this.postsArray.filter(value => value.id != id);
            return (length - this.postsArray.length) == 1;
        }

        editPhotoPost(id, photoPost) {
            if (!PhotoPosts.validatePhotoPost(photoPost)) {
                return false;
            }
            let keys = Object.keys(photoPost).filter(value => value != "id" && value != "author" && value != "createdAt");
            let index = this.postsArray.findIndex(object => object.id == id);
            if (index == undefined) {
                return false;
            }
            for (let i = 0; i < keys.length; i++) {
                this.postsArray[index][keys[i]] = photoPost[keys[i]];
            }
            return true;
        }

        static validatePhotoPost(object) {
            return arraysEqual(Object.keys(object), ["id", "description", "createdAt", "author", "photoLink"]) && object.createdAt instanceof Date;
        }
    }

//TODO: remove the following in production

//reading json objects from file
//read from file does not work?
//fetch('file.txt').then(response => response.text()).then(text => text.split('\n').forEach(object => cap.push(JSON.parse(object))));
    var cap = `{ "id":0, "description":"description_sample_0", "createdAt":"2018-02-23T23:00:00", "author":"<insert_author_0>", "photoLink":"https://link.sample.photo0" }
{ "id":1, "description":"description_sample_1", "createdAt":"2018-02-23T23:00:01", "author":"<insert_author_1>", "photoLink":"https://link.sample.photo1" }
{ "id":2, "description":"description_sample_2", "createdAt":"2018-02-23T23:00:02", "author":"<insert_author_2>", "photoLink":"https://link.sample.photo1" }
{ "id":3, "description":"description_sample_3", "createdAt":"2018-02-23T23:00:03", "author":"<insert_author_3>", "photoLink":"https://link.sample.photo1" }
{ "id":4, "description":"description_sample_4", "createdAt":"2018-02-23T23:00:04", "author":"<insert_author_4>", "photoLink":"https://link.sample.photo4" }
{ "id":5, "description":"description_sample_4", "createdAt":"2018-02-23T23:00:05", "author":"<insert_author_5>", "photoLink":"https://link.sample.photo5" }
{ "id":6, "description":"description_sample_4", "createdAt":"2018-02-23T23:00:06", "author":"<insert_author_5>", "photoLink":"https://link.sample.photo5" }
{ "id":7, "description":"description_sample_7", "createdAt":"2018-02-23T23:00:07", "author":"<insert_author_5>", "photoLink":"https://link.sample.photo7" }
{ "id":8, "description":"description_sample_8", "createdAt":"2018-02-23T23:00:08", "author":"<insert_author_8>", "photoLink":"https://link.sample.photo8" }
{ "id":9, "description":"description_sample_9", "createdAt":"2018-02-23T23:00:09", "author":"<insert_author_9>", "photoLink":"https://link.sample.photo9" }
{ "id":10, "description":"description_sample_10", "createdAt":"2018-02-23T23:00:010", "author":"<insert_author_10>", "photoLink":"https://link.sample.photo10" }
{ "id":11, "description":"description_sample_11", "createdAt":"2018-02-23T23:00:011", "author":"<insert_author_11>", "photoLink":"https://link.sample.photo11" }
{ "id":12, "description":"description_sample_12", "createdAt":"2018-02-23T23:00:012", "author":"<insert_author_12>", "photoLink":"https://link.sample.photo12" }
{ "id":13, "description":"description_sample_13", "createdAt":"2018-02-23T23:00:013", "author":"<insert_author_13>", "photoLink":"https://link.sample.photo13" }
{ "id":14, "description":"description_sample_14", "createdAt":"2018-02-23T23:00:014", "author":"<insert_author_14>", "photoLink":"https://link.sample.photo14" }
{ "id":15, "description":"description_sample_15", "createdAt":"2018-02-23T23:00:015", "author":"<insert_author_15>", "photoLink":"https://link.sample.photo15" }
{ "id":16, "description":"description_sample_16", "createdAt":"2018-02-23T23:00:016", "author":"<insert_author_16>", "photoLink":"https://link.sample.photo16" }
{ "id":17, "description":"description_sample_17", "createdAt":"2018-02-23T23:00:017", "author":"<insert_author_17>", "photoLink":"https://link.sample.photo17" }
{ "id":18, "description":"description_sample_18", "createdAt":"2018-02-23T23:00:018", "author":"<insert_author_18>", "photoLink":"https://link.sample.photo18" }
{ "id":19, "description":"description_sample_19", "createdAt":"2018-02-23T23:00:019", "author":"<insert_author_19>", "photoLink":"https://link.sample.photo19" }`.split('\n').map(object => JSON.parse(object));
    cap.forEach(object => object.createdAt = new Date(object.createdAt));

    let arr = new PhotoPosts(cap);
    console.log("getPhotoPosts examples:");
    console.log(arr.getPhotoPosts({author: "<insert_author_5>"}));
    console.log(arr.getPhotoPosts({description: "description_sample_4", photoLink: "https://link.sample.photo5"}));
    console.log("getPhotoPost examples:");
    console.log(arr.getPhotoPost("5"));
    console.log(arr.getPhotoPost("22")); //undefined
    console.log("validatePhotoPost examples:");
    let wrong_post = JSON.parse(
        `{ "id":3, 
        "description":"description_sample_3", 
        "createdAt":"2018-02-23T23:00:03", 
        "author":"<insert_author_3>", 
        "photoLink":"https://link.sample.photo1" }`);
    console.log(PhotoPosts.validatePhotoPost(wrong_post)); //false
    let good_post = JSON.parse(
        `{ "id":25, 
        "description":"description_sample_11", 
        "createdAt":"2018-02-23T23:00:55", 
        "author":"<insert_author_6>", 
        "photoLink":"https://link.sample.photo11" }`);
    good_post.createdAt = new Date(good_post.createdAt);
    console.log(PhotoPosts.validatePhotoPost(good_post)); //true
    console.log("addPhotoPost examples:");
    console.log(arr.addPhotoPost(wrong_post)); // false
    console.log(arr.addPhotoPost(good_post)); // true
    console.log(arr.getPhotoPosts(null, 21));
    console.log("removePhotoPost examples:");
    console.log(arr.removePhotoPost("24")); // false
    console.log(arr.removePhotoPost("19")); // true
    console.log(arr.getPhotoPosts(null, 21));
    console.log("editPhotoPost examples:");
    console.log(arr.editPhotoPost("0", wrong_post));
    console.log(arr.editPhotoPost("0", good_post));
    console.log(arr.getPhotoPosts(null, 20));
}());