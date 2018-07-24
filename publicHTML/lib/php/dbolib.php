<?php
class DBManager
{
	
	
	
	private $connection;
	
	
	
	
    function __construct()
    {
		
	
		$dbhost="mysql.hostinger.in";
	    $dbuser="u678982488_hor";
	    $dbpass="horizon";
	    $dbname="u678982488_hor";
		
		$this->connection=new mysqli($dbhost,$dbuser,$dbpass,$dbname);
		
		
	}
	
	private function sanitizeStrings($str)
	{
	   
	   $str=stripslashes($str);
       $str=htmlentities($str);
	   $str=strip_tags($str);
       
       return $str;	   
	}
	
	
	public function registerfrm($name,$email,$games,$pass)
	{
		$name=$this->sanitizeStrings($name);
		$name=$this->connection->real_escape_string($name);
		
		$id=md5($email);
		$email=$this->sanitizeStrings($email);
		$email=$this->connection->real_escape_string($email);
		  
		$pass=md5($pass);
		
		  if(($q=$this->connection->query("INSERT INTO USERS VALUES('".$name."','".$email."','".$pass."')"))
			  &&($r=$this->connection->query("INSERT INTO UID VALUES('".$email."','".$id."')"))
		      &&($q=$this->connection->query("INSERT INTO GAMES VALUES('".$id."',".$games.")")))
          {
			 return true;
		  }
          else
          {
			  echo "Query Error!";
			  return false;
		  }
		  
	}
	
	public function checkuser($user)
	{
          $user=$this->sanitizeStrings($user);
		  $user=$this->connection->real_escape_string($user);
		  
	      if($q=$this->connection->query("SELECT * FROM users WHERE USER='".$user."'"))
          {
			 if($q->num_rows>=1)
			    return false; 
			 else
				return true;
		  }  
          else
          {
			  echo "Query Error!";
			  return false;
		  } 
	}
	
	public function loginfrm($user,$pass)
	{     
	      $user=$this->sanitizeStrings($user);
		  $user=$this->connection->real_escape_string($user);
		  $pass=md5($pass);
		  //echo $this->connection==null?"null":"not null";
	      if($q=$this->connection->query("SELECT * FROM USERS WHERE USER='".$user."' AND PASS='".$pass."'"))
          {
			 if($q->num_rows==1)
			    return true; 
			 else
				return false;
		  }  
          else
          {
			  echo "Query Error!";
			  return false;
		  }   			  
	}
    
	public function getUID($user)
	{     
	      $user=$this->sanitizeStrings($user);
		  $user=$this->connection->real_escape_string($user);
		  
		  if($query = $this->connection->query("SELECT ID FROM UID WHERE USER='".$user."'"))
          {
			  if($query->num_rows==1)
				 return $query->fetch_array(MYSQLI_ASSOC)['ID'];  
			 else
				return null;
		  }   
          else
          {
			  echo "Query Error!";
			  return null;
		  }   
	}
	
	public function getusername($user)
	{     
	      $user=$this->sanitizeStrings($user);
		  $user=$this->connection->real_escape_string($user);
		  
		  if($query = $this->connection->query("SELECT NAME FROM USERS WHERE USER='".$user."'"))
          {
			  if($query->num_rows==1)
				 return $query->fetch_array(MYSQLI_ASSOC)['NAME'];  
			 else
				return null;
		  }   
          else
          {
			  echo "Query Error!";
			  return null;
		  }   
	}
	
   
	
	function __destruct()
	{   
		$this->connection->close();
	}
	
}
?>
