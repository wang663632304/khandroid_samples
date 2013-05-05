<?php

$input = 42;
if (array_key_exists('input', $_GET) && is_numeric($_GET['input'])) {
    $input = (int) $_GET['input'];
}

$result = array('some_string_val' => 'my string value', 'some_int_val' => $input + 42);

$response = array("status" => 1, "response_code" => 100, "payload" => $result);

print(json_encode($response));
