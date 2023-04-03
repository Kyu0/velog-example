# 실행 방법
(RabbitMQ가 설치되어있다고 가정합니다.)

1. RabbitMQ 서버를 실행합니다.
 (Intell 맥북 기준 터미널에서 /usr/local/sbin/rabbitmq-server 실행)
2. MQ-CON 프로젝트를 실행합니다.
3. MQ-PUB 프로젝트를 실행합니다.

MQ-CON 프로젝트가 실행된 터미널에 3초 마다 "test" 라는 단어가 출력되면 정상적으로 작동하고 있는겁니다.
혹은 localhost:15672 페이지에서 RabbitMQ 상태를 확인할 수도 있습니다.
