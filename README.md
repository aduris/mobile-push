# mobile-push

# 설명

mobile-push 는 대량의 메시지를 안정적으로 발송하는 데몬(=윈도우 서비스) 프로그램입니다.

지원하는 플랫폼은 윈도우와 리눅스입니다.

Thread Pooling을 지원해 대량의 메시지를 빠르고 안정적으로 발송가능합니다.

Message Queue로는 가장 범용적이고 간편한 MySQL을 이용합니다.

이 어플리케이션은 GCM의 경우 멀티캐스트를 지원하는 java 라이브러리를 이용해 발송합니다.

APNS의 경우, JavaPNS 라이브러리를 기반으로 발송됩니다.

처음 프로젝트를 로드했을때, mobile-push-sender 프로젝트 resource 파일에 config.xml 파일을 수정해주세요.

예를 들어 GCM API키 및 APNS Keystore 및 패스워드, 스레드수등이 설정할수 있습니다.



# 작업순서

서버단에는 table구성 및 sp를 셋팅해야합니다.

mobile-push-sender 프로젝트 resource 파일에 config.xml 파일을 수정해주세요. (예를 들어 GCM API키 및 APNS Keystore 및 패스워드등이 설정되어 있습니다.)

log4j.properties 의 로그가 쌓일 디렉토리를 변경하세요.

mybatis-config.xml 파일의 DB설정을 변경하세요

서버에 배치할때는 daemon으로 설치되며 apache commons daemon으로 동작합니다.
