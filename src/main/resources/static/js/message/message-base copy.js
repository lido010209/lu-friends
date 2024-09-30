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
const token = localStorage.getItem("token");

// Display list friends
const displayFriends = document.getElementById("list-friends")


let currentFriendId = null;
function listFriends(friend){
    const oneFriend = document.createElement("div");
    oneFriend.className = "mb-2";
    oneFriend.innerHTML=
    `
    <a href="#" id="friend${friend.id}" class="link-image d-flex p-2 border rounded" aria-haspopup="true">
        <div class="col-2 text-center me-1" id="profile">
            <img src="${friend.profileImg}" id="base-image" class="img-fluid w-50" style="border-radius: 50%;" >
        </div>
        <div class="d-none d-lg-flex col-9">
            ${friend.name}
        </div>
    </a>
    `
    displayFriends.appendChild(oneFriend);

    const friendId = document.getElementById(`friend${friend.id}`);

    friendId.addEventListener("click", e =>{
        e.preventDefault();

        if (currentFriendId) {
            const previousFriendElement = document.getElementById(`friend${currentFriendId}`);
            if (previousFriendElement) {
                previousFriendElement.classList.remove('active-friend');
            }
        }

        currentFriendId = friend.id; // Update the active friend ID
        friendId.classList.add('active-friend');
            // Display chatbot
        const chatbot = document.getElementById("chat-bot");
            chatbot.classList.remove("d-none");
            const xmark = document.getElementById("xmark");
            xmark.addEventListener("click", e=>{
                e.preventDefault();
                chatbot.classList.add("d-none");
                currentFriendId=null;
            })

            const friendAvatar= document.getElementById("friend-avatar");
            friendAvatar.src=friend.profileImg;
            const friendName= document.getElementById("friend-name");
            friendName.innerText=friend.name;

        const listMessages = document.getElementById("list-messages");
        let replyInputId = null; 
        function displayMessage(message){
                if (message.senderId===friend.id){
                    const senderMessage = document.createElement("div");
                    senderMessage.className="d-flex";
                    
                    const insideMessage = document.createElement("div");
                    insideMessage.className="me-5 chat-messages pb-3 d-flex flex-wrap align-items-center";
        
                    if (message.replyId!==null){
                        const replyMes = document.createElement("div");
                        replyMes.className="w-100";
                        replyMes.innerHTML=
                        `
                            <div class="mb-2 text-secondary" style="font-size: x-small;">
                                <i class="fa-solid fa-reply fa-sm"></i>
                                Reply to ${message.receiverName};
                            </div>
                            <div class="w-100 d-flex">
                                <div class="w-75">
                                    <span class="p-1 d-inline border text-secondary bg-light-subtle rounded">
                                        ${message.reply}
                                    </span>
                                </div>
                            </div>
                        `
                        insideMessage.appendChild(replyMes);
                    }

                    const contentMes = document.createElement("div");
                    contentMes.className="w-100 d-flex flex-wrap align-items-center";
                    contentMes.innerHTML=
                    `
                            <div class="py-2 px-4 rounded text-bg-light d-inline">
                                ${message.content}
                            </div>
                            <a href="#" class="position-relative rounded p-1 bg-light ms-1" id="reply${message.id}">
                                <i class="fa-solid fa-reply fa-sm"></i>
                            </a>
                            <a href="#" class="position-relative rounded p-1 bg-light ms-1" id="vertical">
                                <i class="fa-solid fa-ellipsis-vertical"></i>
                                <div class="d-none ms-1 p-1 rounded position-absolute border border-dark start-100" style="width: 70px; font-size: small; top: -17px;">
                                    <span class="border-bottom" id="edit${message.id}"><i class="fa-solid fa-pen fa-sm"></i> Edit</span><br>
                                    <span class="border-bottom" id="remove${message.id}"><i class="fa-solid fa-trash fa-sm"></i> Remove</span>
                                    <span class="border-bottom" id="copy${message.id}"><i class="fa-solid fa-copy fa-sm"></i> Copy</span>
                                </div>
                            </a>
                    `
                    insideMessage.appendChild(contentMes);
                    senderMessage.appendChild(insideMessage);
                    listMessages.appendChild(senderMessage);
                }
                else if (message.receiverId===friend.id){
                    const senderMessage = document.createElement("div");
                    senderMessage.className="d-flex justify-content-end";
                    
                    const insideMessage = document.createElement("div");
                    insideMessage.className="ms-5 chat-messages pb-3 flex-wrap d-flex justify-content-end align-items-center";
                    if (message.replyId!==null){
                        const replyMes = document.createElement("div");
                        replyMes.className="w-100";
                        replyMes.innerHTML=
                        `
                            <div class="mb-2 text-secondary text-end" style="font-size: x-small;">
                                <i class="fa-solid fa-reply fa-sm"></i>
                                Reply to ${message.receiverName}
                            </div>
                            <div class="w-100 d-flex justify-content-end">
                                <div class="w-75 text-end">
                                    <span class="p-1 d-inline border text-secondary bg-light-subtle rounded">
                                        ${message.reply}
                                    </span>
                                </div>
                            </div>
                        `
                        insideMessage.appendChild(replyMes);
                    }

                    const contentMes = document.createElement("div");
                    contentMes.className="w-100 d-flex justify-content-end align-items-center";
                    contentMes.innerHTML=
                    `
                            <a href="#" class="position-relative rounded p-1 bg-light me-1" id="vertical">
                                <i class="fa-solid fa-ellipsis-vertical"></i>
                                <div class="d-none me-1 p-1 rounded position-absolute border border-dark end-100" style="width: 70px; font-size: small; top: -17px;">
                                    <span class="border-bottom" id="edit${message.id}"><i class="fa-solid fa-pen fa-sm"></i> Edit</span><br>
                                    <span class="border-bottom" id="remove${message.id}"><i class="fa-solid fa-trash fa-sm"></i> Remove</span>
                                    <span class="border-bottom" id="copy${message.id}"><i class="fa-solid fa-copy fa-sm"></i> Copy</span>
                                </div>
                            </a>
                            <a href="#" class="position-relative rounded p-1 bg-light me-1" id="reply${message.id}">
                                <i class="fa-solid fa-reply fa-sm"></i>
                            </a>
                            <div class="py-2 px-4 rounded text-bg-info">
                                ${message.content}
                            </div>
                    `  
                    insideMessage.appendChild(contentMes);
                    senderMessage.appendChild(insideMessage);
                    listMessages.appendChild(senderMessage);
                }

                const replyMesId = document.getElementById(`reply${message.id}`);
                replyMesId.addEventListener("click", e=>{
                        e.preventDefault();
                        const replyDisplay = document.getElementById("reply-message");
                        fetch(`/messages/${message.id}`, {
                            method: "GET",
                            headers: {
                                "Authorization": `Bearer ${token}`,
                                "Content-type": "application/json",
                            }
                        })
                        .then(response=>{
                            if(response.ok){
                                return response.json();
                            } else {
                                return response.text().then(text=>{
                                    alert(text);
                                })
                            }
                        })
                        .then(json=> {
                            replyDisplay.innerHTML=
                            `
                            <span class="p-1 d-inline border text-secondary bg-light-subtle rounded">
                                ${json.content}
                            </span>
                            <a href="#" id="cancel-reply" class="ps-2 pe-2"><i class="fa-solid fa-xmark"></i></a>
                            `
                            replyInputId=json.id;
                        })
                        
                        const cancelReply = document.getElementById("cancel-reply");
                        cancelReply.addEventListener("click", e=>{
                            e.preventDefault();
                            replyDisplay.innerHTML=``;
                            replyInputId=null;
                        })
                    })
            }
        
        fetch(`/messages/chat/${friend.id}`, {
                method: "GET",
                headers:{
                    "Authorization": `Bearer ${token}`,
                    "Content-type": "application/json",
                }
            })
            .then(response=>{
                if (response.ok){
                    return response.json();
                } else {
                    return response.text().then(text=>{
                        alert(text);
                    })
                }
            })
            .then(json=> {
                listMessages.innerHTML=``;
                json.forEach(message=>{
                    displayMessage(message);
                })
            })
            
        const inputMessage = document.getElementById("input-message");
        const formSendMes= document.getElementById("form-sendMessage");
        
        formSendMes.addEventListener("submit", e=>{
            e.preventDefault();
                const dataInput = {
                    content: inputMessage.value,
                    receiverId: friend.id,
                    replyId: replyInputId
                }
            fetch("/messages/sender", {
                    method: "POST",
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-type": "application/json",
                    },
                    body: JSON.stringify(dataInput)
            }).then(response=>{
                    if(response.ok){
                        return response.json();
                    } else{
                        return response.text().then(text=>{
                            alert(text);
                        })
                    }
            }).then(message=>{
                    displayMessage(message);
                    inputMessage.value=null;
            })
        })
    });
    // if (currentFriendId===null || currentFriendId!==friendId) friendId.addEventListener("click", clickFriendId);
    // else currentFriendId.addEventListener("click", clickFriendId);
}

    

fetch("/friends", {
    method:"GET",
    headers: {
        "Authorization": `Bearer ${token}`,
        "Content-type": "application/json",
    }
}).then(response=>{
    if (response.ok){
        return response.json();
    } else {
        return response.text().then(text=>{
            alert.text();
        })
    }
})
.then(json =>{
    json.forEach(friend=>{
        listFriends(friend);
    })
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

