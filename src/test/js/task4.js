class PhotoPosts {

    constructor(cap) {
        this._posts = new Array();
        cap.forEach(post => {
            if(PhotoPosts.validate(post)) {
                this._posts.push(post);
            }
        });
    }

    getPage(filterConfig, top = 10, skip = 0) {
        let result = [];
        let len = top + skip > this._posts.length ? this._posts.length : top + skip;
        let keys = filterConfig == null ? [] : Object.keys(filterConfig);
        for (let i = skip; i < len; i++) {
            let toAdd = true;
            for (let j = 0; j < keys.length; j++) {
                if (!(this._posts[i][keys[j]] === filterConfig[keys[j]])) {
                    toAdd = false;
                    break;
                }
            }
            if (toAdd) {
                result.push(this._posts[i]);
            }
        }
        return result.sort((lhs, rhs) => lhs.createdAt.getTime() - rhs.createdAt.getTime());
    }

    get(id) {
        return this._posts.find(object => object.id == id);
    }

    add(object) {
        if (!PhotoPosts.validate(object)) {
            return false;
        }
        this._posts.push(object);
        return true;
    }

    addAll(posts) {
        let invalidPosts = new Array();
        posts.forEach(post => {
            if(PhotoPosts.validate(post)) {
                this._posts.push(post);
            } else {
                invalidPosts.push(post);
            }
        });
        return invalidPosts;
    }

    remove(id) {
        let length = this._posts.length;
        this._posts = this._posts.filter(value => value.id != id);
        return (length - this._posts.length) == 1;
    }

    edit(id, photoPost) {
        if (!PhotoPosts.validate(photoPost)) {
            return false;
        }
        let keys = Object.keys(photoPost).filter(value => value != "id" && value != "author" && value != "createdAt");
        let index = this._posts.findIndex(object => object.id == id);
        if (index == undefined) {
            return false;
        }
        for (let i = 0; i < keys.length; i++) {
            this._posts[index][keys[i]] = photoPost[keys[i]];
        }
        return true;
    }

    static validate(object) {
        return PhotoPosts._arraysEqual(Object.keys(object),
            ["id", "description", "createdAt", "author", "photoLink"])
            && object.createdAt instanceof Date;
    }

    static _arraysEqual(a, b) {
        if (a === b) return true;
        if (a == null || b == null) return false;
        if (a.length != b.length) return false;
        a = a.sort();
        b = b.sort();
        for (let i = 0; i < a.length; ++i) {
            if (a[i] !== b[i]) return false;
        }
        return true;
    }
}

//TODO: remove the following in production
(function() {
    let cap = `{ "id":0, "description":"description_sample_0", "createdAt":"2018-02-23T23:00:00", "author":"<insert_author_0>", "photoLink":"https://link.sample.photo0" }
{ "id":1, "description":"description_sample_1", "createdAt":"2017-02-23T23:00:01", "author":"<insert_author_1>", "photoLink":"https://link.sample.photo1" }
{ "id":2, "description":"description_sample_2", "createdAt":"2018-02-23T23:00:02", "author":"<insert_author_2>", "photoLink":"https://link.sample.photo1" }
{ "id":3, "description":"description_sample_3", "createdAt":"2014-02-23T23:00:03", "author":"<insert_author_3>", "photoLink":"https://link.sample.photo1" }
{ "id":4, "description":"description_sample_4", "createdAt":"2018-06-23T23:00:04", "author":"<insert_author_4>", "photoLink":"https://link.sample.photo4" }
{ "id":5, "description":"description_sample_4", "createdAt":"2018-02-23T23:00:05", "author":"<insert_author_5>", "photoLink":"https://link.sample.photo5" }
{ "id":6, "description":"description_sample_4", "createdAt":"2018-02-23T23:00:06", "author":"<insert_author_5>", "photoLink":"https://link.sample.photo5" }
{ "id":7, "description":"description_sample_7", "createdAt":"2018-02-23T23:00:07", "author":"<insert_author_5>", "photoLink":"https://link.sample.photo7" }
{ "id":8, "description":"description_sample_8", "createdAt":"2018-02-23T23:00:08", "author":"<insert_author_8>", "photoLink":"https://link.sample.photo8" }
{ "id":9, "description":"description_sample_9", "createdAt":"2018-02-23T23:00:09", "author":"<insert_author_9>", "photoLink":"https://link.sample.photo9" }
{ "id":10, "description":"description_sample_10", "createdAt":"2018-03-23T23:00:10", "author":"<insert_author_10>", "photoLink":"https://link.sample.photo10" }
{ "id":11, "description":"description_sample_11", "createdAt":"2018-04-23T23:00:11", "author":"<insert_author_11>", "photoLink":"https://link.sample.photo11" }
{ "id":12, "description":"description_sample_12", "createdAt":"2018-05-23T23:00:12", "author":"<insert_author_12>", "photoLink":"https://link.sample.photo12" }
{ "id":13, "description":"description_sample_13", "createdAt":"2018-02-23T23:00:13", "author":"<insert_author_13>", "photoLink":"https://link.sample.photo13" }
{ "id":14, "description":"description_sample_14", "createdAt":"2018-02-23T23:00:14", "author":"<insert_author_14>", "photoLink":"https://link.sample.photo14" }
{ "id":15, "description":"description_sample_15", "createdAt":"2018-02-23T23:00:20", "author":"<insert_author_15>", "photoLink":"https://link.sample.photo15" }
{ "id":16, "description":"description_sample_16", "createdAt":"2018-02-23T23:00:16", "author":"<insert_author_16>", "photoLink":"https://link.sample.photo16" }
{ "id":17, "description":"description_sample_17", "createdAt":"2018-02-23T23:00:17", "author":"<insert_author_17>", "photoLink":"https://link.sample.photo17" }
{ "id":18, "description":"description_sample_18", "createdAt":"2018-02-23T23:00:18", "author":"<insert_author_18>", "photoLink":"https://link.sample.photo18" }
{ "id":19, "description":"description_sample_19", "createdAt":"2019-01-23T23:00:19", "author":"<insert_author_19>", "photoLink":"https://link.sample.photo19" }`.split('\n').map(object => JSON.parse(object));
    cap.forEach(object => object.createdAt = new Date(object.createdAt));

    let arr = new PhotoPosts(cap);
    console.log("getPage examples:");
    console.log(arr.getPage([]));
    console.log(arr.getPage({description: "description_sample_4", photoLink: "https://link.sample.photo5"}));
    console.log("get examples:");
    console.log(arr.get("5"));
    console.log(arr.get("22")); //undefined
    console.log("validate examples:");
    let wrong_post = JSON.parse(
        `{ "id":3, 
        "description":"description_sample_3", 
        "createdAt":"2018-02-23T23:00:03", 
        "author":"<insert_author_3>", 
        "photoLink":"https://link.sample.photo1" }`);
    console.log(PhotoPosts.validate(wrong_post)); //false
    let good_post = JSON.parse(
        `{ "id":25, 
        "description":"description_sample_11", 
        "createdAt":"2018-02-23T23:00:55", 
        "author":"<insert_author_6>", 
        "photoLink":"https://link.sample.photo11" }`);
    good_post.createdAt = new Date(good_post.createdAt);
    console.log(PhotoPosts.validate(good_post)); //true
    console.log("add examples:");
    console.log(arr.add(wrong_post)); // false
    console.log(arr.add(good_post)); // true
    console.log(arr.getPage(null, 21));
    console.log("remove examples:");
    console.log(arr.remove("24")); // false
    console.log(arr.remove("19")); // true
    console.log(arr.getPage(null, 21));
    console.log("edit examples:");
    console.log(arr.edit("0", wrong_post));
    console.log(arr.edit("0", good_post));
    console.log(arr.getPage(null, 20));
}());
