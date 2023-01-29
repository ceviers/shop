> 正向代理：client->proxy(正向代理，vpn)->target server
> 
> 反向代理：client->target server->proxy(反向代理，nginx)->real server1/real server2

### nginx 安装
[安装教程](https://docs.nginx.com/nginx/admin-guide/installing-nginx/installing-nginx-open-source/)

[配置教程](https://learn.microsoft.com/zh-cn/troubleshoot/developer/webapps/aspnetcore/practice-troubleshoot-linux/2-2-install-nginx-configure-it-reverse-proxy)
```bash
# 安装
sudo apt-get install nginx
# 启用
sudo systemctl start nginx
# 检查 Nginx 的状态
systemctl status nginx
# 重启 Nginx
sudo systemctl restart nginx
```
