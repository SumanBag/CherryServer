<?php
	
	
	
	
	$dbhost="localhost";
	$dbuser="horizon";
	$dbpass="horizon";
    $dbname="horizon";
	

	
	$conn=new mysqli($dbhost,$dbuser,$dbpass,$dbname) or die("LOGIN Error!");
	
	if($q=$conn->query("select * from users"))
    {
		
		print_r($q);
		
		
	}
	else
	{
		echo "Error!<br><hr>";
	    if($q==null)
			echo "null";
		echo "<br>Not Wroking";
	}  
      
    
    $conn->close();
	
?>