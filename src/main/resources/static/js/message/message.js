const token = localStorage.getItem("token");
if (!token) {
    location.href = "/lu/login";
}

const pathSplit = location.pathname.split("/");
const receiverId = pathSplit[pathSplit.length - 1];

const listMessages = document.getElementById("list-messages");
document.addEventListener("DOMContentLoaded", function () {
    listMessages.scrollTop = listMessages.scrollHeight;
});

function displayMessage(message) {
    if (message.senderId === Number(receiverId)) {
        const senderMessage = document.createElement("div");
        senderMessage.className = "d-flex";

        const insideMessage = document.createElement("div");
        insideMessage.className = "me-5 chat-messages pb-3 d-flex flex-wrap align-items-center";
        insideMessage.id=`insideMes${message.id}`;

        if (message.replyId !== null) {
            const replyMes = document.createElement("div");
            replyMes.className = "w-100";
            replyMes.innerHTML =
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
        contentMes.className = "w-100 d-flex flex-wrap align-items-center";
        const contentMesHTML =
            `
                <div id="messageContent${message.id}" class="py-2 px-4 rounded text-bg-light d-inline">
                    ${message.content}
                </div>
                <a href="#" class="position-relative rounded p-1 bg-light ms-1" id="reply${message.id}">
                    <i class="fa-solid fa-reply fa-sm"></i>
                </a>
                <a href="#" class="position-relative rounded p-1 bg-light ms-1" id="vertical${message.id}">
                    <i class="fa-solid fa-ellipsis-vertical"></i>
                    <div id="display-vertical${message.id}" class="d-none ms-1 p-1 rounded position-absolute border border-dark start-100" style="width: 70px; font-size: small; top: -17px;">
                        <span class="border-bottom" id="copy${message.id}"><i class="fa-solid fa-copy fa-sm"></i> Copy</span><br>
                        <span class="border-bottom" id="remove${message.id}"><i class="fa-solid fa-trash fa-sm"></i> Remove</span><br>
                        <span class="border-bottom d-none" id="edit${message.id}"><i class="fa-solid fa-pen fa-sm"></i> Edit</span>
                    </div>
                 </a>
            `
        contentMes.insertAdjacentHTML('beforeend', contentMesHTML);
        insideMessage.appendChild(contentMes);
        senderMessage.appendChild(insideMessage);
        listMessages.appendChild(senderMessage);
    }
    else if (message.receiverId === Number(receiverId)) {
        const senderMessage = document.createElement("div");
        senderMessage.className = "d-flex justify-content-end";

        const insideMessage = document.createElement("div");
        insideMessage.className = "ms-5 chat-messages pb-3 flex-wrap d-flex justify-content-end align-items-center";
        insideMessage.id=`insideMes${message.id}`;
        
        if (message.replyId !== null) {
            const replyMes = document.createElement("div");
            replyMes.className = "w-100";
            replyMes.innerHTML =
                `
                            <div class="mb-2 text-secondary text-end" style="font-size: x-small;">
                                <i class="fa-solid fa-reply fa-sm"></i>
                                Reply to yourself
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
        contentMes.className = "w-100 d-flex justify-content-end align-items-center";
        const contentMesHTML =
            `
                            <a href="#" class="position-relative rounded p-1 bg-light me-1" id="vertical${message.id}">
                                <i class="fa-solid fa-ellipsis-vertical"></i>
                                <div id="display-vertical${message.id}" class="d-none me-1 p-1 rounded position-absolute border border-dark end-100" style="width: 70px; font-size: small; top: -17px;">
                                    <span class="border-bottom" id="copy${message.id}"><i class="fa-solid fa-copy fa-sm"></i> Copy</span><br>
                                    <span class="border-bottom" id="remove${message.id}"><i class="fa-solid fa-trash fa-sm"></i> Remove</span><br>
                                    <span class="border-bottom d-none" id="edit${message.id}"><i class="fa-solid fa-pen fa-sm"></i> Edit</span>
                                </div>
                            </a>
                            <a href="#" class="position-relative rounded p-1 bg-light me-1" id="reply${message.id}">
                                <i class="fa-solid fa-reply fa-sm"></i>
                            </a>
                            <div id="messageContent${message.id}" class="py-2 px-4 rounded text-bg-info">
                                ${message.content}
                            </div>
            `
        contentMes.insertAdjacentHTML('beforeend', contentMesHTML);
        insideMessage.appendChild(contentMes);
        senderMessage.appendChild(insideMessage);
        listMessages.appendChild(senderMessage);
    }
}

fetch(`/messages/chat/${receiverId}`, {
    method: "GET",
    headers: {
        "Authorization": `Bearer ${token}`,
        "Content-type": "application/json",
    }
})
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            return response.text().then(text => {
                alert(text);
            })
        }
    })
    .then(json => {
        // listMessages.innerHTML = ``;
        json.forEach(message => {
            displayMessage(message);
            confirmMes(message);
            moreFuntion(message);
        })
        listMessages.scrollTop = listMessages.scrollHeight;
    })

function chatbox(friend) {
    const chatbot = document.getElementById("chat-bot");
    chatbot.classList.remove("d-none");

    // const xmark = document.getElementById("xmark");
    // xmark.addEventListener("click", e => {
    //     e.preventDefault();
    //     localtion.href = "/lu/friend";
    // })

    const friendAvatar = document.getElementById("friend-avatar");
    friendAvatar.src = friend.profileImg;

    const friendName = document.getElementById("friend-name");
    friendName.innerText = friend.name;
}

fetch(`/users/${receiverId}`, {
    method: "GET",
    headers: {
        "Authorization": `Bearer ${token}`,
        "Content-type": "application/json",
    }
}).then(response => {
    if (response.ok) {
        return response.json();
    } else {
        return response.text().then(text => {
            alert(text);
        })
    }
}).then(friend => {
    chatbox(friend);
    listMessages.scrollTop = listMessages.scrollHeight;
}).catch(e => {
    alert(e.message);
});

// Reply message
const inputMessage = document.getElementById("input-message");
const formSendMes = document.getElementById("form-sendMessage");

function confirmMes(message) {
    if (message.senderId === Number(receiverId) && !message.confirm) {
        inputMessage.addEventListener("click", e => {
            e.preventDefault();
            fetch(`/messages/receiver/${message.id}`, {
                method: "PUT",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-type": "application/json",
                }
            })
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    } else {
                        return response.text().then(text => {
                            alert(text);
                        })
                    }
                }).catch(e => {
                    alert(e.message());
                })
        })
    }
}
let replyInputId = null;
formSendMes.addEventListener("submit", e => {
    e.preventDefault();
    const dataInput = {
        content: inputMessage.value,
        receiverId: receiverId,
        replyId: replyInputId,
    }
    
    fetch("/messages/sender", {
        method: "POST",
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-type": "application/json",
        },
        body: JSON.stringify(dataInput)
    }).then(response => {
        if (response.ok) {
            return response.json();
        } else {
            return response.text().then(text => {
                alert(text);
            })
        }
    }).then(message => {
        displayMessage(message);
        moreFuntion(message);
        inputMessage.value = null;
        replyDisplay.innerHTML=``;
        replyInputId=null;
        listMessages.scrollTop = listMessages.scrollHeight;
    })
});
let currentMesId = null;
const replyDisplay = document.getElementById("reply-message");
function moreFuntion(message) {
    // Reply message
    const replyMesId = document.getElementById(`reply${message.id}`);
    replyMesId.addEventListener("click", e => {
        e.preventDefault();
        replyDisplay.classList.remove("d-none");
        fetch(`/messages/${message.id}`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-type": "application/json",
            }
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    return response.text().then(text => {
                        alert(text);
                    })
                }
            })
            .then(json => {
                replyDisplay.innerHTML =
                    `
                    <span class="p-1 d-inline border text-secondary bg-light-subtle rounded">
                        ${json.content}
                    </span>
                    <a href="#" id="cancel-reply${json.id}" class="ps-2 pe-2"><i class="fa-solid fa-xmark"></i></a>
                    `
                replyInputId = json.id;
                const cancelReply = document.getElementById(`cancel-reply${json.id}`);
                    cancelReply.addEventListener("click", e => {
                        e.preventDefault();
                        replyDisplay.innerHTML=``;
                        replyInputId = null;
                })
                
            })
            
    })
    

        // < a href = "#" class="position-relative rounded p-1 bg-light me-1" id = "vertical${message.id}" >
        //     <i class="fa-solid fa-ellipsis-vertical"></i>
        //     <div id="display-vertical${message.id}" class="d-none me-1 p-1 rounded position-absolute border border-dark end-100" style="width: 70px; font-size: small; top: -17px;">
        //         <span class="border-bottom" id="copy${message.id}"><i class="fa-solid fa-copy fa-sm"></i> Copy</span><br>
        //         <span class="border-bottom" id="remove${message.id}"><i class="fa-solid fa-trash fa-sm"></i> Remove</span><br>
        //         <span class="border-bottom d-none" id="edit${message.id}"><i class="fa-solid fa-pen fa-sm"></i> Edit</span>
        //     </div>
        // </a>
    // Edit message
    const vertical = document.getElementById(`vertical${message.id}`);
    const displayVertical = document.getElementById(`display-vertical${message.id}`);
    const edit = document.getElementById(`edit${message.id}`);
    const remove = document.getElementById(`remove${message.id}`);
    const mesContent= document.getElementById(`messageContent${message.id}`);
    vertical.addEventListener("click", e=> {
        e.preventDefault();
        if (displayVertical.classList.contains("d-none"))
            displayVertical.classList.remove("d-none");
        else displayVertical.classList.add("d-none");
        
        if (currentMesId && currentMesId !== message.id){
            const disVerCur= document.getElementById(`display-vertical${currentMesId}`);
            if (!disVerCur.classList.contains("d-none"))
                disVerCur.classList.add("d-none");
        }
        currentMesId=message.id;
    })
    
    if (!message.confirm && message.receiverId===Number(receiverId)){
        edit.classList.remove("d-none");
        remove.addEventListener("click", e=>{
                e.preventDefault();
                const deleteMes= confirm("Are you sure to delete this message?")
                if (deleteMes){
                fetch(`/messages/sender/${message.id}`, {
                    method:"DELETE",
                    headers: {
                        "Authorization": `Bearer ${token}`,
                    }
                }).then(response=>{
                    if (response.ok){
                        alert("Your message delete successful!!!")
                        location.href=`/lu/friend/${message.receiverId}`;
                    } else{
                        alert("Your message cannot delete");
                    }
                })
            }
        })
    }
    else if (message.confirm && message.receiverId===Number(receiverId)){
        remove.addEventListener("click", e=>{
            e.preventDefault();
            fetch(`/messages/remove/${message.id}`, {
                method: "PUT",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-type": "application/json",
                }
            }).then(response=>{
                if(response.ok){
                    return response.json();
                } else{
                    return response.text().then(text=>{
                        alert(text);
                    })
                }
            }).then(message=>{
                const insideMes= document.getElementById(`insideMes${message.id}`);
                insideMes.innerHTML=
                `
                <div class="py-1 px-2 rounded text-bg-secondary d-inline">
                    This message has been removed!!!
                </div>
                `
            }).catch(e=>{
                alert(e.message);
            })
        })
    }
    edit.addEventListener("click", e=>{
        e.preventDefault();
        const inputEdit = document.getElementById("input-edit");
        inputEdit.value=message.content;
        const editMes= document.getElementById("editMes");
        formSendMes.classList.add("d-none");
        editMes.classList.remove("d-none");

        editMes.addEventListener("submit", e=>{
            e.preventDefault();
            fetch(`/messages/sender/${message.id}`, {
                method: "PUT",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-type": "application/json",
                },
                body: JSON.stringify({content: inputEdit.value})
            }).then(response=>{
                if(response.ok){
                    return response.json();
                } else{
                    return response.text().then(text=>{
                        alert(text);
                    })
                }
            }).then(message=>{
                mesContent.innerText = message.content;
                formSendMes.classList.remove("d-none");
                editMes.classList.add("d-none");
            }).catch(e=>{
                alert(e.message);
            })
        })
    })
}

