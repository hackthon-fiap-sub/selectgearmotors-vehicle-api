# sevenfood-product-api

docker-compose build
docker-compose build sonarqube
docker-compose up -d
docker-compose logs -f --tail=50000

docker stop $(docker ps -qa)
docker rm $(docker ps -qa)
docker rmi $(docker images -qa) -f

./mvnw clean install test jacoco:report
sudo sysctl -w vm.max_map_count=262144


docker build -t rogeriofontes/selectgearmotors-vehicle-api:v10 .
docker login
docker push rogeriofontes/selectgearmotors-vehicle-api:v10

docker pull rogeriofontes/selectgearmotors-vehicle-api:v4
docker run -p 9924:9924 rogeriofontes/selectgearmotors-vehicle-api:v4
docker run -p 9924:9924 --env-file .env -e SPRING_PROFILES_ACTIVE=dev rogeriofontes/selectgearmotors-vehicle-api:v4

====
https://www.zaproxy.org/docs/docker/api-scan/
https://www.zaproxy.org/docs/docker/about/
====
docker run -u zap -p 8080:8080 -v "$(pwd)/docs/zap_workdir:/zap/wrk" -i ghcr.io/zaproxy/zaproxy:stable zap-api-scan.py -t http://localhost:9914/api/v3/api-docs -f openapi -c zap-rules.conf -r zap_report_antes_op1.html
docker run -u zap -p 8080:8080 -v "$(pwd)/docs/zap_workdir:/zap/wrk" -i ghcr.io/zaproxy/zaproxy:stable zap-api-scan.py -t http://localhost:9914/api/v3/api-docs -f openapi -c zap-rules.conf -r zap_report_antes_op1.html

http://localhost:9914/api/swagger-ui/index.html#/
http://localhost:9914/api/v3/api-docs

docker run -u zap -p 8080:8080 -v "$(pwd)/docs/zap_workdir:/zap/wrk" -i ghcr.io/zaproxy/zaproxy:stable zap-api-scan.py -t http://192.168.100.31:9924/api/v3/api-docs -f openapi -c zap-rules.conf -r zap_report_antes_op1.html

Dominio para ajustar:

Veiculos--
Marca
Modelo
tipo - (Carro, Moto, Caminhão/Onibus)
Ano
Combustivel (Gasolina, etanol, flex, Eletrico)
KM
Descricao - falar um pouco sobre o carro.
foto
preço
categoria (Camionete, Hat)
codigo
manual-automatico
local

- name: Create Kubernetes Secret YAML
  run: |
  echo "apiVersion: v1
  kind: Secret
  metadata:
  name: selectgearmotors-vehicle-api-secrets
  type: Opaque
  data:
  AWS_ACCESS_KEY_ID: $(echo -n ${{ secrets.AWS_ACCESS_KEY_ID }} | base64)
  AWS_SECRET_ACCESS_KEY: $(echo -n ${{ secrets.AWS_SECRET_ACCESS_KEY }} | base64)
  DATABASE_PASSWORD: $(echo -n ${{ secrets.DATABASE_PASSWORD }} | base64)
  MAIL_PASSWORD: $(echo -n ${{ secrets.MAIL_PASSWORD }} | base64)
  SECURITY_JWT_SECRET_KEY: $(echo -n ${{ secrets.SECURITY_JWT_SECRET_KEY }} | base64)" > infra/k8s/secrets.yaml
