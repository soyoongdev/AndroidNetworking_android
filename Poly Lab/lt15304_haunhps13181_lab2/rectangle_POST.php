<?php
    $width = $_POST['width'];
    $length = $_POST['length'];
    $perimeter = ($width + $length) * 2; // tính chu vi
    $acreage = $width * $length; // tính diện tích
    
    echo "Chu vi: " . $perimeter . " ; Dien tich: " . $acreage;
?>