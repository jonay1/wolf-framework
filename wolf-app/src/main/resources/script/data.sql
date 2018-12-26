INSERT INTO T_SYS_PARAM (id, param_name, param_val, status) VALUES ('LOG.ACCESS', '是否记录访问日志', 'Y', '1');
INSERT INTO T_SYS_PARAM (id, param_name, param_val, status) VALUES ('FILE.UPLOADPATH', '文件上传路径', '/Users/jonay/Documents/workspace-oxygen/wolf-app/upload', '1');
INSERT INTO T_SYS_PARAM (id, param_name, param_val, status) VALUES ('WHITE_LIST', '允许访问的白名单IP,逗号分隔', '0.0.0.0.0', '1');


INSERT INTO T_TASK_INF (id, group_name, name, cron, bean_name, dolog) VALUES ('timer', 'DEFAULT', 'DEMO任务', '0/10 * * * * ? ', 'timerTask', '0');
