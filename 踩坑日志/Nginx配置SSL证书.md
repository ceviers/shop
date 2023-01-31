### Nginx配置SSL证书

* 使用 apt 安装 Certbot 和它的 Nginx 插件

```bash
sudo apt install certbot python3-certbot-nginx
```
* 在配置文件中配置好server节点
```
server {
        listen 80;
        server_name example.com; # 监听的域名

        location / { # 路径映射  "/" 根路径映射到html文件夹
                root /usr/html/;
                index index.html;
        }

}
```
* 获取证书
```bash
sudo certbot --nginx -d www.myfreax.com
```
* OK，结束

> 疑惑点：好像在一个server节点配置两个域名会出问题？？
