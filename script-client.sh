# 1. REST API test
echo ">> [1] REST API TEST SCENARIO\n"
URL="localhost:8080/users"
userId=$(curl -s -X POST -H "Content-Type: application/json"  -d '{ "name": "test 0" }' ${URL})
echo "1. created user:\n${userId}\n"
result=$(curl -s "${URL}/${userId}")
echo "2. query user:\n${result}\n"
result=$(curl -s -X POST -H "Content-Type: application/json" -d '{"role":"Admin"}' "${URL}/${userId}/roles")
echo "3. add role:\n${result}\n"

# 2. gRPC test
echo "\n>> [2] GRPC TEST SCENARIO"
# java -jar simpleClient/target/simpleClient-1.0.jar

# 3. GraphQL test
# echo "\n\n>> [3] GRAPHQL TEST SCENARIO"
# URL="localhost:8080/graphql"
# userId=$(curl -s -X POST -H "Content-Type: application/json"  -d '{query: "mutation {\n  addUser(user: {\n    name: \"test 0\"\n    roles: [Admin, Manager]\n  })\n}" }' ${URL} | jq -r '.data.addUser')
# echo "1. created user:\n${userId}\n"
# result=$(curl -s -X POST -H "Content-Type: application/json"  -d '{query: "{\n  getUser(id: \"'${userId}'\") {\n    id\n    name\n  }\n}"}' ${URL} | jq -r '.data')
# echo "2. query user:\n${result}\n"