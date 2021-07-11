cloudera
hive:
beeline -u "jdbc:hive2://quickstart.cloudera:10000/source;principal=hive/quickstart.cloudera@ETLUSER.COM;hive.server2.proxy.user=etluser" -e "select count(*) from source.test" > ~/etlscript/tmp/result.log
beeline -u "jdbc:hive2://quickstart.cloudera:10000/source;principal=hive/quickstart.cloudera@ETLUSER.COM;hive.server2.proxy.user=etluser"
#kadmin.local
#kinit -kt /home/etluser/etlscript/Kerberos/keytab/etluser.keytab etluser
#用root执行
#sh /home/etluser/etlscript/Kerberos/readme.sh
#会给hive    kinit -kt /var/run/cloudera-scm-agent/process/1168-hive-HIVEMETASTORE/hive.keytab hive/quickstart.cloudera(要开放权限给etluser去运行)
beeline -u "jdbc:hive2://quickstart.cloudera:10000/source;principal=hive/quickstart.cloudera@ETLUSER.COM;hive.server2.proxy.user=hive"






kinit -kt /var/run/cloudera-scm-agent/process/1386-hive-HIVESERVER2/hive.keytab hive/quickstart.cloudera@HADOOP.COM
beeline -u "jdbc:hive2://quickstart.cloudera:10000/default;principal=hive/quickstart.cloudera@HADOOP.COM;hive.server2.proxy.user=hive"



beeline -u "jdbc:hive2://quickstart.cloudera:10000/default;principal=hive/quickstart.cloudera@HADOOP.COM"