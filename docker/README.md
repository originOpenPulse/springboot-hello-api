## Docker notes

Build the image from the project root after preparing the application jar:

```bash
docker build -t springboot-hello-api:latest ./docker/springboot-hello-api
```

If you publish to GitHub Container Registry, tag and push with:

```bash
docker tag springboot-hello-api:latest ghcr.io/originopenpulse/springboot-hello-api:latest
docker push ghcr.io/originopenpulse/springboot-hello-api:latest
```
