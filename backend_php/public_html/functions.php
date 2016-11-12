<?php 

function get_rows($select) {
	$query = mysqli_query(db(), $select);
	return mysqli_fetch_assoc($query);
}

function insert($insert) {
	$query = mysqli_query(db(), $insert);
	return $query;
}

function get_error($error) {
	$response = array('error' => $error);
	echo json_encode(array('response' => $response));
}

?>