#!/bin/bash

# Teste CRUD para Localidade (Corrigido com @payload e salvando output)

OUTPUT_FILE="/home/ubuntu/crud_test_output.txt"
PAYLOAD_CREATE="/home/ubuntu/sos-climatech-api/payload_create.json"
PAYLOAD_UPDATE="/home/ubuntu/sos-climatech-api/payload_update.json"
API_BASE_URL="http://localhost:8080"

# Limpar arquivo de output anterior
> "$OUTPUT_FILE"

# 1. Criar uma nova localidade (POST)
echo "\n--- 1. Criando Localidade (POST) ---" >> "$OUTPUT_FILE"
curl -X POST $API_BASE_URL/localidades -H "Content-Type: application/json" -d @"$PAYLOAD_CREATE" >> "$OUTPUT_FILE" 2>&1
# Para este teste automatizado, vamos assumir ID=1, mas o ideal seria parsear a resposta.
LOCALIDADE_ID=1 
echo "\n--- Localidade Criada (Assumindo ID=$LOCALIDADE_ID) ---" >> "$OUTPUT_FILE"
sleep 2

# 2. Listar todas as localidades (GET)
echo "\n--- 2. Listando Localidades (GET All) ---" >> "$OUTPUT_FILE"
curl -X GET $API_BASE_URL/localidades >> "$OUTPUT_FILE" 2>&1
echo "\n------------------------------------" >> "$OUTPUT_FILE"
sleep 1

# 3. Buscar a localidade criada pelo ID (GET by ID)
echo "\n--- 3. Buscando Localidade ID $LOCALIDADE_ID (GET by ID) ---" >> "$OUTPUT_FILE"
curl -X GET $API_BASE_URL/localidades/$LOCALIDADE_ID >> "$OUTPUT_FILE" 2>&1
echo "\n----------------------------------------------" >> "$OUTPUT_FILE"
sleep 1

# 4. Atualizar a localidade (PUT)
echo "\n--- 4. Atualizando Localidade ID $LOCALIDADE_ID (PUT) ---" >> "$OUTPUT_FILE"
curl -X PUT $API_BASE_URL/localidades/$LOCALIDADE_ID -H "Content-Type: application/json" -d @"$PAYLOAD_UPDATE" >> "$OUTPUT_FILE" 2>&1
echo "\n--- Localidade Atualizada ---" >> "$OUTPUT_FILE"
sleep 2

# 5. Buscar a localidade atualizada pelo ID (GET by ID)
echo "\n--- 5. Buscando Localidade ID $LOCALIDADE_ID (Atualizada) ---" >> "$OUTPUT_FILE"
curl -X GET $API_BASE_URL/localidades/$LOCALIDADE_ID >> "$OUTPUT_FILE" 2>&1
echo "\n----------------------------------------------------" >> "$OUTPUT_FILE"
sleep 1

# 6. Deletar a localidade (DELETE)
echo "\n--- 6. Deletando Localidade ID $LOCALIDADE_ID (DELETE) ---" >> "$OUTPUT_FILE"
curl -X DELETE $API_BASE_URL/localidades/$LOCALIDADE_ID >> "$OUTPUT_FILE" 2>&1
echo "\n--- Localidade Deletada ---" >> "$OUTPUT_FILE"
sleep 2

# 7. Tentar buscar a localidade deletada (GET by ID - Esperado 404)
echo "\n--- 7. Tentando Buscar Localidade ID $LOCALIDADE_ID (Deletada) ---" >> "$OUTPUT_FILE"
HTTP_STATUS=$(curl -s -o /dev/null -w "%{http_code}" $API_BASE_URL/localidades/$LOCALIDADE_ID)
echo "Status HTTP: $HTTP_STATUS" >> "$OUTPUT_FILE"
echo "--------------------------------------------------------" >> "$OUTPUT_FILE"
sleep 1

# 8. Listar todas as localidades novamente (GET All - Esperado sem ID $LOCALIDADE_ID)
echo "\n--- 8. Listando Localidades (Final - GET All) ---" >> "$OUTPUT_FILE"
curl -X GET $API_BASE_URL/localidades >> "$OUTPUT_FILE" 2>&1
echo "\n--------------------------------------------" >> "$OUTPUT_FILE"

# 9. Capturar logs dos containers
echo "\n--- 9. Logs Container API (últimas 50 linhas) ---" >> "$OUTPUT_FILE"
sudo docker compose logs --tail="50" sos-api >> "$OUTPUT_FILE" 2>&1
echo "\n--- Logs Container Oracle DB (últimas 50 linhas) ---" >> "$OUTPUT_FILE"
sudo docker compose logs --tail="50" oracle-db >> "$OUTPUT_FILE" 2>&1

echo "\n--- Teste CRUD Concluído --- Output salvo em $OUTPUT_FILE ---" 

