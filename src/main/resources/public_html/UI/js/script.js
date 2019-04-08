( function() {
    if (localStorage.length) {
        return;
    }
    let db = `{ "id":0, "description":"description #sample 25", "createdAt":"2018-02-23T23:00:03", "author":"author_0", "src":"UI/img/placeholder-1.jpg" }
    { "id":1, "description":"#hashtag sample_1", "date":"2018-02-23T23:00:03", "author":"author_1", "src":"UI/img/placeholder-2.jpg" }
    { "id":2, "description":"description_sample_2", "date":"2017-12-23T23:00:03", "author":"author_0", "src":"UI/img/placeholder-3.jpg" }
    { "id":3, "description":"none", "date":"2018-01-22T23:00:03", "author":"author_3", "src":"UI/img/placeholder-4.jpg" }
    { "id":4, "description":"description_sample_4", "date":"2018-01-23T23:00:04", "author":"author_4", "src":"UI/img/placeholder-5.jpg" }
    { "id":5, "description":"description_sample_4", "date":"2018-01-24T23:00:05", "author":"author_4", "src":"UI/img/placeholder-6.jpg" }
    { "id":6, "description":"description_sample_4", "date":"2018-02-24T23:00:06", "author":"author_5", "src":"UI/img/placeholder-7.jpg" }
    { "id":7, "description":"description_sample_7", "date":"2018-02-23T23:00:07", "author":"author_5", "src":"UI/img/placeholder-8.jpg" }
    { "id":8, "description":"description_sample_8", "date":"2018-02-25T23:00:08", "author":"author_8", "src":"UI/img/placeholder-9.jpg" }
    { "id":9, "description":"description_sample_9", "date":"2018-02-23T23:00:09", "author":"author_9", "src":"UI/img/placeholder-10.jpg" }
    { "id":10, "description":"none", "date":"2018-02-23T23:00:03", "author":"author_0", "src":"UI/img/placeholder-11.jpg" }
    { "id":11, "description":"description_sample_11", "date":"2018-02-23T23:00:03", "author":"author_11", "src":"UI/img/placeholder-12.jpg" }
    { "id":12, "description":"none", "date":"2018-02-23T23:00:03", "author":"author_12", "src":"UI/img/placeholder-13.jpg" }
    { "id":13, "description":"#hashtag sample_13", "date":"2018-02-23T23:00:03", "author":"author_12", "src":"UI/img/placeholder-14.jpg" }
    { "id":14, "description":"description_sample_14", "date":"2018-02-23T23:00:03", "author":"author_14", "src":"UI/img/placeholder-15.jpg" }
    { "id":15, "description":"none", "date":"2018-02-23T23:00:03", "author":"author_15", "src":"UI/img/placeholder-16.jpg" }
    { "id":16, "description":"description #sample again", "date":"2018-02-23T23:00:03", "author":"author_16", "src":"UI/img/placeholder-17.jpg" }
    { "id":17, "description":"description_sample_17", "date":"2018-02-23T23:00:03", "author":"author_15", "src":"UI/img/placeholder-18.jpg" }
    { "id":18, "description":"description_sample_18", "date":"2018-02-23T23:00:03", "author":"author_15", "src":"UI/img/placeholder-19.jpg" }
    { "id":19, "description":"description_sample_19", "date":"2018-02-23T23:00:03", "author":"author_0", "src":"UI/img/placeholder-20.jpg" }`.split('\n');
    for(let i = 0; i < 20; ++i) {
        localStorage.setItem("post_" + i.toString(), db[i]);
    }
    localStorage.setItem("id", "20");
}());

class Context {
    constructor() {
        this.id = parseInt(localStorage.getItem("id"));
        if (!this.id) {
            this.id = 0;
            localStorage.setItem("id", this.id);
        }
    }

    load() {
        this.user = document.getElementById("user-name").innerHTML;
        this.loadSkip = 0;
        this.searchOption = function (obj) {
            return true;
        };
        this.searchMenu = new SelectMenu([document.getElementById("search-hashtags"),
            document.getElementById("search-author"),
            document.getElementById("search-date-after"),
            document.getElementById("search-date-before")]);
        this.selectValue = new SelectValue();
    }

    saveAttribute(key, value) {
        localStorage.setItem(key, value);
    }

    removeAttribute(key) {
        localStorage.removeItem(key);
    }

    loadOption() {
        this.searchOption = this.selectValue.forName(this.searchMenu.getSelected().id);
    }

    getPosts() {
        let result = [];
        for(let i = 0; i < this.id; ++i) {
            let value = localStorage.getItem("post_" + i.toString());
            if (value) {
                let post = JSON.parse(value);
                post.date = new Date(post.date);
                if(!post.likes) {
                    post.likes = [];
                }
                result.push(post);
            }
        }
        return result;
    }

    getId() {
        localStorage.setItem("id", (++this.id).toString());
        return this.id;
    }
}

class SelectMenu {
    //elements must contain id
    constructor(elements) {
        this.selectMap = new Array();
        for(let i = 0; i < elements.length; ++i) {
            if(elements[i]) {
                this.selectMap[elements[i].id] = elements[i];
            }
        }
        this.currentId = elements[0].id;
        this.pattern = "<i class=\"fa fa-check\"></i>";
        this.previousHTML = elements[0].innerHTML;
        this._highlight(elements[0])
    }

    select (element) {
        this._unhighlight(this.selectMap[this.currentId]);
        this._highlight(element);
    }

    getSelected() {
        return this.selectMap[this.currentId];
    }

    _highlight (element) {
        this.previousHTML = element.innerHTML;
        this.currentId = element.id;
        element.innerHTML = element.innerHTML.concat(this.pattern)
    }

    _unhighlight (element) {
        element.innerHTML = this.previousHTML.slice();
    }
}

class SelectValue {
    constructor() {
        this.author = function (obj) {
            return obj.author === document.getElementById("search-query").value;
        };
        this.hashtags = function (obj) {
            let query = document.getElementById("search-query").value.replace('#', '');
            return new RegExp('#' + query).exec(obj.description);
        };
        this.dateAfter = function (obj) {
            return obj.date.getTime() > new Date(document.getElementById("search-query").value).getTime();
        };
        this.dateBefore = function (obj) {
            return obj.date.getTime() < new Date(document.getElementById("search-query").value).getTime();
        };
    }

    forName(str) {
        switch (str) {
            case 'search-hashtags':
                return this.hashtags;
            case 'search-author':
                return this.author;
            case 'search-date-after':
                return this.dateAfter;
            case 'search-date-before':
                return this.dateBefore;
        }
        return null;
    }
}

class PostCollection {
    constructor(context) {
        this.context = context;
        this.list = context.getPosts();
    }

    add(post) {
        if(!post.description || !post.src) {
            return false;
        }
        post.id = this.context.getId();
        post.date = new Date();
        post.author = this.context.user;
        post.likes = [];
        //this.list.push(post);
        this.list.unshift(post);
        this.context.saveAttribute('post_' + post.id.toString(), JSON.stringify(post));
        return post;
    }

    like(id) {
        let post = this.list.find(e => e.id == id);
        if (post) {
            if (post.likes.find(name => name === context.user)) {
                post.likes = post.likes.filter(name => name !== context.user);
            } else {
                post.likes.push(context.user);
            }
        }
        this.context.saveAttribute('post_' + post.id.toString(), JSON.stringify(post));
    }

    remove(id) {
        let length = this.list.length;
        this.list = this.list.filter(value => value.id != id);
        this.context.removeAttribute("post_" + id);
        return (length - this.list.length) === 1;
    }

    get(callback, skip, count) {
        let result = [];
        for(let i = 0, n = 0; i < this.list.length && n < count; ++i) {
            if (skip) {
                --skip;
                continue;
            }
            if (callback(this.list[i])) {
                result.push(this.list[i]);
                ++n;
            }
        }
        return result;
    }
}

class Display {
    constructor(context) {
        this.holder = document.getElementById("posts-holder");
        this.lastElement = document.getElementById("load-more-button");
        this.firstElement = document.getElementById("add-post-container");
        this.context = context;
        this.pattern = document.createElement("div");
        this.pattern.innerHTML = "<div class=\"post-holder\">\n" +
            "        <div class=\"post-image-holder\">\n" +
            "            <img src=\"placeholder-image.jpg\">\n" +
            "        </div>\n" +
            "        <div class=\"like-holder\">\n" +
            "            <button class=\"ulike\">\n" +
            "                <i class=\"fa fa-heart fa-lg\"></i>\n" +
            "            </button>\n" +
            "            <span class=\"like-number\">0</span>\n" +
            "        </div>\n" +
            "        <div class=\"post-text-holder\">\n" +
            "        </div>\n" +
            "        <div class=\"post-author\"><i>undefined</i></div>\n" +
            "        <div class=\"post-date\"><i>00.00.0000</i></div>\n" +
            "    </div>";
    }

    display(posts) {
        let lst = this.holder.getElementsByClassName("post-holder");
        let length = lst.length;
        for(let i = 0; i < length; ++i) {
            lst[0].remove();
        }
        this.append(posts);
    }

    append(posts) {
        for(let i = 0; i < posts.length; ++i) {
            let post = this.pattern.cloneNode(true);
            post.id = "post_" + posts[i].id;
            post.getElementsByClassName("post-author")[0].innerHTML = "<i>" + posts[i].author + "</i>";
            post.getElementsByClassName("post-date")[0].innerHTML = "<i>" + posts[i].date.toString() + "</i>";
            post.getElementsByClassName("post-text-holder")[0].innerHTML = "<p>" + posts[i].description + "</p>";
            let likes = posts[i].likes ? posts[i].likes : [];
            post.getElementsByClassName("like-number")[0].innerHTML = likes.length.toString();
            post.getElementsByClassName("ulike")[0].id = "like_" + posts[i].id;
            //let like = post.getElementsByClassName("ulike")[0];
            //like.id = "like_" + posts[i].id;
            post.getElementsByClassName("ulike")[0].setAttribute('onclick','likePost(this);');
            if (likes.indexOf(this.context.user) !== -1) {
                let classList = post.getElementsByClassName("ulike")[0].classList;
                classList.remove("ulike");
                classList.add("like");
            }
            if (posts[i].author === this.context.user) {
                let deleteButton = document.createElement("i");
                deleteButton.classList.add("fa", "fa-times", "delete");
                deleteButton.setAttribute("aria-hidden", "true");
                deleteButton.id = "delete_" + posts[i].id;
                deleteButton.addEventListener("click", function (event) {
                    event.preventDefault();
                    removePost(parseInt(event.target.id.substr(7)));
                });
                post.getElementsByClassName("like-holder")[0].appendChild(deleteButton);
            }
            post.getElementsByClassName("post-image-holder")[0].innerHTML = "<img src=" + posts[i].src + " />";
            this.holder.insertBefore(post, this.lastElement);
        }
    }

    push_front(post) {
        let newPost = this.pattern.cloneNode(true);
        newPost.id = "post_" + post.id;
        newPost.getElementsByClassName("post-author")[0].innerHTML = "<i>" + post.author + "</i>";
        newPost.getElementsByClassName("post-date")[0].innerHTML = "<i>" + post.date.toString() + "</i>";
        newPost.getElementsByClassName("post-text-holder")[0].innerHTML = "<p>" + post.description + "</p>";
        let likes = post.likes ? post.likes : [];
        newPost.getElementsByClassName("like-number")[0].innerHTML = likes.length.toString();
        newPost.getElementsByClassName("ulike")[0].id = "like_" + post.id;
        newPost.getElementsByClassName("ulike")[0].setAttribute('onclick','likePost(this);');
        if (likes.indexOf(this.context.user) !== -1) {
            let classList = newPost.getElementsByClassName("ulike")[0].classList;
            classList.remove("ulike");
            classList.add("like");
        }
        if (post.author === this.context.user) {
            let deleteButton = document.createElement("i");
            deleteButton.classList.add("fa", "fa-times", "delete");
            deleteButton.setAttribute("aria-hidden", "true");
            deleteButton.id = "delete_" + post.id;
            deleteButton.addEventListener("click", function (event) {
                event.preventDefault();
                removePost(parseInt(event.target.id.substr(7)));
            });
            newPost.getElementsByClassName("like-holder")[0].appendChild(deleteButton);
        }
        newPost.getElementsByClassName("post-image-holder")[0].innerHTML = "<img src=" + post.src + " />";
        this.holder.insertBefore(newPost, this.firstElement.nextSibling);
    }

    like(id) {
        let post = document.getElementById(id);
        let like = post.getElementsByClassName("ulike");
        let likeNumber = post.getElementsByClassName("like-number")[0];
        if(!like.length) {
            like = post.getElementsByClassName("like")[0];
            like.classList.remove("like");
            like.classList.add("ulike");
            likeNumber.innerHTML = (parseInt(likeNumber.innerHTML) - 1).toString();
        } else {
            like = like[0];
            like.classList.remove("ulike");
            like.classList.add("like");
            likeNumber.innerHTML = (parseInt(likeNumber.innerHTML) + 1).toString();
        }

    }

    exclude(id) {
        document.getElementById(id).remove();
    }
}

document.getElementById("search-submit").addEventListener("click", function(event){
    event.preventDefault();
    context.loadSkip = 0;
    let query = document.getElementById("search-query").value;
    if(!query) {
        context.searchOption = function (obj) {
            return true;
        };
        display.display(posts.get(context.searchOption, 0, 10));
        return;
    }
    context.loadOption();
    display.display(posts.get(context.searchOption, 0, 10));

});

document.getElementById("add-post-submit").addEventListener("click", function(event) {
    event.preventDefault();

    let selectedFile = document.getElementById("add-post-file").files[0];
    let description = document.getElementById("add-post-description").value;
    if(!selectedFile || !description) {
        alert("Please load image file and leave a comment!");
        return;
    }
    let post = Object.create(null);
    post.src = URL.createObjectURL(selectedFile);
    post.description = description;
    //add post
    display.push_front(posts.add(post));
});

document.getElementById("add-post-file-button").addEventListener("click", function (event) {
   event.preventDefault();
   document.getElementById("add-post-file").click();
});

document.getElementById("load-more-button").addEventListener("click", function (event) {
    event.preventDefault();
    context.loadSkip += 10;
    display.append(posts.get(context.searchOption, context.loadSkip, 10));
});

function login(event) {
    document.getElementById('id01').style.display='block'
}

document.getElementById("profile-menu").addEventListener("click", login);

document.getElementById("login-submit").addEventListener("click", function (event) {
    event.preventDefault();
    let username = document.getElementById("uname").value;
    let password = document.getElementById("psw").value;
    if (!username || !password) {
        return false;
    }
    let oldPassword = localStorage.getItem(username);
    if(oldPassword) {
        if(password !== oldPassword) {
            alert("wrong password!");
            return false;
        }
    } else {
        localStorage.setItem(username, password);
    }
    document.getElementById("profile-menu").innerHTML = "<i id=\"user-name\">" + username + "</i><i class=\"fa fa-user fa-fw fa-lg\"></i>";
    context.load();
    document.getElementById("posts-holder").style.display = "block";
    modal.style.display = "none";
    document.getElementById("profile-menu").removeEventListener("click", login);
    display.display(posts.get(context.searchOption, 0, 10));
});


function selectOption(element) {
    context.searchMenu.select(element);
}

function removePost(id) {
    posts.remove(id);
    display.exclude("post_" + id);
    --context.loadSkip;
}

function likePost(element) {
    posts.like(element.id.substr(5));
    display.like("post_" + element.id.substr(5));
}


let context = new Context();
let posts = new PostCollection(context);
let display = new Display(context);
let modal = document.getElementById('id01');

window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
}
