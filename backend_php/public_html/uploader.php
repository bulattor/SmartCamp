<?php 

header('Access-Control-Allow-Origin: *'); 
header('Access-Control-Allow-Methods: GET, PUT, POST, DELETE, OPTIONS');

function file_load($_type, $_dir, $_description, $_resolution, $_file) {

    if ($_type == 'image') {

        $uploaddir = '../../files/'.$_dir;
        $name = $_type.'_' . date('Ymd') . date('His') . '_' . transliterate(basename($_file['name']));
        $uploadfile = $uploaddir . $name;//basename($_FILES['userfile']['name']);


        if (move_uploaded_file($_file['tmp_name'], $uploadfile)) {

            $link = "http://api.tatarcha.biz/files/".$_dir . $name;
            image_resize($uploadfile, $uploadfile, $_resolution, false, 100);

            $query = mysqli_query(db(), "INSERT INTO files (`id`, `type`, `description`, `link`) VALUES (NULL, '$_type', '$_description', '$link')");
            if ($query) {
                $query = mysqli_query(db(), "SELECT * FROM files WHERE `link` = '$link' AND `description` = '$_description'");
                $row = mysqli_fetch_assoc($query);
                return $row['id'];
            } else {
                return 0;
            }   
        } else {
            return 0;
        }
    } else if ($_type == 'sound') {

        $uploaddir = '../../files/'.$_dir;
        $name = $_type.'_' . date('Ymd') . date('His') . '_' . transliterate(basename($_file['name']));
        $uploadfile = $uploaddir . $name;//basename($_FILES['userfile']['name']);


        if (move_uploaded_file($_file['tmp_name'], $uploadfile)) {

            $link = "http://api.tatarcha.biz/files/".$_dir . $name;

            $query = mysqli_query(db(), "INSERT INTO files (`id`, `type`, `description`, `link`) VALUES (NULL, '$_type', '$_description', '$link')");
            if ($query) {
                $query = mysqli_query(db(), "SELECT * FROM files WHERE `link` = '$link' AND `description` = '$_description'");
                $row = mysqli_fetch_assoc($query);
                return $row['id'];
            } else {
                return 0;
            }   
        } else {
            return 0;
        }
    }

}

function tasks_complete() {
	$type = $_POST['type'];
	$resolution = $_POST['resolution'];
	$description = $_POST['description'];
	$dir = $_POST['dir'];
    $key = $_POST['key'];

	if ($type == 'image') {

		$uploaddir = '../../files/'.$dir;
		$name = $type.'_' . date('Ymd') . date('His') . '_' . transliterate(basename($_FILES['file']['name']));
		$uploadfile = $uploaddir . $name;//basename($_FILES['userfile']['name']);


		if (move_uploaded_file($_FILES['file']['tmp_name'], $uploadfile)) {

			$link = "http://api.tatarcha.biz/files/".$dir . $name;
        	image_resize($uploadfile, $uploadfile, $resolution, false, 100);

        	$query = mysqli_query(db(), "INSERT INTO files (`id`, `type`, `description`, `link`) VALUES (NULL, '$type', '$description', '$link')");
        	if ($query) {
        		$query = mysqli_query(db(), "SELECT * FROM files WHERE `link` = '$link' AND `description` = '$description'");
        		$row = mysqli_fetch_assoc($query);
                if (isset($_POST['user_id']) and $key == 'avatar') {
                    insert("UPDATE user_info SET `photo_id` = '$row[id]' WHERE `id` = '$_POST[user_id]'");
                }
        		echo $row['id'];
        	} else {
				get_error('error');
        	}	
		} else {
			get_error('error');
		}
	} else {
		get_error('type is invalide');
	}
}

function image_resize($source_path, $destination_path, $newwidth, $newheight = FALSE, $quality = FALSE) {

    ini_set("gd.jpeg_ignore_warning", 1); // иначе на некотоых jpeg-файлах не работает
    
    list($oldwidth, $oldheight, $type) = getimagesize($source_path);
    
    switch ($type) {
        case IMAGETYPE_JPEG: $typestr = 'jpeg'; break;
        case IMAGETYPE_GIF: $typestr = 'gif' ;break;
        case IMAGETYPE_PNG: $typestr = 'png'; break;
    }
    $function = "imagecreatefrom$typestr";
    $src_resource = $function($source_path);
    
    if (!$newheight) { $newheight = round($newwidth * $oldheight/$oldwidth); }
    elseif (!$newwidth) { $newwidth = round($newheight * $oldwidth/$oldheight); }
    $destination_resource = imagecreatetruecolor($newwidth,$newheight);
    
    imagecopyresampled($destination_resource, $src_resource, 0, 0, 0, 0, $newwidth, $newheight, $oldwidth, $oldheight);
    
    if ($type = 2) { # jpeg
        imageinterlace($destination_resource, 1); // чересстрочное формирование изображение
        imagejpeg($destination_resource, $destination_path, $quality);      
    }
    else { # gif, png
        $function = "image$typestr";
        $function($destination_resource, $destination_path);
    }
    
    imagedestroy($destination_resource);
    imagedestroy($src_resource);
}

function transliterate($string) {
    $roman = array("Sch","sch",'Yo','Zh','Kh','Ts','Ch','Sh','Yu','ya','yo','zh','kh','ts','ch','sh','yu','ya','A','B','V','G','D','E','Z','I','Y','K','L','M','N','O','P','R','S','T','U','F','','Y','','E','a','b','v','g','d','e','z','i','y','k','l','m','n','o','p','r','s','t','u','f','','y','','e');
    $cyrillic = array("Щ","щ",'Ё','Ж','Х','Ц','Ч','Ш','Ю','я','ё','ж','х','ц','ч','ш','ю','я','А','Б','В','Г','Д','Е','З','И','Й','К','Л','М','Н','О','П','Р','С','Т','У','Ф','Ь','Ы','Ъ','Э','а','б','в','г','д','е','з','и','й','к','л','м','н','о','п','р','с','т','у','ф','ь','ы','ъ','э');
    return str_replace($cyrillic, $roman, $string);
}

?>