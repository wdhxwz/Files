## Docker搭建私服

1. 下载镜像



2. 创建证书

		mkdir -p certs && openssl req -newkey rsa:4096 -nodes -sha256 -keyout certs/domain.key -x509 -days 365 -out certs/domain.crt

