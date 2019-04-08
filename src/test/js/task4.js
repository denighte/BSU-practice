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
