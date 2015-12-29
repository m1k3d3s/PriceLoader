# PriceLoader

code to take in yahoo finance data and upload to mysql database.

1. create database with the following columns

+---------------+-------------+------+-----+-------------------+-----------------------------+
| Field         | Type        | Null | Key | Default           | Extra                       |
+---------------+-------------+------+-----+-------------------+-----------------------------+
| date          | varchar(50) | YES  |     | NULL              |                             |
| open          | float(7,4)  | YES  |     | NULL              |                             |
| high          | float(7,4)  | YES  |     | NULL              |                             |
| low           | float(7,4)  | YES  |     | NULL              |                             |
| close         | float(7,4)  | YES  |     | NULL              |                             |
| volume        | int(20)     | YES  |     | NULL              |                             |
| adjclose      | float(7,4)  | YES  |     | NULL              |                             |
| stock         | varchar(10) | NO   |     | NULL              |                             |
| timestamp_all | timestamp   | NO   |     | CURRENT_TIMESTAMP | on update CURRENT_TIMESTAMP |
+---------------+-------------+------+-----+-------------------+-----------------------------+

2. config file should carry all the values
