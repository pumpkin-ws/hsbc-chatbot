<html>
<head>
    <title>hsbc chat bot</title>
    <script src='https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js'></script>
    <style>
        #box {
            position: relative;
            margin-bottom: 20px;
        }
        .left {
            position: absolute;
            width: 150px;
            height: 900px;
            border-style: solid;
            border-color: darkturquoise;
        }
        .right {
            height: 900px;
            margin-left: 150px;
            border-style: solid;
            border-color: aqua;
        }
        .butt_elem{
            margin: 20px;
            font-size: large;
        }
    </style>
</head>
<body>
<div style="border-style: dashed; border-color: blue;"; >
    <h1 style="text-align: center;">Metaverse Chat bot</h1>
    <h2 style="text-align: center; font-size: 20px;">You can create your own metaverse avatar here, and talk to a chat bot</h2>
</div>
<div id="box">
    <div class="left">
        <div>
            <button id="create" class="butt_elem">create avatar</button>
        </div>
        <div>
            <button id="chat" class="butt_elem"> chat with avatar </button>
        </div>
    </div>
    <div class="right" id="interact_div">
        <iframe id="main_frame" src="web_pages/create_avatar.html" width="100%" height="100%" allowtransparency="true" style="background-color: transparent">
            Your browser does not support iframe.
        </iframe>
    </div>
</div>
<script src="jscripts/index_script.js"></script>
<p>Created by Sheng Wei. All rights reserved, 2022. Powered by Huawei Cloud.</p>
</body>
</html>
