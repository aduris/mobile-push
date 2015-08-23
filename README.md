# mobile-push

GCM/APNS Mobile push sender & linux daemon tutorial

mobile-push 어플리케이션은 GCM의 경우 멀티캐스트 지원 java 라이브러리를 포함하고 있습니다. 
APNS의 경우, JavaPNS 라이브러리를 기반으로 발송됩니다.
처음 프로젝트를 로드했을때, mobile-push-sender 프로젝트 resource 파일에 config.xml 파일을 수정해주세요.
예를 들어 GCM API키 및 APNS Keystore 및 패스워드등이 설정되어 있습니다.
