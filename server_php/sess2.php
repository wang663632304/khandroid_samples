<?php 

session_start();

if (array_key_exists('my_variable', $_SESSION)) {
    print($_SESSION['my_variable']);
} else {
    print('No session variable found!');
}