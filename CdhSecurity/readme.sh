#!/bin/bash
##########################################
#author:liucheng                         #
#createtime:2020/04/02                   #
#desc:kerberos命令                       #
#para:$user ${1}Kerbero认证用户          #
##########################################
#以下部分手动执行
#之后会生成或更新/var/kerberos/krb5kdc/principal
#删除principal
#kdb5_util -r ETLUSER.COM -m destroy -f
#创建realms域密码
#cd /usr/sbin/
#/usr/sbin/kdb5_util create -s -r ETLUSER.COM
#创建CM使用的超级用户,以后会用此账户管理CM主体 admin/admin 指:用户/用户组
#/usr/sbin/kadmin.local -q "addprinc admin/admin"
#启动
#service krb5kdc start
#service kadmin start
#登录
#kadmin.local
#查看用户
#listprincs
#添加用户:etluser
#addprinc etluser
#生成票据
#kinit etluser
#klist
#生成keytab文件
#kadmin.local
#xst -k /home/etluser/etlscript/Kerberos/keytab/etluser.keytab etluser
#基于keytab文件进行认证
#kinit -kt /home/etluser/etlscript/Kerberos/keytab/etluser.keytab etluser
#注销授权(看需要执行)
#kdestory
#krbtgt/ETLUSER.COM@ETLUSER.COM 管理票据刷新时间等信息

user=${1}
function init_kerberos() {
#user=${USER}
#相应用户授予相应kerberos权限
if [ "$user" == "root" ]
then
    echo "${user}用户授予kerberos hive权限"
    line=`find / -name "hive.keytab" | wc -l`
    for((i=1;i<=${line};i++));
    do
        result=`find / -name "hive.keytab" | sed -n "${i}p"`
        kinit -kt "${result}" hive/quickstart.cloudera
    done
#else
#    echo "${user}用户授予kerberos ${user}权限"
#    line=`find / -name "${user}.keytab" | wc -l`
#    for((i=1;i<=${line};i++));
#    do
#        result=`find / -name "hive.keytab" | sed -n "${i}p"`
#        kinit -kt "${result}" ${user}/quickstart.cloudera
#    done
fi

if [ "$user" == "hdfs" ]
then
    echo "${user}用户授予kerberos hdfs权限"
    kinit -kt /var/run/cloudera-scm-agent/process/921-hdfs-SECONDARYNAMENODE/hdfs.keytab hdfs/quickstart.cloudera
    kinit -kt /var/run/cloudera-scm-agent/process/920-hdfs-DATANODE/hdfs.keytab hdfs/quickstart.cloudera
    kinit -kt /var/run/cloudera-scm-agent/process/919-hdfs-NAMENODE/hdfs.keytab hdfs/quickstart.cloudera
elif [ "$user" != "hdfs" ]&&[ "$user" != "root" ]
then
    echo "${user}用户授予kerberos ${user}权限"
    kinit -kt /home/etluser/etlscript/Kerberos/keytab/${user}.keytab ${user}
else
    echo "...."
fi

}

init_kerberos
exit 0
