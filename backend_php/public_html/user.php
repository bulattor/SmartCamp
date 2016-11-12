<?php

function user_signup() {

	$name = $_GET['name'];
	$surname = $_GET['surname'];
	$login = $_GET['login'];
	$password = $_GET['password'];
	$status = $_GET['status'];

	$row = get_rows("SELECT id FROM user WHERE `login` = '$login'");
	if (!$row['id']) {
		$query = insert("INSERT INTO user (`id`, `login`, `password`, `name`, `surname`, `status`) VALUES (NULL, '$login', '$password', '$name', '$surname', '$status')");
		if ($query) {
			$row = get_rows("SELECT id FROM user WHERE `login` = '$login' AND `password` = '$password'");
			user_get_info($row['id']);
		}
	} else {
		get_error('this login already exists!');
	}
}

function user_signin() {

	$login = $_GET['login'];
	$password = $_GET['password'];

	$row = get_rows("SELECT * FROM user WHERE `login` = '$login' AND `password` = '$password'");
	if ($row['id']) {
		user_get_info($row['id']);
	} else {
		get_error('login or password is wrong!');
	}
}

function user_get() {
	$id = $_GET['user_id'];

	user_get_info($id);
}

function user_get_info($user_id) {

	$user_info = get_rows("SELECT * FROM user WHERE `id` = '$user_id'");
	if ($user_info['status'] == 0) {
		$leader = get_rows("SELECT * FROM members WHERE `member_id` = '$user_id' ORDER BY id DESC LIMIT 1");
		$user_info['leader_id'] = $leader['leader_id'];
		$user_info['tour_id'] = $leader['tour_id'];
	}

	echo json_encode(array('response' => $user_info));
}

?>