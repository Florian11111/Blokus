docker build -f Dockerfile.dockergui blokus:gui .
docker run -e DISPLAY=$ip:0 -v /tmp/.X11-unix:/tmp/.X11-unix -ti blokus:gui --rm --it