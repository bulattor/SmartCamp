<?php 

function schedule_get() {

	//$leader_id = $_GET['leader_id'];
	$tour_id = $_GET['tour_id'];

	$dates = array();
	$schedule = array();

	$query = mysqli_query(db(), "SELECT * FROM schedule WHERE `tour_id` = '$tour_id' GROUP BY date ");
	while ($row = mysqli_fetch_assoc($query)) {
		$dates[] = $row['date'];
	}

	for ($i = 0; $i < count($dates); $i++) {

		$day = array();
		$day['day'] = $dates[$i];

		$time = array();

		$query = mysqli_query(db(), "SELECT time, todo FROM schedule WHERE `tour_id` = '$tour_id' AND `date` = '$dates[$i]'");
		while ($row = mysqli_fetch_assoc($query)) {
			$time[] = $row;
		}

		$day['tasks'] = $time;
		$schedule[] = $day;

	}

	echo json_encode(array('response' => $schedule));
}

?>