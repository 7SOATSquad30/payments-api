# Payments API

## Como rodar

Instalar dependências e buildar
```
./gradlew build --refresh-dependencies
```

Subir infra local
```
docker-compose up -d dynamodb aws-cli
```

Rodar aplicação
```
./gradlew bootRun
```

Swagger: http://localhost:8080/swagger-ui/index.html#/

## Como rodar com integração do Mercado Pago

Configure as variáveis de ambiente:
```
MERCADOPAGO_PUBLIC_KEY=secret
MERCADOPAGO_PRIVATE_ACCESS_TOKEN=secret
MERCADOPAGO_APP_USER_ID=secret
MERCADOPAGO_POINT_OF_SALE_ID=secret
MERCADOPAGO_NOTIFICATIONS_URL=url pública apontando pro endpoint /public/webhooks/mercadopago
```

OBS: pra criar uma url pública local, pode criar um tunnel com ngrok:
```
ngrok http 8080
```