<?php


//current state of PHP code simply outputs the resulting JSON blob
// after the data has been saved to SQL server
if($_SERVER['REQUEST_METHOD'] == 'POST'){
    if(isset($_POST["spin"])){
        $slot = new SlotMachine($_POST["UID"], $_POST["Bet"], $_POST["Win"], $_POST["salt"]);
        echo $slot->SaveSpin();
    }
}

class SlotMachine {
    
    private $PlayerID;
    private $BetCoins;
    private $WonCoins;
    private $Salt;
    
    
    function __construct($PlayerID, $BetCoins, $WonCoins, $salt){
        $this->PlayerID = $PlayerID;
        $this->BetCoins = $BetCoins;
        $this->WonCoins = $WonCoins;
        $this->Salt = $salt;
        if($this->BetCoins < 0){
            echo 'Player is not able to bet a negative amount of coins.';
            die();
        }
    }
    
    function SaveSpin(){
        $hash = $this->GenerateHash();
        if($this->ValidateHash($hash, $this->PlayerID, $this->WonCoins, $this->BetCoins)){
            //hash is valid, save results to server and return JSON with
            //Player ID, Name, Credits, Lifetime spins, and avg return
            $mysqli = SlotMachine::getConnection();
            $sql = "UPDATE Slot_Machine SET Credits = Credits + " . ($this->WonCoins - $this->BetCoins) . ", LifetimeSpins = LifetimeSpins + 1 WHERE PlayerID = " . $this->PlayerID . "; ";
            $sql = $sql . "SELECT PlayerID, Name, Credits, LifetimeSpins FROM Slot_Machine WHERE PlayerID = " . $this->PlayerID . " LIMIT 1;";
            if($mysqli->multi_query($sql)){
                $mysqli->store_result();
                $mysqli->next_result();
                if($result = $mysqli->store_result()){
                    $row = $result->fetch_row();
                    $data = array('PlayerID' => $row[0],
                                  'Name' => $row[1],
                                  'Credits' => $row[2],
                                  'LifetimeSpins' => $row[3],
                                  'LifetimeAverageReturn' => $row[2] / $row[3]);
                    return $post_data = json_encode($data);
                }
            }
            else{
                echo "Multi query failed: (" . $mysqli->errno . ") " . $mysqli->error . "<br />";
                die();
            }
        }
        else{
            //something is wrong with the client, probably invalid salt value
            return "Error: Client security token invalid";
            die();
        }
    }
    
    /*
     *hash should be a hash of the salt, playerID, bet, and winnings.
     *Check current salt against salt stored in the server
     */
    function ValidateHash($originalHash, $PlayerID, $winnings, $bet){
        $mysqli = SlotMachine::getConnection();
        if($result = $mysqli->query("SELECT Salt FROM Slot_Machine WHERE PlayerID = " . $PlayerID . ";")){
            if($result->num_rows > 0){
                $result->data_seek(0);
                $row = $result->fetch_assoc();
                $ServerSalt = $row["Salt"];
                $ServerHash = hash("sha256", serialize($ServerSalt . $this->PlayerID . $this->BetCoins . $this->WonCoins));
                if (strcmp($ServerHash, $originalHash) !== 0){
                    return false;
                }
                else{
                    return true;
                }
            }
            else{
                echo "Player with this ID not found." ;
                die();
            }
        }
        else{
            echo $mysqli->error;
            return false;
        }
    }
    
    //Inserts new user with default settings and new name and salt
    //Initially used by an AddClient html to add further clients to server.
    static function AddUser($Name){
        $mysqli = SlotMachine::getConnection();

        //new user will have 500 credits to start;
        $safe_name = $mysqli->real_escape_string($Name);
        $salt = $mysqli->real_escape_string(mcrypt_create_iv(128));
        if($mysqli->query("INSERT INTO Slot_Machine (Name, Credits, LifetimeSpins, Salt) " .
                    "VALUES ('" . $safe_name . "', 500, 0, '" . $salt . "');")){
            return $mysqli->insert_id;
        }
        else{
            return $mysqli->error;
        }
    }
    
    //Generates sha256 hash for salt, ID, bet, and win for this instance
    function GenerateHash(){
        $args = $this->Salt . $this->PlayerID . $this->BetCoins . $this->WonCoins;
        return hash("sha256", serialize($args));
    }
    
    //Gets connection information for this slot machine sql server.
    static function getConnection(){
        $mysqli = new mysqli("127.0.0.1", "test", "test", "SlotMachineSpin", 8889);
        if($mysqli->connect_errno){
            echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
            exit();
        }
        return $mysqli;
    }
}

?>