<?php

function tasks_get() {

	//$leader_id = $_GET['leader_id'];
	$tour_id = $_GET['tour_id'];
	$date = $_GET['date'];

	$task = array();
	$tasks = array();

	$query = mysqli_query(db(), "SELECT id, time, todo, confirm FROM tasks WHERE `tour_id` = '$tour_id' AND `date` = '$date'");
	while ($row = mysqli_fetch_assoc($query)) {
		$tasks[] = $row;
	}

	$task['tasks'] = $tasks;

	echo json_encode(array('response' => $task));
}

?>