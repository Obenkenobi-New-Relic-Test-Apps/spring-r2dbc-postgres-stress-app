docker buildx build --platform linux/arm64 -f repro.dockerfile . -t postgres-app-repro
docker buildx build --platform linux/arm64 -f fix.dockerfile . -t postgres-app-fix