<?php

include 'db_connect.php';
include 'functions.php';

include 'schedule.php';
include 'tasks.php';
include 'user.php';

if (isset($_GET['method'])) {
	$m = $_GET['method'];
} else {
	$m = $_POST['method'];
}


if ($m == 'user.signin') {
	user_signin();
} else if ($m == 'user.signup') {
	user_signup();
} else if ($m == 'user.get') {
	user_get();
} else if ($m == 'schedule.get') {
	schedule_get();
} else if ($m == 'tasks.get') {
	tasks_get();
}

?>