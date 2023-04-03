FROM openjdk:11
RUN mkdir /home/app
COPY . /home/app
CMD ["java","-jar","./target/"]