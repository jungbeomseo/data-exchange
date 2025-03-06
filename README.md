# API Design Model: REST vs. gRPC vs. GraphQL

## How to run

> [!NOTE]
> you need to run both grpcServer and simpleServer before running the test scenario.

1. grpcServer: `./script-server grpcServer`
2. simpleServer: `./script-server simpleServer`
3. test scenarios: `./script-client`

## Directory layout

```
.
├── grpc                # grpc library (including spec. and stub code)
├── grpcServer          # grpc server implementation
├── simpleServer        # rest api server implementation
├── simpleClient        # grpc client implementation
├── pom.xml
├── README.md
├── script-server.sh    # bash script for running servers.
└── script-client.sh    # bash script for api call test scenarios: rest api + grpc + (graphQL later)
```