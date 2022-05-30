DELIMITER $$

CREATE PROCEDURE `sp_fail`()
BEGIN
    DECLARE `_rollback` BOOL DEFAULT 0;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET `_rollback` = 1;
    START TRANSACTION;

        create table teste (
                               id bigint not null auto_increment,
                               nome varchar(255) not null,

                               primary key (id)
        ) engine=InnoDB default charset=utf8;

        create table teste1 (
                                id bigint not null auto_increment,
                                sobrenome varchar(255) not null,

                                primary key (id)
        ) engine=InnoDB default charset=utf8;

        create table teste2 (
                                id bigint not null auto_increment,
                                cpf varchar(255) not null

                                    primary key (id)
        ) engine=InnoDB default charset=utf8

    IF `_rollback` THEN
        ROLLBACK;
    ELSE
        COMMIT;
    END IF;
    END$$

DELIMITER ;
