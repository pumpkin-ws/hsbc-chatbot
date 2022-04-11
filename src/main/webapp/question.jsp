<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src='https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js'></script>
</head>
<body>
<div style="display: flex">
    <textarea id="question" placeholder="Please enter your questions here" style="width:200px; height:100px; margin:10px;"></textarea>
    <button id="ask" style="width: 50px; height: 50px; margin-top: 40px">Ask</button>
    <textarea id="answer" placeholder="Your answer will be displayed here" style="width:400px; height:100px; margin:10px"></textarea>
</div>

<script>
    $("#ask").click(function(){
        let question = $("#question").val();
        $("#answer").val("Your answer will arrive soon...");
        $.post("cloud_service", {"question":question}, function(data, textStatus, fn) {
                if (textStatus=="success") {
                    $("#answer").val(data);
                }
            },
            "html");


    })
</script>
</body>

</html>