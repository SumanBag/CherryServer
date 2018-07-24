<?php
class DBConnect
{
	
	private $dbhost="horizon";
	private $dbuser="horizon";
	private $dbpass="horizon";
	private $dbname="horizon";
	
	private $connection;
	
	
	public function __construct()
    {
		
		mysql_connect($dbhost,$dbuser,$dbpass) or die("Error");
		mysql_select_db($dbname) or die("DB Error");
		
	}
	
	public function loginfrm($user,$pass)
	{
	      if($querry = mysql_query("SELECT * FORM USERS WHERE USER=".$user." PASS=".md5($pass));
             return true;
          else
             return false;			  
	}
    
	private function getUID($user)
	{
		  if($querry = mysql_query("SELECT ID FORM UID WHERE USER=".$user);
             $uid=
          else
             return false;
	}
    public function getUserMin($uid)	
	
	
	
	
}
	
?>
