//Get token
if (!localStorage.getItem("token")){
    fetch("/token/oauth", {
        method: "GET",
        headers: {
            "Content-type":"application/json"
        }
    })
    .then(response => {
        if (response.ok){
            return response.json();
        } else {
            location.href = "/lu/login";
        }
    })
    .then(json => {
        localStorage.setItem("token", json.token);
    })
    .catch(e=>{
        alert(e.message);
    })   
}
const tokenFriend = localStorage.getItem("token");

// Display list friends
const displayFriends = document.getElementById("list-friends")

function listFriends(friend){
    const oneFriend = document.createElement("div");
    oneFriend.className = "mb-2";
    oneFriend.innerHTML=
    `
    <a href="/lu/friend/${friend.id}" id="friend${friend.id}" class="link-image d-flex p-2 border rounded" aria-haspopup="true">
        <div class="d-flex">
            <div class="col-2 text-center me-1" id="profile">
                <img src="${friend.profileImg}" id="base-image" class="img-fluid w-50" style="border-radius: 50%;" >
            </div>
            <div class="d-none d-lg-flex col-9">
                ${friend.name}
            </div>
        </div>
        <div class="position-relative me-3">
            <i class="fa-solid fa-comments"></i>
            <span id="mes-count" class="d-none position-absolute" style="top: -1px; right: -7px; background: red; color: white; border-radius: 50%; padding:1px 6px; font-size: 10px;">3</span>
        </div>
    </a>
    `
    displayFriends.appendChild(oneFriend);

    fetch(`/users/mes/${friend.id}`, {
        method:"GET",
        headers: {
            "Authorization": `Bearer ${tokenFriend}`,
            "Content-type": "application/json",
        }
    }).then(response=>{
        if (response.ok){
            return response.json();
        } else {
            return response.text().then(text=>{
                alert(text);
            })
        }
    })
    .then(numOfMes=>{
        const mesCountDis = document.getElementById("mes-count");
        mesCountDis.innerText= numOfMes;
        if (numOfMes>0) mesCountDis.classList.remove("d-none");
    }).catch(e=>{
        alert(e.message);
    })
}

    
fetch("/friends", {
    method:"GET",
    headers: {
        "Authorization": `Bearer ${tokenFriend}`,
        "Content-type": "application/json",
    }
}).then(response=>{
    if (response.ok){
        return response.json();
    } else {
        return response.text().then(text=>{
            alert(text);
        })
    }
})
.then(json =>{
    json.forEach(friend=>{
        listFriends(friend);
    })
}).catch(e=>{
    alert(e.message);
})








// const socket = new SockJS("/ws");
                // const stompClient = Stomp.over(socket);

                // stompClient.connect({}, function (frame) {
                //     console.log('Connected: ' + frame);
                //     stompClient.subscribe('/topic/messages', function (message) {
                //         showReceivedMessage(JSON.parse(message.body));
                //     });
                // });

                // function sendMessage(content, receiverId, replyId) {
                //     const message = { content: content, receiverId: receiverId, replyId: replyId };
                //     stompClient.send("/app/send", {}, JSON.stringify(message));
                // }

