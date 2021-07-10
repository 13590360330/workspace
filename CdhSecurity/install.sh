#!/bin/bash
#############################################
#author:liucheng                            #
#date:2020/03/29 11:56                      #
#describle:kerbers安装程序,在root下运行     #
#############################################

function install() {
result=`rpm -qa|grep krb5-libs`
if [ ! $result ]
then
    yum install krb5-libs
   echo "krb5-libs 安装结束"
else
   echo "已安装${result}请检查版本是否一致"
fi

result1=`rpm -qa|grep krb5-server`
if [ ! $result1 ]
then
    rpm -ivh krb5-server-1.10.3-65.el6.x86_64.rpm
    echo "krb5-server-1.10.3-65.el6.x86_64.rpm 安装结束"
else
    echo "已安装${result1}请检查版本是否一致"
fi

result2=`rpm -qa|grep krb5-workstation`
if [ ! $result2 ]
then
    rpm -ivh  krb5-workstation-1.10.3-65.el6.x86_64.rpm
    echo "krb5-workstation-1.10.3-65.el6.x86_64.rpm 安装结束"
else
    echo "已安装${result2}请检查版本是否一致"
fi

rm -rf /var/kerberos/krb5kdc/kdc.conf
cp ./kdc.conf /var/kerberos/krb5kdc/
rm -rf /etc/krb5.conf
cp ./krb5.conf /etc/
}

install
echo "kerbres服务器端安装完毕"

exit 0
