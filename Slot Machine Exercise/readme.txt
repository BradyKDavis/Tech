The current slot machine code points to a DB called SlotMachineSpin and
to a table called Slot_Machine. The schema and an instance of test data
has been included in the attached .sql file.

Included with the SlotMachine.php file code is two SlotClient files, one
using a hidden salt value that is used with the current data on the SQL
server for the salt, mimicking a client that has the proper salt value.
There is also a SlotClientWrong.html file that has a different salt
value than the test data, and as such fails when any kind of spin result
is sent to the SlotMachine php code. A success in updating the
SlotMachine SQL server will return a JSON blob (the code will output it
to string upon success), and upon failure will return an appropriate
error, indicating either a User ID that does not exist, or a mismatch in
the clients salt value (or security key);