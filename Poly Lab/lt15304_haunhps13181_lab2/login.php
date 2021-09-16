<?php
    $usernameAdmin = "admin";
    $passwordAdmin = "admin@123";
    $username = $_POST['username'];
    $password = $_POST['password'];

    if ($username == $usernameAdmin && $password == $passwordAdmin) {
        echo "Login successfully!";
    }else {
        echo "Login failed!";
    }

?>