<?php

define('HOST', 'localhost');
define('USER', 'emilg1101_smartc');
define('PASS', '4nrHQWae');
define('DB', 'emilg1101_smartc');

function db() {
	$db = mysqli_connect(HOST, USER, PASS, DB);
	mysqli_query($db, "SET NAMES 'utf8'");
	return $db;
}

?>