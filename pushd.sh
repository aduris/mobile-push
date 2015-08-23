#!/bin/sh

JAVA_HOME=/usr/local/programming/jdk1.8.0_60
DAEMON_HOME=/usr/local/server/daemon/commons-daemon-1.0.15-src/src/native/unix
DAEMON_USER=root
DAEMON_APP_HOME=/usr/local/server/daemon/mobile-push
PID_FILE=$DAEMON_APP_HOME/daemon.pid
OUT_FILE=$DAEMON_APP_HOME/daemon.log

CLASSPATH=$DAEMON_APP_HOME/mobile-push-sender-0.1.jar

MAIN_CLASS=mobile.push.sender.main.MobilePushDaemon

case $1 in
  start)
    #
    # Start Mobile Push
    #

    $DAEMON_HOME/jsvc \
    -user $DAEMON_USER \
    -java-home $JAVA_HOME \
    -wait 10 \
    -pidfile $PID_FILE \
    -errfile '&1' \
    -cp $CLASSPATH \
    $MAIN_CLASS

    #
    # To get a verbose JVM
    #-verbose \
    # To get a debug of jsvc.
    #-debug \
    exit $?
    ;;

  stop)
    #
    # Stop Mobile Push
    #

    $DAEMON_HOME/jsvc \
    -stop \
    -pidfile $PID_FILE \
    $MAIN_CLASS

    exit $?
    ;;
#
  *)
    echo "Usage service.sh start|stop"
    exit 1;;
esac