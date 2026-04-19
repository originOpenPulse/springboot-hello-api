FROM eclipse-temurin:8-jre

# Add IoT Service Application
ADD scripts/libsg.so /app/libsg.so
#RUN yum -y install sg3_utils
ADD dist/springboot-hello-api-server.jar /app/springboot-hello-api-server.jar

# add script to run filebeat and start service
ADD scripts/entrypoint.sh /bin/entrypoint.sh
RUN chmod a+x /bin/entrypoint.sh

#set timezone 
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /app

EXPOSE 18081

CMD ["/bin/entrypoint.sh"]
