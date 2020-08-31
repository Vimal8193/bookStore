To run we need a sql database connection with below table -

CREATE TABLE book (
id INT NOT NULL AUTO_INCREMENT, 
title VARCHAR(220) NOT NULL, 
auhtor VARCHAR(220) NOT NULL, 
isbn VARCHAR(220) NOT NULL, 
price double not null, 
inventory int not null,
PRIMARY KEY ( id ));


As per database connection, changes in properties file might be needed.