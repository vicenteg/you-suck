you-suck
========

A spark-streaming application that lets people tell you what a bad presenter you are.

Quick Start
===========

0. Install Java. On Redhat or CentOS:
    
    `sudo yum -y install java-1.7.0-openjdk-devel java-1.7.0-openjdk`

1. Install spark (no Hadoop needed, but I used Spark 1.1.0 built for MapR)
    
    `cd /tmp; curl -O http://package.mapr.com/tools/apache-spark/1.1.0/spark-1.1.0-bin-mapr4.tgz`

2. Unpack spark
    
    `mkdir -p /opt; cd /opt; tar -vxzf /tmp/spark-1.1.0-bin-mapr4.tgz`

3. Clone this repository
    
    `cd ~; git clone https://github.com/vicenteg/you-suck.git`

4. Install scala (this works on Redhat or CentOS, for Ubuntu, you're on your own):
    
    `sudo yum -y install http://www.scala-lang.org/files/archive/scala-2.10.4.rpm`

5. Install sbt (scala build tool):
   ```
   cd /tmp
   curl -LO https://dl.bintray.com/sbt/native-packages/sbt/0.13.6/sbt-0.13.6.zip
   cd /opt
   sudo unzip /tmp/sbt-0.13.6.zip
   ```

6. Build the application:
    ```
    cd ~/you-suck
    sbt package
    ```

7. Run the application as a local streaming application, setting the working directory as a command line option:
    `cd ~/you-suck; /opt/spark-1.1.0-bin-mapr4/bin/spark-submit --class "YouSuck" --master 'local[2]' target/scala-2.10/you-suck-_2.10-1.0.r "file:/home/vince/ratings"`
