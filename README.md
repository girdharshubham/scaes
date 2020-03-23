# scaes
Scala Elastic4s

# Running Scaes
```
docker run -d -p 9200:9200 -p 9300:9300 -e discovery.type=single-node elasticsearch:7.3.1
sbt run
```
