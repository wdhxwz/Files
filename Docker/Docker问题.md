## Docker问题

-  Error response from daemon: Cannot start container tomcat: Error getting container e2a6941adc28e448a48fd102e44b529c020c40608745dd81be6c247671b318a9 from driver devicemapper: Error mounting '/dev/mapper/docker-8:2-148193-e2a6941adc28e448a48fd102e44b529c020c40608745dd81be6c247671b318a9' on '/var/lib/docker/devicemapper/mnt/e2a6941adc28e448a48fd102e44b529c020c40608745dd81be6c247671b318a9': device or resource busy
Error: failed to start containers: [tomcat]

【原因】  有container在运行的时候重启 docker 服务， 可能会导致 container无法启动，这是一个Docker的 bug   
【解决办法】先找出没有umount的路径，然后依次umount  

	cat /proc/mounts | grep "mapper/docker" | awk '{print $2}'

- docker容器中启动service服务 Failed to get D-Bus connection: Operation not permitted

		解决方案：  
		启动时设置参数  --privileged  
		使用该参数，Container内的root拥有真正的root权限。  
		否则，container内的root只是外部的一个普通用户权限。

