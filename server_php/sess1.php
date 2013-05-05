<?php

session_start();


if (array_key_exists('my_variable', $_GET)) {
    $_SESSION['my_variable'] = $_GET['my_variable'];
}


print($_GET['my_variable']);