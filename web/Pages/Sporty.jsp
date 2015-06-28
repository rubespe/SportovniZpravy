<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>

</head>
<body>
<div>
<h2>Sporty</h2>

</div>

<div style="display: none" id="inputDiv">
    <form id="inputForm" >
        Sport:<br>
        <input type="text" id="category" >
        <br>


        <input type="submit" value="Add">
    </form>
    <form action="/PagesA/AddEvent.jsp">
        <input type="submit" value="Create Event">
    </form>
</div>

<div>
    <nav id="listNav">

    </nav>
</div>

<script src="/js/sport.js"></script>

</body>
</html>