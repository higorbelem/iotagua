<?php 


/**
 * STATUS MANEGER
 */
class user 
{

	public $email;
	public $password;
	public $username;
	public $img;

	function __construct($user)
	{

		$this->email = $user['email'];
		$this->password = $user['password'];
		$this->username = $user['username'];
		$this->img = $user['image'];

	}

	public function insert_user($con)
	{

		$sql = "INSERT INTO user (username, email, password, acess, user_img) VALUES(?,?,?,?,?)";
		$state = mysqli_stmt_init($con);

		if(!mysqli_stmt_prepare($state,$sql)){

			header("Location: ../index.php?link=error");
			die;

		}
		else{

			$acess = 0;
			mysqli_stmt_bind_param($state, "sssib", $this->username, $this->email ,$this->password, $acess, $this->user_img);
			mysqli_stmt_execute($state);
			mysqli_stmt_store_result($state);
			header("Location: ../index.php?&link=sucess");

		}

	}

	public function remove_user($con)
	{

		$sql =  "DELETE FROM `user` WHERE `username` = '$this->username' ";
		
		if (mysqli_query($con, $sql)) {
			header("Location: ../index.php?&link=sucess");
		}else{
			header("Location: ../index.php?&link=fail");
		}

	}

}

/**
 * CONNECTION
 */
class con
{
	
	public $con;
	public $host;
	public $dbusername;
	public $dbpassword;
	public $dbname;

	function __construct()
	{

		$this->host = "localhost";
		$this->dbusername = "root";
		$this->dbpassword = "";
		$this->dbname = "mateus";

		// Create connection
		$this->con = new mysqli ($this->host, $this->dbusername, $this->dbpassword, $this->dbname);
		if(!$this->con){
		  die("Connection failed: ".mysqli_connect_error());
		}

	}

	public function login($user){

		$sql = "SELECT * FROM user WHERE password =". $user['password'] ."AND username = ".$user['username'] ."";
		mysqli_query($this->con, $sql);

		if($this->con->affected_rows()){
			session_start();
			$_SESSION['username'] = $user['username'];
			$_SESSION['password'] = $user['password'];
			header("Location: ../index.php?&login=sucess");
		}else{
			header("Location: ../index.php?&login=fail");
		}

	}

	public function select()
	{


		$sql = "SELECT * FROM  sensor ";
		$sf = $this->con->query($sql);
		$res= mysqli_fetch_array($sf);

		return $res;


	}

}

?>