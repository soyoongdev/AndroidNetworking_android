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

  case 'insertProduct':  

if(isTheseParametersAvailable(array('name','price','id_type'))){
    $name = $_POST['name'];   
    $price = $_POST['price']; 
    $id_type = $_POST['id_type']; 
   
    $stmt = $conn->prepare("SELECT id FROM products WHERE name = ? OR price = ?");  
    $stmt->bind_param("ss", $name, $price);
    $stmt->execute();  
    $stmt->store_result();  
   
    if($stmt->num_rows > 0){ 
        $response['error'] = true;  
        $response['message'] = 'Product already exists!'; 
        $stmt->close();   
    }   
    else{  
      if (!empty($name) && !empty($price) && $name != " " && $price != " ") {
        $stmt = $conn->prepare("INSERT INTO products (name, price, created_at, updated_at, id_type) VALUES (?, ?, NOW(), NOW(), ?)");  
        $stmt->bind_param("sss", $name, $price, $id_type);

        if($stmt->execute()){ 
          
          $stmt = $conn->prepare("SELECT id, name, price, created_at, updated_at, id_type FROM products WHERE name=? OR price=?");   
          $stmt->bind_param("ss", $name, $price);
          $stmt->execute();  
          $stmt->bind_result($id, $name, $price, $created_at, $updated_at, $id_type );
          $stmt->fetch();
          
            
          $product = array(
          'id'=>$id,   
          'name'=>$name,   
          'price'=>$price,
          'created_at'=>$created_at, 
          'updated_at'=>$updated_at,
          'id_type'=>$id_type
          );

          $stmt->close();

          $response['error'] = false;
          $response['message'] = 'Product created successfully!';
          $response['product'] = $product;
        }
      } else {
        $response['error'] = true;
        $response['message'] = 'Failed!';
      }
    }
} 
else{  
  $response['error'] = true;   
  $response['message'] = 'required parameters are not available';
}

break;   

  case 'getAllProduct':  
    $sql_select = "SELECT id, name, price, created_at, updated_at, id_type FROM products";
    $result = mysqli_query($conn, $sql_select);

    if (mysqli_num_rows($result) > 0) {
        $response_pro["products"] = array();
        // output data of each row
        while($row = mysqli_fetch_assoc($result)) {
          $product = array();
          $product["id"] = $row["id"];
          $product["name"] = $row["name"];
          $product["price"] = $row["price"];
          $product["created_at"] = $row["created_at"];
          $product["updated_at"] = $row["updated_at"];
          $product["id_type"] = $row["id_type"];

          // push single product into final response array
          array_push($response_pro["products"], $product);
        }

        $response['error'] = false;
        $response['message'] = 'Get all product successful!';
        $response['product'] = $response_pro;
    } else {
        $response['error'] = true;
        $response['message'] = 'Get all product failed!';
    } 
    break; 

  case 'updateProduct':
  if(isTheseParametersAvailable(array('id', 'name', 'price', 'id_type'))){
      $id = $_POST['id']; 
      $name = $_POST['name']; 
      $price = $_POST['price']; 
      $id_type = $_POST['id_type']; 
     
      $stmt = $conn->prepare("SELECT id, name, price, created_at, updated_at, id_type FROM products WHERE id = ?");  
      $stmt->bind_param("s", $id);   
      $stmt->execute(); 
      $stmt->store_result();  

      if($stmt->num_rows > 0){  
        if (!empty($name) && !empty($price)) { 
            // update
            $stmt = $conn->prepare("UPDATE products SET name = ?, price = ?, updated_at = NOW(), id_type = ? WHERE id = ?");  
            $stmt->bind_param("ssss", $name, $price, $id_type, $id);
            if($stmt->execute()){ 
          
                $stmt = $conn->prepare("SELECT id, name, price, created_at, updated_at, id_type FROM products WHERE id = ?");   
                $stmt->bind_param("s", $id);
                $stmt->execute();  
                $stmt->bind_result($id, $name, $price, $created_at, $updated_at, $id_type);
                $stmt->fetch();
        
                $product = array(
                'id'=>$id,   
                'name'=>$name,   
                'price'=>$price,
                'created_at'=>$created_at,
                'updated_at'=>$updated_at,
                'id_type'=>$id_type
                );  
                $stmt->close(); 
                $response['error'] = false;
                $response['message'] = 'The product has been successfully updated';
                $response['product'] = $product;
              } 
        }else {
            $response['error'] = true;
            $response['message'] = 'Product update failed';
        }
        
      }    
      else {    
        $response['error'] = false;   
        $response['message'] = 'Product is not found!';  
      }
  }
  else{  
    $response['error'] = true;   
    $response['message'] = 'Required parameters are not available';
  } 
  break;  

  case 'deleteProduct':  
    if(isTheseParametersAvailable(array('id'))){
      $id = $_POST['id']; 

      $stmt = $conn->prepare("SELECT id, name, price, created_at, updated_at FROM products WHERE id = ?");  
      $stmt->bind_param("s", $id);   
      $stmt->execute(); 
      $stmt->store_result();  

      if ($stmt->num_rows > 0) {
      // output data of each row
        $sql = "DELETE FROM products WHERE id = $id";
        if (mysqli_query($conn, $sql)) {
          $response['error'] = false;
          $response['message'] = 'The product has been removed successfully';
        } 
      } 
      else {
        $response['error'] = true;  
        $response['message'] = 'Product does not exist!';
      }
    } 
    else{  
      $response['error'] = true;   
      $response['message'] = 'Required parameters are not available';
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