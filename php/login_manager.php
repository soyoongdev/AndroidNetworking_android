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
  case 'signup':  
    if(isTheseParametersAvailable(array('username','email','password'))){  
    $username = $_POST['username'];   
    $email = $_POST['email'];   
    $password = md5($_POST['password']);
    // $created_at = time();
   
    $stmt = $conn->prepare("SELECT id FROM users WHERE username = ? OR email = ?");  
    $stmt->bind_param("ss", $username, $email);
    $stmt->execute();  
    $stmt->store_result();  
   
    if($stmt->num_rows > 0){  
        $response['error'] = true;  
        $response['message'] = 'User already registered';  
        $stmt->close();  
    }  
    else{  
        $stmt = $conn->prepare("INSERT INTO users (username, email, password, created_at) VALUES (?, ?, ?, NOW())");  
        $stmt->bind_param("sss", $username, $email, $password);

        if($stmt->execute()){
            
            $stmt = $conn->prepare("SELECT id, username, email, created_at FROM users WHERE username = ?");   
            $stmt->bind_param("s", $username);
            $stmt->execute();  
            $stmt->bind_result($id, $username, $email, $created_at);
            $stmt->fetch();
            
   
            $user = array(
            'id'=>$id,   
            'username'=>$username,   
            'email'=>$email,
            'created_at'=>$created_at
            );
   
            $stmt->close();  
   
            $response['error'] = false;   
            $response['message'] = 'User registered successfully';   
            $response['user'] = $user;
        }  
    }  
   
}  
else{  
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';   
}  
break;   
case 'login':  
  if(isTheseParametersAvailable(array('email', 'password'))){  
    $email = $_POST['email'];  
    $password = md5($_POST['password']);   
   
    $stmt = $conn->prepare("SELECT id, username, email, created_at FROM users WHERE email = ? AND password = ?");  
    $stmt->bind_param("ss",$email, $password);  
    $stmt->execute();
    $stmt->store_result();  
    if($stmt->num_rows > 0){  
    $stmt->bind_result($id, $username, $email, $created_at);  
    $stmt->fetch();  
    $user = array(
    'id'=>$id,   
    'username'=>$username,   
    'email'=>$email,
    'created_at'=>$created_at
    );
   
    $response['error'] = false;   
    $response['message'] = 'Login successfull';   
    $response['user'] = $user;   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid username or password';  
 }  
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