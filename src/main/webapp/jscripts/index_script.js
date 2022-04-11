// $count = 1;
// $("#generate").click(function(){
//     $.post("welcome", {"username":"hello", "count":$count.toString()}, function(data, textStatus, fn) {
//             if (textStatus=="success") {
//                 document.getElementById("return_value").innerText = data;
//                 document.getElementById("return_value").append($count);
//             }
//         },
//         "html");
//     $count++;
// });
// $("#clear").click(function(){
//     document.getElementById("return_value").innerText = "";
// });


$("#create").click(
  function(){
      console.log("Refresh main_frame to avatar creation");
      $("#main_frame").attr("src", "web_pages/create_avatar.html");
  }
);

$("#chat").click(
    function(){
        console.log("Switching to the chat interface");
        $("#main_frame").attr("src", "web_pages/chat_bot.html");
    }
)
