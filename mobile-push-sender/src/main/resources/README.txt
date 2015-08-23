설명서

2015-07-09 (최일규) - 최초작성

1. 윈도우 환경
 - 윈도우의 경우 log4j.properties, config.xml에 상대경로는 현재 프로젝트의 위치임.
 - 윈도우에서는 JavaService 랩퍼를 이용해 윈도우 서비스에 올린다.
 
2. 리눅스 환경
 - Daemon으로 올리고자 하면, Commons Daemon이 설치되어있어야 함..
 - 리눅스의 경우 log4j.properties, config.xml에 절대경로로 준다.