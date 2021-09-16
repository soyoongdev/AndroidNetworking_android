<?php

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "id17319313_android_networking_db";
$conn = mysqli_connect($servername, $username, $password, $dbname);

// Check connection
if (!$conn) {
  die("Connection failed: " . mysqli_connect_error());
}
echo "Connected database successfully \n";

  $response = array();
  if(isset($_GET['apicall'])){  
  switch($_GET['apicall']){  

  case 'insertCat':  

  if(isTheseParametersAvailable(array('name_type'))){
    $name_type = $_POST['name_type'];  
   
    $stmt_query = $conn->prepare("SELECT id_type, name_type FROM category WHERE name_type = ?");  
    $stmt_query->bind_param("s", $name_type);
    $stmt_query->execute();  
    $stmt_query->store_result();  
   
    if($stmt_query->num_rows > 0){ 
        $response['error'] = true;  
        $response['message'] = 'Category name already exists!'; 
        $stmt_query->close();   
    }   
    else{  
      if (!empty($name_type) && trim($name_type)){
        $stmt_insert_value = $conn->prepare("INSERT INTO category (name_type, created_at, updated_at) VALUES (?, NOW(), NOW())");  
        $stmt_insert_value->bind_param("s", $name_type);

        if($stmt_insert_value->execute()){ 
          
          $stmt_select_value = $conn->prepare("SELECT id_type, name_type, created_at, updated_at FROM category WHERE id_type=? OR name_type=?");   
          $stmt_select_value->bind_param("ss", $id_type, $name_type);
          $stmt_select_value->execute();  
          $stmt_select_value->bind_result($id_type, $name_type, $created_at, $updated_at);
          $stmt_select_value->fetch();
          
            
          $cat = array(
          'id_type'=>$id_type,   
          'name_type'=>$name_type,
          'created_at'=>$created_at, 
          'updated_at'=>$updated_at
          );

          $stmt_select_value->close();

          $response['error'] = false;
          $response['message'] = 'Category created successfully!';
          $response['category'] = $cat;
        } else {
          $response['error'] = true;
          $response['message'] = 'Category created failed!';
        }
      }else {
        $response['error'] = true;
        $response['message'] = 'Category is not found!';
      }
    }
}  
else{  
  $response['error'] = true;   
  $response['message'] = 'required parameters are not available';
}
$conn->close();
break;   

  case 'getAllCat':  
    $sql_select = "SELECT id_type, name_type, created_at, updated_at FROM category";
    $result = $conn->query($sql_select);

    if ($result->num_rows > 0) {
        $response_cat["categories"] = array();
        // output data of each row
        while($row = $result->fetch_assoc()) { 
          $category = array();
          $category["id_type"] = $row["id_type"];
          $category["name_type"] = $row["name_type"];
          $category["created_at"] = $row["created_at"];
          $category["updated_at"] = $row["updated_at"];

          // push single product into final response array
          array_push($response_cat["categories"], $category);
        }

        $response['error'] = false;
        $response['message'] = 'Get all category successful!';
        $response['category'] = $response_cat;
    } else {
        $response['error'] = true;
        $response['message'] = 'Get all category failed!';  
    } 
    break; 

  case 'updateCat':
    if(isTheseParametersAvailable(array('id_type', 'name_type'))){ 
      $id_type = $_POST['id_type']; 
      $name_type = $_POST['name_type'];
     
      $stmt = $conn->prepare("SELECT id_type, name_type, created_at, updated_at FROM category WHERE id_type = ?");  
      $stmt->bind_param("s", $id_type);   
      $stmt->execute(); 
      $stmt->store_result();  

      if($stmt->num_rows > 0){  
        if (!empty($id_type) && !empty($name_type)) { 
            // update
            $stmt = $conn->prepare("UPDATE category SET name_type = ?, updated_at = NOW() WHERE id_type = ?");  
            $stmt->bind_param("ss", $name_type, $id_type);
            if($stmt->execute()){ 
          
                $stmt = $conn->prepare("SELECT id_type, name_type, created_at, updated_at FROM category WHERE id_type = ?");   
                $stmt->bind_param("s", $id_type);
                $stmt->execute();  
                $stmt->bind_result($id_type, $name_type, $created_at, $updated_at);
                $stmt->fetch();
        
                $category = array(
                'id_type'=>$id_type,   
                'name_type'=>$name_type,
                'created_at'=>$created_at,
                'updated_at'=>$updated_at
                );  
                $stmt->close(); 
                $response['error'] = false;
                $response['message'] = 'The category has been successfully updated';
                $response['category'] = $category;
              } 
        }else {
            $response['error'] = true;
            $response['message'] = 'Category update failed';
        }
        
      }    
      else {    
        $response['error'] = false;   
        $response['message'] = 'Category is not found!';  
      }
  }
  else{  
    $response['error'] = true;   
    $response['message'] = 'Required parameters are not available';
  } 
  break;  

  case 'getAllCat':  
    $sql_select = "SELECT id_type, name_type, created_at, updated_at FROM category";
    $result = $conn->query($sql_select);

    if ($result->num_rows > 0) {
        $response_cat["categories"] = array();
        // output data of each row
        while($row = $result->fetch_assoc()) { 
          $category = array();
          $category["id_type"] = $row["id_type"];
          $category["name_type"] = $row["name_type"];
          $category["created_at"] = $row["created_at"];
          $category["updated_at"] = $row["updated_at"];

          // push single product into final response array
          array_push($response_cat["categories"], $category);
        }

        $response['error'] = false;
        $response['message'] = 'Get all category successful!';
        $response['category'] = $response_cat;
    } else {
        $response['error'] = true;
        $response['message'] = 'Get all category failed!';  
    } 
    break; 

  default:   
    $response['error'] = true;   
    $response['message'] = 'Invalid Operation Called';  
  }
}

  else{  
    $response['error'] = true;   
    $response['message'] = 'Invalid API Call';  
  }
  echo json_encode($response);

  function isTheseParametersAvailable($params){  
  foreach($params as $param){  
  if(!isset($_POST[$param])){  
      return false;   
    }  
  }  
  return true;   
  }

?>