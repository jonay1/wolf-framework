DROP TABLE
    T_TASK_INF;
CREATE TABLE
    T_TASK_INF
    (
        id VARCHAR(32) NOT NULL,
        group_name VARCHAR(100) NOT NULL DEFAULT "DEFAULT",
        name VARCHAR(100) NOT NULL,
        cron VARCHAR(100) NOT NULL,
        bean_name VARCHAR(255) NOT NULL,
        dolog CHAR(1) NOT NULL,
        start_time CHAR(19),
        rmk VARCHAR(256),
        create_time CHAR(19),
        creator VARCHAR(32),
        update_time CHAR(19),
        updator VARCHAR(32),
        PRIMARY KEY (id)
    );
DROP TABLE
    T_TASK_LOG;
CREATE TABLE
    T_TASK_LOG
    (
        id VARCHAR(32),
        task_id VARCHAR(32) NOT NULL,
        exec_time BIGINT,
        exec_status VARCHAR(10),
        exec_result VARCHAR(1024),
        fire_time CHAR(19),
        rmk VARCHAR(256),
        create_time CHAR(19),
        creator VARCHAR(32),
        update_time CHAR(19),
        updator VARCHAR(32),
        PRIMARY KEY (id)
    );
DROP TABLE
    T_ACCESS_LOG;
CREATE TABLE
    T_ACCESS_LOG
    (
        id VARCHAR(32),
        requri VARCHAR(255) NOT NULL,
        proc_cost BIGINT,
        success CHAR(1),
        access_year CHAR(4),
        access_month CHAR(2),
        access_day CHAR(2),
        access_hour CHAR(2),
        access_minute CHAR(2),
        access_second CHAR(2),
        rmk VARCHAR(256),
        create_time CHAR(19),
        creator VARCHAR(32),
        update_time CHAR(19),
        updator VARCHAR(32),
        PRIMARY KEY (id)
    );
DROP TABLE
    T_SYS_PARAM;
CREATE TABLE
    T_SYS_PARAM
    (
        id VARCHAR(32),
        param_name VARCHAR(255) NOT NULL,
        param_val VARCHAR(2000),
        status CHAR(1) NOT NULL,
        rmk VARCHAR(256),
        create_time CHAR(19),
        creator VARCHAR(32),
        update_time CHAR(19),
        updator VARCHAR(32),
        PRIMARY KEY (id)
    );
    

DROP TABLE
    T_FILE_INF;
CREATE TABLE
    T_FILE_INF
    (
        id VARCHAR(32) NOT NULL,
        content_type VARCHAR(20),
        filename VARCHAR(256),
        filepath VARCHAR(512),
        filesize BIGINT,
        rel_id VARCHAR(32),
        rel_type CHAR(4),
        rmk VARCHAR(256),
        create_time CHAR(19),
        creator VARCHAR(32),
        update_time CHAR(19),
        updator VARCHAR(32),
        PRIMARY KEY (id)
    );

DROP TABLE T_SEQUENCE;
CREATE TABLE T_SEQUENCE
    (
        CATAGORY VARCHAR(32) NOT NULL COMMENT '序号分类',
        NAME VARCHAR(32) NOT NULL COMMENT '序号键名',
        VAL BIGINT NOT NULL DEFAULT 0 COMMENT '序号值',
        CONSTRAINT TSEQUENCE_IX1 UNIQUE (CATAGORY, NAME)
    )
    ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='序号表';
    
