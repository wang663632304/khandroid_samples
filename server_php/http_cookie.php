<?php

$cookie_value = 0;
if (array_key_exists('my_cookie', $_COOKIE)) {
  $cookie_value = $_COOKIE['my_cookie'];
  $cookie_value++;
}

setcookie('my_cookie', $cookie_value, 0);
