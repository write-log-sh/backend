# Write-log backend

## 1. Requirements

- mysql

```sh
$ docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=1234abcd -d -p 3306:3306 mysql:8.0.26
```

```mysql
create schema `write-log`;
```