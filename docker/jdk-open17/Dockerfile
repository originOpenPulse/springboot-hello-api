FROM centos:7

# 基于cntos7，如果没有这个镜像那么它会下载这个镜像。
FROM centos:7
# 编写人
LABEL author=info@wizteco.com
# 为Dockerfile中所有RUN、CMD、ENTRYPOINT、COPY和ADD指令设定工作目录
WORKDIR /usr
# 执行命令(这里创建了一个目录)
RUN mkdir /usr/local/java
# 和copy一样，复制文件到指定目录，但是copy不能解压，add自动解压
ADD dist/openjdk-17.0.2_linux-x64_bin.tar.gz /usr/local/java
# 设置环境变量
ENV JAVA_HOME /usr/local/java/jdk-17.0.2
#ENV JRE_HOME ${JAVA_HOME}/jre
ENV CLASS_PATH ${JAVA_HOME}/lib/dt.jar:${JAVA_HOME}/lib/tools.jar
#:${JAVA_HOME}/jre/lib:$CLASS_PATH
ENV PATH ${JAVA_HOME}/bin:$PATH
