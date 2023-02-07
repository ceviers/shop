下载解压keepalived源码
```bash 
wget https://keepalived.org/software/keepalived-2.2.7.tar.gz
tar -zxvf keepalived-2.2.7.tar.gz
```
安装，提示WARNING，原因：缺少libnl libnl-devel
```
cd keepalived-2.2.7/
./configure  --prefix=/usr/local/keepalived
*** WARNING - this build will not support IPVS with IPv6. Please install libnl/libnl-3 dev libraries to support IPv6 with IPVS.
```
安装 libnl libnl-devel
```
yum -y install libnl libnl-devel
上次元数据过期检查：1 day, 9:17:27 前，执行于 2023年02月02日 星期四 06时24分09秒。
未找到匹配的参数: libnl
未找到匹配的参数: libnl-devel
错误：没有任何匹配: libnl libnl-devel
```
yum安装失败，源码安装libnl
```
wget https://github.com/thom311/libnl/releases/download/libnl3_4_0/libnl-3.4.0.tar.gz
tar -zxvf libnl-3.7.0.tar.gz 
cd libnl-3.7.0/
./configure --prefix=/usr/local --sysconfdir=/etc --disable-static  && make
make install
```
源码安装libnl3-devel
```
wget https://vault.centos.org/centos/8/BaseOS/aarch64/os/Packages/libnl3-devel-3.5.0-1.el8.aarch64.rpm
dnf install libnl3-devel
./configure  --prefix=/usr/local/keepalived
make && make install
```
安装keepalived
```
cd keepalived-2.2.7/
./configure  --prefix=/usr/local/keepalived && make && make install
```
keepalived配置文
```
/usr/local/keepalived/etc/keepalived/
nano keepalived.conf
```
```
# global全局配置
global_defs {
   #运行keepalived服务器的一个标识，可以用作发送邮件的主题信息
   router_id LVS_DEVEL
   # 严格遵守VRRP协议
   vrrp_strict
}

# 设置keepalived实例信息,VI_1为VRRP实例名称
vrrp_instance VI_1 {
    # 两个值可选MASTER主 BACKUP备，即主节点就选MASTER
    state MASTER
    # vrrp实例绑定的当前服务器使用的网卡名称，用于发送VRRP包，使用命令ifconfig查看
    interface ens160
    # 指定VRRP实例ID，范围是0-255
    virtual_router_id 51
    # 指定优先级，优先级高的将成为MASTER
    priority 100
    # 指定发送VRRP通告的间隔，单位是秒
    advert_int 1
    # vrrp之间通信的认证信息
    authentication {
        # 指定认证方式。PASS简单密码认证(推荐)
        auth_type PASS
        # 指定认证使用的密码，最多8位
        auth_pass 1111
    }
    # 设置虚拟IP地址，供用户访问使用，可设置多个，一行一个
    virtual_ipaddress {
        172.16.228.132
    }
}
```
启动keepalived
```
cp /home/cevie/下载/keepalived/etc/init.d/keepalived /etc/init.d/
mkdir -p /etc/keepalived
cp /usr/local/keepalived//etc/keepalived/keepalived.conf /etc/keepalived/
cp /home/cevie/下载/keepalived/etc//sysconfig/keepalived /etc/sysconfig/keepalived
service keepalived start
Starting keepalived (via systemctl):                       [  OK  ]
```
