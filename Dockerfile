FROM java:8
VOLUME /tmp
ADD target/app.jar app.jar
RUN bash -c 'touch /app.jar'
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \&& echo 'Asia/Shanghai' >/etc/timezone
EXPOSE 10099
ENTRYPOINT ["/bin/sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]
