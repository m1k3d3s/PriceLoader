# PriceLoader - read in raw format to see database description.

Code to take in yahoo finance data and upload to mysql database. drop csv in 'watched dir' and it will upload data to data. There are classes to launch charts , either final price or volume. Switch is made in the .config file.

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

8. Config file should carry all the values needed by GetProperties class.

3. Necessary external jar files needed in build path. Java (java-8-oracle)
  opencsv-3.6.jar
  mysql-connector-java-3.1.14-bin.jar
  jcommon-1.0.23.jar
  jfreechart-1.0.19.jar
  jfxrt.jar

4. JAX / XML classes to create xml file

